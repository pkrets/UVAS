// This is Fragment 1 of 4 => contains the functions of the tab "Sensores"

package com.setec2015.appsetec;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;




public class tabSensores extends Fragment {

    private Button btnZona1, btnZona2, btnZona3;
    private ImageButton btnSatellite;
    private Button btnZoomIn;
    private Button btnZoomOut;
    private TextView txtZonaAtual, txt_newTemp, txt_newLum, txt_newHumSolo, txt_newHumAr, txt_newPluv, txt_newData;

    String lastTemp, lastLum, lastHumSolo, lastHumAr, lastPluv, lastData;
    Boolean newValueSensor1, newValueSensor2, newValueSensor3;

    String zona1 = "Zona 1";
    String zona2 = "Zona 2";
    String zona3 = "Zona 3";

    float zoomLevel = 14;

    private SupportMapFragment fragment;
    private GoogleMap map;

    String zonaBundle, zonaEscolhida;
    String tempBundle, lumBundle, humSoloBundle, humArBundle, pluvBundle, dataBundle;

    Bundle savedInstanceState;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null) {
            // Restore last state for checked position.
            zonaBundle = savedInstanceState.getString("zona");
            tempBundle = savedInstanceState.getString("temp");
            lumBundle = savedInstanceState.getString("lum");
            humSoloBundle = savedInstanceState.getString("humS");
            humArBundle = savedInstanceState.getString("humA");
            pluvBundle = savedInstanceState.getString("pluv");
            dataBundle = savedInstanceState.getString("data");
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.savedInstanceState =savedInstanceState;
        View view = inflater.inflate(R.layout.tab_sensores, container, false);

        btnZona1 = (Button) view.findViewById(R.id.btnZona1);
        btnZona2 = (Button) view.findViewById(R.id.btnZona2);
        btnZona3 = (Button) view.findViewById(R.id.btnZona3);

        btnSatellite = (ImageButton) view.findViewById(R.id.btnSatellite);
        btnZoomIn = (Button) view.findViewById(R.id.btnZoomIn);
        btnZoomOut = (Button) view.findViewById(R.id.btnZoomOut);

        txtZonaAtual = (TextView) view.findViewById(R.id.txtZonaAtual);
            txtZonaAtual.setText(zonaBundle);

        txt_newTemp = (TextView) view.findViewById(R.id.txt_newTemp);
        txt_newLum = (TextView) view.findViewById(R.id.txt_newLum);
        txt_newHumSolo = (TextView) view.findViewById(R.id.txt_newHumSolo);
        txt_newHumAr = (TextView) view.findViewById(R.id.txt_newHumAr);
        txt_newPluv = (TextView) view.findViewById(R.id.txt_newPluv);
        txt_newData = (TextView) view.findViewById(R.id.txt_newData);

        populateLastSensorValue(tempBundle, lumBundle, humSoloBundle, humArBundle, pluvBundle, dataBundle);


        // Display warning icons of new Value
            SharedPreferences prefs = getActivity().getSharedPreferences("DataWarningsUI", Context.MODE_PRIVATE);
            newValueSensor1 = prefs.getBoolean("newValueSensor1", false);
            newValueSensor2 = prefs.getBoolean("newValueSensor2", false);
            newValueSensor3 = prefs.getBoolean("newValueSensor3", false);

            if(newValueSensor1) {
                btnZona1.setCompoundDrawablesRelativeWithIntrinsicBounds(R.mipmap.ic_aviso, 0, 0, 0);
                btnZona1.setTextSize(13);
            }
            if(newValueSensor2) {
                btnZona2.setCompoundDrawablesRelativeWithIntrinsicBounds(R.mipmap.ic_aviso, 0, 0, 0);
                btnZona2.setTextSize(13);
            }
            if(newValueSensor3) {
                btnZona3.setCompoundDrawablesRelativeWithIntrinsicBounds(R.mipmap.ic_aviso, 0, 0, 0);
                btnZona3.setTextSize(13);
            }

/////////// Map Buttons ///////////

        // Change Map Type: normal view <-> satellite view
            btnSatellite.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (map.getMapType() == GoogleMap.MAP_TYPE_NORMAL) {
                        map.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                    } else
                        map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                }
            });

        // Perform "Zoom In" in the map
            btnZoomIn.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    map.animateCamera(CameraUpdateFactory.zoomIn());
                }
            });

        // Perform "Zoom Out" in the map
            btnZoomOut.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    map.animateCamera(CameraUpdateFactory.zoomOut());
                }
            });


/////////// Zonas & BLE Buttons ///////////

        // Button "Zona 1"
        btnZona1.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                txtZonaAtual.setText(zona1);
                zonaEscolhida = txtZonaAtual.getText().toString();
                    btnZona1.setText(Html.fromHtml("<u><b>"+zona1+"<b></u>"));
                    btnZona2.setText(zona2);
                    btnZona3.setText(zona3);

                LatLng zona1Location = new LatLng(13.687140112679154, 100.53925868803263);
                map.addMarker(new MarkerOptions().position(zona1Location).title("Zona 1"));
                map.animateCamera(CameraUpdateFactory.newLatLngZoom(zona1Location, zoomLevel));

                BackgroundDbTask backgroundDbTask = new BackgroundDbTask(getContext());
                backgroundDbTask.execute("last_info_1");

                // Get values from SharedPreferences
                    SharedPreferences prefs = getActivity().getSharedPreferences("DataLastValues", Context.MODE_PRIVATE);
                        lastTemp = prefs.getString("lastTemp1", "- -");
                        lastLum = prefs.getString("lastLum1", "- -");
                        lastHumSolo = prefs.getString("lastHumSolo1", "- -");
                        lastHumAr = prefs.getString("lastHumAr1", "- -");
                        lastPluv = prefs.getString("lastPluv1", "- -");
                        lastData = prefs.getString("lastData1", "- -");
                    populateLastSensorValue(lastTemp, lastLum, lastHumSolo, lastHumAr, lastPluv, lastData);

                // Remove warning icon of new Alert
                    btnZona1.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, 0, 0);
                    btnZona1.setTextSize(15);

                    SharedPreferences pref = getActivity().getSharedPreferences("DataWarningsUI", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putBoolean("newValueSensor1", false);
                    editor.commit();
            }
        });

        // Button "Zona 2"
        btnZona2.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                txtZonaAtual.setText(zona2);
                zonaEscolhida = txtZonaAtual.getText().toString();
                    btnZona2.setText(Html.fromHtml("<u><b>"+zona2+"<b></u>"));
                    btnZona1.setText(zona1);
                    btnZona3.setText(zona3);

                LatLng zona2Location = new LatLng(13.682140112679154, 100.53525868803263);
                map.addMarker(new MarkerOptions().position(zona2Location).title("Zona 2"));
                map.animateCamera(CameraUpdateFactory.newLatLngZoom(zona2Location, zoomLevel));

                BackgroundDbTask backgroundDbTask = new BackgroundDbTask(getContext());
                backgroundDbTask.execute("last_info_2");

                // Get values from SharedPreferences
                    SharedPreferences prefs = getActivity().getSharedPreferences("DataLastValues", Context.MODE_PRIVATE);
                        lastTemp = prefs.getString("lastTemp2", "- -");
                        lastLum = prefs.getString("lastLum2", "- -");
                        lastHumSolo = prefs.getString("lastHumSolo2", "- -");
                        lastHumAr = prefs.getString("lastHumAr2", "- -");
                        lastPluv = prefs.getString("lastPluv2", "- -");
                        lastData = prefs.getString("lastData2", "- -");
                    populateLastSensorValue(lastTemp, lastLum, lastHumSolo, lastHumAr, lastPluv, lastData);

                // Remove warning icon of new Alert
                    btnZona2.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, 0, 0);
                    btnZona2.setTextSize(15);

                    SharedPreferences pref = getActivity().getSharedPreferences("DataWarningsUI", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putBoolean("newValueSensor2", false);
                    editor.commit();
            }
        });

        // Button "Zona 3"
        btnZona3.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                txtZonaAtual.setText(zona3);
                zonaEscolhida = txtZonaAtual.getText().toString();
                    btnZona3.setText(Html.fromHtml("<u><b>"+zona3+"<b></u>"));
                    btnZona2.setText(zona2);
                    btnZona1.setText(zona1);

                LatLng zona3Location = new LatLng(13.685140112679154, 100.53125868803263);
                map.addMarker(new MarkerOptions().position(zona3Location).title("Zona 3"));
                map.animateCamera(CameraUpdateFactory.newLatLngZoom(zona3Location, zoomLevel));

                BackgroundDbTask backgroundDbTask = new BackgroundDbTask(getContext());
                backgroundDbTask.execute("last_info_3");

                // Get values from SharedPreferences
                    SharedPreferences prefs = getActivity().getSharedPreferences("DataLastValues", Context.MODE_PRIVATE);
                        lastTemp = prefs.getString("lastTemp3", "- -");
                        lastLum = prefs.getString("lastLum3", "- -");
                        lastHumSolo = prefs.getString("lastHumSolo3", "- -");
                        lastHumAr = prefs.getString("lastHumAr3", "- -");
                        lastPluv = prefs.getString("lastPluv3", "- -");
                        lastData = prefs.getString("lastData3", "- -");
                    populateLastSensorValue(lastTemp, lastLum, lastHumSolo, lastHumAr, lastPluv, lastData);

                // Remove warning icon of new Alert
                    btnZona3.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, 0, 0);
                    btnZona3.setTextSize(15);

                    SharedPreferences pref = getActivity().getSharedPreferences("DataWarningsUI", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putBoolean("newValueSensor3", false);
                    editor.commit();
            }
        });




        return view;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        FragmentManager fm = getChildFragmentManager();
        fragment = (SupportMapFragment) fm.findFragmentById(R.id.map);
        if (fragment == null) {
            fragment = SupportMapFragment.newInstance();
            fm.beginTransaction().replace(R.id.map, fragment).commit();
        }

    }




    public void populateLastSensorValue(String temp, String lum, String humSolo, String humAr, String pluv, String data) {
        txt_newTemp.setText(temp);
        txt_newLum.setText(lum);
        txt_newHumSolo.setText(humSolo);
        txt_newHumAr.setText(humAr);
        txt_newPluv.setText(pluv);
        txt_newData.setText(data);
    }



    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
            savedInstanceState.putString("zona", zonaEscolhida);
            savedInstanceState.putString("temp", lastTemp);
            savedInstanceState.putString("lum", lastLum);
            savedInstanceState.putString("humS", lastHumSolo);
            savedInstanceState.putString("humA", lastHumAr);
            savedInstanceState.putString("pluv", lastPluv);
            savedInstanceState.putString("data", lastData);
    }


    @Override
    public void onResume() {
        super.onResume();
        if (map == null) {
            map = fragment.getMap();
        }
        // Enables the location of my current position via GPS
        map.setMyLocationEnabled(true);
    }

}





