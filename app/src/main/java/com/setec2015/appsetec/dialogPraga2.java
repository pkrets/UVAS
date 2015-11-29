package com.setec2015.appsetec;

import android.app.Activity;
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
public class dialogPraga2 extends DialogFragment implements View.OnClickListener {

    Button btnConfigPraga2_ok, btnConfigPraga2_cancel;
    TextView txtPragaNome2, txtPragaDescricao2;

    EditText edt_minTempPraga2, edt_maxTempPraga2;
    String minTempPraga2, minTempPraga2_saved, maxTempPraga2, maxTempPraga2_saved;

    EditText edt_minHumPraga2, edt_maxHumPraga2;
    String minHumPraga2, minHumPraga2_saved, maxHumPraga2, maxHumPraga2_saved;

    EditText edt_minPluvPraga2, edt_maxPluvPraga2;
    String minPluvPraga2, minPluvPraga2_saved, maxPluvPraga2, maxPluvPraga2_saved;


    private static boolean RUN_ONCE = true;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_praga2, null);
        setCancelable(false);

        txtPragaNome2 = (TextView) view.findViewById(R.id.txtPragaNome2);
        txtPragaNome2.setText("Oídio da Videira (Oidium Tuckeri Berk)");

        txtPragaDescricao2 = (TextView) view.findViewById(R.id.txtPragaDescricao2);
        txtPragaDescricao2.setText("São favoráveis ao desenvolvimento da doença dias nublados com manhãs de elevada humidade relativa do ar (superior a 90%), seguidos de períodos de sol aberto e com temperaturas de 25°C (24 -26°C). Para além destes factores climatéricos, sistemas de condução que favoreçam uma exuberância da vegetação, porta-enxertos vigorosos e castas sensíveis podem também influenciar a instalação e virulência da doença.");

        btnConfigPraga2_ok = (Button) view.findViewById(R.id.btnConfigPraga2_ok);
        btnConfigPraga2_ok.setOnClickListener(this);

        btnConfigPraga2_cancel = (Button) view.findViewById(R.id.btnConfigPraga2_cancel);
        btnConfigPraga2_cancel.setOnClickListener(this);

        //

        SharedPreferences prefs = getActivity().getSharedPreferences("DataPraga2", Context.MODE_PRIVATE);
        minTempPraga2_saved = prefs.getString("minTempPraga2", "0");
        maxTempPraga2_saved = prefs.getString("maxTempPraga2", "0");
        minHumPraga2_saved = prefs.getString("minHumPraga2", "0");
        maxHumPraga2_saved = prefs.getString("maxHumPraga2", "0");
        minPluvPraga2_saved = prefs.getString("minPluvPraga2", "0");
        maxPluvPraga2_saved = prefs.getString("maxPluvPraga2", "0");

            edt_minTempPraga2 = (EditText) view.findViewById(R.id.edt_minTempPraga2);
                edt_minTempPraga2.setText(minTempPraga2_saved);
            edt_maxTempPraga2 = (EditText) view.findViewById(R.id.edt_maxTempPraga2);
                edt_maxTempPraga2.setText(maxTempPraga2_saved);

            edt_minHumPraga2 = (EditText) view.findViewById(R.id.edt_minHumPraga2);
                edt_minHumPraga2.setText(minHumPraga2_saved);
            edt_maxHumPraga2 = (EditText) view.findViewById(R.id.edt_maxHumPraga2);
                edt_maxHumPraga2.setText(maxHumPraga2_saved);

            edt_minPluvPraga2 = (EditText) view.findViewById(R.id.edt_minPluvPraga2);
                edt_minPluvPraga2.setText(minPluvPraga2_saved);
            edt_maxPluvPraga2 = (EditText) view.findViewById(R.id.edt_maxPluvPraga2);
                edt_maxPluvPraga2.setText(maxPluvPraga2_saved);



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

            edt_minTempPraga2.setText(null);
            edt_maxTempPraga2.setText(null);
            edt_minHumPraga2.setText(null);
            edt_maxHumPraga2.setText(null);
            edt_minPluvPraga2.setText(null);
            edt_maxPluvPraga2.setText(null);

            Toast.makeText(getActivity(), "Dialog is running for the first time!", Toast.LENGTH_SHORT).show();
        }
    }



    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.btnConfigPraga2_ok) {

            minTempPraga2 = edt_minTempPraga2.getText().toString();
            maxTempPraga2 = edt_maxTempPraga2.getText().toString();
            minHumPraga2 = edt_minHumPraga2.getText().toString();
            maxHumPraga2 = edt_maxHumPraga2.getText().toString();
            minPluvPraga2 = edt_minPluvPraga2.getText().toString();
            maxPluvPraga2 = edt_maxPluvPraga2.getText().toString();

            SharedPreferences prefs = getActivity().getSharedPreferences("DataPraga2", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("minTempPraga2", minTempPraga2);
            editor.putString("maxTempPraga2", maxTempPraga2);
            editor.putString("minHumPraga2", minHumPraga2);
            editor.putString("maxHumPraga2", maxHumPraga2);
            editor.putString("minPluvPraga2", minPluvPraga2);
            editor.putString("maxPluvPraga2", maxPluvPraga2);
            editor.commit();

            dismiss();
        }
        else
        {
            dismiss();
        }
    }

}
