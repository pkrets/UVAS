// This is Fragment 1 of 4 => contains the functions of the tab "Sensores"

package com.setec2015.appsetec;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.LightingColorFilter;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.w3c.dom.Text;


public class tabSensores extends Fragment {

    private Button btnZona1, btnZona2, btnZona3;
    private ImageButton btnSatellite;
    private Button btnZoomIn;
    private Button btnZoomOut;
    TextView txtZonaAtual;
    TextView txtTemperaturaMin;

    String zona1 = "   ZONA 1 - Norte";
    String zona2 = "   ZONA 2 - Este";
    String zona3 = "   ZONA 3 - Oeste";

    float zoomLevel = 14;

    private SupportMapFragment fragment;
    private GoogleMap map;

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
        View view = inflater.inflate(R.layout.tab_sensores, container, false);

        btnZona1 = (Button) view.findViewById(R.id.btnZona1);
        btnZona2 = (Button) view.findViewById(R.id.btnZona2);
        btnZona3 = (Button) view.findViewById(R.id.btnZona3);

        btnSatellite = (ImageButton) view.findViewById(R.id.btnSatellite);
        btnZoomIn = (Button) view.findViewById(R.id.btnZoomIn);
        btnZoomOut = (Button) view.findViewById(R.id.btnZoomOut);

        txtZonaAtual = (TextView) view.findViewById(R.id.txtZonaAtual);
        txtZonaAtual.setText(zonaAtual);

        txtTemperaturaMin = (TextView) view.findViewById(R.id.txtTemperaturaMin);


        // Button "Zona 1"
        btnZona1.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                txtZonaAtual.setText(zona1);
                zonaEscolhida = txtZonaAtual.getText().toString();

                LatLng zona1Location = new LatLng(13.687140112679154, 100.53925868803263);
                map.addMarker(new MarkerOptions().position(zona1Location).title("Zona 1"));
                map.animateCamera(CameraUpdateFactory.newLatLngZoom(zona1Location, zoomLevel));
            }
      });

        // Button "Zona 2"
        btnZona2.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                txtZonaAtual.setText(zona2);
                zonaEscolhida = txtZonaAtual.getText().toString();

                LatLng zona2Location = new LatLng(13.682140112679154, 100.53525868803263);
                map.addMarker(new MarkerOptions().position(zona2Location).title("Zona 2"));
                map.animateCamera(CameraUpdateFactory.newLatLngZoom(zona2Location, zoomLevel));
            }
        });

        // Button "Zona 3"
        btnZona3.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                txtZonaAtual.setText(zona3);
                zonaEscolhida = txtZonaAtual.getText().toString();

                LatLng zona3Location = new LatLng(13.685140112679154, 100.53125868803263);
                map.addMarker(new MarkerOptions().position(zona3Location).title("Zona 3"));
                map.animateCamera(CameraUpdateFactory.newLatLngZoom(zona3Location, zoomLevel));
            }
        });


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


    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putString("zona", zonaEscolhida);
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





