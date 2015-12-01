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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class tabHistorico extends Fragment {



    private Button btnZona1, btnZona2, btnZona3;
    TextView txtZonaAtual;

    String zona1 = "   ZONA 1 - Norte";
    String zona2 = "   ZONA 2 - Este";
    String zona3 = "   ZONA 3 - Oeste";

    public String zonaAtual, zonaEscolhida;

    private List<listaHistorico> myHistorico = new ArrayList<>();
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

        txtZonaAtual = (TextView) view.findViewById(R.id.txtZonaAtual);
        txtZonaAtual.setText(zonaAtual);



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


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // Show ListView and its componentes
        populateHistoricoList();
        populateHistoricoListView();
        // Action performed when a row in the list is clicked
        registerHistoricoClickCallBack();
    }


    //Populates the list of alerts received
    private void populateHistoricoList() {
        // Registo 1
        myHistorico.add(new listaHistorico(1, 20, 66, 60, 45, 50, "13 Jun '15"));
        // Registo 2
        myHistorico.add(new listaHistorico(2, 20, 69, 68, 46, 46, "14 Jun '15"));
        // Registo 3
        myHistorico.add(new listaHistorico(3, 21, 70, 61, 42, 53, "15 Jun '15"));
        // Registo 4
        myHistorico.add(new listaHistorico(4, 20, 76, 55, 51, 53, "16 Jun '15"));
        // Registo 5
        myHistorico.add(new listaHistorico(5, 22, 74, 59, 36, 26, "17 Jun '15"));
        // Registo 6
        myHistorico.add(new listaHistorico(6, 23, 70, 44, 32, 22, "18 Jun '15"));
        // Registo 7
        myHistorico.add(new listaHistorico(7, 23, 71, 52, 39, 35, "19 Jun '15"));
        // Registo 8
        myHistorico.add(new listaHistorico(8, 21, 72, 66, 40, 33, "20 Jun '15"));
    }


    // Populate ListView
    private void populateHistoricoListView() {
        ArrayAdapter<listaHistorico> adapter = new MyListAdapter();
        if (getView() != null) {
            getView().findViewById(R.id.historicoListView);
        }
        ListView list = (ListView) getView().findViewById(R.id.historicoListView);
        list.setAdapter(adapter);
    }

    private class MyListAdapter extends ArrayAdapter<listaHistorico> {
        public MyListAdapter() {
            super(getActivity(), R.layout.historico_list_item, myHistorico);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // Make sure we have a view to work with (may have been given null)
            View itemView = convertView;
            if(itemView == null) {
                itemView = getLayoutInflater(savedInstanceState).inflate(R.layout.historico_list_item, parent, false);
            }

            // Find the sensor to work with
            listaHistorico currentHistorico = myHistorico.get(position);

            // Fill the sensor's ID
            TextView item_txtID = (TextView) itemView.findViewById(R.id.item_txtID);
            item_txtID.setText("" + currentHistorico.getNumHistorico());

            // Fill the sensor1's value
            TextView item_txtSensor1 = (TextView) itemView.findViewById(R.id.item_txtSensor1);
            item_txtSensor1.setText("" + currentHistorico.getSensor1());

            // Fill the sensor2's value
            TextView item_txtSensor2 = (TextView) itemView.findViewById(R.id.item_txtSensor2);
            item_txtSensor2.setText("" + currentHistorico.getSensor2());

            // Fill the sensor3's value
            TextView item_txtSensor3 = (TextView) itemView.findViewById(R.id.item_txtSensor3);
            item_txtSensor3.setText("" + currentHistorico.getSensor3());

            // Fill the sensor4's value
            TextView item_txtSensor4 = (TextView) itemView.findViewById(R.id.item_txtSensor4);
            item_txtSensor4.setText("" + currentHistorico.getSensor4());

            // Fill the sensor4's value
            TextView item_txtSensor5 = (TextView) itemView.findViewById(R.id.item_txtSensor5);
            item_txtSensor5.setText("" + currentHistorico.getSensor5());

            // Fill the transmission's date
            TextView item_txtDate = (TextView) itemView.findViewById(R.id.item_txtDate);
            item_txtDate.setText(currentHistorico.getHistoricoDate());

            return itemView;
        }
    }


    // Registers onClick events inside the ListView
    private void registerHistoricoClickCallBack() {
        ListView list = (ListView) getView().findViewById(R.id.historicoListView);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View viewClicked, int position, long id) {
                listaHistorico clickedHistorico = myHistorico.get(position);
                String listMessage = "Histórico :: Item " + position + " :: Date " + clickedHistorico.getHistoricoDate();
                Toast.makeText(getActivity(), listMessage, Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putString("zona", zonaEscolhida);
    }



}

