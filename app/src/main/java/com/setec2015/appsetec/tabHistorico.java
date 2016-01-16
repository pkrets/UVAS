// This is Fragment 3 of 4 => contains the functions of the tab "Histórico"

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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


public class tabHistorico extends Fragment {


    private Button btnZona1, btnZona2, btnZona3;
    private ImageButton btnDeleteTable;
    private TextView txtZonaAtual;

    String zona1 = " ZONA 1 - Norte";
    String zona2 = " ZONA 2 - Este";
    String zona3 = " ZONA 3 - Oeste";

    String zonaAtual, zonaEscolhida;
    Boolean newValueHist1, newValueHist2, newValueHist3;

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
        View view = inflater.inflate(R.layout.tab_historico, container, false);

        btnZona1 = (Button) view.findViewById(R.id.btnZona1);
        btnZona2 = (Button) view.findViewById(R.id.btnZona2);
        btnZona3 = (Button) view.findViewById(R.id.btnZona3);

        btnDeleteTable = (ImageButton) view.findViewById(R.id.btnDeleteTable);

        txtZonaAtual = (TextView) view.findViewById(R.id.txtZonaAtual);
            txtZonaAtual.setText(zonaAtual);


        // Display warning icons of new Alert
        SharedPreferences prefs = getActivity().getSharedPreferences("DataWarningsUI", Context.MODE_PRIVATE);
            newValueHist1 = prefs.getBoolean("newValueHist1", false);
            newValueHist2 = prefs.getBoolean("newValueHist2", false);
            newValueHist3 = prefs.getBoolean("newValueHist3", false);

            if(newValueHist1) {
                btnZona1.setCompoundDrawablesRelativeWithIntrinsicBounds(R.mipmap.ic_aviso, 0, 0, 0);
                btnZona1.setTextSize(13);
            }
            if(newValueHist2) {
                btnZona2.setCompoundDrawablesRelativeWithIntrinsicBounds(R.mipmap.ic_aviso, 0, 0, 0);
                btnZona2.setTextSize(13);
            }
            if(newValueHist3) {
                btnZona3.setCompoundDrawablesRelativeWithIntrinsicBounds(R.mipmap.ic_aviso, 0, 0, 0);
                btnZona3.setTextSize(13);
            }




        // Button "Zona 1"
        btnZona1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                txtZonaAtual.setText(zona1);
                zonaEscolhida = txtZonaAtual.getText().toString();

                BackgroundDbTask backgroundDbTask = new BackgroundDbTask(getContext());
                backgroundDbTask.execute("get_info_1");

                // Remove warning icon of new Alert
                    btnZona1.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, 0, 0);
                    btnZona1.setTextSize(15);

                    SharedPreferences prefs = getActivity().getSharedPreferences("DataWarningsUI", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putBoolean("newValueHist1", false);
                    editor.commit();
            }
        });

        // Button "Zona 2"
        btnZona2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                txtZonaAtual.setText(zona2);
                zonaEscolhida = txtZonaAtual.getText().toString();

                BackgroundDbTask backgroundDbTask = new BackgroundDbTask(getContext());
                backgroundDbTask.execute("get_info_2");

                // Remove warning icon of new Alert
                    btnZona2.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, 0, 0);
                    btnZona2.setTextSize(15);

                    SharedPreferences prefs = getActivity().getSharedPreferences("DataWarningsUI", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putBoolean("newValueHist2", false);
                    editor.commit();
            }
        });

        // Button "Zona 3"
        btnZona3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                txtZonaAtual.setText(zona3);
                zonaEscolhida = txtZonaAtual.getText().toString();

                BackgroundDbTask backgroundDbTask = new BackgroundDbTask(getContext());
                backgroundDbTask.execute("get_info_3");

                // Remove warning icon of new Alert
                    btnZona3.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, 0, 0);
                    btnZona3.setTextSize(15);

                    SharedPreferences prefs = getActivity().getSharedPreferences("DataWarningsUI", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putBoolean("newValueHist3", false);
                    editor.commit();
            }
        });

        // Button "Delete Table" - from Local DB
        btnDeleteTable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                zonaEscolhida = txtZonaAtual.getText().toString();

                // Delete Table from "Histórico" - Zona 1
                    if (zonaEscolhida.matches(zona1)) {
                        new android.app.AlertDialog.Builder(getContext())
                                .setTitle("Limpar registos do Histórico")
                                .setIcon(R.mipmap.ic_delete)
                                .setMessage("Deseja apagar os registos do Histórico de" + zona1 + " ?\nEsta ação é irreversível.")
                                .setCancelable(false)
                                .setPositiveButton("Apagar", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {

                                        // Delete ALL ROWS in the table from Zona 1 - "pandlet1_table"
                                        BackgroundDbTask backgroundDbTask_A = new BackgroundDbTask(getContext());
                                        backgroundDbTask_A.execute("delete_info_1");

                                        // Update UI: GET ALL ROWS in the table from Zona 1 - "pandlet1_table"
                                        BackgroundDbTask backgroundDbTask_B = new BackgroundDbTask(getContext());
                                        backgroundDbTask_B.execute("get_info_1");
                                    }
                                })
                                .setNegativeButton("Não", null)
                                .show();
                    }
                // Delete Table from "Histórico" - Zona 2
                    else if (zonaEscolhida.matches(zona2)) {
                        new android.app.AlertDialog.Builder(getContext())
                                .setTitle("Limpar registos do Histórico")
                                .setIcon(R.mipmap.ic_delete)
                                .setMessage("Deseja apagar os registos do Histórico de" + zona2 + " ?\nEsta ação é irreversível.")
                                .setCancelable(false)
                                .setPositiveButton("Apagar", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {

                                        // Delete ALL ROWS in the table from Zona 2 - "pandlet2_table"
                                        BackgroundDbTask backgroundDbTask_A = new BackgroundDbTask(getContext());
                                        backgroundDbTask_A.execute("delete_info_2");

                                        // Update UI: GET ALL ROWS in the table from Zona 2 - "pandlet2_table"
                                        BackgroundDbTask backgroundDbTask_B = new BackgroundDbTask(getContext());
                                        backgroundDbTask_B.execute("get_info_2");
                                    }
                                })
                                .setNegativeButton("Não", null)
                                .show();
                    }
                // Delete Table from "Histórico" - Zona 3
                    else if (zonaEscolhida.matches(zona3)) {
                        new android.app.AlertDialog.Builder(getContext())
                                .setTitle("Limpar registos do Histórico")
                                .setIcon(R.mipmap.ic_delete)
                                .setMessage("Deseja apagar os registos do Histórico de" + zona3 + " ?\nEsta ação é irreversível.")
                                .setCancelable(false)
                                .setPositiveButton("Apagar", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {

                                        // Delete ALL ROWS in the table from Zona 3 - "pandlet3_table"
                                        BackgroundDbTask backgroundDbTask_A = new BackgroundDbTask(getContext());
                                        backgroundDbTask_A.execute("delete_info_3");

                                        // Update UI: GET ALL ROWS in the table from Zona 3 - "pandlet3_table"
                                        BackgroundDbTask backgroundDbTask_B = new BackgroundDbTask(getContext());
                                        backgroundDbTask_B.execute("get_info_3");
                                    }
                                })
                                .setNegativeButton("Não", null)
                                .show();
                    }
                // Display warning message if no table (zona) is selected
                    else
                    {
                        Toast.makeText(getContext(), "Para apagar registos do 'Histórico', selecione primeiro uma zona.", Toast.LENGTH_SHORT).show();
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

