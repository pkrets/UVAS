package com.setec2015.appsetec;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class StartActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnEntrar, btnLoginOnline, btnBDhistorico, btnBDalertas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        btnEntrar = (Button) findViewById(R.id.btnEntrar);
        btnEntrar.setOnClickListener(this);

        btnLoginOnline = (Button) findViewById(R.id.btnLoginOnline);
        btnLoginOnline.setOnClickListener(this);

        btnBDalertas = (Button) findViewById(R.id.btnBDalertas);
        btnBDalertas.setOnClickListener(this);

        btnBDhistorico = (Button) findViewById(R.id.btnBDhistorico);
        btnBDhistorico.setOnClickListener(this);

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

