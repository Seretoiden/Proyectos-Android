package com.example.unaicanales.sharedpreferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

public class MainActivity extends AppCompatActivity {

    //declaramos los componentes de la vista
    private RadioGroup rBGroup;
    private RadioButton rBAzul, rBRojo, rBAmarillo;
    private RelativeLayout lLayout;

    //declaramos las variables que vayamos a utilizar
    private String color;
    private SharedPreferences sharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sharedPref = getPreferences(Context.MODE_PRIVATE);

        //instanciamos todos los componentes de la vista
        rBGroup = (RadioGroup) findViewById(R.id.rBGroup);
        rBAmarillo = (RadioButton) findViewById(R.id.rBAmarillo);
        rBAzul = (RadioButton) findViewById(R.id.rBAzul);
        rBRojo = (RadioButton) findViewById(R.id.rBRojo);
        lLayout = (RelativeLayout) findViewById(R.id.rL);

        //Intentamos recuperar desde las SharedPreferences el valor del color guardado
        color = sharedPref.getString("Color", "");
        //Si el color es NULL, querra decir que aun no se ha elegido uno, asique seguira con su camino
        if(!color.equals("")){
            cambiarColor(color);
        }

        //Este metodo se ejecutara cuando haya un cambio de seleccion dentro del RadioGroup
        rBGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()            {

            @Override
            public void onCheckedChanged(RadioGroup arg0, int selectedId) {
                //Sacamos qué id tiene el radiobutton seleccionado y despues lo intanciamos para coger su valor de color
                selectedId = rBGroup.getCheckedRadioButtonId();
                RadioButton seleccionado = (RadioButton) findViewById(selectedId);
                color = seleccionado.getText().toString();

                //cambiamos el color con el metodo
                cambiarColor(color);

                //recogemos en el shared preferences lo que necesitemos mediante el putString
                SharedPreferences.Editor guardar = sharedPref.edit();
                guardar.putString("Color", color);
                guardar.commit();

            }
        });
    }

    //Metodo que pasandole una cadena con texto de color ROJO, AMARILLO, AZUL, cambiamos el color de fondo
    public void cambiarColor(String color){
        if(color.equals("ROJO")) {
            lLayout.setBackgroundColor(Color.RED);
            rBRojo.setChecked(true);
        }
        else if(color.equals("AZUL")){
            lLayout.setBackgroundColor(Color.BLUE);
            rBAzul.setChecked(true);
        }
        else {
            lLayout.setBackgroundColor(Color.YELLOW);
            rBAmarillo.setChecked(true);
        }
    }
}
