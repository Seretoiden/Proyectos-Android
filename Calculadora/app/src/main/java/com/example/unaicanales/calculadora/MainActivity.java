package com.example.unaicanales.calculadora;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private static Double operacion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button b0 = (Button) findViewById(R.id.b0);
        Button b1 = (Button) findViewById(R.id.b1);
        Button b2 = (Button) findViewById(R.id.b2);
        Button b3 = (Button) findViewById(R.id.b3);
        Button b4 = (Button) findViewById(R.id.b4);
        Button b5 = (Button) findViewById(R.id.b5);
        Button b6 = (Button) findViewById(R.id.b6);
        Button b7 = (Button) findViewById(R.id.b7);
        Button b8 = (Button) findViewById(R.id.b8);
        Button b9 = (Button) findViewById(R.id.b9);
        Button bIgual = (Button) findViewById(R.id.bIgual);
        Button bMasMenos = (Button) findViewById(R.id.bMasMenos);
        Button bAC = (Button) findViewById(R.id.bAC);
        Button bDivision = (Button) findViewById(R.id.bDivision);
        Button bMultiplicar = (Button) findViewById(R.id.bMultiplicar);
        Button bPorcentaje = (Button) findViewById(R.id.bPorcentaje);
        Button bMas = (Button) findViewById(R.id.bMas);
        Button bMenos = (Button) findViewById(R.id.bMenos);


        b0.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Button b0 = (Button) findViewById(R.id.b0);
                //Llamamos a un metodo en el momento de hacer click en el botón correspondiente
                operacion = Parser.eval("0");
            }
        });

        b1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Button b1 = (Button) findViewById(R.id.b1);
                //Llamamos a un metodo en el momento de hacer click en el botón correspondiente
                Parser.eval("1");
            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Button b2 = (Button) findViewById(R.id.b2);
                //Llamamos a un metodo en el momento de hacer click en el botón correspondiente
                Parser.eval("2");
            }
        });

        b3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Button b3 = (Button) findViewById(R.id.b3);
                //Llamamos a un metodo en el momento de hacer click en el botón correspondiente
                Parser.eval("3");
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
/*
        bMasMenos.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //Llamamos a un metodo en el momento de hacer click en el botón correspondiente
                signoPulsado("");
            }
        });
*/
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
                signoPulsado("x");

            }
        });

        bPorcentaje.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //Llamamos a un metodo en el momento de hacer click en el botón correspondiente
                signoPulsado("%");

            }

        });
    }
}
