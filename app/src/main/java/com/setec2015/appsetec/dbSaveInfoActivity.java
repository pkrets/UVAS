package com.setec2015.appsetec;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class dbSaveInfoActivity extends AppCompatActivity {

    EditText new_temp, new_lum, new_humSolo, new_humAr, new_pluv, new_data;
    String temp, lum, humSolo, humAr, pluv, data;


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

    // Add the manually inserted row to the table from Zona 1 - "pandlet1_data"
        public void saveData1(View view)
        {
            temp = new_temp.getText().toString();
            lum = new_lum.getText().toString();
            humSolo = new_humSolo.getText().toString();
            humAr = new_humAr.getText().toString();
            pluv = new_pluv.getText().toString();
            data = new_data.getText().toString();

            BackgroundDbTask backgroundDbTask = new BackgroundDbTask(this);
            backgroundDbTask.execute("add_info_1", temp, lum, humSolo, humAr, pluv, data);
            finish();
        }

    // Add the manually inserted row to the table from Zona 2 - "pandlet2_data"
        public void saveData2(View view)
        {
            temp = new_temp.getText().toString();
            lum = new_lum.getText().toString();
            humSolo = new_humSolo.getText().toString();
            humAr = new_humAr.getText().toString();
            pluv = new_pluv.getText().toString();
            data = new_data.getText().toString();

            BackgroundDbTask backgroundDbTask = new BackgroundDbTask(this);
            backgroundDbTask.execute("add_info_2", temp, lum, humSolo, humAr, pluv, data);
            finish();
        }

    // Add the manually inserted row to the table from Zona 3 - "pandlet3_data"
        public void saveData3(View view)
        {
            temp = new_temp.getText().toString();
            lum = new_lum.getText().toString();
            humSolo = new_humSolo.getText().toString();
            humAr = new_humAr.getText().toString();
            pluv = new_pluv.getText().toString();
            data = new_data.getText().toString();

            BackgroundDbTask backgroundDbTask = new BackgroundDbTask(this);
            backgroundDbTask.execute("add_info_3", temp, lum, humSolo, humAr, pluv, data);
            finish();
        }


}
