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

    public String zonaAtual, zonaEscolhida;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null) {
            // Restore last state for checked position.
            zonaAtual = savedInstanceState.getString("zona");
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab_alertas, container, false);

        btnZona1 = (Button) view.findViewById(R.id.btnZona1);
        btnZona2 = (Button) view.findViewById(R.id.btnZona2);
        btnZona3 = (Button) view.findViewById(R.id.btnZona3);

        txtZonaAtual = (TextView) view.findViewById(R.id.txtZonaAtual);
        txtZonaAtual.setText(zonaAtual);

        /*
            String myTag = getTag();

            ((MainActivity)getActivity()).setTabFragmentB(myTag);
        */


        // Button "Zona 1"
        btnZona1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                txtZonaAtual.setText(zona1);
                zonaEscolhida = txtZonaAtual.getText().toString();
            }
        });

        // Button "Zona 2"
        btnZona2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                txtZonaAtual.setText(zona2);
                zonaEscolhida = txtZonaAtual.getText().toString();
            }
        });

        // Button "Zona 3"
        btnZona3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                txtZonaAtual.setText(zona3);
                zonaEscolhida = txtZonaAtual.getText().toString();
            }
        });


        return view;
    }

    /*
        public void b_updateText(String t) {
            txtZonaAtual.setText(t);
        }
    */

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putString("zona", zonaEscolhida);
    }


}


