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
public class dialogPraga4 extends DialogFragment implements View.OnClickListener {

    Button btnConfigPraga4_ok, btnConfigPraga4_cancel;
    TextView txtPragaNome4, txtPragaDescricao4;

    EditText edt_minTempPraga4, edt_maxTempPraga4;
    String minTempPraga4, minTempPraga4_saved, maxTempPraga4, maxTempPraga4_saved;

    EditText edt_minHumArPraga4, edt_maxHumArPraga4;
    String minHumArPraga4, minHumArPraga4_saved, maxHumArPraga4, maxHumArPraga4_saved;

    EditText edt_minHumSoloPraga4, edt_maxHumSoloPraga4;
    String minHumSoloPraga4, minHumSoloPraga4_saved, maxHumSoloPraga4, maxHumSoloPraga4_saved;

    EditText edt_minPluvPraga4, edt_maxPluvPraga4;
    String minPluvPraga4, minPluvPraga4_saved, maxPluvPraga4, maxPluvPraga4_saved;


    private static boolean RUN_ONCE = true;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_praga4, null);
        setCancelable(false);

        txtPragaNome4 = (TextView) view.findViewById(R.id.txtPragaNome4);
        txtPragaNome4.setText("Escoriose da Videira (Phosmosis Viticola)");

        txtPragaDescricao4 = (TextView) view.findViewById(R.id.txtPragaDescricao4);
        txtPragaDescricao4.setText("Na Primavera, perto do abrolhamento, o fungo entra numa fase de intensa actividade. Os picnídeos libertam os seus esporos incolores, aglutinados em cirros (massa gelatinosa emergente dos picnídeos), através da acção da chuva e da humidade. As gotículas de chuva quebram os cirros e projectam os esporos por arrastamento para os gomos recentemente abrolhados. Os esporos germinam bem na água e podem contaminar os pâmpanose todos os órgãos verdes, caso estes fiquem humectados durante um período de tempo de humectação consecutivo superior a 7 horas. Etc ");

        btnConfigPraga4_ok = (Button) view.findViewById(R.id.btnConfigPraga4_ok);
        btnConfigPraga4_ok.setOnClickListener(this);

        btnConfigPraga4_cancel = (Button) view.findViewById(R.id.btnConfigPraga4_cancel);
        btnConfigPraga4_cancel.setOnClickListener(this);

        //

        SharedPreferences prefs = getActivity().getSharedPreferences("DataPraga4", Context.MODE_PRIVATE);
        minTempPraga4_saved = prefs.getString("minTempPraga4", "0");
        maxTempPraga4_saved = prefs.getString("maxTempPraga4", "0");
        minHumArPraga4_saved = prefs.getString("minHumArPraga4", "0");
        maxHumArPraga4_saved = prefs.getString("maxHumArPraga4", "0");
        minHumSoloPraga4_saved = prefs.getString("minHumSoloPraga4", "0");
        maxHumSoloPraga4_saved = prefs.getString("maxHumSoloPraga4", "0");
        minPluvPraga4_saved = prefs.getString("minPluvPraga4", "0");
        maxPluvPraga4_saved = prefs.getString("maxPluvPraga4", "0");

            edt_minTempPraga4 = (EditText) view.findViewById(R.id.edt_minTempPraga4);
                edt_minTempPraga4.setText(minTempPraga4_saved);
            edt_maxTempPraga4 = (EditText) view.findViewById(R.id.edt_maxTempPraga4);
                edt_maxTempPraga4.setText(maxTempPraga4_saved);

            edt_minHumArPraga4 = (EditText) view.findViewById(R.id.edt_minHumArPraga4);
                edt_minHumArPraga4.setText(minHumArPraga4_saved);
            edt_maxHumArPraga4 = (EditText) view.findViewById(R.id.edt_maxHumArPraga4);
                edt_maxHumArPraga4.setText(maxHumArPraga4_saved);

            edt_minHumSoloPraga4 = (EditText) view.findViewById(R.id.edt_minHumSoloPraga4);
                edt_minHumSoloPraga4.setText(minHumSoloPraga4_saved);
            edt_maxHumSoloPraga4 = (EditText) view.findViewById(R.id.edt_maxHumSoloPraga4);
                edt_maxHumSoloPraga4.setText(maxHumSoloPraga4_saved);

            edt_minPluvPraga4 = (EditText) view.findViewById(R.id.edt_minPluvPraga4);
                edt_minPluvPraga4.setText(minPluvPraga4_saved);
            edt_maxPluvPraga4 = (EditText) view.findViewById(R.id.edt_maxPluvPraga4);
                edt_maxPluvPraga4.setText(maxPluvPraga4_saved);



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

            edt_minTempPraga4.setText(null);
            edt_maxTempPraga4.setText(null);
            edt_minHumArPraga4.setText(null);
            edt_maxHumArPraga4.setText(null);
            edt_minHumSoloPraga4.setText(null);
            edt_maxHumSoloPraga4.setText(null);
            edt_minPluvPraga4.setText(null);
            edt_maxPluvPraga4.setText(null);

            Toast.makeText(getActivity(), "Dialog is running for the first time!", Toast.LENGTH_SHORT).show();
        }
    }



    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.btnConfigPraga4_ok) {

            minTempPraga4 = edt_minTempPraga4.getText().toString();
            maxTempPraga4 = edt_maxTempPraga4.getText().toString();
            minHumArPraga4 = edt_minHumArPraga4.getText().toString();
            maxHumArPraga4 = edt_maxHumArPraga4.getText().toString();
            minHumSoloPraga4 = edt_minHumSoloPraga4.getText().toString();
            maxHumSoloPraga4 = edt_maxHumSoloPraga4.getText().toString();
            minPluvPraga4 = edt_minPluvPraga4.getText().toString();
            maxPluvPraga4 = edt_maxPluvPraga4.getText().toString();

            SharedPreferences prefs = getActivity().getSharedPreferences("DataPraga4", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("minTempPraga4", minTempPraga4);
            editor.putString("maxTempPraga4", maxTempPraga4);
            editor.putString("minHumArPraga4", minHumArPraga4);
            editor.putString("maxHumArPraga4", maxHumArPraga4);
            editor.putString("minHumSoloPraga4", minHumSoloPraga4);
            editor.putString("maxHumSoloPraga4", maxHumSoloPraga4);
            editor.putString("minPluvPraga4", minPluvPraga4);
            editor.putString("maxPluvPraga4", maxPluvPraga4);
            editor.commit();

            dismiss();
        }
        else
        {
            dismiss();
        }
    }

}

