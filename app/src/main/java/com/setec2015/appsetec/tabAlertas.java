// This is Fragment 2 of 4 => contains the functions of the tab "Alertas"

package com.setec2015.appsetec;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;


public class tabAlertas extends Fragment {


    private Button btnZona1, btnZona2, btnZona3;
    private ImageButton btnDeleteTable;
    private TextView txtZonaAtual;

    String zona1 = " ZONA 1 - Norte";
    String zona2 = " ZONA 2 - Este";
    String zona3 = " ZONA 3 - Oeste";

    String zonaAtual, zonaEscolhida;
    Boolean newAlerta1, newAlerta2, newAlerta3;

    Bundle savedInstanceState;

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
        this.savedInstanceState =savedInstanceState;
        View view = inflater.inflate(R.layout.tab_alertas, container, false);

        btnZona1 = (Button) view.findViewById(R.id.btnZona1);
        btnZona2 = (Button) view.findViewById(R.id.btnZona2);
        btnZona3 = (Button) view.findViewById(R.id.btnZona3);

        btnDeleteTable = (ImageButton) view.findViewById(R.id.btnDeleteTable);

        txtZonaAtual = (TextView) view.findViewById(R.id.txtZonaAtual);
        txtZonaAtual.setText(zonaAtual);


        // Display warning icons of new Alert
        SharedPreferences prefs = getActivity().getSharedPreferences("DataWarningsUI", Context.MODE_PRIVATE);
            newAlerta1 = prefs.getBoolean("newAlerta1", false);
            newAlerta2 = prefs.getBoolean("newAlerta2", false);
            newAlerta3 = prefs.getBoolean("newAlerta3", false);

            if(newAlerta1) {
                btnZona1.setCompoundDrawablesRelativeWithIntrinsicBounds(R.mipmap.ic_aviso, 0, 0, 0);
                btnZona1.setTextSize(13);
            }
            if(newAlerta2) {
                btnZona2.setCompoundDrawablesRelativeWithIntrinsicBounds(R.mipmap.ic_aviso, 0, 0, 0);
                btnZona2.setTextSize(13);
            }
            if(newAlerta3) {
                btnZona3.setCompoundDrawablesRelativeWithIntrinsicBounds(R.mipmap.ic_aviso, 0, 0, 0);
                btnZona3.setTextSize(13);
            }



        // Button "Zona 1"
        btnZona1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                txtZonaAtual.setText(zona1);
                zonaEscolhida = txtZonaAtual.getText().toString();

                BackgroundDbTask2 backgroundDbTask2 = new BackgroundDbTask2((getContext()));
                backgroundDbTask2.execute("get_alerta_1");

                // Remove warning icon of new Alert
                    btnZona1.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, 0, 0);
                    btnZona1.setTextSize(15);

                    SharedPreferences prefs = getActivity().getSharedPreferences("DataWarningsUI", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putBoolean("newAlerta1", false);
                    editor.commit();
            }
        });

        // Button "Zona 2"
        btnZona2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                txtZonaAtual.setText(zona2);
                zonaEscolhida = txtZonaAtual.getText().toString();

                BackgroundDbTask2 backgroundDbTask2 = new BackgroundDbTask2((getContext()));
                backgroundDbTask2.execute("get_alerta_2");

                // Remove warning icon of new Alert
                    btnZona2.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, 0, 0);
                    btnZona2.setTextSize(15);

                    SharedPreferences prefs = getActivity().getSharedPreferences("DataWarningsUI", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putBoolean("newAlerta2", false);
                    editor.commit();
            }
        });

        // Button "Zona 3"
        btnZona3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                txtZonaAtual.setText(zona3);
                zonaEscolhida = txtZonaAtual.getText().toString();

                BackgroundDbTask2 backgroundDbTask2 = new BackgroundDbTask2((getContext()));
                backgroundDbTask2.execute("get_alerta_3");

                // Remove warning icon of new Alert
                    btnZona3.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, 0, 0);
                    btnZona3.setTextSize(15);

                    SharedPreferences prefs = getActivity().getSharedPreferences("DataWarningsUI", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putBoolean("newAlerta3", false);
                    editor.commit();
            }
        });

        // Button "Delete Table" - from Local DB
        btnDeleteTable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                zonaEscolhida = txtZonaAtual.getText().toString();

                // Delete Table from Alertas - Zona 1
                if (zonaEscolhida.matches(zona1)) {
                    new android.app.AlertDialog.Builder(getContext())
                            .setTitle("Limpar registos de Alertas")
                            .setIcon(R.mipmap.ic_delete)
                            .setMessage("Deseja apagar os registos de Alertas de" + zona1 + " ?\nEsta ação é irreversível.")
                            .setCancelable(false)
                            .setPositiveButton("Apagar", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {

                                    // Delete ALL ROWS in the table from Zona 1 - "alertas1_table"
                                    BackgroundDbTask2 backgroundDbTask2_A = new BackgroundDbTask2(getContext());
                                    backgroundDbTask2_A.execute("delete_alerta_1");

                                    // Update UI: GET ALL ROWS in the table from Zona 1 - "alertas1_table"
                                    BackgroundDbTask2 backgroundDbTask2_B = new BackgroundDbTask2(getContext());
                                    backgroundDbTask2_B.execute("get_alerta_1");
                                }
                            })
                            .setNegativeButton("Não", null)
                            .show();
                }
                // Delete Table from Alertas - Zona 2
                else if (zonaEscolhida.matches(zona2)) {
                    new android.app.AlertDialog.Builder(getContext())
                            .setTitle("Limpar registos de Alertas")
                            .setIcon(R.mipmap.ic_delete)
                            .setMessage("Deseja apagar os registos de Alertas de" + zona2 + " ?\nEsta ação é irreversível.")
                            .setCancelable(false)
                            .setPositiveButton("Apagar", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {

                                    // Delete ALL ROWS in the table from Zona 2 - "alertas2_table"
                                    BackgroundDbTask2 backgroundDbTask2_A = new BackgroundDbTask2(getContext());
                                    backgroundDbTask2_A.execute("delete_alerta_2");

                                    // Update UI: GET ALL ROWS in the table from Zona 2 - "alertas2_table"
                                    BackgroundDbTask2 backgroundDbTask2_B = new BackgroundDbTask2(getContext());
                                    backgroundDbTask2_B.execute("get_alerta_2");
                                }
                            })
                            .setNegativeButton("Não", null)
                            .show();
                }
                // Delete Table from Alertas - Zona 3
                else if (zonaEscolhida.matches(zona3)) {
                    new android.app.AlertDialog.Builder(getContext())
                            .setTitle("Limpar registos de Alertas")
                            .setIcon(R.mipmap.ic_delete)
                            .setMessage("Deseja apagar os registos de Alertas de" + zona3 + " ?\nEsta ação é irreversível.")
                            .setCancelable(false)
                            .setPositiveButton("Apagar", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {

                                    // Delete ALL ROWS in the table from Zona 3 - "alertas3_table"
                                    BackgroundDbTask2 backgroundDbTask2_A = new BackgroundDbTask2(getContext());
                                    backgroundDbTask2_A.execute("delete_alerta_3");

                                    // Update UI: GET ALL ROWS in the table from Zona 3 - "alertas3_table"
                                    BackgroundDbTask2 backgroundDbTask2_B = new BackgroundDbTask2(getContext());
                                    backgroundDbTask2_B.execute("get_alerta_3");
                                }
                            })
                            .setNegativeButton("Não", null)
                            .show();
                }
                // Display warning message if no table (zona) is selected
                else
                {
                    Toast.makeText(getContext(), "Para apagar registos de 'Alertas', selecione primeiro uma zona.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }



    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putString("zona", zonaEscolhida);
    }



}


