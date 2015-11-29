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

    EditText edt_minHumidade, edt_maxHumidade;
    String minHumidade, minHumidade_saved, maxHumidade, maxHumidade_saved;


    private static boolean RUN_ONCE = true;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_humidade, null);
        setCancelable(false);

        txtNomeHum = (TextView) view.findViewById(R.id.txtNomeHum);
        txtNomeHum.setText("Huminosidade");

        txtDescricaoHum = (TextView) view.findViewById(R.id.txtDescricaoHum);
        txtDescricaoHum.setText("Preencha os seguintes campos com o intervalo de valores de Huminosidade (%) que considera ideais. Se alguns dos sensores registar uma leitura de Humidade com valores inferiores ao 'mínimo' ou superiores ao 'máximo' estipulado, será emitido um Alerta.");

        btnConfigHum_ok = (Button) view.findViewById(R.id.btnConfigHum_ok);
        btnConfigHum_ok.setOnClickListener(this);

        btnConfigHum_cancel = (Button) view.findViewById(R.id.btnConfigHum_cancel);
        btnConfigHum_cancel.setOnClickListener(this);

        //

        SharedPreferences prefs = getActivity().getSharedPreferences("DataHumidade", Context.MODE_PRIVATE);
        minHumidade_saved = prefs.getString("minHumidade", "0");
        maxHumidade_saved = prefs.getString("maxHumidade", "0");

            edt_minHumidade = (EditText) view.findViewById(R.id.edt_minHumidade);
            edt_minHumidade.setText(minHumidade_saved);

            edt_maxHumidade = (EditText) view.findViewById(R.id.edt_maxHumidade);
            edt_maxHumidade.setText(maxHumidade_saved);



        runOnce();
        return view;
    }


    /* >>>>> FOR NOW <<<<<
    If the Dialog is running for the first time (i.e. the App was opened and not closed),
    all the values stored in the AlertDialogs are deleted or set to default values.
    >> Still have to figure out how to delete the SharedPreferences file when the App is closed.*/
    private void runOnce() {
        if(RUN_ONCE) {
            RUN_ONCE = false;

            edt_minHumidade.setText(null);
            edt_maxHumidade.setText(null);
            Toast.makeText(getActivity(), "Dialog is running for the first time!", Toast.LENGTH_SHORT).show();
        }
    }



    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.btnConfigHum_ok) {

            minHumidade = edt_minHumidade.getText().toString();
            maxHumidade = edt_maxHumidade.getText().toString();

            SharedPreferences prefs = getActivity().getSharedPreferences("DataHumidade", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("minHumidade", minHumidade);
            editor.putString("maxHumidade", maxHumidade);
            editor.commit();

            dismiss();
        }
        else
        {
            dismiss();
        }
    }
}