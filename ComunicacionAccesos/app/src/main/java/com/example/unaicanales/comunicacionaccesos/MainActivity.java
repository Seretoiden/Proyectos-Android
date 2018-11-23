package com.example.unaicanales.comunicacionaccesos;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.nfc.NfcAdapter;
import android.provider.BaseColumns;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.util.Preconditions;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.tasks.OnSuccessListener;

import butterknife.ButterKnife;


public class MainActivity extends AppCompatActivity {

    private Button bCamara, bAct;
    private TextView txtNFC, txtBluetooth, txtConexion, txtSSID, txtLong, txtLat;
    private Switch sBluetooth;
    private Switch sGPS;
    private BluetoothAdapter bAdapter;
    private ConnectivityManager cm;
    private NetworkInfo activeNetwork;
    private LocationManager manager;
    private boolean GpsStatus;
    private NfcAdapter mNfcAdapter;

    private FusedLocationProviderClient mFusedLocationClient;
    private SettingsClient mSettingsClient;
    private LocationCallback mLocationCallback;
    private Location mCurrentLocation;

    private static final int REQUEST_BLUETOOTH = 1;
    private static final int REQUEST_LOCATION = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        cm = (ConnectivityManager) this.getSystemService(this.CONNECTIVITY_SERVICE);
        activeNetwork = cm.getActiveNetworkInfo();
        manager = (LocationManager) getSystemService(LOCATION_SERVICE);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        bAdapter = BluetoothAdapter.getDefaultAdapter();
        bCamara = (Button) findViewById(R.id.bCamara);
        bAct = (Button) findViewById(R.id.bAct);
        txtConexion = (TextView) findViewById(R.id.txtConexion);
        txtBluetooth = (TextView) findViewById(R.id.txtBluetooth);
        txtLong = (TextView) findViewById(R.id.txtLong);
        txtLat = (TextView) findViewById(R.id.txtLat);
        txtSSID = (TextView) findViewById(R.id.txtSsid);
        txtNFC = (TextView) findViewById(R.id.txtNFC);
        sBluetooth = (Switch) findViewById(R.id.sBluetooth);
        sGPS = (Switch) findViewById(R.id.sGPS);


        //COMPROBAMOS WIFI Y ACTUALIZAMOS LA VIEW
        if (activeNetwork == null) {
            txtSSID.setText("NO CONECTADO");
            txtConexion.setText("NO CONECTADO");
        } else {
            txtSSID.setText(activeNetwork.getTypeName());
            txtConexion.setText(activeNetwork.getExtraInfo());
        }

        //COMPROBAMOS BLUETOOTH Y CAMBIAMOS EL SWITCH
        if (bAdapter == null) {
            sBluetooth.setEnabled(false);
            txtBluetooth.setText(txtBluetooth.getText() + " (NO SOPORTADO)");
        } else {
            if (bAdapter.isEnabled()) {
                sBluetooth.setChecked(true);
            } else {
                sBluetooth.setChecked(false);
            }
        }

        bAct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                cogerCoordenadas();
            }
        });

        bCamara.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                startActivity(intent);
            }
        });

        //COMPROBAMOS EL NFC
        mNfcAdapter = NfcAdapter.getDefaultAdapter(this);

        if (mNfcAdapter == null) {
            // Stop here, we definitely need NFC
            txtNFC.setText("Lo siento, pero este móvil NO DISPONE de NFC.");
        }

        if (!mNfcAdapter.isEnabled()) {
            txtNFC.setText("El móvil dispone de NFC, pero está apagado...");
        } else {
            txtNFC.setText("¡NFC ACTIVADO Y EN MARCHA!");
        }

        handleIntent(getIntent());

        sBluetooth.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                //Si el adaptador es null, significará que el bluetooth no está disponible en este dispositivo, por lo que deshabilitamos el boton
                if (bAdapter == null) {
                    sBluetooth.setEnabled(false);
                    return;
                } else {
                    //Si posee bluetooth, comprobamos si el switch está encendido o apagado
                    if (isChecked)
                        encenderBluetooth();
                    else
                        apagarBluetooth();
                }
            }
        });

        sGPS.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    pedirPermisosLocalizacion();
                else
                    volverACero();
            }
        });
    }

    //Metodo que se encarga de pasar los datos de la LAT y LONG a los textviews
    private void escribirEnPantalla() {
        if (mCurrentLocation != null) {

            txtLat.setText(mCurrentLocation.getLatitude() + "");
            txtLong.setText(mCurrentLocation.getLongitude() + "");

            // giving a blink animation on TextView
            txtLat.setAlpha(0);
            txtLat.setTextColor(Color.BLACK);
            txtLat.animate().alpha(1).setDuration(300);
            txtLong.setAlpha(0);
            txtLong.setTextColor(Color.BLACK);
            txtLong.animate().alpha(1).setDuration(300);

        }
    }

    //Método que se encargara de actualizar la ultima lozalizacion del movil
    @SuppressLint("MissingPermission")
    private void cogerCoordenadas() {
        Toast.makeText(getApplicationContext(), "ACTUALIZANDO LAT Y LONG", Toast.LENGTH_SHORT).show();

        //De este objeto FusedLocationClient, sacaremos nuestra ultima localizacion
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        mFusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        Toast.makeText(getApplicationContext(), "COORDENADAS RECIBIDAS, PRINTEAMOS", Toast.LENGTH_SHORT).show();
                        mCurrentLocation = location;
                        escribirEnPantalla();
                        if (location != null) {
                            // Logic to handle location object
                        }
                    }
                });º
    }

    private void pedirPermisosLocalizacion(){
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, REQUEST_LOCATION);

        }else{
            cogerCoordenadas();
            sGPS.setChecked(true);
            bAct.setEnabled(true);
        }
    }

    private void handleIntent(Intent intent) {
        // TODO: handle Intent
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case REQUEST_BLUETOOTH:
                if(resultCode == RESULT_OK)
                    Toast.makeText(getApplicationContext(),"Bluetooth turned ON" , Toast.LENGTH_SHORT).show();
                else {
                    sBluetooth.setChecked(false);
                    Toast.makeText(getApplicationContext(), "DENEGADO", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case REQUEST_LOCATION:
                Toast.makeText(getApplicationContext(),"ENTRAMOS EN EL " , Toast.LENGTH_SHORT).show();

                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    bAct.setEnabled(true);
                    cogerCoordenadas();
                }
                else {
                    volverACero();
                }
                break;
        }
    }

    public void encenderBluetooth(){
        if (!bAdapter.isEnabled()) {
            Intent turnOnIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(turnOnIntent, 1);

        }
        else{
            Toast.makeText(getApplicationContext(),"Bluetooth is already ON", Toast.LENGTH_SHORT).show();
        }
    }
    public void apagarBluetooth() {
        bAdapter.disable();

        Toast.makeText(getApplicationContext(),"Bluetooth turned OFF" , Toast.LENGTH_SHORT).show();
    }

    public void volverACero(){
        sGPS.setChecked(false);
        txtLong.setText(R.string.indefinido);
        txtLong.setTextColor(getResources().getColor(R.color.cardview_shadow_start_color));
        txtLat.setTextColor(getResources().getColor(R.color.cardview_shadow_start_color));
        txtLat.setText(R.string.indefinido);
        bAct.setEnabled(false);
    }
}
