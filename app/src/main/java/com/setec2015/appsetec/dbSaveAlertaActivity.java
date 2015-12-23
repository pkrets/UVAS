package com.setec2015.appsetec;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

public class dbSaveAlertaActivity extends AppCompatActivity {


    EditText new_type, new_alert, new_value, new_data;
    String type, alert, value, data;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_db_save_alerta);

        new_type = (EditText) findViewById(R.id.new_type);
        new_alert = (EditText) findViewById(R.id.new_alert);
        new_value = (EditText) findViewById(R.id.new_value);
        new_data = (EditText) findViewById(R.id.new_data);

    }

    // Add the manually inserted row to the table from Zona 1 - "alertas1_table"
    public void saveAlerta1(View view)
    {
        type = new_type.getText().toString();
        alert = new_alert.getText().toString();
        value = new_value.getText().toString();
        data = new_data.getText().toString();

        BackgroundDbTask2 backgroundDbTask2 = new BackgroundDbTask2(this);
        backgroundDbTask2.execute("add_alerta_1", type, alert, value, data);
        finish();
    }

    // Add the manually inserted row to the table from Zona 1 - "alertas2_table"
    public void saveAlerta2(View view)
    {
        type = new_type.getText().toString();
        alert = new_alert.getText().toString();
        value = new_value.getText().toString();
        data = new_data.getText().toString();

        BackgroundDbTask2 backgroundDbTask2 = new BackgroundDbTask2(this);
        backgroundDbTask2.execute("add_alerta_2", type, alert, value, data);
        finish();
    }

    // Add the manually inserted row to the table from Zona 1 - "alertas3_table"
    public void saveAlerta3(View view)
    {
        type = new_type.getText().toString();
        alert = new_alert.getText().toString();
        value = new_value.getText().toString();
        data = new_data.getText().toString();

        BackgroundDbTask2 backgroundDbTask2 = new BackgroundDbTask2(this);
        backgroundDbTask2.execute("add_alerta_3", type, alert, value, data);
        finish();
    }

//////////////////////////////

    // Delete ALL ROWS in the table from Zona 1 - "alertas1_table"
    public  void deleteAllRows1(View view)
    {
        BackgroundDbTask2 backgroundDbTask2 = new BackgroundDbTask2(this);
        backgroundDbTask2.execute("delete_alerta_1");
    }

    // Delete ALL ROWS in the table from Zona 2 - "alertas2_table"
    public  void deleteAllRows2(View view)
    {
        BackgroundDbTask2 backgroundDbTask2 = new BackgroundDbTask2(this);
        backgroundDbTask2.execute("delete_alerta_2");
    }

    // Delete ALL ROWS in the table from Zona 3 - "alertas3_table"
    public  void deleteAllRows3(View view)
    {
        BackgroundDbTask2 backgroundDbTask2 = new BackgroundDbTask2(this);
        backgroundDbTask2.execute("delete_alerta_3");
    }


}
