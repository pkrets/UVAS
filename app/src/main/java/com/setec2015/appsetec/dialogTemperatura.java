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
 * Created by Krets on 28/11/2015.
 */
public class dialogTemperatura extends DialogFragment implements View.OnClickListener {

    Button btnConfigTemp_ok, btnConfigTemp_cancel;
    TextView txtNomeTemp, txtDescricaoTemp;

    EditText edt_minTemperatura, edt_maxTemperatura;
    String minTemperatura, minTemperatura_saved, maxTemperatura, maxTemperatura_saved;


    private static boolean RUN_ONCE = true;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_temperatura, null);
        setCancelable(false);

        txtNomeTemp = (TextView) view.findViewById(R.id.txtNomeTemp);
        txtNomeTemp.setText("Temperatura");

        txtDescricaoTemp = (TextView) view.findViewById(R.id.txtDescricaoTemp);
        txtDescricaoTemp.setText("Preencha os seguintes campos com o intervalo de valores de Temperatura (em ºC) que considera ideais. Se alguns dos sensores registar uma leitura de temperatura com valores inferiores ao 'mínimo' ou superiores ao 'máximo' estipulado, será emitido um Alerta.");

        btnConfigTemp_ok = (Button) view.findViewById(R.id.btnConfigTemp_ok);
        btnConfigTemp_ok.setOnClickListener(this);

        btnConfigTemp_cancel = (Button) view.findViewById(R.id.btnConfigTemp_cancel);
        btnConfigTemp_cancel.setOnClickListener(this);

        //

        SharedPreferences prefs = getActivity().getSharedPreferences("DataTemperatura", Context.MODE_PRIVATE);
        minTemperatura_saved = prefs.getString("minTemperatura", "0");
        maxTemperatura_saved = prefs.getString("maxTemperatura", "0");

        edt_minTemperatura = (EditText) view.findViewById(R.id.edt_minTemperatura);
        edt_minTemperatura.setText(minTemperatura_saved);

        edt_maxTemperatura = (EditText) view.findViewById(R.id.edt_maxTemperatura);
        edt_maxTemperatura.setText(maxTemperatura_saved);
        Toast.makeText(getActivity(), "Value retrieved = " + maxTemperatura_saved, Toast.LENGTH_SHORT).show();



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

                edt_minTemperatura.setText(null);
                edt_maxTemperatura.setText(null);
                Toast.makeText(getActivity(), "Dialog is running for the first time!", Toast.LENGTH_SHORT).show();
            }
        }



    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.btnConfigTemp_ok) {

            minTemperatura = edt_minTemperatura.getText().toString();
            maxTemperatura = edt_maxTemperatura.getText().toString();

            SharedPreferences prefs = getActivity().getSharedPreferences("DataTemperatura", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("minTemperatura", minTemperatura);
            editor.putString("maxTemperatura", maxTemperatura);
            editor.commit();

            Toast.makeText(getActivity(), "OK was clicked with value = " + maxTemperatura, Toast.LENGTH_SHORT).show();
            dismiss();
        }
        else
        {
            Toast.makeText(getActivity(), "CANCELAR was clicked", Toast.LENGTH_SHORT).show();
            dismiss();
        }
    }
}
