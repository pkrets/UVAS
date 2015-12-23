package com.setec2015.appsetec;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class StartActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnLogin, btnRegistarRede, btnBDhistorico, btnBDalertas;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(this);

        btnRegistarRede = (Button) findViewById(R.id.btnRegistarRede);
        btnRegistarRede.setOnClickListener(this);

        btnBDalertas = (Button) findViewById(R.id.btnBDalertas);
        btnBDalertas.setOnClickListener(this);

        btnBDhistorico = (Button) findViewById(R.id.btnBDhistorico);
        btnBDhistorico.setOnClickListener(this);

    }


    @Override
    public void onClick(View view) {
        // Button "Iniciar Sessão"
        if (view.getId() == R.id.btnLogin) {
            startActivity(new Intent(this, MainActivity.class));
        }

        // Button "Registar na Rede"
        if (view.getId() == R.id.btnRegistarRede) {
            Toast.makeText(getApplicationContext(), "Botão > Registar na Rede < clicado!", Toast.LENGTH_LONG).show();

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

