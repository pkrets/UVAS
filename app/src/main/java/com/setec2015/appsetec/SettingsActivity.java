package com.setec2015.appsetec;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.app.FragmentManager;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.internal.VersionUtils;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class SettingsActivity extends AppCompatActivity {

    private Toolbar mToolbar;

    private Switch switchAlertas;
    String alertasOn, alertasOn_saved;

    private TextView txt_off, txt_on;

    View view;
    LayoutInflater inflater;

    // Declare UI objects from "Sensores" alerts
        private CheckBox checkBoxTemperatura, checkBoxLuminosidade, checkBoxHumidade, checkBoxPluviosidade, checkBoxOutros;
        private Button btnConfigTemp, btnConfigLum, btnConfigHum, btnConfigPluv;

    // Declare UI objects from "Doenças" alerts
        private CheckBox checkBoxPraga1, checkBoxPraga2, checkBoxPraga3, checkBoxPraga4;
        private Button btnConfigPraga1, btnConfigPraga2, btnConfigPraga3, btnConfigPraga4;

    String boxTemp, boxLum, boxHum, boxPluv, boxOutros, boxPraga1, boxPraga2, boxPraga3, boxPraga4;
    String boxTemp_saved, boxLum_saved, boxHum_saved, boxPluv_saved, boxOutros_saved,
                boxPraga1_saved, boxPraga2_saved, boxPraga3_saved, boxPraga4_saved;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        mToolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_settings);

        inflater = SettingsActivity.this.getLayoutInflater();

        // Make available and display the "return Home button"
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        switchAlertas = (Switch) findViewById(R.id.switchAlertas);
        txt_off = (TextView) findViewById(R.id.txt_off);
        txt_on = (TextView) findViewById(R.id.txt_on);


    // Fetch UI objects from "Sensores" alerts
        checkBoxTemperatura = (CheckBox) findViewById(R.id.checkBoxTemperatura);
        btnConfigTemp = (Button) findViewById(R.id.btnConfigTemp);

        checkBoxLuminosidade = (CheckBox) findViewById(R.id.checkBoxLuminosidade);
        btnConfigLum = (Button) findViewById(R.id.btnConfigLum);
            btnConfigLum.setVisibility(View.INVISIBLE);

        checkBoxHumidade = (CheckBox) findViewById(R.id.checkBoxHumidade);
        btnConfigHum = (Button) findViewById(R.id.btnConfigHum);
            btnConfigHum.setVisibility(View.INVISIBLE);

        checkBoxPluviosidade = (CheckBox) findViewById(R.id.checkBoxPluviosidade);
        btnConfigPluv = (Button) findViewById(R.id.btnConfigPLuv);
            btnConfigPluv.setVisibility(View.INVISIBLE);

        checkBoxOutros = (CheckBox) findViewById(R.id.checkBoxOutros);


    // Fetch UI objects from "Doenças" alerts
        checkBoxPraga1 = (CheckBox) findViewById(R.id.checkBoxPraga1);
        btnConfigPraga1 = (Button) findViewById(R.id.btnConfigPraga1);
            btnConfigPraga1.setVisibility(View.INVISIBLE);

        checkBoxPraga2 = (CheckBox) findViewById(R.id.checkBoxPraga2);
        btnConfigPraga2 = (Button) findViewById(R.id.btnConfigPraga2);
            btnConfigPraga2.setVisibility(View.INVISIBLE);

        checkBoxPraga3 = (CheckBox) findViewById(R.id.checkBoxPraga3);
        btnConfigPraga3 = (Button) findViewById(R.id.btnConfigPraga3);
            btnConfigPraga3.setVisibility(View.INVISIBLE);

        checkBoxPraga4 = (CheckBox) findViewById(R.id.checkBoxPraga4);
        btnConfigPraga4 = (Button) findViewById(R.id.btnConfigPraga4);
            btnConfigPraga4.setVisibility(View.INVISIBLE);



    // Retrieve data from the SharedPreferences (which alerts where selected)
        SharedPreferences prefs = getSharedPreferences("DataSettingsState", Context.MODE_PRIVATE);
        alertasOn_saved = prefs.getString("alertasOn", null);
            switchAlertas.setChecked(Boolean.parseBoolean(alertasOn_saved));
            alertasOn = alertasOn_saved;
               /* if(alertasOn.equals("false")) {
                    btnConfigTemp.setVisibility(View.INVISIBLE);
                    btnConfigLum.setVisibility(View.INVISIBLE);
                    btnConfigHum.setVisibility(View.INVISIBLE);
                    btnConfigPluv.setVisibility(View.INVISIBLE);
                    btnConfigPraga1.setVisibility(View.INVISIBLE);
                    btnConfigPraga2.setVisibility(View.INVISIBLE);
                    btnConfigPraga3.setVisibility(View.INVISIBLE);
                    btnConfigPraga4.setVisibility(View.INVISIBLE);
                    btnConfigTemp.setVisibility(View.INVISIBLE);

                }
                else {
                    btnConfigTemp.setVisibility(View.VISIBLE);
                }*/
        boxTemp_saved = prefs.getString("boxTemp", null);
            checkBoxTemperatura.setChecked(Boolean.parseBoolean(boxTemp_saved));
            boxTemp = boxTemp_saved;
                if(boxTemp.equals("false")) {
                    btnConfigTemp.setVisibility(View.INVISIBLE);
                }
                else {
                    btnConfigTemp.setVisibility(View.VISIBLE);
                }
        boxLum_saved = prefs.getString("boxLum", null);
            checkBoxLuminosidade.setChecked(Boolean.parseBoolean(boxLum_saved));
            boxLum = boxLum_saved;
                if(boxLum.equals("false")) {
                    btnConfigLum.setVisibility(View.INVISIBLE);
                }
                else {
                    btnConfigLum.setVisibility(View.VISIBLE);
                }
        boxHum_saved = prefs.getString("boxHum", null);
            checkBoxHumidade.setChecked(Boolean.parseBoolean(boxHum_saved));
            boxHum = boxHum_saved;
                if(boxHum.equals("false")) {
                    btnConfigHum.setVisibility(View.INVISIBLE);
                }
                else {
                    btnConfigHum.setVisibility(View.VISIBLE);
                }
        boxPluv_saved = prefs.getString("boxPluv", null);
            checkBoxPluviosidade.setChecked(Boolean.parseBoolean(boxPluv_saved));
            boxPluv = boxPluv_saved;
                if(boxPluv.equals("false")) {
                    btnConfigPluv.setVisibility(View.INVISIBLE);
                }
                else {
                    btnConfigPluv.setVisibility(View.VISIBLE);
                }
        boxOutros_saved = prefs.getString("boxOutros", null);
            checkBoxOutros.setChecked(Boolean.parseBoolean(boxOutros_saved));
            boxOutros = boxOutros_saved;
        boxPraga1_saved = prefs.getString("boxPraga1", null);
            checkBoxPraga1.setChecked(Boolean.parseBoolean(boxPraga1_saved));
            boxPraga1 = boxPraga1_saved;
            if(boxPraga1.equals("false")) {
                btnConfigPraga1.setVisibility(View.INVISIBLE);
            }
            else {
                btnConfigPraga1.setVisibility(View.VISIBLE);
            }
        boxPraga2_saved = prefs.getString("boxPraga2", null);
            checkBoxPraga2.setChecked(Boolean.parseBoolean(boxPraga2_saved));
            boxPraga2 = boxPraga2_saved;
                if(boxPraga2.equals("false")) {
                    btnConfigPraga2.setVisibility(View.INVISIBLE);
                }
                else {
                    btnConfigPraga2.setVisibility(View.VISIBLE);
                }
        boxPraga3_saved = prefs.getString("boxPraga3", null);
            checkBoxPraga3.setChecked(Boolean.parseBoolean(boxPraga3_saved));
            boxPraga3 = boxPraga3_saved;
                if(boxPraga3.equals("false")) {
                    btnConfigPraga3.setVisibility(View.INVISIBLE);
                }
                else {
                    btnConfigPraga3.setVisibility(View.VISIBLE);
                }
        boxPraga4_saved = prefs.getString("boxPraga4", null);
            checkBoxPraga4.setChecked(Boolean.parseBoolean(boxPraga4_saved));
            boxPraga4 = boxPraga4_saved;
                if(boxPraga4.equals("false")) {
                    btnConfigPraga4.setVisibility(View.INVISIBLE);
                }
                else {
                    btnConfigPraga4.setVisibility(View.VISIBLE);
                }


        if(alertasOn_saved.matches("true")) {
            txt_off.setVisibility(View.INVISIBLE);
        } else if(alertasOn_saved.matches("false")) {
            txt_on.setVisibility(View.INVISIBLE);
        }


//////////////////// Switch and toggles

        // Switch activate/deactivate "Alertas"

            switchAlertas.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        Toast.makeText(getApplicationContext(), "A receção de Alertas foi ativada!", Toast.LENGTH_SHORT).show();

                            txt_on.setVisibility(View.VISIBLE);
                            txt_off.setVisibility(View.INVISIBLE);
                            alertasOn = "true";
                        // When the switch "Alertas" is ON, the alerts regarding the sensors are activated
                            checkBoxTemperatura.setChecked(true);
                            checkBoxLuminosidade.setChecked(true);
                            checkBoxHumidade.setChecked(true);
                            checkBoxPluviosidade.setChecked(true);
                            checkBoxOutros.setChecked(true);
                    }
                    else {
                        Toast.makeText(getApplicationContext(), "A receção de Alertas foi desativada!", Toast.LENGTH_SHORT).show();

                            txt_off.setVisibility(View.VISIBLE);
                            txt_on.setVisibility(View.INVISIBLE);
                            alertasOn = "false";
                        // When the switch "Alertas" is OFF, all the alerts are deactivated
                            checkBoxTemperatura.setChecked(false);
                            checkBoxLuminosidade.setChecked(false);
                            checkBoxHumidade.setChecked(false);
                            checkBoxPluviosidade.setChecked(false);
                            checkBoxOutros.setChecked(false);
                            checkBoxPraga1.setChecked(false);
                            checkBoxPraga2.setChecked(false);
                            checkBoxPraga3.setChecked(false);
                            checkBoxPraga4.setChecked(false);
                        }
                }
            });


        // Checkbox 1 - "Temperatura"
            checkBoxTemperatura.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(isChecked) {
                        btnConfigTemp.setVisibility(View.VISIBLE);
                        boxTemp = "true";
                    }
                    else {
                        btnConfigTemp.setVisibility(View.INVISIBLE);
                        boxTemp = "false";
                    }
                }
            });

        // Checkbox 2 - "Luminosidade"
            checkBoxLuminosidade.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(isChecked) {
                        btnConfigLum.setVisibility(View.VISIBLE);
                        boxLum = "true";
                    }
                    else {
                        btnConfigLum.setVisibility(View.INVISIBLE);
                        boxLum = "false";
                    }
                }
            });

        // Checkbox 3 - "Humidade"
            checkBoxHumidade.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(isChecked) {
                        btnConfigHum.setVisibility(View.VISIBLE);
                        boxHum = "true";
                    }
                    else {
                        btnConfigHum.setVisibility(View.INVISIBLE);
                        boxHum = "false";
                    }
                }
            });

        // Checkbox 4 - "Pluviosidade"
            checkBoxPluviosidade.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(isChecked) {
                        btnConfigPluv.setVisibility(View.VISIBLE);
                        boxPluv = "true";
                    }
                    else {
                        btnConfigPluv.setVisibility(View.INVISIBLE);
                        boxPluv = "false";
                    }
                }
            });

        // Checkbox 5 - "Outros"
            checkBoxOutros.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(isChecked) {
                        boxOutros = "true";
                    }
                    else {
                        boxOutros = "false";
                    }
                }
            });


        // Checkbox 6 - "Praga 1"
            checkBoxPraga1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(isChecked) {
                        btnConfigPraga1.setVisibility(View.VISIBLE);
                        boxPraga1 = "true";
                    }
                    else {
                        btnConfigPraga1.setVisibility(View.INVISIBLE);
                        boxPraga1 = "false";
                    }
                }
            });

        // Checkbox 7 - "Praga 2"
            checkBoxPraga2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(isChecked) {
                        btnConfigPraga2.setVisibility(View.VISIBLE);
                        boxPraga2 = "true";
                    }
                    else {
                        btnConfigPraga2.setVisibility(View.INVISIBLE);
                        boxPraga2 = "false";
                    }
                }
            });

        // Checkbox 8 - "Praga 3"
            checkBoxPraga3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(isChecked) {
                        btnConfigPraga3.setVisibility(View.VISIBLE);
                        boxPraga3 = "true";
                    }
                    else {
                        btnConfigPraga3.setVisibility(View.INVISIBLE);
                        boxPraga3 = "false";
                    }
                }
            });

        // Checkbox 9 - "Praga 4"
            checkBoxPraga4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(isChecked) {
                        btnConfigPraga4.setVisibility(View.VISIBLE);
                        boxPraga4 = "true";
                    }
                    else {
                        btnConfigPraga4.setVisibility(View.INVISIBLE);
                        boxPraga4 = "false";
                    }
                }
            });



////////////////////// Configuration buttons to inflate the Dialogs (DialogFragment)

        // Temperatura - button to show dialog with info/values
            btnConfigTemp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    FragmentManager manager = getFragmentManager();
                    dialogTemperatura myDialog = new dialogTemperatura();
                    myDialog.show(manager, "DataTemperatura"); // What for ??????
                    myDialog.setStyle(DialogFragment.STYLE_NO_TITLE, 0);

                }
            });

        // Luminosidade - button to show dialog with info/values
            btnConfigLum.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    FragmentManager manager = getFragmentManager();
                    dialogLuminosidade myDialog = new dialogLuminosidade();
                    myDialog.show(manager, "DataLuminosidade");
                    myDialog.setStyle(DialogFragment.STYLE_NO_TITLE, 0);

                }
            });

        // Humidade - button to show dialog with info/values
            btnConfigHum.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    FragmentManager manager = getFragmentManager();
                    dialogHumidade myDialog = new dialogHumidade();
                    myDialog.show(manager, "DataHumidade");
                    myDialog.setStyle(DialogFragment.STYLE_NO_TITLE, 0);

                }
            });

        // Pluviosidade - button to show dialog with info/values
            btnConfigPluv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    FragmentManager manager = getFragmentManager();
                    dialogPluviosidade myDialog = new dialogPluviosidade();
                    myDialog.show(manager, "DataPluviosidade");
                    myDialog.setStyle(DialogFragment.STYLE_NO_TITLE, 0);

                }
            });


        ////
        // Praga 1 - button to show dialog with info/values
            btnConfigPraga1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    FragmentManager manager = getFragmentManager();
                    dialogPraga1 myDialog = new dialogPraga1();
                    myDialog.show(manager, "DataPraga1");
                    myDialog.setStyle(DialogFragment.STYLE_NO_TITLE, 0);

                }
            });

        // Praga 2 - button to show dialog with info/values
            btnConfigPraga2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    FragmentManager manager = getFragmentManager();
                    dialogPraga2 myDialog = new dialogPraga2();
                    myDialog.show(manager, "DataPraga2");
                    myDialog.setStyle(DialogFragment.STYLE_NO_TITLE, 0);

                }
            });

        // Praga 3 - button to show dialog with info/values
            btnConfigPraga3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    FragmentManager manager = getFragmentManager();
                    dialogPraga3 myDialog = new dialogPraga3();
                    myDialog.show(manager, "DataPraga3");
                    myDialog.setStyle(DialogFragment.STYLE_NO_TITLE, 0);

                }
            });

        // Praga 4 - button to show dialog with info/values
            btnConfigPraga4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    FragmentManager manager = getFragmentManager();
                    dialogPraga4 myDialog = new dialogPraga4();
                    myDialog.show(manager, "DataPraga4");
                    myDialog.setStyle(DialogFragment.STYLE_NO_TITLE, 0);

                }
            });

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

        SharedPreferences prefs = getSharedPreferences("DataSettingsState", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("alertasOn", alertasOn);
        editor.putString("boxTemp", boxTemp);
        editor.putString("boxLum", boxLum);
        editor.putString("boxHum", boxHum);
        editor.putString("boxPluv", boxPluv);
        editor.putString("boxOutros", boxOutros);
        editor.putString("boxPraga1", boxPraga1);
        editor.putString("boxPraga2", boxPraga2);
        editor.putString("boxPraga3", boxPraga3);
        editor.putString("boxPraga4", boxPraga4);
        editor.commit();

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
