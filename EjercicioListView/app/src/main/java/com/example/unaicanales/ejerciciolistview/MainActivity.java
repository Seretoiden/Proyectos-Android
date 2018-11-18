package com.example.unaicanales.ejerciciolistview;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static android.provider.AlarmClock.EXTRA_MESSAGE;

public class MainActivity extends AppCompatActivity  {

    //Equipos que tendremos guardados en memoria
    private static ArrayList<Equipo> equipos = new ArrayList<Equipo>();
    private static EditText eT;
    private static Button anadirEquipo;
    private Bbdd bbdd = new Bbdd(this);
    private AlertDialog.Builder builder;

    private static Equipo item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        builder = new AlertDialog.Builder(this);
        setContentView(R.layout.activity_main);
        eT = (EditText) findViewById(R.id.buscador);
        anadirEquipo = (Button) findViewById(R.id.anadirEquipo);

        anadirEquipo.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                abrirInfo(null);
            }
        });

        //añadimos los equipos en memoria
        equipos = bbdd.leerEquiposDesdeBBDD();
        System.out.println("nombre: " + equipos.get(0).getNombre());

        final ListView lV = (ListView) findViewById(R.id.listView);
        final AdapterEquipo adapter = new AdapterEquipo(this, equipos);

        lV.setAdapter(adapter);

        //Listener del click en cualquier item del ListView
        lV.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {
                String[] colors = {"MODIFICAR", "ELIMINAR"};
                item = (Equipo) parent.getItemAtPosition(position);
                builder.setTitle("¿QUE ACCION DESEAS REALIZAR?");
                builder.setItems(colors, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case 0:
                                abrirInfo(item);
                                break;

                            case 1:
                                bbdd.eliminar(item.getId());
                                recargarPagina();

                                break;
                        }
                        // the user clicked on colors[which]
                    }
                });
                builder.show();

                //Toast.makeText(getApplicationContext(), item.getNombre() + " SE HA HECHO CLICK", Toast.LENGTH_SHORT).show();
            }

        });

        //Añadimos listeners al edittext:
        eT.addTextChangedListener(new TextWatcher() {

            //A la hora de que el texto cambie
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Call back the Adapter with current character to Filter
                Filter filtro = adapter.getFilter();
                filtro.filter(s.toString());
            }

            //Antes de cambiar el texto en el editext
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,int after) {
                Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
            }

            //Despues de que el texto haya sido cambiado
            @Override
            public void afterTextChanged(Editable s) {
            }

        });

    }

    private void recargarPagina() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    //Metodo para abrir el segundo activity con la informacion del equipo
    public void abrirInfo(Equipo equipo) {
        if(equipo == null){
            Intent intent = new Intent(this, SecondActivity.class);
            startActivity(intent);
        }else{
            System.out.println("ID EQUIPO: " + equipo.getId());
            Intent intent = new Intent(this, SecondActivity.class);
            intent.putExtra("idEquipo", equipo.getId());
            startActivity(intent);
        }

    }

}