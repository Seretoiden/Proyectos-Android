package com.example.unaicanales.ejerciciolistview;

import android.app.Activity;
import android.content.Context;
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

public class MainActivity extends Activity {

    private static ArrayList<Equipo> equipos = new ArrayList<Equipo>();
    private static EditText eT;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        eT = (EditText) findViewById(R.id.buscador);

        anadirEquipos();

        ListView lV = (ListView) findViewById(R.id.listView);
        final AdapterEquipo adapter = new AdapterEquipo(this, equipos);

        lV.setAdapter(adapter);

        lV.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {
                Equipo item = (Equipo) parent.getItemAtPosition(position);
                Toast.makeText(getApplicationContext(), item.getNombre() + " SE HA HECHO CLICK", Toast.LENGTH_SHORT).show();
            }

        });

        eT.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Call back the Adapter with current character to Filter
                Filter filtro = adapter.getFilter();
                filtro.filter(s.toString());
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,int after) {
                Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void afterTextChanged(Editable s) {
            }

        });


    }

    public static void anadirEquipos(){
        Equipo barcelona = new Equipo("Barcelona FC", "DESCRIPCION AQUI", 1200000, R.mipmap.barcelona);
        Equipo madrid = new Equipo("Real Madrid FC", "DESCRIPCION AQUI", 1200000, R.mipmap.madrid_logo);
        Equipo realSociedad = new Equipo("RealSociedad FC", "DESCRIPCION AQUI", 1200000, R.mipmap.realsociedad_logo);

        equipos.add(barcelona);
        equipos.add(madrid);
        equipos.add(realSociedad);
        equipos.add(barcelona);
        equipos.add(madrid);
        equipos.add(realSociedad);
        equipos.add(barcelona);
        equipos.add(madrid);
        equipos.add(realSociedad);
        equipos.add(barcelona);
        equipos.add(madrid);
        equipos.add(realSociedad);
    }

}