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
public class dialogPraga3 extends DialogFragment implements View.OnClickListener {

    Button btnConfigPraga3_ok, btnConfigPraga3_cancel;
    TextView txtPragaNome3, txtPragaDescricao3;

    EditText edt_minTempPraga3, edt_maxTempPraga3;
    String minTempPraga3, minTempPraga3_saved, maxTempPraga3, maxTempPraga3_saved;

    EditText edt_minHumArPraga3, edt_maxHumArPraga3;
    String minHumArPraga3, minHumArPraga3_saved, maxHumArPraga3, maxHumArPraga3_saved;

    EditText edt_minHumSoloPraga3, edt_maxHumSoloPraga3;
    String minHumSoloPraga3, minHumSoloPraga3_saved, maxHumSoloPraga3, maxHumSoloPraga3_saved;

    EditText edt_minPluvPraga3, edt_maxPluvPraga3;
    String minPluvPraga3, minPluvPraga3_saved, maxPluvPraga3, maxPluvPraga3_saved;


    private static boolean RUN_ONCE = true;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_praga3, null);
        setCancelable(false);

        txtPragaNome3 = (TextView) view.findViewById(R.id.txtPragaNome3);
        txtPragaNome3.setText("Podridão Cinzenta (Botrytis Cinerea)");

        txtPragaDescricao3 = (TextView) view.findViewById(R.id.txtPragaDescricao3);
        txtPragaDescricao3.setText("Na maioria dos casos, podridões severas dos cachos estão associadas com humidade relativa alta (acima dos 95%) e temperaturas entre 15 °C e 25 °C durante a maturação da uva. Vários fatores explicam as diferenças na suscetibilidade entre as cultivares de uva ao Botrytis. Cultivares de uvas tintas contém compostos que inibem em parte o fungo, sendo menos atacadas que as brancas, a espessura da película também é um fator determinante na suscetibilidade da cultivar.");

        btnConfigPraga3_ok = (Button) view.findViewById(R.id.btnConfigPraga3_ok);
        btnConfigPraga3_ok.setOnClickListener(this);

        btnConfigPraga3_cancel = (Button) view.findViewById(R.id.btnConfigPraga3_cancel);
        btnConfigPraga3_cancel.setOnClickListener(this);

        //

        SharedPreferences prefs = getActivity().getSharedPreferences("DataPraga3", Context.MODE_PRIVATE);
        minTempPraga3_saved = prefs.getString("minTempPraga3", "0");
        maxTempPraga3_saved = prefs.getString("maxTempPraga3", "0");
        minHumArPraga3_saved = prefs.getString("minHumArPraga3", "0");
        maxHumArPraga3_saved = prefs.getString("maxHumArPraga3", "0");
        minHumSoloPraga3_saved = prefs.getString("minHumSoloPraga3", "0");
        maxHumSoloPraga3_saved = prefs.getString("maxHumSoloPraga3", "0");
        minPluvPraga3_saved = prefs.getString("minPluvPraga3", "0");
        maxPluvPraga3_saved = prefs.getString("maxPluvPraga3", "0");

            edt_minTempPraga3 = (EditText) view.findViewById(R.id.edt_minTempPraga3);
                edt_minTempPraga3.setText(minTempPraga3_saved);
            edt_maxTempPraga3 = (EditText) view.findViewById(R.id.edt_maxTempPraga3);
                edt_maxTempPraga3.setText(maxTempPraga3_saved);

            edt_minHumArPraga3 = (EditText) view.findViewById(R.id.edt_minHumArPraga3);
                edt_minHumArPraga3.setText(minHumArPraga3_saved);
            edt_maxHumArPraga3 = (EditText) view.findViewById(R.id.edt_maxHumArPraga3);
                edt_maxHumArPraga3.setText(maxHumArPraga3_saved);

            edt_minHumSoloPraga3 = (EditText) view.findViewById(R.id.edt_minHumSoloPraga3);
                edt_minHumSoloPraga3.setText(minHumSoloPraga3_saved);
            edt_maxHumSoloPraga3 = (EditText) view.findViewById(R.id.edt_maxHumSoloPraga3);
                edt_maxHumSoloPraga3.setText(maxHumSoloPraga3_saved);

            edt_minPluvPraga3 = (EditText) view.findViewById(R.id.edt_minPluvPraga3);
                edt_minPluvPraga3.setText(minPluvPraga3_saved);
            edt_maxPluvPraga3 = (EditText) view.findViewById(R.id.edt_maxPluvPraga3);
                edt_maxPluvPraga3.setText(maxPluvPraga3_saved);



        runOnce();
        return view;
    }

    /* >>>>> FOR NOW <<<<<
    If the SettingsActivity is running for the first time (i.e. the App was opened and not closed),
    all the values stored in the AlertDialogs are deleted or set to default values.
    >> Still have to figure out how to delete the SharedPreferences file when the App is closed.*/
    private void runOnce() {
        if(RUN_ONCE) {
            RUN_ONCE = false;

            edt_minTempPraga3.setText(null);
            edt_maxTempPraga3.setText(null);
            edt_minHumArPraga3.setText(null);
            edt_maxHumArPraga3.setText(null);
            edt_minHumSoloPraga3.setText(null);
            edt_maxHumSoloPraga3.setText(null);
            edt_minPluvPraga3.setText(null);
            edt_maxPluvPraga3.setText(null);

            Toast.makeText(getActivity(), "Dialog is running for the first time!", Toast.LENGTH_SHORT).show();
        }
    }



    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.btnConfigPraga3_ok) {

            minTempPraga3 = edt_minTempPraga3.getText().toString();
            maxTempPraga3 = edt_maxTempPraga3.getText().toString();
            minHumArPraga3 = edt_minHumArPraga3.getText().toString();
            maxHumArPraga3 = edt_maxHumArPraga3.getText().toString();
            minHumSoloPraga3 = edt_minHumSoloPraga3.getText().toString();
            maxHumSoloPraga3 = edt_maxHumSoloPraga3.getText().toString();
            minPluvPraga3 = edt_minPluvPraga3.getText().toString();
            maxPluvPraga3 = edt_maxPluvPraga3.getText().toString();

            SharedPreferences prefs = getActivity().getSharedPreferences("DataPraga3", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("minTempPraga3", minTempPraga3);
            editor.putString("maxTempPraga3", maxTempPraga3);
            editor.putString("minHumArPraga3", minHumArPraga3);
            editor.putString("maxHumArPraga3", maxHumArPraga3);
            editor.putString("minHumSoloPraga3", minHumSoloPraga3);
            editor.putString("maxHumSoloPraga3", maxHumSoloPraga3);
            editor.putString("minPluvPraga3", minPluvPraga3);
            editor.putString("maxPluvPraga3", maxPluvPraga3);
            editor.commit();

            dismiss();
        }
        else
        {
            dismiss();
        }
    }

}
