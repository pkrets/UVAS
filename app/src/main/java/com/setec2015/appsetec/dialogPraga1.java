package com.setec2015.appsetec;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
public class dialogPraga1 extends DialogFragment implements View.OnClickListener {

    Button btnConfigPraga1_ok, btnConfigPraga1_cancel;
    TextView txtPragaNome1, txtPragaDescricao1;

    EditText edt_minTempPraga1, edt_maxTempPraga1;
    String minTempPraga1, minTempPraga1_saved, maxTempPraga1, maxTempPraga1_saved;

    EditText edt_minHumArPraga1, edt_maxHumArPraga1;
    String minHumArPraga1, minHumArPraga1_saved, maxHumArPraga1, maxHumArPraga1_saved;

    EditText edt_minHumSoloPraga1, edt_maxHumSoloPraga1;
    String minHumSoloPraga1, minHumSoloPraga1_saved, maxHumSoloPraga1, maxHumSoloPraga1_saved;

    EditText edt_minPluvPraga1, edt_maxPluvPraga1;
    String minPluvPraga1, minPluvPraga1_saved, maxPluvPraga1, maxPluvPraga1_saved;

    DataSettings dataSettings;

    private static boolean RUN_ONCE = true;


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        dataSettings = (DataSettings) activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_praga1, null);
        setCancelable(false);

        txtPragaNome1 = (TextView) view.findViewById(R.id.txtPragaNome1);
        txtPragaNome1.setText("Míldio (Plasmopora Viticola)");

        txtPragaDescricao1 = (TextView) view.findViewById(R.id.txtPragaDescricao1);
        txtPragaDescricao1.setText("A temperatura ideal para o desenvolvimento do Míldio é acima dos 10ºC. O fungo necessita de água livre nos tecidos por um período mínimo de 2 horas para haver infecção. A presença de água livre, seja proveniente de chuva, de orvalho, ou de gutação e a ausença total de luz, são indispensáveis para haver a infecção. Sendo a humidade relativa do ar acima de 95% e escuridão por um periodo de 4 horas necessárias para haver a esporulação. A infecção do fungo nas folhas se dá pelos estômatos presentes na face inferior, estômatos e pedicelos durante a floração e inicio da frutificação e pedicelos quando a uva já está mais desenvolvida.");

        btnConfigPraga1_ok = (Button) view.findViewById(R.id.btnConfigPraga1_ok);
        btnConfigPraga1_ok.setOnClickListener(this);

        btnConfigPraga1_cancel = (Button) view.findViewById(R.id.btnConfigPraga1_cancel);
        btnConfigPraga1_cancel.setOnClickListener(this);

        //

        SharedPreferences prefs = getActivity().getSharedPreferences("DataPraga1", Context.MODE_PRIVATE);
        minTempPraga1_saved = prefs.getString("minTempPraga1", "0");
        maxTempPraga1_saved = prefs.getString("maxTempPraga1", "0");
        minHumArPraga1_saved = prefs.getString("minHumArPraga1", "0");
        maxHumArPraga1_saved = prefs.getString("maxHumArPraga1", "0");
        minHumSoloPraga1_saved = prefs.getString("minHumSoloPraga1", "0");
        maxHumSoloPraga1_saved = prefs.getString("maxHumSoloPraga1", "0");
        minPluvPraga1_saved = prefs.getString("minPluvPraga1", "0");
        maxPluvPraga1_saved = prefs.getString("maxPluvPraga1", "0");

            edt_minTempPraga1 = (EditText) view.findViewById(R.id.edt_minTempPraga1);
                edt_minTempPraga1.setText(minTempPraga1_saved);
            edt_maxTempPraga1 = (EditText) view.findViewById(R.id.edt_maxTempPraga1);
                edt_maxTempPraga1.setText(maxTempPraga1_saved);

            edt_minHumArPraga1 = (EditText) view.findViewById(R.id.edt_minHumArPraga1);
                edt_minHumArPraga1.setText(minHumArPraga1_saved);
            edt_maxHumArPraga1 = (EditText) view.findViewById(R.id.edt_maxHumArPraga1);
                edt_maxHumArPraga1.setText(maxHumArPraga1_saved);

            edt_minHumSoloPraga1 = (EditText) view.findViewById(R.id.edt_minHumSoloPraga1);
                edt_minHumSoloPraga1.setText(minHumSoloPraga1_saved);
            edt_maxHumSoloPraga1 = (EditText) view.findViewById(R.id.edt_maxHumSoloPraga1);
                edt_maxHumSoloPraga1.setText(maxHumSoloPraga1_saved);

            edt_minPluvPraga1 = (EditText) view.findViewById(R.id.edt_minPluvPraga1);
                edt_minPluvPraga1.setText(minPluvPraga1_saved);
            edt_maxPluvPraga1 = (EditText) view.findViewById(R.id.edt_maxPluvPraga1);
                edt_maxPluvPraga1.setText(maxPluvPraga1_saved);



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

                edt_minTempPraga1.setText(null);
                edt_maxTempPraga1.setText(null);
                edt_minHumArPraga1.setText(null);
                edt_maxHumArPraga1.setText(null);
                edt_minHumSoloPraga1.setText(null);
                edt_maxHumSoloPraga1.setText(null);
                edt_minPluvPraga1.setText(null);
                edt_maxPluvPraga1.setText(null);

                Toast.makeText(getActivity(), "Dialog is running for the first time!", Toast.LENGTH_SHORT).show();
            }
        }



    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.btnConfigPraga1_ok) {

            minTempPraga1 = edt_minTempPraga1.getText().toString();
            maxTempPraga1 = edt_maxTempPraga1.getText().toString();
            minHumArPraga1 = edt_minHumArPraga1.getText().toString();
            maxHumArPraga1 = edt_maxHumArPraga1.getText().toString();
            minHumSoloPraga1 = edt_minHumSoloPraga1.getText().toString();
            maxHumSoloPraga1 = edt_maxHumSoloPraga1.getText().toString();
            minPluvPraga1 = edt_minPluvPraga1.getText().toString();
            maxPluvPraga1 = edt_maxPluvPraga1.getText().toString();

            SharedPreferences prefs = getActivity().getSharedPreferences("DataPraga1", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("minTempPraga1", minTempPraga1);
            editor.putString("maxTempPraga1", maxTempPraga1);
            editor.putString("minHumArPraga1", minHumArPraga1);
            editor.putString("maxHumArPraga1", maxHumArPraga1);
            editor.putString("minHumSoloPraga1", minHumSoloPraga1);
            editor.putString("maxHumSoloPraga1", maxHumSoloPraga1);
            editor.putString("minPluvPraga1", minPluvPraga1);
            editor.putString("maxPluvPraga1", maxPluvPraga1);
            editor.commit();

            dataSettings.onDialogMessage("OK was clicked with value = " + minTempPraga1);
            dismiss();
        }
        else
        {
            dataSettings.onDialogMessage("CANCELAR was clicked");
            dismiss();
        }
    }


    interface DataSettings {
        public void onDialogMessage(String message);
    }
}
