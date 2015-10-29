// This is Fragment 3 of 4 => contains the functions of the tab "Histórico"

package com.setec2015.appsetec;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.LightingColorFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;


public class tabHistorico extends Fragment {

    private Button btnHum, btnTemp, btnSol, btnChuva, btnGPS;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab_historico, container, false);

        btnHum = (Button) view.findViewById(R.id.btnHum);

        btnTemp = (Button) view.findViewById(R.id.btnTemp);

        btnSol = (Button) view.findViewById(R.id.btnSol);

        btnChuva = (Button) view.findViewById(R.id.btnChuva);

        btnGPS = (Button) view.findViewById(R.id.btnGPS);



/*
        // Button "Zona 1"
        btnZona1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                txtZonaAtual.setText(zona1);
            }
        });

        // Button "Zona 2"
        btnZona2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                txtZonaAtual.setText(zona2);
            }
        });

        // Button "Zona 3"
        btnZona3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                txtZonaAtual.setText(zona3);
            }
        });
*/

        return view;
    }


    @Override
    public void onSaveInstanceState (Bundle outState) {
        super.onSaveInstanceState(outState);
    }
}