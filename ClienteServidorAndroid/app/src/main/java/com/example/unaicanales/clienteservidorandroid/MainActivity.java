package com.example.unaicanales.clienteservidorandroid;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class MainActivity extends AppCompatActivity {

    private static Socket socket;
    private static AsyncTask task;
    private Conexion conexion;

    private static EditText eTServidor;
    private static Button bComunicacion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        eTServidor = (EditText) findViewById(R.id.etServidor);
        bComunicacion = (Button) findViewById(R.id.bComunicacion);

        conexion = new Conexion(getApplicationContext());
        conexion.iniciarConexion();

        bComunicacion.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                conexion.enviarMensaje(eTServidor.getText().toString());
            }
        });
    }

}
