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

    EditText edt_minPluviosidade, edt_maxPluviosidade;
    String minPluviosidade, minPluviosidade_saved, maxPluviosidade, maxPluviosidade_saved;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_pluviosidade, null);
        setCancelable(false);

        txtNomePluv = (TextView) view.findViewById(R.id.txtNomePluv);
        txtNomePluv.setText("Pluviosidade");

        txtDescricaoPluv = (TextView) view.findViewById(R.id.txtDescricaoPluv);
        txtDescricaoPluv.setText("Preencha os seguintes campos com o intervalo de valores de Pluviosidade (mm^3/h) que considera ideais. Se alguns dos sensores registar uma leitura de Pluviosidade com valores inferiores ao 'mínimo' ou superiores ao 'máximo' estipulado, será emitido um Alerta.");

        btnConfigPluv_ok = (Button) view.findViewById(R.id.btnConfigPluv_ok);
        btnConfigPluv_ok.setOnClickListener(this);

        btnConfigPluv_cancel = (Button) view.findViewById(R.id.btnConfigPluv_cancel);
        btnConfigPluv_cancel.setOnClickListener(this);

        //

        SharedPreferences prefs = getActivity().getSharedPreferences("DataPluviosidade", Context.MODE_PRIVATE);
        minPluviosidade_saved = prefs.getString("minPluviosidade", "0");
        maxPluviosidade_saved = prefs.getString("maxPluviosidade", "0");

            edt_minPluviosidade = (EditText) view.findViewById(R.id.edt_minPluviosidade);
                edt_minPluviosidade.setText(minPluviosidade_saved);

            edt_maxPluviosidade = (EditText) view.findViewById(R.id.edt_maxPluviosidade);
                edt_maxPluviosidade.setText(maxPluviosidade_saved);



        return view;
    }



    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.btnConfigPluv_ok) {

            minPluviosidade = edt_minPluviosidade.getText().toString();
            maxPluviosidade = edt_maxPluviosidade.getText().toString();

            SharedPreferences prefs = getActivity().getSharedPreferences("DataPluviosidade", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("minPluviosidade", minPluviosidade);
            editor.putString("maxPluviosidade", maxPluviosidade);
            editor.commit();

            dismiss();
        }
        else
        {
            dismiss();
        }
    }
}