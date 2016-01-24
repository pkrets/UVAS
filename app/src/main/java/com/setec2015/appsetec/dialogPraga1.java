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

    Button btnConfigPraga1_ok;
    TextView txtPragaNome1, txtPragaDescricao1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_praga1, null);

        txtPragaNome1 = (TextView) view.findViewById(R.id.txtPragaNome1);
        txtPragaNome1.setText("Míldio (Plasmopora Viticola)");

        txtPragaDescricao1 = (TextView) view.findViewById(R.id.txtPragaDescricao1);
        txtPragaDescricao1.setText("A temperatura ideal para o desenvolvimento do Míldio é acima dos 10 ºC. " +
                "O fungo necessita de água livre nos tecidos por um período mínimo de 2 horas para haver infeção. " +
                "A presença de água livre, seja proveniente de chuva, de orvalho, ou de gutação e a ausência total de luz, são indispensáveis para haver a infeção. " +
                "Sendo a humidade relativa do ar acima de 95 % e escuridão por um periodo de 4 horas necessárias para haver a esporulação. " +
                "A infeção do fungo nas folhas dá-se pelos estômatos presentes na face inferior, estômatos e pedicelos durante a floração e início da frutificação, e pedicelos quando a uva já está mais desenvolvida.");

        btnConfigPraga1_ok = (Button) view.findViewById(R.id.btnConfigPraga1_ok);
        btnConfigPraga1_ok.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.btnConfigPraga1_ok) {
            dismiss();
        }
    }
}
