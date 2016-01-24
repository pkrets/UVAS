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

    Button btnConfigPraga2_ok;
    TextView txtPragaNome2, txtPragaDescricao2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_praga2, null);

        txtPragaNome2 = (TextView) view.findViewById(R.id.txtPragaNome2);
        txtPragaNome2.setText("Oídio da Videira (Oidium Tuckeri Berk)");

        txtPragaDescricao2 = (TextView) view.findViewById(R.id.txtPragaDescricao2);
        txtPragaDescricao2.setText("São favoráveis ao desenvolvimento da doença dias nublados com manhãs de elevada humidade relativa do ar (superior a 90 %), seguidos de períodos de sol aberto e com temperaturas a rondar os 25 °C (24 a 26 °C). " +
                "Para além destes fatores climatéricos, sistemas de condução que favoreçam uma exuberância da vegetação, porta-enxertos vigorosos e castas sensíveis podem também influenciar a instalação e virulência da doença.");

        btnConfigPraga2_ok = (Button) view.findViewById(R.id.btnConfigPraga2_ok);
        btnConfigPraga2_ok.setOnClickListener(this);

        return view;
    }


    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.btnConfigPraga2_ok) {
            dismiss();
        }
    }

}
