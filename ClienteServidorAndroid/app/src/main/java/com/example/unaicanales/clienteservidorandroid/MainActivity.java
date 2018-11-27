package com.example.unaicanales.clienteservidorandroid;

import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

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

    //private Handler handlerUIThread;

    private static EditText eTServidor;
    private static Button bComunicacion;
    private static TextView txtServer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        eTServidor = (EditText) findViewById(R.id.etServidor);
        bComunicacion = (Button) findViewById(R.id.bComunicacion);

        conexion = new Conexion(getApplicationContext(), this);
        conexion.iniciarConexion();

        bComunicacion.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                conexion.enviarMensaje(eTServidor.getText().toString());
            }
        });
    }

    public void recuperarDatosServidor(final String recibidoServidor){
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                txtServer = (TextView) findViewById(R.id.txtServer);
                txtServer.setText(recibidoServidor);
            }
        });
    }

}
