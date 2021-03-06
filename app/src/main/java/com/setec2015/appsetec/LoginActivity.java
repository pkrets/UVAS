package com.setec2015.appsetec;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;

public class LoginActivity extends AppCompatActivity {

    private Toolbar mToolbar;

    private EditText edt_email, edt_password;
    String email = "", password = "";

    View view;
    LayoutInflater inflater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


            try {
                Class.forName("android.os.AsyncTask");
                Log.i("ASYNCTASK ERROR LOGIN", "android.os.AsyncTask found.");
            } catch (ClassNotFoundException e) {
                Log.i("ASYNCTASK ERROR LOGIN", "Class android.os.AsyncTask not found!!!");
                e.printStackTrace();
            }


        mToolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_login);

        inflater = LoginActivity.this.getLayoutInflater();

        // Make available and display the "return Home button"
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        edt_email = (EditText) findViewById(R.id.edt_email);
        edt_password = (EditText) findViewById(R.id.edt_password);
    }



    // Button "Iniciar Sessão"
    public void LoginOnline(View view)
    {
        email = edt_email.getText().toString();
        password = edt_password.getText().toString();

        // Check if internet connection is available
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if(networkInfo != null && networkInfo.isConnected()) {
            if(!email.matches("") && !password.matches(""))
            {
                BackgroundOnlineDbTask2 backgroundOnlineDbTask2 = new BackgroundOnlineDbTask2(this);
                backgroundOnlineDbTask2.execute("login", email, password);
            }
            else if(email.matches("") && password.matches(""))
            {
                Toast.makeText(this, "Preencha os campos 'E-mail' e 'Password' antes de iniciar sessão.", Toast.LENGTH_SHORT).show();
            }
            else if(email.matches(""))
            {
                Toast.makeText(this, "Preencha o campo 'E-mail' antes de iniciar sessão.", Toast.LENGTH_SHORT).show();
            }
            else if(password.matches(""))
            {
                Toast.makeText(this, "Preencha o campo 'Password' antes de iniciar sessão.", Toast.LENGTH_SHORT).show();
            }
        }
        else {
            new AlertDialog.Builder(this)
                    .setTitle("Sem conexão à Internet")
                    .setIcon(R.mipmap.ic_aviso)
                    .setMessage("Para iniciar sessão online, necessita de uma conexão à internet por Wi-Fi ou 3G (dados móveis).\nDeseja ligar-se à Internet?")
                    .setCancelable(false)
                    .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            Intent enableInternetIntent = new Intent(Settings.ACTION_WIFI_SETTINGS);
                            startActivity(enableInternetIntent);
                        }
                    })
                    .setNegativeButton("Não", null)
                    .show();
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == android.R.id.home) {
            NavUtils.navigateUpFromSameTask(this);
        }


        return super.onOptionsItemSelected(item);
    }
}
