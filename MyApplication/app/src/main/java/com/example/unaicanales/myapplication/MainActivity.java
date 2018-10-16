package com.example.unaicanales.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    //variables globales a utilizar
    Double resultado = 0.0;
    Boolean signoPulsao = false;

    //declaramos los componentes que tenemos en la vista
    Button b0, b1, b2, b3, b4, b5, b6, b7, b8, b9, bIgual, bComa, bAC, bDivision, bMultiplicar, bMas, bMenos;
    EditText et;
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //instanciamos todos los componentes que tenemos en la vista
        tv = (TextView) findViewById(R.id.textView);
        et = (EditText) findViewById(R.id.editText);
        b0 = (Button) findViewById(R.id.b0);
        b1 = (Button) findViewById(R.id.b1);
        b2 = (Button) findViewById(R.id.b2);
        b3 = (Button) findViewById(R.id.b3);
        b4 = (Button) findViewById(R.id.b4);
        b5 = (Button) findViewById(R.id.b5);
        b6 = (Button) findViewById(R.id.b6);
        b7 = (Button) findViewById(R.id.b7);
        b8 = (Button) findViewById(R.id.b8);
        b9 = (Button) findViewById(R.id.b9);
        bIgual = (Button) findViewById(R.id.bIgual);
        bComa = (Button) findViewById(R.id.bComa);
        bAC = (Button) findViewById(R.id.bAC);
        bDivision = (Button) findViewById(R.id.bDivision);
        bMultiplicar = (Button) findViewById(R.id.bMultiplicar);
        bMas = (Button) findViewById(R.id.bMas);
        bMenos = (Button) findViewById(R.id.bMenos);

        b0.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //Llamamos a un metodo en el momento de hacer click en el botón correspondiente
                recogerNumeros(b0);
            }
        });

        b1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Button b1 = (Button) findViewById(R.id.b1);
                //Llamamos a un metodo en el momento de hacer click en el botón correspondiente
                recogerNumeros(b1);
            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Button b2 = (Button) findViewById(R.id.b2);
                //Llamamos a un metodo en el momento de hacer click en el botón correspondiente
                recogerNumeros(b2);
            }
        });

        b3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Button b3 = (Button) findViewById(R.id.b3);
                //Llamamos a un metodo en el momento de hacer click en el botón correspondiente
                recogerNumeros(b3);
            }
        });

        b4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Button b4 = (Button) findViewById(R.id.b4);
                //Llamamos a un metodo en el momento de hacer click en el botón correspondiente
                recogerNumeros(b4);
            }
        });

        b5.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Button b5 = (Button) findViewById(R.id.b5);
                //Llamamos a un metodo en el momento de hacer click en el botón correspondiente
                recogerNumeros(b5);
            }
        });

        b6.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Button b6 = (Button) findViewById(R.id.b6);
                //Llamamos a un metodo en el momento de hacer click en el botón correspondiente
                recogerNumeros(b6);
            }
        });

        b7.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Button b7 = (Button) findViewById(R.id.b7);
                //Llamamos a un metodo en el momento de hacer click en el botón correspondiente
                recogerNumeros(b7);
            }
        });

        b8.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Button b8 = (Button) findViewById(R.id.b8);
                //Llamamos a un metodo en el momento de hacer click en el botón correspondiente
                recogerNumeros(b8);
            }
        });

        b9.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Button b9 = (Button) findViewById(R.id.b9);
                //Llamamos a un metodo en el momento de hacer click en el botón correspondiente
                recogerNumeros(b9);
            }
        });

        bIgual.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //Llamamos a un metodo en el momento de hacer click en el botón correspondiente
                signoPulsado("=");
            }
        });

        bComa.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //Llamamos a un metodo en el momento de hacer click en el botón correspondiente
                recogerNumeros(bComa);
            }
        });

        bMenos.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //Llamamos a un metodo en el momento de hacer click en el botón correspondiente
                signoPulsado("-");

            }
        });

        bDivision.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //Llamamos a un metodo en el momento de hacer click en el botón correspondiente
                signoPulsado("/");

            }
        });

        bMas.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //Llamamos a un metodo en el momento de hacer click en el botón correspondiente
                signoPulsado("+");

            }
        });

        bAC.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //Llamamos a un metodo en el momento de hacer click en el botón correspondiente
                ponerAC();

            }
        });

        bMultiplicar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //Llamamos a un metodo en el momento de hacer click en el botón correspondiente
                signoPulsado("*");

            }
        });

    }

    //método que se encarga de gestionar el click en un botón numérico
    public void recogerNumeros(Button boton){

        tv.setText(tv.getText().toString() + boton.getText().toString());
        resultado = Parser.eval(tv.getText().toString());
        et.setText(comprobarDecimales(resultado).toString());

        signoPulsao = false;
    }

    //metodo que se encarga de poner los textview, edittext y variables en su valor inicial
    public void ponerAC(){
        et.setText("");
        tv.setText("");

        resultado = 0.0;

    }

    //cuando se pulsa un signo llamaremos a este metodo
    public void signoPulsado(String signo){
        //la variable signoPulsao, refiere a que si el usuario pulsa seguidas veces los botones de signo
        if(signoPulsao){
            //conseguimos que cambie el que está por el nuevo
            tv.setText(tv.getText().toString().substring(0, tv.getText().toString().length() - 1) + signo);
        }else {
            //si el signo pulsado es el "=", vaciaremos el edittext, y dejaremos el resultado en el textview
            if (signo.equals("=")) {
                et.setText("");
                tv.setText(comprobarDecimales(resultado).toString());
            } else {
                tv.setText(tv.getText().toString() + signo);
                signoPulsao = true;
            }
        }
    }

    //simple metodo para comprobar si el numero tiene decimales, si las tiene exactas, quitarselas.
    public String comprobarDecimales(Double numero){
        Log.d("", "numero = " + numero.toString());
        if(numero % 1 == 0){
            return numero.toString().substring(0, numero.toString().length()-2);
        }else{
            return numero.toString();
        }
    }
}
