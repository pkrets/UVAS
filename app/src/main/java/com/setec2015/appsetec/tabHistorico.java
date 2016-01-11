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

    public String zonaAtual, zonaEscolhida;

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



        // Button "Zona 1"
        btnZona1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                txtZonaAtual.setText(zona1);
                zonaEscolhida = txtZonaAtual.getText().toString();

                BackgroundDbTask backgroundDbTask = new BackgroundDbTask(getContext());
                backgroundDbTask.execute("get_info_1");
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
            }
        });

        // Button "Delete Table" - from Local DB
        btnDeleteTable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                zonaEscolhida = txtZonaAtual.getText().toString();

                // Delete Table from "Histórico" - Zona 1
                    if (zonaEscolhida.matches(zona1)) {
                        new AlertDialog.Builder(getContext())
                                .setTitle(Html.fromHtml("Apagar os registos do " + "<u>" +"Histórico" +"</u>" + " da:"))
                                .setMessage(Html.fromHtml("<b><i>" + "&emsp&emsp&emsp&emsp" + zona1 + "</i></b>"))
                                .setIcon(R.mipmap.ic_delete)
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
                        new AlertDialog.Builder(getContext())
                                .setTitle(Html.fromHtml("Apagar os registos do " + "<u>" +"Histórico" +"</u>" + " da:"))
                                .setMessage(Html.fromHtml("<b><i>" + "&emsp&emsp&emsp&emsp" + zona2 + "</i></b>"))
                                .setIcon(R.mipmap.ic_delete)
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
                        new AlertDialog.Builder(getContext())
                                .setTitle(Html.fromHtml("Apagar os registos do " + "<u>" +"Histórico" +"</u>" + " da:"))
                                .setMessage(Html.fromHtml("<b><i>" + "&emsp&emsp&emsp&emsp" + zona1 + "</i></b>"))
                                .setIcon(R.mipmap.ic_delete)
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

