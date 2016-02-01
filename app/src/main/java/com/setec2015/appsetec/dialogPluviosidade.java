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
public class dialogPluviosidade extends DialogFragment implements View.OnClickListener {

    Button btnConfigPluv_ok, btnConfigPluv_cancel;
    TextView txtNomePluv, txtDescricaoPluv;

    EditText edt_maxHorasPluv;
    String maxHorasPluviosidade, maxHorasPluviosidade_saved;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_pluviosidade, null);
        setCancelable(false);

        txtNomePluv = (TextView) view.findViewById(R.id.txtNomePluv);
        txtNomePluv.setText("Pluviosidade");

        txtDescricaoPluv = (TextView) view.findViewById(R.id.txtDescricaoPluv);
        txtDescricaoPluv.setText("Preencha o seguinte campo com o número máximo de horas de chuva que considera aceitável. Se alguns dos sensores registar leituras de Pluviosidade durante um período de tempo superior ao 'máximo' estipulado, será emitido um Alerta.");

        btnConfigPluv_ok = (Button) view.findViewById(R.id.btnConfigPluv_ok);
        btnConfigPluv_ok.setOnClickListener(this);

        btnConfigPluv_cancel = (Button) view.findViewById(R.id.btnConfigPluv_cancel);
        btnConfigPluv_cancel.setOnClickListener(this);

        //

        SharedPreferences prefs = getActivity().getSharedPreferences("DataPluviosidade", Context.MODE_PRIVATE);
        maxHorasPluviosidade_saved = prefs.getString("maxHorasPluviosidade", "0");

            edt_maxHorasPluv = (EditText) view.findViewById(R.id.edt_maxHorasPluv);
                edt_maxHorasPluv.setText(maxHorasPluviosidade_saved);

        return view;
    }



    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.btnConfigPluv_ok) {

            maxHorasPluviosidade = edt_maxHorasPluv.getText().toString();

            SharedPreferences prefs = getActivity().getSharedPreferences("DataPluviosidade", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("maxHorasPluviosidade", maxHorasPluviosidade);
            editor.commit();

            dismiss();
        }
        else
        {
            dismiss();
        }
    }
}