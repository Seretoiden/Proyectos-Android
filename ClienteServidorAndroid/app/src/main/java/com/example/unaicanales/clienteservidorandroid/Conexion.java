package com.example.unaicanales.clienteservidorandroid;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

public class Conexion extends AsyncTask {

    private static String IP = "10.0.2.2";
    private static int PORT = 5050;
    private static List<Socket> sockets;
    private static BufferedWriter bufferedWriter;
    private static BufferedReader bufferedReader;
    private static Context contexto;
    public static HandlerThread handlerThreadEnvio;
    public static HandlerThread handlerThreadRecibimiento;
    public static Handler handler;
    public static Handler handlerRecibir;

    private static Socket socket;

    public Conexion(Context contexto){
        this.contexto = contexto;
    }

    public void iniciarConexion() {
        this.execute();

        handlerThreadEnvio = new HandlerThread("HandlerThread de envio.");
        handlerThreadEnvio.start();

        handlerThreadRecibimiento = new HandlerThread("HandlerThread de recibimiento.");
        handlerThreadRecibimiento.start();

        handler = new Handler(handlerThreadEnvio.getLooper()){
            @Override
            public void handleMessage(Message msg) {
                String mensaje = (String) msg.obj;
                try {
                    if(bufferedWriter != null){
                        bufferedWriter.write(mensaje+"\n");
                        bufferedWriter.flush();
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };

        handlerRecibir = new Handler(handlerThreadRecibimiento.getLooper());

        handlerRecibir.post(new Runnable() {
            @Override
            public void run() {
                String entrada = "";

                while(bufferedReader == null){
                    try{
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                try{
                    while(true){
                        while((entrada = bufferedReader.readLine()) != null) {
                            System.out.println(entrada);
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    @Override
    protected Object doInBackground(Object[] objects) {
        try{
            socket = new Socket(IP, PORT);


            bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));


            }catch (IOException e1) {
                e1.printStackTrace();
            }


        return "";
    }

    public void enviarMensaje(String msg){
        Message message = Message.obtain();
        message.obj = msg;

        handler.sendMessage(message);
    }
}
