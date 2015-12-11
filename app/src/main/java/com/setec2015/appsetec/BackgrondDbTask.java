package com.setec2015.appsetec;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.widget.Toast;

/**
 * Created by Krets on 11/12/2015.
 */
public class BackgrondDbTask extends AsyncTask<String, Void, String> {

    Context ctx;

    BackgrondDbTask(Context ctx)
    {
        this.ctx = ctx;
    }



    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... params) {

        DbOperations dbOperations = new DbOperations(ctx);

        String method = params[0];

        if(method.equals("add_info_1"))
        {
            String temp1 = params[1];
            String lum1 = params[2];
            String humSolo1 = params[3];
            String humAr1 = params[4];
            String pluv1 = params[5];
            String data1 = params[6];

            SQLiteDatabase db1 = dbOperations.getWritableDatabase();
            dbOperations.addInfo1(temp1, lum1, humSolo1, humAr1, pluv1, data1, db1);

            return "One Row Inserted (BackgroundTask) in table: " + DbDataContract.DataEntry_1.TABLE_NAME;

        } else if(method.equals("add_info_2"))
        {
            String temp2 = params[1];
            String lum2 = params[2];
            String humSolo2 = params[3];
            String humAr2 = params[4];
            String pluv2 = params[5];
            String data2 = params[6];

            SQLiteDatabase db2 = dbOperations.getWritableDatabase();
            dbOperations.addInfo2(temp2, lum2, humSolo2, humAr2, pluv2, data2, db2);

            return "One Row Inserted (BackgroundTask) in table: " + DbDataContract.DataEntry_2.TABLE_NAME;

        } else if (method.equals("add_info_3"))
        {
            String temp3 = params[1];
            String lum3 = params[2];
            String humSolo3 = params[3];
            String humAr3 = params[4];
            String pluv3 = params[5];
            String data3 = params[6];

            SQLiteDatabase db3 = dbOperations.getWritableDatabase();
            dbOperations.addInfo3(temp3, lum3, humSolo3, humAr3, pluv3, data3, db3);

            return "One Row Inserted (BackgroundTask) in table: " + DbDataContract.DataEntry_3.TABLE_NAME;
        }
        return null;
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(String result) {
        Toast.makeText(ctx, result, Toast.LENGTH_LONG).show();
    }
}
