// This is Fragment 2 of 4 => contains the functions of the tab "Alertas"

package com.setec2015.appsetec;

import android.content.Intent;
import android.graphics.LightingColorFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;


public class tabAlertas extends Fragment {

    private Button btnZona1, btnZona2, btnZona3;
    TextView txtZonaAtual;

    String zona1 = "   ZONA 1 - Norte";
    String zona2 = "   ZONA 2 - Este";
    String zona3 = "   ZONA 3 - Oeste";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab_alertas, container, false);

        btnZona1 = (Button) view.findViewById(R.id.btnZona1);
        //btnZona1.getBackground().setColorFilter(new LightingColorFilter(0x43A047, 0x00111111));

        btnZona2 = (Button) view.findViewById(R.id.btnZona2);
        //btnZona2.getBackground().setColorFilter(new LightingColorFilter(0x43A047, 0x00111111));

        btnZona3 = (Button) view.findViewById(R.id.btnZona3);
        //btnZona3.getBackground().setColorFilter(new LightingColorFilter(0x43A047, 0x00111111));

        txtZonaAtual = (TextView) view.findViewById(R.id.txtZonaAtual);


        // Button "Zona 1"
        btnZona1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                txtZonaAtual.setText(zona1);

                /*String textZona1 = zona1;
                tabSensores.a_updateText(textZona1);*/
            }
        });

        // Button "Zona 2"
        btnZona2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                txtZonaAtual.setText(zona2);

                /*String textZona2 = zona2;
                tabSensores.a_updateText(textZona2);*/
            }
        });

        // Button "Zona 3"
        btnZona3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                txtZonaAtual.setText(zona3);

                /*String textZona3 = zona3;
                tabSensores.a_updateText(textZona3);*/
            }
        });


        return view;
    }

    /*public static void b_updateText(String t){
        txtZonaAtual.setText(t);
    }*/

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }
}


