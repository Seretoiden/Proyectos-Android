package com.example.unaicanales.ejerciciolistview;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SecondActivity extends Activity {

    private int idEquipo;
    private Bbdd bbdd = new Bbdd(this);
    private ArrayList<Integer> aIdImagen;
    private Equipo equipo;

    private EditText eTTitulo, eTDesc;
    private Spinner spImagen;
    private Button bSave;

    private String accionARealizar = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        aIdImagen = new ArrayList<Integer>();
        aIdImagen.add(R.mipmap.barcelona_logo);
        aIdImagen.add(R.mipmap.madrid_logo);
        aIdImagen.add(R.mipmap.realsociedad_logo);

        comprobarGuardarMod();

        eTTitulo = (EditText) findViewById(R.id.eTTitulo);
        eTDesc = (EditText) findViewById(R.id.eTDesc);
        spImagen = (Spinner) findViewById(R.id.spImagen);
        bSave = (Button) findViewById(R.id.bSave);

        String[] aEquiposSpinner = new String[] {"Barcelona", "Real Madrid", "Real Sociedad"};
        spImagen.setSelection(4);
        spImagen.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, aEquiposSpinner));

        if(accionARealizar.equals("guardar")){
            bSave.setText("GUARDAR");
        }else{
            bSave.setText("MODIFICAR");

            idEquipo = getIntent().getExtras().getInt("idEquipo");

            System.out.println("HOLA SEGUNDA");
            equipo = bbdd.buscarEquipo(idEquipo);

            System.out.println(equipo.getNombre() + " HLELLELELLE");

            eTTitulo.setText(equipo.getNombre());
            eTDesc.setText(equipo.getDescripcion());

            equipo.setImagen(aIdImagen.get(spImagen.getSelectedItemPosition()));
        }

        bSave.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //recogemos en cuanto clicka el boton de guardar o modificar, todos los datos sobre el jugador en el objeto
                Equipo nuevo = new Equipo();
                nuevo.setDescripcion(eTDesc.getText().toString());
                nuevo.setNombre(eTTitulo.getText().toString());
                nuevo.setImagen(aIdImagen.get(spImagen.getSelectedItemPosition()));
                nuevo.setDorsal(1);
                if(bSave.getText().equals("GUARDAR")) {
                    if(comprobarFields()){
                        bbdd.nuevoEquipo(nuevo);
                        Toast.makeText(getApplicationContext(), "Insertado en BBDD..", Toast.LENGTH_SHORT).show();

                    }else{
                        Toast.makeText(getApplicationContext(), "DEBES RELLENAR TODOS LOS CAMPOS...", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    if(comprobarFields()){
                        //Le damos al objeto equipo que vamos a introducir en la base de datos, el id que traemos desde la primera activity
                        nuevo.setId(equipo.getId());
                        nuevo.setDorsal(equipo.getDorsal());
                        bbdd.modificarEquipo(nuevo);
                    }else{
                        Toast.makeText(getApplicationContext(), "DEBES RELLENAR TODOS LOS CAMPOS...", Toast.LENGTH_SHORT).show();
                    }
                }
                volverAtras();

            }
        });
    }

    public void volverAtras(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public boolean comprobarFields(){
        if(eTTitulo.getText().equals("") || eTDesc.getText().equals(""))
            return false;
        return true;
    }

    public void comprobarGuardarMod(){
        try{
            idEquipo = getIntent().getExtras().getInt("idEquipo");
            accionARealizar = "modificar";
        }catch (Exception e){
            accionARealizar = "guardar";
        }
        System.out.println(accionARealizar);
    }
}
