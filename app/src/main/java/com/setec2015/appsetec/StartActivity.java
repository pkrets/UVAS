package com.setec2015.appsetec;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class StartActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnEntrar, btnLoginOnline, btnBDhistorico, btnBDalertas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        if (getIntent().getBooleanExtra("EXIT", false)) {
            onDestroy();
        }

        btnEntrar = (Button) findViewById(R.id.btnEntrar);
        btnEntrar.setOnClickListener(this);

        btnLoginOnline = (Button) findViewById(R.id.btnLoginOnline);
        btnLoginOnline.setOnClickListener(this);

        btnBDalertas = (Button) findViewById(R.id.btnBDalertas);
        btnBDalertas.setOnClickListener(this);

        btnBDhistorico = (Button) findViewById(R.id.btnBDhistorico);
        btnBDhistorico.setOnClickListener(this);


        SharedPreferences prefs = this.getSharedPreferences("LoginStatus", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean("isLogged", false);
        editor.commit();

            Boolean log = prefs.getBoolean("isLogged", false);
            Log.i("LOGGED STATUS 1", String.valueOf(log));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        finish();
        System.exit(0);
    }

    @Override
    public void onClick(View view) {
        // Button "Iniciar Sessão"
        if (view.getId() == R.id.btnEntrar) {
            startActivity(new Intent(this, MainActivity.class));
        }

        // Button "Registar na Rede"
        if (view.getId() == R.id.btnLoginOnline) {
            startActivity(new Intent(this, LoginActivity.class));
        }

        // Button "BD Alertas"
        if (view.getId() == R.id.btnBDalertas) {
            startActivity(new Intent(this, dbSaveAlertaActivity.class));
        }

        // Button "BD Historico"
        if (view.getId() == R.id.btnBDhistorico) {
            startActivity(new Intent(this, dbSaveInfoActivity.class));
        }
    }


}

