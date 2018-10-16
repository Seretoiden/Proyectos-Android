package com.example.unaicanales.dosactividadesintents;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import org.w3c.dom.Text;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    //Declaramos los componentes de la vista
    private EditText etFecha, etHora;
    private TextView tvFecha, tvHora;
    private Button bEnviar;

    //Pasando el Locale ES por parametro a un objeto Date, conseguiremos la fecha en español
    private Calendar calendario = Calendar.getInstance();
    private TimePicker timePicker1;
    private Locale ES = new Locale("es", "ES");

    //Variables que enviaremos a la siguiente activity para imprimir en pantalla
    private String dia, mes, anio, hora, minutos, sDiaSemana, sMesAnio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etFecha = (EditText) findViewById(R.id.etFecha);
        etHora = (EditText) findViewById(R.id.etHora);
        tvFecha = (TextView) findViewById(R.id.tvFecha);
        tvHora = (TextView) findViewById(R.id.tvHora);

        bEnviar = (Button) findViewById(R.id.bEnviar);

        bEnviar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //Llamamos a un metodo en el momento de hacer click en el botón correspondiente
                pasarActividad();
            }
        });

        etFecha.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //Mostraremos la fecha actual del sistema
                //en el datepicker generado al hacer click en el editText de la Fecha
                Calendar mcurrentDate = Calendar.getInstance();
                int mYear = mcurrentDate.get(Calendar.YEAR);
                int mMonth = mcurrentDate.get(Calendar.MONTH);
                int mDay = mcurrentDate.get(Calendar.DAY_OF_MONTH);

                //Declaramos el Dialog que se abrirá
                DatePickerDialog mDatePicker;
                //Y lo instanciamos
                mDatePicker = new DatePickerDialog(MainActivity.this, new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
                        //Al mes que nos da le sumamos uno debido a que siempre devuelve uno menos.
                        selectedmonth = selectedmonth + 1;
                        //Les damos el valor a las variables que enviaremos a la siguiente vista
                        dia = String.valueOf(selectedday);
                        mes = Integer.toString(selectedmonth);
                        anio = String.valueOf(selectedyear);
                        //Declaramos e instanciamos los formatos de salida de las fechas
                        SimpleDateFormat diaSemanaFormat = new SimpleDateFormat("EEEE", ES);
                        SimpleDateFormat mesAnioFormat = new SimpleDateFormat("MMMM", ES);
                        //Generamos un objeto Date para conseguir el dia de semana(martes, miercoles...) y mes (enero, febrero...)
                        Date date = new Date(selectedyear, selectedmonth, selectedday-1);
                        sDiaSemana = diaSemanaFormat.format(date);
                        sMesAnio = mesAnioFormat.format(date);
                        //Al edit text de la fecha le colocamos el elegido por el usuario
                        etFecha.setText("" + selectedday + "/" + selectedmonth + "/" + selectedyear);
                    }
                }, mYear, mMonth, mDay);
                mDatePicker.setTitle("Selecciona una Fecha:");
                mDatePicker.show();
            }
        });

        etHora.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //Mostraremos la hora actual del sistema en el dialog de la hora
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);

                //Declaramos el Dialog que se abrirá
                TimePickerDialog mTimePicker;
                //Y lo instanciamos
                mTimePicker = new TimePickerDialog(MainActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        //Cogemos la hora y los minutos seleccionados para luego enviar a la siguiente vista
                        hora = selectedHour + "";
                        minutos = selectedMinute + "";
                        //Funcion ComprobarCeros, sirve para cuando la hora o el minuto sea <10, le añada el 01:03, correspondiente
                        etHora.setText(comprobarCeros(selectedHour) + ":" + comprobarCeros(selectedMinute));
                    }
                }, hour, minute, true);
                mTimePicker.setTitle("Selecciona una Hora:");
                mTimePicker.show();

            }
        });
    }

    private void pasarActividad() {
        //Para pasar de activities instanciamos un objeto de la clase Intent, dándole el packageContext de esta actividad
        // y ademas de qué clase debe de ejecutar proximamente y qé variables se van a pasar
        Intent intent = new Intent(this, SecondActivity.class);
        intent.putExtra("sDia", dia);
        intent.putExtra("sMes", mes);
        intent.putExtra("sAnio", anio);
        intent.putExtra("sHora", comprobarCeros(Integer.parseInt(hora)));
        intent.putExtra("sMinutos", comprobarCeros(Integer.parseInt(minutos)));
        intent.putExtra("sDiaSemana", ponerMayusculas(sDiaSemana));
        intent.putExtra("sMesAnio", ponerMayusculas(sMesAnio));
        startActivity(intent);
    }

    private String comprobarCeros(int num){
        if(num < 10)
            return "0" + num;
        else
            return num + "";
    }

    private String ponerMayusculas(String cadena){
        return cadena.substring(0, 1).toUpperCase() + cadena.substring(1, cadena.length());
    }

}
