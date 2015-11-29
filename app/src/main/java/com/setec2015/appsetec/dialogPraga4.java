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

    EditText edt_minHumPraga4, edt_maxHumPraga4;
    String minHumPraga4, minHumPraga4_saved, maxHumPraga4, maxHumPraga4_saved;

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
        minHumPraga4_saved = prefs.getString("minHumPraga4", "0");
        maxHumPraga4_saved = prefs.getString("maxHumPraga4", "0");
        minPluvPraga4_saved = prefs.getString("minPluvPraga4", "0");
        maxPluvPraga4_saved = prefs.getString("maxPluvPraga4", "0");

        edt_minTempPraga4 = (EditText) view.findViewById(R.id.edt_minTempPraga4);
        edt_minTempPraga4.setText(minTempPraga4_saved);
        edt_maxTempPraga4 = (EditText) view.findViewById(R.id.edt_maxTempPraga4);
        edt_maxTempPraga4.setText(maxTempPraga4_saved);

        edt_minHumPraga4 = (EditText) view.findViewById(R.id.edt_minHumPraga4);
        edt_minHumPraga4.setText(minHumPraga4_saved);
        edt_maxHumPraga4 = (EditText) view.findViewById(R.id.edt_maxHumPraga4);
        edt_maxHumPraga4.setText(maxHumPraga4_saved);

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
            edt_minHumPraga4.setText(null);
            edt_maxHumPraga4.setText(null);
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
            minHumPraga4 = edt_minHumPraga4.getText().toString();
            maxHumPraga4 = edt_maxHumPraga4.getText().toString();
            minPluvPraga4 = edt_minPluvPraga4.getText().toString();
            maxPluvPraga4 = edt_maxPluvPraga4.getText().toString();

            SharedPreferences prefs = getActivity().getSharedPreferences("DataPraga4", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("minTempPraga4", minTempPraga4);
            editor.putString("maxTempPraga4", maxTempPraga4);
            editor.putString("minHumPraga4", minHumPraga4);
            editor.putString("maxHumPraga4", maxHumPraga4);
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

