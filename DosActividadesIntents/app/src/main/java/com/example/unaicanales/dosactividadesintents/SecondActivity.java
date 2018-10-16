package com.example.unaicanales.dosactividadesintents;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;


public class SecondActivity extends AppCompatActivity {

    //Declaramos el único TextView que tenemos en esta vista
    private TextView tvTexto;
    //Declaramos todas las variables que vamos a recibir por parametro.
    private String sDia, sMes, sAnio, sHora, sMinutos, sDiaSemana, sMesAnio;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        tvTexto = (TextView) findViewById(R.id.tvTexto);

        //Recogemos las variables desde el Bundle, y imprimimos en pantalla el mensaje correspondiente
        Bundle datos = this.getIntent().getExtras();
        sDia = datos.getString("sDia");
        sMes = datos.getString("sMes");
        sAnio = datos.getString("sAnio");
        sHora = datos.getString("sHora");
        sMinutos = datos.getString("sMinutos");
        sDiaSemana = datos.getString("sDiaSemana");
        sMesAnio = datos.getString("sMesAnio");

        tvTexto.setText(sDiaSemana + " " + sDia + " de " + sMesAnio + " " + sHora + ":" + sMinutos + ".");

    }
}
