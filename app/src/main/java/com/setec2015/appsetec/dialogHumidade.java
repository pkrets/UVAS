package com.setec2015.appsetec;

import android.app.DialogFragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Krets on 29/11/2015.
 */
public class dialogHumidade extends DialogFragment implements View.OnClickListener {

    Button btnConfigHum_ok, btnConfigHum_cancel;
    TextView txtNomeHum, txtDescricaoHum;

    EditText edt_minHumidadeAr, edt_maxHumidadeAr;
    String minHumidadeAr, minHumidadeAr_saved, maxHumidadeAr, maxHumidadeAr_saved;

    EditText edt_minHumidadeSolo, edt_maxHumidadeSolo;
    String minHumidadeSolo, minHumidadeSolo_saved, maxHumidadeSolo, maxHumidadeSolo_saved;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_humidade, null);
        setCancelable(false);

        txtNomeHum = (TextView) view.findViewById(R.id.txtNomeHum);
        txtNomeHum.setText("Humidade (solo e ar)");

        txtDescricaoHum = (TextView) view.findViewById(R.id.txtDescricaoHum);
        txtDescricaoHum.setText("Preencha os seguintes campos com o intervalos de valores de Humidade do solo e Humidade relativa do ar (em %) que considera ideais. Se alguns dos sensores registar uma leitura de Humidade com valores inferiores ao 'mínimo' ou superiores ao 'máximo' estipulado, será emitido um Alerta.");

        btnConfigHum_ok = (Button) view.findViewById(R.id.btnConfigHum_ok);
        btnConfigHum_ok.setOnClickListener(this);

        btnConfigHum_cancel = (Button) view.findViewById(R.id.btnConfigHum_cancel);
        btnConfigHum_cancel.setOnClickListener(this);

        //

        SharedPreferences prefs = getActivity().getSharedPreferences("DataHumidade", Context.MODE_PRIVATE);
        minHumidadeAr_saved = prefs.getString("minHumidadeAr", "0");
        maxHumidadeAr_saved = prefs.getString("maxHumidadeAr", "0");
        minHumidadeSolo_saved = prefs.getString("minHumidadeSolo", "0");
        maxHumidadeSolo_saved = prefs.getString("maxHumidadeSolo", "0");

            edt_minHumidadeAr = (EditText) view.findViewById(R.id.edt_minHumidadeAr);
                edt_minHumidadeAr.setText(minHumidadeAr_saved);

            edt_maxHumidadeAr = (EditText) view.findViewById(R.id.edt_maxHumidadeAr);
                edt_maxHumidadeAr.setText(maxHumidadeAr_saved);

            edt_minHumidadeSolo = (EditText) view.findViewById(R.id.edt_minHumidadeSolo);
                edt_minHumidadeSolo.setText(minHumidadeSolo_saved);

            edt_maxHumidadeSolo = (EditText) view.findViewById(R.id.edt_maxHumidadeSolo);
                edt_maxHumidadeSolo.setText(maxHumidadeSolo_saved);

        return view;
    }




    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.btnConfigHum_ok) {

            minHumidadeAr = edt_minHumidadeAr.getText().toString();
            maxHumidadeAr = edt_maxHumidadeAr.getText().toString();
            minHumidadeSolo = edt_minHumidadeSolo.getText().toString();
            maxHumidadeSolo = edt_maxHumidadeSolo.getText().toString();

            SharedPreferences prefs = getActivity().getSharedPreferences("DataHumidade", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("minHumidadeAr", minHumidadeAr);
            editor.putString("maxHumidadeAr", maxHumidadeAr);
            editor.putString("minHumidadeSolo", minHumidadeSolo);
            editor.putString("maxHumidadeSolo", maxHumidadeSolo);
            editor.commit();

            dismiss();
        }
        else
        {
            dismiss();
        }
    }
}