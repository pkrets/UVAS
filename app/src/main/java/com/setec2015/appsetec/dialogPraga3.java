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

    Button btnConfigPraga3_ok;
    TextView txtPragaNome3, txtPragaDescricao3;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_praga3, null);

        txtPragaNome3 = (TextView) view.findViewById(R.id.txtPragaNome3);
        txtPragaNome3.setText("Podridão Cinzenta (Botrytis Cinerea)");

        txtPragaDescricao3 = (TextView) view.findViewById(R.id.txtPragaDescricao3);
        txtPragaDescricao3.setText("Na maioria dos casos, podridões severas dos cachos estão associadas a uma humidade relativa alta (acima dos 95 %) e temperaturas entre 15 °C e 25 °C durante a maturação da uva. " +
                "Cultivares de uvas tintas contém compostos que inibem em parte o fungo, sendo menos atacadas do que as brancas. A espessura da película também é um fator determinante na suscetibilidade da cultivar. " +
                "Nos cachos compactos, a água persiste por mais tempo e a penetração dos fungicidas é dificultada o que favorece o desenvolvimento de podridões.");

        btnConfigPraga3_ok = (Button) view.findViewById(R.id.btnConfigPraga3_ok);
        btnConfigPraga3_ok.setOnClickListener(this);

        return view;
    }


    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.btnConfigPraga3_ok) {
            dismiss();
        }
    }

}
