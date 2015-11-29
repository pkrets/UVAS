package com.setec2015.appsetec;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity implements Communicator{

    private Toolbar mToolbar;
    private TabLayout mTabLayout;
    private ViewPager mPager;
    private MyPagerAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAdapter = new MyPagerAdapter(getSupportFragmentManager());

        mToolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_action);


        mTabLayout = (TabLayout) findViewById(R.id.tab_layout);
        mTabLayout.setTabsFromPagerAdapter(mAdapter);

        mPager = (ViewPager) findViewById(R.id.pager);
        mPager.setAdapter(mAdapter);

        mTabLayout.setupWithViewPager(mPager);
        mPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        // Button -- Definições
        if (id == R.id.action_settings) {
            startActivity(new Intent(this, SettingsActivity.class));
        }

        // Button -- Ajuda
        if (id == R.id.action_help) {
            Toast.makeText(getApplicationContext(), "Botão > Ajuda < clicado!", Toast.LENGTH_SHORT).show();
        }

        // Button -- Terminar Sessão
        if (id == R.id.action_logout) {
            new AlertDialog.Builder(this)
                    .setMessage("Deseja terminar a sessão?")
                    .setCancelable(false)
                    .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            startActivity(new Intent(((Dialog) dialog).getContext(), StartActivity.class));
                        }
                    })
                    .setNegativeButton("Não", null)
                    .show();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setMessage("Deseja terminar a sessão?")
                .setCancelable(false)
                .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        startActivity(new Intent(((Dialog) dialog).getContext(), StartActivity.class));
                    }
                })
                .setNegativeButton("Não", null)
                .show();
    }

    @Override
    public void respond(String data) {

    }


    class MyPagerAdapter extends FragmentStatePagerAdapter {

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        // Returns the Fragment at a given position
        @Override
        public Fragment getItem(int i) {
            Fragment fragment = null;
            if (i == 0) {
                fragment = new tabSensores();
            }
            if (i == 1) {
                fragment = new tabAlertas();
            }
            if (i == 2) {
                fragment = new tabHistorico();
            }
            /*if (i == 3) {
                fragment = new tabAvaliacao();
            }*/
            return fragment;
        }



        // Returns the total number of pages or Fragments
        @Override
        public int getCount() {
            return 3;
        }

        // Show the name of each Tab in the Toolbar
        @Override
        public CharSequence getPageTitle(int position) {
            if (position == 0) {
                return "Sensores";
            }
            if (position == 1) {
                return "Alertas";
            }
            if (position == 2) {
                return "Histórico";
            }
            /*if (position == 3) {
                return "Avaliação";
            }*/
            return null;
        }


    }

}

