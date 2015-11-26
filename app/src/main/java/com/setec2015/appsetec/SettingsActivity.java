package com.setec2015.appsetec;

import android.app.AlertDialog;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.internal.VersionUtils;
import android.support.v7.widget.Toolbar;
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

public class SettingsActivity extends AppCompatActivity implements View.OnClickListener {

    private Toolbar mToolbar;

    private Switch switchAlertas;

    View view;
    LayoutInflater inflater;

    // UI objects from sensors' alerts
        private CheckBox checkBoxTemperatura;
        private EditText edt_minTemperatura, edt_maxTemperatura;
        private TextView textTil1;

        private CheckBox checkBoxLuminosidade;
        private EditText edt_minLuminosidade, edt_maxLuminosidade;
        private TextView textTil2;

        private CheckBox checkBoxHumidade;
        private EditText edt_minHumidade, edt_maxHumidade;
        private TextView textTil3;

        private CheckBox checkBoxPluviosidade;
        private EditText edt_minPluviosidade, edt_maxPluviosidade;
        private TextView textTil4;

        private CheckBox checkBoxOutros;

    // UI objects from diseases' alerts
        private CheckBox checkBoxPraga1;
        private Button btnConfigPraga1;
        private TextView txtPragaNome, txtPragaDescricao;
        private Button btnConfigPraga1_ok;
        private EditText edt_minTempPraga1, edt_maxTempPraga1;
        private EditText edt_minHumPraga1, edt_maxHumPraga1;
        private EditText edt_minPluvPraga1, edt_maxPluvPraga1;

        private CheckBox checkBoxPraga2;
        private Button btnConfigPraga2;

        private CheckBox checkBoxPraga3;
        private Button btnConfigPraga3;

        private CheckBox checkBoxPraga4;
        private Button btnConfigPraga4;

        private CheckBox checkBoxPraga5;
        private Button btnConfigPraga5;



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

        checkBoxTemperatura = (CheckBox) findViewById(R.id.checkBoxTemperatura);
        edt_minTemperatura = (EditText) findViewById(R.id.edt_minTemperatura);
        edt_maxTemperatura = (EditText) findViewById(R.id.edt_maxTemperatura);
        textTil1 = (TextView) findViewById(R.id.textTil1);

        checkBoxLuminosidade = (CheckBox) findViewById(R.id.checkBoxLuminosidade);
        edt_minLuminosidade = (EditText) findViewById(R.id.edt_minLuminosidade);
        edt_maxLuminosidade = (EditText) findViewById(R.id.edt_maxLuminosidade);
        textTil2 = (TextView) findViewById(R.id.textTil2);

        checkBoxHumidade = (CheckBox) findViewById(R.id.checkBoxHumidade);
        edt_minHumidade = (EditText) findViewById(R.id.edt_minHumidade);
        edt_maxHumidade = (EditText) findViewById(R.id.edt_maxHumidade);
        textTil3 = (TextView) findViewById(R.id.textTil3);

        checkBoxPluviosidade = (CheckBox) findViewById(R.id.checkBoxPluviosidade);
        edt_minPluviosidade = (EditText) findViewById(R.id.edt_minPluviosidade);
        edt_maxPluviosidade = (EditText) findViewById(R.id.edt_maxPluviosidade);
        textTil4 = (TextView) findViewById(R.id.textTil4);

        checkBoxOutros = (CheckBox) findViewById(R.id.checkBoxOutros);

        checkBoxPraga1 = (CheckBox) findViewById(R.id.checkBoxPraga1);
        btnConfigPraga1 = (Button) findViewById(R.id.btnConfigPraga1);
            btnConfigPraga1.setVisibility(View.INVISIBLE);
            btnConfigPraga1.setOnClickListener(this);

        checkBoxPraga2 = (CheckBox) findViewById(R.id.checkBoxPraga2);
        btnConfigPraga2 = (Button) findViewById(R.id.btnConfigPraga2);
            btnConfigPraga2.setVisibility(View.INVISIBLE);
            btnConfigPraga2.setOnClickListener(this);


        checkBoxPraga3 = (CheckBox) findViewById(R.id.checkBoxPraga3);
        btnConfigPraga3 = (Button) findViewById(R.id.btnConfigPraga3);
            btnConfigPraga3.setVisibility(View.INVISIBLE);
            //btnConfigPraga3.setOnClickListener(this);

        checkBoxPraga4 = (CheckBox) findViewById(R.id.checkBoxPraga4);
        btnConfigPraga4 = (Button) findViewById(R.id.btnConfigPraga4);
            btnConfigPraga4.setVisibility(View.INVISIBLE);
            //btnConfigPraga4.setOnClickListener(this);

        checkBoxPraga5 = (CheckBox) findViewById(R.id.checkBoxPraga5);
        btnConfigPraga5 = (Button) findViewById(R.id.btnConfigPraga5);
            btnConfigPraga5.setVisibility(View.INVISIBLE);
            //btnConfigPraga5.setOnClickListener(this);



        // Switch activate/deactivate "Alertas"
            switchAlertas.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        Toast.makeText(getApplicationContext(), "A receção de Alertas foi ativada!", Toast.LENGTH_SHORT).show();

                        // When the switch "Alertas" is ON, the alerts regarding the sensors are activated
                            checkBoxTemperatura.setChecked(true);
                            checkBoxLuminosidade.setChecked(true);
                            checkBoxHumidade.setChecked(true);
                            checkBoxPluviosidade.setChecked(true);
                            checkBoxOutros.setChecked(true);
                    }
                    else {
                        Toast.makeText(getApplicationContext(), "A receção de Alertas foi desativada!", Toast.LENGTH_SHORT).show();

                        // When the switch "Alertas" is OFF, all the alerts are deactivated
                            checkBoxTemperatura.setChecked(false); checkBoxLuminosidade.setChecked(false); checkBoxHumidade.setChecked(false);
                            checkBoxPluviosidade.setChecked(false); checkBoxOutros.setChecked(false);
                            checkBoxPraga1.setChecked(false); checkBoxPraga2.setChecked(false); checkBoxPraga3.setChecked(false);
                            checkBoxPraga4.setChecked(false); checkBoxPraga5.setChecked(false);
                        }
                }
            });


        // Checkbox 1 - "Temperatura"
            checkBoxTemperatura.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(isChecked) {
                        // Show "min" and "max" entry fields
                        edt_minTemperatura.setVisibility(View.VISIBLE);
                        textTil1.setVisibility(View.VISIBLE);
                        edt_maxTemperatura.setVisibility(View.VISIBLE);
                    }
                    else {
                        // Hide "min" and "max" entry fields
                        edt_minTemperatura.setVisibility(View.INVISIBLE);
                        textTil1.setVisibility(View.INVISIBLE);
                        edt_maxTemperatura.setVisibility(View.INVISIBLE);
                    }
                }
            });

        // Checkbox 2 - "Luminosidade"
        checkBoxLuminosidade.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    // Show "min" and "max" entry fields
                    edt_minLuminosidade.setVisibility(View.VISIBLE);
                    textTil2.setVisibility(View.VISIBLE);
                    edt_maxLuminosidade.setVisibility(View.VISIBLE);
                }
                else {
                    // Hide "min" and "max" entry fields
                    edt_minLuminosidade.setVisibility(View.INVISIBLE);
                    textTil2.setVisibility(View.INVISIBLE);
                    edt_maxLuminosidade.setVisibility(View.INVISIBLE);
                }
            }
        });

        // Checkbox 3 - "Humidade"
        checkBoxHumidade.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    // Show "min" and "max" entry fields
                    edt_minHumidade.setVisibility(View.VISIBLE);
                    textTil3.setVisibility(View.VISIBLE);
                    edt_maxHumidade.setVisibility(View.VISIBLE);
                }
                else {
                    // Hide "min" and "max" entry fields
                    edt_minHumidade.setVisibility(View.INVISIBLE);
                    textTil3.setVisibility(View.INVISIBLE);
                    edt_maxHumidade.setVisibility(View.INVISIBLE);
                }
            }
        });

        // Checkbox 4 - "Pluviosidade"
        checkBoxPluviosidade.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    // Show "min" and "max" entry fields
                    edt_minPluviosidade.setVisibility(View.VISIBLE);
                    textTil4.setVisibility(View.VISIBLE);
                    edt_maxPluviosidade.setVisibility(View.VISIBLE);
                }
                else {
                    // Hide "min" and "max" entry fields
                    edt_minPluviosidade.setVisibility(View.INVISIBLE);
                    textTil4.setVisibility(View.INVISIBLE);
                    edt_maxPluviosidade.setVisibility(View.INVISIBLE);
                }
            }
        });

        // Checkbox 5 - "Outros"
        checkBoxOutros.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    // Show "min" and "max" entry fields
                }
                else {
                    // Hide "min" and "max" entry fields
                }
            }
        });

        // Checkbox 6 - "Praga 1"
        checkBoxPraga1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    // Show button that enables to change Praga1's parameters
                    btnConfigPraga1.setVisibility(View.VISIBLE);
                }
                else {
                    // Hide button that enables to change Praga1's parameters
                    btnConfigPraga1.setVisibility(View.INVISIBLE);
                }
            }
        });

        // Checkbox 7 - "Praga 2"
        checkBoxPraga2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    // Show button that enables to change Praga1's parameters
                    btnConfigPraga2.setVisibility(View.VISIBLE);
                }
                else {
                    // Hide button that enables to change Praga1's parameters
                    btnConfigPraga2.setVisibility(View.INVISIBLE);
                }
            }
        });

        // Checkbox 8 - "Praga 3"
        checkBoxPraga3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    // Show button that enables to change Praga1's parameters
                    btnConfigPraga3.setVisibility(View.VISIBLE);
                }
                else {
                    // Hide button that enables to change Praga1's parameters
                    btnConfigPraga3.setVisibility(View.INVISIBLE);
                }
            }
        });

        // Checkbox 9 - "Praga 4"
        checkBoxPraga4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    // Show button that enables to change Praga1's parameters
                    btnConfigPraga4.setVisibility(View.VISIBLE);
                }
                else {
                    // Hide button that enables to change Praga1's parameters
                    btnConfigPraga4.setVisibility(View.INVISIBLE);
                }
            }
        });

        // Checkbox 10 - "Praga 5"
        checkBoxPraga5.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    // Show button that enables to change Praga1's parameters
                    btnConfigPraga5.setVisibility(View.VISIBLE);
                }
                else {
                    // Hide button that enables to change Praga1's parameters
                    btnConfigPraga5.setVisibility(View.INVISIBLE);
                }
            }
        });



    }


    @Override
    public void onClick(View view) {
        this.view = view;
        switch (view.getId()) {

        // Button to show dialog with info/values regarding "Praga 1"
            case R.id.btnConfigPraga1:
                view = inflater.inflate(R.layout.dialog_praga1, null);
                AlertDialog.Builder builder = new AlertDialog.Builder(SettingsActivity.this);

                btnConfigPraga1_ok = (Button) view.findViewById(R.id.btnConfigPraga1_ok);

                txtPragaNome = (TextView) view.findViewById(R.id.txtPragaNome);
                txtPragaNome.setText("1. Míldio (Plasmopora Viticola)");

                txtPragaDescricao = (TextView) view.findViewById(R.id.txtPragaDescricao);
                txtPragaDescricao.setText("A temperatura ideal para o desenvolvimento de Míldio fica entre 18 °C e 25 °C. O fungo necessita de água livre nos tecidos por um  período mínimo de 2 horas para haver infecção. A presença de água livre, seja proveniente de chuva, de orvalho, ou de gutação, é indispensável para haver a infecção, sendo a umidade relativa do ar acima de 98% necessária para haver a esporulação.");

                edt_minTemperatura = (EditText) view.findViewById(R.id.edt_minTemperatura);
                edt_maxTemperatura = (EditText) view.findViewById(R.id.edt_maxTemperatura);

                edt_minHumidade = (EditText) view.findViewById(R.id.edt_minHumidade);
                edt_maxHumidade = (EditText) view.findViewById(R.id.edt_maxHumidade);

                edt_minPluviosidade = (EditText) view.findViewById(R.id.edt_minPluviosidade);
                edt_maxPluviosidade = (EditText) view.findViewById(R.id.edt_maxPluviosidade);


                builder.setView(view);
                builder.show();
                break;

            // Button to show dialog with info/values regarding "Praga 2"
            case R.id.btnConfigPraga2:
                view = inflater.inflate(R.layout.dialog_praga1, null);
                AlertDialog.Builder builder2 = new AlertDialog.Builder(SettingsActivity.this);

                btnConfigPraga1_ok = (Button) view.findViewById(R.id.btnConfigPraga1_ok);

                txtPragaNome = (TextView) view.findViewById(R.id.txtPragaNome);
                txtPragaNome.setText("1. Míldio (Plasmopora Viticola)");

                txtPragaDescricao = (TextView) view.findViewById(R.id.txtPragaDescricao);
                txtPragaDescricao.setText("A temperatura ideal para o desenvolvimento de Míldio fica entre 18 °C e 25 °C. O fungo necessita de água livre nos tecidos por um  período mínimo de 2 horas para haver infecção. A presença de água livre, seja proveniente de chuva, de orvalho, ou de gutação, é indispensável para haver a infecção, sendo a umidade relativa do ar acima de 98% necessária para haver a esporulação.");

                edt_minTemperatura = (EditText) view.findViewById(R.id.edt_minTemperatura);
                edt_maxTemperatura = (EditText) view.findViewById(R.id.edt_maxTemperatura);

                edt_minHumidade = (EditText) view.findViewById(R.id.edt_minHumidade);
                edt_maxHumidade = (EditText) view.findViewById(R.id.edt_maxHumidade);

                edt_minPluviosidade = (EditText) view.findViewById(R.id.edt_minPluviosidade);
                edt_maxPluviosidade = (EditText) view.findViewById(R.id.edt_maxPluviosidade);


                builder2.setView(view);
                builder2.show();
                break;
        }
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
