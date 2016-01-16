package com.setec2015.appsetec;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class dbSaveInfoActivity extends AppCompatActivity {

    EditText new_temp, new_lum, new_humSolo, new_humAr, new_pluv, new_data;
    String temp, lum, humSolo, humAr, pluv, data;
    String alertaDbTask, alertaUI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_db_save_info);

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

            // Write in Local DB
                BackgroundDbTask backgroundDbTask = new BackgroundDbTask(this);
                backgroundDbTask.execute("add_info_1", temp, lum, humSolo, humAr, pluv, data);

            // ALERTAS
                alertaDbTask = "add_alerta_1";
                alertaTemperatura();
                alertaLuminosidade();
                alertaHumidadeSolo();
                alertaHumidadeAr();
                alertaPluviosidade();
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

            // Write in Local DB
                BackgroundDbTask backgroundDbTask = new BackgroundDbTask(this);
                backgroundDbTask.execute("add_info_2", temp, lum, humSolo, humAr, pluv, data);

            // ALERTAS
                alertaDbTask = "add_alerta_2";
                alertaTemperatura();
                alertaLuminosidade();
                alertaHumidadeSolo();
                alertaHumidadeAr();
                alertaPluviosidade();
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

            // Write in Local DB
                BackgroundDbTask backgroundDbTask = new BackgroundDbTask(this);
                backgroundDbTask.execute("add_info_3", temp, lum, humSolo, humAr, pluv, data);

            // ALERTAS
                alertaDbTask = "add_alerta_3";
                alertaTemperatura();
                alertaLuminosidade();
                alertaHumidadeSolo();
                alertaHumidadeAr();
                alertaPluviosidade();
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
            String alert1 = "Ultrapassou o valor mínimo desejado: " + minTemp;
            String alert2 = "Ultrapassou o valor máximo desejado: " + maxTemp;
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
            String alert1 = "Ultrapassou o valor mínimo desejado: " + minLum;
            String alert2 = "Ultrapassou o valor máximo desejado: " + maxLum;
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
            String alert1 = "Ultrapassou o valor mínimo desejado: " + minHumSolo;
            String alert2 = "Ultrapassou o valor máximo desejado: " + maxHumSolo;
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
            String alert1 = "Ultrapassou o valor mínimo desejado: " + minHumAr;
            String alert2 = "Ultrapassou o valor máximo desejado: " + maxHumAr;
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
            String minPluv = prefs.getString("minPluviosidade", "0");
                double min_pluv = Double.parseDouble(minPluv);
            String maxPluv = prefs.getString("maxPluviosidade", "0");
                double max_pluv = Double.parseDouble(maxPluv);

        double PLUV;
        if(pluv != null && !pluv.isEmpty()) {
            PLUV = Double.parseDouble(pluv);
        }else {
            PLUV = 0.0;
        }

        if(PLUV != 0.0) {
            String type = "Pluviosidade";
            String alert1 = "Ultrapassou o valor mínimo desejado: " + minPluv;
            String alert2 = "Ultrapassou o valor máximo desejado: " + maxPluv;
            String value = pluv + " mm^3/h";
            String date = data;

            if (PLUV < min_pluv) {
                BackgroundDbTask2 backgroundDbTask2 = new BackgroundDbTask2(this);
                backgroundDbTask2.execute(alertaDbTask, type, alert1, value, date);
            } else if (PLUV > max_pluv) {
                BackgroundDbTask2 backgroundDbTask2 = new BackgroundDbTask2(this);
                backgroundDbTask2.execute(alertaDbTask, type, alert2, value, date);
            }
        }
    }
}
