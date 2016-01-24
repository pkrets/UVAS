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

    Button btnConfigPraga4_ok;
    TextView txtPragaNome4, txtPragaDescricao4;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_praga4, null);

        txtPragaNome4 = (TextView) view.findViewById(R.id.txtPragaNome4);
        txtPragaNome4.setText("Escoriose da Videira (Phosmosis Viticola)");

        txtPragaDescricao4 = (TextView) view.findViewById(R.id.txtPragaDescricao4);
        txtPragaDescricao4.setText("Na Primavera, perto do abrolhamento, o fungo entra numa fase de intensa actividade. Os picnídeos libertam os seus esporos incolores, aglutinados em cirros (massa gelatinosa emergente dos picnídeos), através da acção da chuva e da humidade. As gotículas de chuva quebram os cirros e projectam os esporos por arrastamento para os gomos recentemente abrolhados. " +
                "Os esporos germinam bem na água e podem contaminar os pâmpanose todos os órgãos verdes, caso estes fiquem humectados durante um período de tempo de humectação consecutivo superior a 7 horas. Etc ");

        btnConfigPraga4_ok = (Button) view.findViewById(R.id.btnConfigPraga4_ok);
        btnConfigPraga4_ok.setOnClickListener(this);

        return view;
    }


    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.btnConfigPraga4_ok) {
            dismiss();
        }
    }

}

