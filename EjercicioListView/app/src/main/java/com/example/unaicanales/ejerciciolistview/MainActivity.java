package com.example.unaicanales.ejerciciolistview;

import android.app.Activity;
import android.content.Context;
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
    private Bbdd bbdd = new Bbdd(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        eT = (EditText) findViewById(R.id.buscador);

        //añadimos los equipos en memoria
        equipos = bbdd.leerEquiposDesdeBBDD();
        System.out.println("Tsamñano: " + equipos.size());

        ListView lV = (ListView) findViewById(R.id.listView);
        final AdapterEquipo adapter = new AdapterEquipo(this, equipos);

        lV.setAdapter(adapter);

        //Listener del click en cualquier item del ListView
        lV.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {
                Equipo item = (Equipo) parent.getItemAtPosition(position);
                abrirInfo(item);
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

    //Metodo para abrir el segundo activity con la informacion del equipo
    public void abrirInfo(Equipo equipo) {
        Intent intent = new Intent(this, SecondActivity.class);
        intent.putExtra("Equipo", equipo.getId());
        startActivity(intent);
    }

}