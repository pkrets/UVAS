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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class tabAlertas extends Fragment {


    private Button btnZona1, btnZona2, btnZona3;
    TextView txtZonaAtual;

    String zona1 = "   ZONA 1 - Norte";
    String zona2 = "   ZONA 2 - Este";
    String zona3 = "   ZONA 3 - Oeste";

    public String zonaAtual, zonaEscolhida;

    private List<listaAlertas> myAlertas = new ArrayList<>();
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
        populateAlertasList();
        populateAlertasListView();
        // Action performed when a row in the list is clicked
        registerAlertasClickCallBack();
    }


    //Populates the list of alerts received
    private void populateAlertasList() {
        // Alerta 1
        myAlertas.add(new listaAlertas(1, "Humidade", "Ultrapassou valor máximo pretendido", R.mipmap.ic_humidade, 70, "13 Jun '15"));
        // Alerta 2
        myAlertas.add(new listaAlertas(2, "Temperatura", "Ultrapassou valor mínimo pretendido", R.mipmap.ic_temperatura, 18, "20 Jun '15"));
        // Alerta 3
        myAlertas.add(new listaAlertas(3, "Pluviosidade", "Ultrapassou valor mínimo pretendido", R.mipmap.ic_pluviosidade, 5, "29 Jun '15"));
        // Alerta 4
        myAlertas.add(new listaAlertas(4, "Temperatura", "Ultrapassou valor mínimo pretendido", R.mipmap.ic_temperatura, 19, "03 Jul '15"));
        // Alerta 5
        myAlertas.add(new listaAlertas(15, "Luminosidade", "Ultrapassou valor máximo pretendido", R.mipmap.ic_luminosidade, 78, "14 Jul '15"));
        // Alerta 6
        myAlertas.add(new listaAlertas(263, "Humidade", "Ultrapassou valor máximo pretendido", R.mipmap.ic_humidade, 65, "24 Jul '15"));
        // Alerta 7
        myAlertas.add(new listaAlertas(7, "Pluviosidade", "Ultrapassou valor mínimo pretendido", R.mipmap.ic_pluviosidade, 72, "26 Jul '15"));
        // Alerta 8
        myAlertas.add(new listaAlertas(8, "Temperatura", "Ultrapassou valor máximo pretendido", R.mipmap.ic_temperatura, 34, "12 Aug '15"));
    }


    // Populate ListView
    private void populateAlertasListView() {
        ArrayAdapter<listaAlertas> adapter = new MyListAdapter();
        if (getView() != null) {
            getView().findViewById(R.id.alertListView);
        }
        ListView list = (ListView) getView().findViewById(R.id.alertListView);
        list.setAdapter(adapter);
    }

    private class MyListAdapter extends ArrayAdapter<listaAlertas> {
        public MyListAdapter() {
            super(getActivity(), R.layout.alerta_list_item, myAlertas);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // Make sure we have a view to work with (may have been given null)
            View itemView = convertView;
            if(itemView == null) {
                itemView = getLayoutInflater(savedInstanceState).inflate(R.layout.alerta_list_item, parent, false);
            }

            // Find the sensor to work with
            listaAlertas currentAlerta = myAlertas.get(position);

            // Fill the sensor's ID
            TextView item_txtID = (TextView) itemView.findViewById(R.id.item_txtID);
            item_txtID.setText("" + currentAlerta.getNumAlert());

            // Fill the sensor's icon (imageView)
            ImageView item_icon = (ImageView) itemView.findViewById(R.id.item_icon);
            item_icon.setImageResource(currentAlerta.getIconID());

            // Fill the sensor's name
            TextView item_txtSensor = (TextView) itemView.findViewById(R.id.item_txtSensor);
            item_txtSensor.setText(currentAlerta.getSensor());

            // Fill the sensor's type/description
            TextView item_txtAlertType = (TextView) itemView.findViewById(R.id.item_txtAlertType);
            item_txtAlertType.setText(currentAlerta.getAlertType());

            // Fill the sensor's value
            TextView item_txtValue = (TextView) itemView.findViewById(R.id.item_txtValue);
            item_txtValue.setText("" + currentAlerta.getValue());

            // Fill the sensor's date
            TextView item_txtDate = (TextView) itemView.findViewById(R.id.item_txtDate);
            item_txtDate.setText(currentAlerta.getAlertDate());

            return itemView;
        }
    }


    // Registers onClick events inside the ListView
    private void registerAlertasClickCallBack() {
        ListView list = (ListView) getView().findViewById(R.id.alertListView);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View viewClicked, int position, long id) {
                listaAlertas clickedAlerta = myAlertas.get(position);
                String listMessage = "Item " + position + " :: Alerta de " + clickedAlerta.getSensor();
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


