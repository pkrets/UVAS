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
public class dialogLuminosidade extends DialogFragment implements View.OnClickListener {

    Button btnConfigLum_ok, btnConfigLum_cancel;
    TextView txtNomeLum, txtDescricaoLum;

    EditText edt_minLuminosidade, edt_maxLuminosidade;
    String minLuminosidade, minLuminosidade_saved, maxLuminosidade, maxLuminosidade_saved;


    private static boolean RUN_ONCE = true;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_luminosidade, null);
        setCancelable(false);

        txtNomeLum = (TextView) view.findViewById(R.id.txtNomeLum);
        txtNomeLum.setText("Luminosidade");

        txtDescricaoLum = (TextView) view.findViewById(R.id.txtDescricaoLum);
        txtDescricaoLum.setText("Preencha os seguintes campos com o intervalo de valores de Luminosidade (em lm/m^3) que considera ideais. Se alguns dos sensores registar uma leitura de Luminosidade com valores inferiores ao 'mínimo' ou superiores ao 'máximo' estipulado, será emitido um Alerta.");

        btnConfigLum_ok = (Button) view.findViewById(R.id.btnConfigLum_ok);
        btnConfigLum_ok.setOnClickListener(this);

        btnConfigLum_cancel = (Button) view.findViewById(R.id.btnConfigLum_cancel);
        btnConfigLum_cancel.setOnClickListener(this);

        //

        SharedPreferences prefs = getActivity().getSharedPreferences("DataLuminosidade", Context.MODE_PRIVATE);
        minLuminosidade_saved = prefs.getString("minLuminosidade", "0");
        maxLuminosidade_saved = prefs.getString("maxLuminosidade", "0");

        edt_minLuminosidade = (EditText) view.findViewById(R.id.edt_minLuminosidade);
        edt_minLuminosidade.setText(minLuminosidade_saved);

        edt_maxLuminosidade = (EditText) view.findViewById(R.id.edt_maxLuminosidade);
        edt_maxLuminosidade.setText(maxLuminosidade_saved);



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

            edt_minLuminosidade.setText(null);
            edt_maxLuminosidade.setText(null);
            Toast.makeText(getActivity(), "Dialog is running for the first time!", Toast.LENGTH_SHORT).show();
        }
    }



    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.btnConfigLum_ok) {

            minLuminosidade = edt_minLuminosidade.getText().toString();
            maxLuminosidade = edt_maxLuminosidade.getText().toString();

            SharedPreferences prefs = getActivity().getSharedPreferences("DataLuminosidade", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("minLuminosidade", minLuminosidade);
            editor.putString("maxLuminosidade", maxLuminosidade);
            editor.commit();

            dismiss();
        }
        else
        {
            dismiss();
        }
    }
}
