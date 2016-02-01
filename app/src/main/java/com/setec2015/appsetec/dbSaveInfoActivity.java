package com.setec2015.appsetec;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.Date;

public class dbSaveInfoActivity extends AppCompatActivity {

    EditText new_temp, new_lum, new_humSolo, new_humAr, new_pluv, new_data;
    String temp, lum, humSolo, humAr, pluv, data;
    String alertaDbTask, alertaUI;
    Boolean alertasOn, boxTemp, boxLum, boxHum, boxPluv, boxOutros, boxPraga1, boxPraga2, boxPraga3, boxPraga4;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_db_save_info);

        try {
            Class.forName("android.os.AsyncTask");
            Log.i("ASYNCTASK ERROR LOGIN", "android.os.AsyncTask found.");
        } catch (ClassNotFoundException e) {
            Log.i("ASYNCTASK ERROR LOGIN", "Class android.os.AsyncTask not found!!!");
            e.printStackTrace();
        }

        new_temp = (EditText) findViewById(R.id.new_temp);
        new_lum = (EditText) findViewById(R.id.new_lum);
        new_humSolo = (EditText) findViewById(R.id.new_humSolo);
        new_humAr = (EditText) findViewById(R.id.new_humAr);
        new_pluv = (EditText) findViewById(R.id.new_pluv);
        new_data = (EditText) findViewById(R.id.new_data);

    }

    // Add the manually inserted row to the table from Zona 1 - "pandlet1_table"
        public void saveData1(View view)
        {
            temp = new_temp.getText().toString();
            lum = new_lum.getText().toString();
            humSolo = new_humSolo.getText().toString();
            humAr = new_humAr.getText().toString();
            pluv = new_pluv.getText().toString();
            data = new_data.getText().toString();

            // Convert Unix Timestamp to Date
                long ts = Long.valueOf(data)*1000; // it needs to be in milliseconds
                Date df = new java.util.Date(ts);
                String Data = new SimpleDateFormat("dd/MM/yyyy, HH:mm").format(df);

            // Write in Local DB
                BackgroundDbTask backgroundDbTask = new BackgroundDbTask(this);
                backgroundDbTask.execute("add_info_1", temp, lum, humSolo, humAr, pluv, Data);

            // ALERTAS
            SharedPreferences prefs = getSharedPreferences("DataSettingsState", Context.MODE_PRIVATE);
                alertasOn = prefs.getBoolean("alertasOn", false);
                boxTemp = prefs.getBoolean("boxTemp", false);
                boxLum = prefs.getBoolean("boxLum", false);
                boxHum = prefs.getBoolean("boxHum", false);
                boxPluv = prefs.getBoolean("boxPluv", false);
                //boxOutros = prefs.getBoolean("boxOutros", false);
                //boxPraga1 = prefs.getBoolean("boxPraga1", false);
                //boxPraga2 = prefs.getBoolean("boxPraga2", false);
                //boxPraga3 = prefs.getBoolean("boxPraga3", false);
                //boxPraga4 = prefs.getBoolean("boxPraga4", false);
                    if(alertasOn) {
                        alertaDbTask = "add_alerta_1";
                        if(boxTemp) { alertaTemperatura(); }
                        if(boxLum) { alertaLuminosidade(); }
                        if(boxHum) { alertaHumidadeSolo(); alertaHumidadeAr(); }
                        if(boxPluv) { alertaPluviosidade(); }
                        //if(boxOutros) { alertaOutros(); }
                        //if(boxPraga1) { alertaPraga1(); }
                        //if(boxPraga2) { alertaPraga2(); }
                        //if(boxPraga3) { alertaPraga3(); }
                        //if(boxPraga4) { alertaPraga4(); }
                    }
        }

    // Add the manually inserted row to the table from Zona 2 - "pandlet2_table"
        public void saveData2(View view)
        {
            temp = new_temp.getText().toString();
            lum = new_lum.getText().toString();
            humSolo = new_humSolo.getText().toString();
            humAr = new_humAr.getText().toString();
            pluv = new_pluv.getText().toString();
            data = new_data.getText().toString();

            // Convert Unix Timestamp to Date
                long ts = Long.valueOf(data)*1000; // it needs to be in milliseconds
                Date df = new java.util.Date(ts);
                String Data = new SimpleDateFormat("dd/MM/yyyy, HH:mm").format(df);

            // Write in Local DB
                BackgroundDbTask backgroundDbTask = new BackgroundDbTask(this);
                backgroundDbTask.execute("add_info_2", temp, lum, humSolo, humAr, pluv, Data);

            // ALERTAS
            SharedPreferences prefs = getSharedPreferences("DataSettingsState", Context.MODE_PRIVATE);
                alertasOn = prefs.getBoolean("alertasOn", false);
                boxTemp = prefs.getBoolean("boxTemp", false);
                boxLum = prefs.getBoolean("boxLum", false);
                boxHum = prefs.getBoolean("boxHum", false);
                boxPluv = prefs.getBoolean("boxPluv", false);
                //boxOutros = prefs.getBoolean("boxOutros", false);
                //boxPraga1 = prefs.getBoolean("boxPraga1", false);
                //boxPraga2 = prefs.getBoolean("boxPraga2", false);
                //boxPraga3 = prefs.getBoolean("boxPraga3", false);
                //boxPraga4 = prefs.getBoolean("boxPraga4", false);
                    if(alertasOn) {
                        alertaDbTask = "add_alerta_2";
                        if(boxTemp) { alertaTemperatura(); }
                        if(boxLum) { alertaLuminosidade(); }
                        if(boxHum) { alertaHumidadeSolo(); alertaHumidadeAr(); }
                        if(boxPluv) { alertaPluviosidade(); }
                        //if(boxOutros) { alertaOutros(); }
                        //if(boxPraga1) { alertaPraga1(); }
                        //if(boxPraga2) { alertaPraga2(); }
                        //if(boxPraga3) { alertaPraga3(); }
                        //if(boxPraga4) { alertaPraga4(); }
                    }
        }

    // Add the manually inserted row to the table from Zona 3 - "pandlet3_table"
        public void saveData3(View view)
        {
            temp = new_temp.getText().toString();
            lum = new_lum.getText().toString();
            humSolo = new_humSolo.getText().toString();
            humAr = new_humAr.getText().toString();
            pluv = new_pluv.getText().toString();
            data = new_data.getText().toString();

            // Convert Unix Timestamp to Date
                long ts = Long.valueOf(data)*1000; // it needs to be in milliseconds
                Date df = new java.util.Date(ts);
                String Data = new SimpleDateFormat("dd/MM/yyyy, HH:mm").format(df);

            // Write in Local DB
                BackgroundDbTask backgroundDbTask = new BackgroundDbTask(this);
                backgroundDbTask.execute("add_info_3", temp, lum, humSolo, humAr, pluv, Data);

            // ALERTAS
            SharedPreferences prefs = getSharedPreferences("DataSettingsState", Context.MODE_PRIVATE);
                alertasOn = prefs.getBoolean("alertasOn", false);
                boxTemp = prefs.getBoolean("boxTemp", false);
                boxLum = prefs.getBoolean("boxLum", false);
                boxHum = prefs.getBoolean("boxHum", false);
                boxPluv = prefs.getBoolean("boxPluv", false);
                //boxOutros = prefs.getBoolean("boxOutros", false);
                //boxPraga1 = prefs.getBoolean("boxPraga1", false);
                //boxPraga2 = prefs.getBoolean("boxPraga2", false);
                //boxPraga3 = prefs.getBoolean("boxPraga3", false);
                //boxPraga4 = prefs.getBoolean("boxPraga4", false);
                    if(alertasOn) {
                        alertaDbTask = "add_alerta_3";
                        if(boxTemp) { alertaTemperatura(); }
                        if(boxLum) { alertaLuminosidade(); }
                        if(boxHum) { alertaHumidadeSolo(); alertaHumidadeAr(); }
                        if(boxPluv) { alertaPluviosidade(); }
                        //if(boxOutros) { alertaOutros(); }
                        //if(boxPraga1) { alertaPraga1(); }
                        //if(boxPraga2) { alertaPraga2(); }
                        //if(boxPraga3) { alertaPraga3(); }
                        //if(boxPraga4) { alertaPraga4(); }
                    }
        }

//////////////////////////////

    // Delete ALL ROWS in the table from Zona 1 - "pandlet1_table"
        public  void deleteAllRows1(View view)
        {
            BackgroundDbTask backgroundDbTask = new BackgroundDbTask(this);
            backgroundDbTask.execute("delete_info_1");
        }

    // Delete ALL ROWS in the table from Zona 2 - "pandlet2_table"
        public  void deleteAllRows2(View view)
        {
            BackgroundDbTask backgroundDbTask = new BackgroundDbTask(this);
            backgroundDbTask.execute("delete_info_2");
        }

    // Delete ALL ROWS in the table from Zona 3 - "pandlet3_table"
        public  void deleteAllRows3(View view)
        {
            BackgroundDbTask backgroundDbTask = new BackgroundDbTask(this);
            backgroundDbTask.execute("delete_info_3");
        }


////////////////   ALERTAS   ////////////////

    private void alertaTemperatura() {
        SharedPreferences prefs = getSharedPreferences("DataTemperatura", Context.MODE_PRIVATE);
            String minTemp = prefs.getString("minTemperatura", "0");
                double min_temp = Double.parseDouble(minTemp);
            String maxTemp = prefs.getString("maxTemperatura", "0");
                double max_temp = Double.parseDouble(maxTemp);

        double TMP;
        if(temp != null && !temp.isEmpty()) {
            TMP = Double.parseDouble(temp);
        }else {
            TMP = 0.0;
        }

        if(TMP != 0.0) {
            String type = "Temperatura";
            String alert1 = "Valor recebido é inferior ao valor mínimo desejado:  " +minTemp+ " ºC";
            String alert2 = "Valor recebido é superior ao valor máximo desejado:  " +maxTemp+ " ºC";
            String value = temp + " ºC";
            String date = data;

            if (TMP < min_temp) {
                BackgroundDbTask2 backgroundDbTask2 = new BackgroundDbTask2(this);
                backgroundDbTask2.execute(alertaDbTask, type, alert1, value, date);

            }
            else if (TMP > max_temp) {
                BackgroundDbTask2 backgroundDbTask2 = new BackgroundDbTask2(this);
                backgroundDbTask2.execute(alertaDbTask, type, alert2, value, date);
            }
        }
    }

    private void alertaLuminosidade() {
        SharedPreferences prefs = getSharedPreferences("DataLuminosidade", Context.MODE_PRIVATE);
            String minLum = prefs.getString("minLuminosidade", "0");
                double min_lum = Double.parseDouble(minLum);
            String maxLum = prefs.getString("maxLuminosidade", "0");
                double max_lum = Double.parseDouble(maxLum);

        double LUM;
        if(lum != null && !lum.isEmpty()) {
            LUM = Double.parseDouble(lum);
        }else {
            LUM = 0.0;
        }

        if(LUM != 0.0) {
            String type = "Luminosidade";
            String alert1 = "Valor recebido é inferior ao valor mínimo desejado:  " +minLum+ " lux";
            String alert2 = "Valor recebido é superior ao valor máximo desejado:  " +maxLum+ " lux";
            String value = lum + " lux";
            String date = data;

            if (LUM < min_lum) {
                BackgroundDbTask2 backgroundDbTask2 = new BackgroundDbTask2(this);
                backgroundDbTask2.execute(alertaDbTask, type, alert1, value, date);
            } else if (LUM > max_lum) {
                BackgroundDbTask2 backgroundDbTask2 = new BackgroundDbTask2(this);
                backgroundDbTask2.execute(alertaDbTask, type, alert2, value, date);
            }
        }
    }

    private void alertaHumidadeSolo() {
        SharedPreferences prefs = getSharedPreferences("DataHumidade", Context.MODE_PRIVATE);
            String minHumSolo = prefs.getString("minHumidadeSolo", "0");
                double min_humSolo = Double.parseDouble(minHumSolo);
            String maxHumSolo = prefs.getString("maxHumidadeSolo", "0");
                double max_humSolo = Double.parseDouble(maxHumSolo);

        double HSOLO;
        if(humSolo != null && !humSolo.isEmpty()) {
            HSOLO = Double.parseDouble(humSolo);
        }else {
            HSOLO = 0.0;
        }

        if(HSOLO != 0.0) {
            String type = "Humidade (solo)";
            String alert1 = "Valor recebido é inferior ao valor mínimo desejado:  " +minHumSolo+ " %";
            String alert2 = "Valor recebido é superior ao valor máximo desejado:  " +maxHumSolo+ " %";
            String value = humSolo + " %";
            String date = data;

            if (HSOLO < min_humSolo) {
                BackgroundDbTask2 backgroundDbTask2 = new BackgroundDbTask2(this);
                backgroundDbTask2.execute(alertaDbTask, type, alert1, value, date);
            } else if (HSOLO > max_humSolo) {
                BackgroundDbTask2 backgroundDbTask2 = new BackgroundDbTask2(this);
                backgroundDbTask2.execute(alertaDbTask, type, alert2, value, date);
            }
        }
    }

    private void alertaHumidadeAr() {
        SharedPreferences prefs = getSharedPreferences("DataHumidade", Context.MODE_PRIVATE);
            String minHumAr = prefs.getString("minHumidadeAr", "0");
                double min_humAr = Double.parseDouble(minHumAr);
            String maxHumAr = prefs.getString("maxHumidadeAr", "0");
                double max_humAr = Double.parseDouble(maxHumAr);

        double HAR;
        if(humAr != null && !humAr.isEmpty()) {
            HAR = Double.parseDouble(humAr);
        }else {
            HAR = 0.0;
        }

        if(HAR != 0.0) {
            String type = "Humidade (ar)";
            String alert1 = "Valor recebido é inferior ao valor mínimo desejado:  " +minHumAr+ " %";
            String alert2 = "Valor recebido é superior ao valor máximo desejado:  " +maxHumAr+ " %";
            String value = humAr + " %";
            String date = data;

            if (HAR < min_humAr) {
                BackgroundDbTask2 backgroundDbTask2 = new BackgroundDbTask2(this);
                backgroundDbTask2.execute(alertaDbTask, type, alert1, value, date);
            } else if (HAR > max_humAr) {
                BackgroundDbTask2 backgroundDbTask2 = new BackgroundDbTask2(this);
                backgroundDbTask2.execute(alertaDbTask, type, alert2, value, date);
            }
        }
    }

    private void alertaPluviosidade() {
        SharedPreferences prefs = getSharedPreferences("DataPluviosidade", Context.MODE_PRIVATE);
            String maxHorasPluv = prefs.getString("maxHorasPluviosidade", "0");
                double max_horasPluv = Double.parseDouble(maxHorasPluv);

        double PLUV;
        if(pluv != null && !pluv.isEmpty()) {
            PLUV = Double.parseDouble(pluv);
        }else {
            PLUV = 0.0;
        }

        if(PLUV != 0.0) {
            String type = "Pluviosidade";
            String alert1 = "O número de horas de ocorrência de pluviosidade foi superior ao máximo desejado:  " +maxHorasPluv+ " horas";
            String value = pluv + " horas";
            String date = data;

            if (PLUV > max_horasPluv) {
                BackgroundDbTask2 backgroundDbTask2 = new BackgroundDbTask2(this);
                backgroundDbTask2.execute(alertaDbTask, type, alert1, value, date);
            }
        }
    }

    private void alertaOutros() {

    }

    private void alertaPraga1() {
        double TEMP, HUM_A, LUM, PLUV;
        if((temp != null && !temp.isEmpty()) || (humAr != null && !humAr.isEmpty())
                || (lum != null && !lum.isEmpty()) || (pluv != null && !pluv.isEmpty())) {
            TEMP = Double.parseDouble(temp); HUM_A = Double.parseDouble(humAr);
            LUM = Double.parseDouble(lum); PLUV = Double.parseDouble(pluv);
        }else {
            TEMP = 0.0; HUM_A = 0.0; LUM = 0.0; PLUV = 0.0;
        }

        // Conditions favorable for Praga 1 (Míldio)
            if(TEMP > 10.0 || HUM_A > 95.0 || LUM == 0.0 || PLUV > 0)        // LUM ?? PLUV ??
            {
                String type = "Doença";
                String alert = "Foram reunidas as condições necessárias para a existência de 'Míldio' nesta zona.";
                String value = "n/a";
                String date = data;

                    BackgroundDbTask2 backgroundDbTask2 = new BackgroundDbTask2(this);
                    backgroundDbTask2.execute(alertaDbTask, type, alert, value, date);
            }
    }

    private void alertaPraga2() {
        double TEMP, HUM_A, PLUV;
        if((temp != null && !temp.isEmpty()) || (humAr != null && !humAr.isEmpty())
                || (pluv != null && !pluv.isEmpty())) {
            TEMP = Double.parseDouble(temp); HUM_A = Double.parseDouble(humAr); PLUV = Double.parseDouble(pluv);
        }else {
            TEMP = 0.0; HUM_A = 0.0; PLUV = 0.0;
        }

        // Conditions favorable for Praga 2 (Oídio da Videira)
        if(TEMP >= 24.0 || TEMP <= 26.0 || HUM_A > 90.0 || PLUV != 0.0)                 // PLUV ??
            {
                String type = "Doença";
                String alert = "Foram reunidas as condições necessárias para a existência de 'Oídio da Videira' nesta zona.";
                String value = "n/a";
                String date = data;

                    BackgroundDbTask2 backgroundDbTask2 = new BackgroundDbTask2(this);
                    backgroundDbTask2.execute(alertaDbTask, type, alert, value, date);
            }
    }

    private void alertaPraga3() {
        double TEMP, HUM_A;
        if((temp != null && !temp.isEmpty()) || (humAr != null && !humAr.isEmpty())) {
            TEMP = Double.parseDouble(temp); HUM_A = Double.parseDouble(humAr);
        }else {
            TEMP = 0.0; HUM_A = 0.0;
        }

        // Conditions favorable for Praga 2 (Oídio da Videira)
        if(TEMP >= 15.0 || TEMP <= 25.0 || HUM_A > 95.0)                 // PLUV ??
        {
            String type = "Doença";
            String alert = "Foram reunidas as condições necessárias para a existência de 'Podridão Cinzenta' nesta zona.";
            String value = "n/a";
            String date = data;

            BackgroundDbTask2 backgroundDbTask2 = new BackgroundDbTask2(this);
            backgroundDbTask2.execute(alertaDbTask, type, alert, value, date);
        }
    }

    private void alertaPraga4() {
        double TEMP, HUM_S, PLUV;
        if((temp != null && !temp.isEmpty()) || (humSolo != null && !humSolo.isEmpty())
                || (pluv != null && !pluv.isEmpty())) {
            TEMP = Double.parseDouble(temp); HUM_S = Double.parseDouble(humSolo); PLUV = Double.parseDouble(pluv);
        }else {
            TEMP = 0.0; HUM_S = 0.0; PLUV = 0.0;
        }

        // Conditions favorable for Praga 2 (Oídio da Videira)
        if(TEMP < 37.0 || HUM_S > 95.0 || PLUV > 0.0)                               // PLUV ??
        {
            String type = "Doença";
            String alert = "Foram reunidas as condições necessárias para a existência de 'Escoriose da Videira' nesta zona.";
            String value = "n/a";
            String date = data;

            BackgroundDbTask2 backgroundDbTask2 = new BackgroundDbTask2(this);
            backgroundDbTask2.execute(alertaDbTask, type, alert, value, date);
        }
    }

}
