package com.setec2015.appsetec;

import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

public class SettingsActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private Switch switch1;
    private EditText edtTemperaturaMin, edtTemperaturaMax, edtHumidadeMin, edtHumidadeMax, edtSolMin, edtSolMax;
    private Button btnAplicar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        mToolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_settings);

        // Display and make available the "return Home button"
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Enable switch button from "Definições"
        switch1 = (Switch) findViewById(R.id.switch1);

        switch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    Toast.makeText(getApplicationContext(), "Recepção de Alertas foi activado!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Recepção de Alertas foi desactivado!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        edtTemperaturaMin = (EditText) findViewById(R.id.edtTemperaturaMin);
        edtTemperaturaMax = (EditText) findViewById(R.id.edtTemperaturaMax);

        edtHumidadeMin = (EditText) findViewById(R.id.edtHumidadeMin);
        edtHumidadeMax = (EditText) findViewById(R.id.edtHumidadeMax);

        edtSolMin = (EditText) findViewById(R.id.edtSolMin);
        edtSolMax = (EditText) findViewById(R.id.edtSolMax);

        btnAplicar = (Button) findViewById(R.id.btnAplicar);

        /*btnAplicar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("edtTemperaturaMin", "From Activity");
                // set Fragmentclass Arguments
                tabSensores fragobj = new tabSensores();
                fragobj.setArguments(bundle);
            }
        });*/
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        // getMenuInflater().inflate(R.menu.menu_settings, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
    /*    if (id == R.id.action_settings) {
            return true;
        } */
        if (id == android.R.id.home) {
            NavUtils.navigateUpFromSameTask(this);
        }

        return super.onOptionsItemSelected(item);
    }
}
