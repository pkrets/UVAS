package com.setec2015.appsetec;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

public class StartActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnLogin, btnRegistarRede;

    protected  static  final int TIMER_RUNTIME = 5000;
    protected boolean mbActive;
    protected ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(this);

        btnRegistarRede = (Button) findViewById(R.id.btnRegistarRede);
        btnRegistarRede.setOnClickListener(this);

        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_start, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        // Button "Iniciar Sessão"
        if (view.getId() == R.id.btnLogin) {

            final Thread timerThread = new Thread() {

                @Override
                public void run () {
                    mbActive = true;
                    try {
                        int waited = 0;
                        while(mbActive && (waited <TIMER_RUNTIME)) {
                            sleep(200);
                            if(mbActive) {
                                waited += 200;
                                updateProgress(waited);
                            }
                        }
                    } catch (InterruptedException e) {
                        // In case of error
                    } finally {
                        onContinue();
                    }
                }
            };
            timerThread.start();
        }

        // Button "Registar na Rede"
        if (view.getId() == R.id.btnRegistarRede) {
            //Toast.makeText(getApplicationContext(), "Botão > Registar na Rede < clicado!", Toast.LENGTH_LONG).show();
           startActivity(new Intent(this, SimpleXYPlotActivity.class));

        }
    }

    public void updateProgress(final int TimePassed) {
        if(mProgressBar != null) {
            final int progress = mProgressBar.getMax() * TimePassed / TIMER_RUNTIME;
            mProgressBar.setProgress(progress);
        }
    }

    public void onContinue() {
        startActivity(new Intent(this, MainActivity.class));
    }
}

