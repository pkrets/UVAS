package com.setec2015.appsetec;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.widget.ListView;
import android.widget.Toast;

/**
 * Created by Krets on 11/12/2015.
 */
public class BackgroundDbTask extends AsyncTask<String, ListData, String> {

    String newId, newTemp, newLum, newHumSolo, newHumAr, newPluv, newData;

    String lastTemp1, lastLum1, lastHumSolo1, lastHumAr1, lastPluv1, lastData1;
    String lastTemp2, lastLum2, lastHumSolo2, lastHumAr2, lastPluv2, lastData2;
    String lastTemp3, lastLum3, lastHumSolo3, lastHumAr3, lastPluv3, lastData3;

    String table;

    Context ctx;

    Activity activity;

    ListDataAdapter listDataAdapter;

    ListView historicoListView;

    BackgroundDbTask(Context ctx)
    {
        this.ctx = ctx;
        activity=(Activity) ctx;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... params) {

        DbOperations dbOperations = new DbOperations(ctx);

        String method = params[0];

/*  --------- HISTORICO 1 --------- */
        if(method.equals("add_info_1"))
        {
            String temp = params[1];
            String lum = params[2];
            String humSolo = params[3];
            String humAr = params[4];
            String pluv = params[5];
            String data = params[6];
            table = "Zona 1";

            SQLiteDatabase db = dbOperations.getWritableDatabase();
            dbOperations.addInfo1(temp, lum, humSolo, humAr, pluv, data, db);

            SharedPreferences prefs = ctx.getSharedPreferences("DataWarningsUI", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putBoolean("newValueSensor1", true);
            editor.putBoolean("newValueHist1", true);
            editor.commit();

            return "One Row Inserted (BackgroundTask) in table: " + DbDataContract.DataEntry_1.TABLE_NAME;
        }

        else if(method.equals("get_info_1"))
        {
            String id, temp, lum, humSolo, humAr, pluv, data;
            int row = 0;

            historicoListView = (ListView) activity.findViewById(R.id.historicoListView);

            listDataAdapter = new ListDataAdapter(ctx, R.layout.historico_list_item);

            SQLiteDatabase db = dbOperations.getReadableDatabase();
            Cursor cursor = dbOperations.getInfo1(db);

            // Check if data is available
            if(cursor.moveToLast())
            {
                do {
                    id = cursor.getString(cursor.getColumnIndex(DbDataContract.DataEntry_1.ID));
                    temp = cursor.getString(cursor.getColumnIndex(DbDataContract.DataEntry_1.TEMP));
                    lum = cursor.getString(cursor.getColumnIndex(DbDataContract.DataEntry_1.LUM));
                    humSolo = cursor.getString(cursor.getColumnIndex(DbDataContract.DataEntry_1.HUM_SOLO));
                    humAr = cursor.getString(cursor.getColumnIndex(DbDataContract.DataEntry_1.HUM_AR));
                    pluv = cursor.getString(cursor.getColumnIndex(DbDataContract.DataEntry_1.PLUV));
                    data = cursor.getString(cursor.getColumnIndex(DbDataContract.DataEntry_1.DATA));

                    ListData listData = new ListData(id, temp, lum, humSolo, humAr, pluv, data);
                    publishProgress(listData);
                    row++;

                } while (row<3 && cursor.moveToPrevious()); // only the last 3 rows (most recent)
            }

        return "get_info_1";
        }

        else if(method.equals("get_new_1"))
        {
            SQLiteDatabase db = dbOperations.getReadableDatabase();
            Cursor cursor = dbOperations.getInfo1(db);
            table = "Zona 1";

            if(cursor.moveToLast())
            {
                do {
                    newId = cursor.getString(cursor.getColumnIndex(DbDataContract.DataEntry_1.ID));
                    newTemp = cursor.getString(cursor.getColumnIndex(DbDataContract.DataEntry_1.TEMP));
                    newLum = cursor.getString(cursor.getColumnIndex(DbDataContract.DataEntry_1.LUM));
                    newHumSolo = cursor.getString(cursor.getColumnIndex(DbDataContract.DataEntry_1.HUM_SOLO));
                    newHumAr = cursor.getString(cursor.getColumnIndex(DbDataContract.DataEntry_1.HUM_AR));
                    newPluv = cursor.getString(cursor.getColumnIndex(DbDataContract.DataEntry_1.PLUV));
                    newData = cursor.getString(cursor.getColumnIndex(DbDataContract.DataEntry_1.DATA));

                } while (cursor.moveToPrevious()); // get all rows
            }

            return "get_new_1";
        }

        else if(method.equals("last_info_1"))
        {
            SQLiteDatabase db = dbOperations.getReadableDatabase();
            Cursor cursor = dbOperations.getLastRow1(db);
            table = "Zona 1";

            // Check if data is available
            if(cursor.moveToLast())
            {
                lastTemp1 = cursor.getString(cursor.getColumnIndex(DbDataContract.DataEntry_1.TEMP));
                lastLum1 = cursor.getString(cursor.getColumnIndex(DbDataContract.DataEntry_1.LUM));
                lastHumSolo1 = cursor.getString(cursor.getColumnIndex(DbDataContract.DataEntry_1.HUM_SOLO));
                lastHumAr1 = cursor.getString(cursor.getColumnIndex(DbDataContract.DataEntry_1.HUM_AR));
                lastPluv1 = cursor.getString(cursor.getColumnIndex(DbDataContract.DataEntry_1.PLUV));
                lastData1 = cursor.getString(cursor.getColumnIndex(DbDataContract.DataEntry_1.DATA));
            }

            return "last_info_1";
        }

        else if(method.equals("delete_info_1"))
        {
            table = "Zona 1";
            SQLiteDatabase db = dbOperations.getWritableDatabase();
            dbOperations.deleteInfo1(db);

            return "delete_info_1";
        }

/*  --------- HISTORICO 2 --------- */
        else if(method.equals("add_info_2"))
        {
            String temp = params[1];
            String lum = params[2];
            String humSolo = params[3];
            String humAr = params[4];
            String pluv = params[5];
            String data = params[6];
            table = "Zona 2";

            SQLiteDatabase db = dbOperations.getWritableDatabase();
            dbOperations.addInfo2(temp, lum, humSolo, humAr, pluv, data, db);

            SharedPreferences prefs = ctx.getSharedPreferences("DataWarningsUI", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putBoolean("newValueSensor2", true);
            editor.putBoolean("newValueHist2", true);
            editor.commit();

            return "One Row Inserted (BackgroundTask) in table: " + DbDataContract.DataEntry_2.TABLE_NAME;
        }

        else if(method.equals("get_info_2"))
        {
            String id, temp, lum, humSolo, humAr, pluv, data;
            int row = 0;

            historicoListView = (ListView) activity.findViewById(R.id.historicoListView);

            listDataAdapter = new ListDataAdapter(ctx, R.layout.historico_list_item);

            SQLiteDatabase db2 = dbOperations.getReadableDatabase();
            Cursor cursor = dbOperations.getInfo2(db2);

            // Check if data is available
            if(cursor.moveToLast())
            {
                do {
                    id = cursor.getString(cursor.getColumnIndex(DbDataContract.DataEntry_2.ID));
                    temp = cursor.getString(cursor.getColumnIndex(DbDataContract.DataEntry_2.TEMP));
                    lum = cursor.getString(cursor.getColumnIndex(DbDataContract.DataEntry_2.LUM));
                    humSolo = cursor.getString(cursor.getColumnIndex(DbDataContract.DataEntry_2.HUM_SOLO));
                    humAr = cursor.getString(cursor.getColumnIndex(DbDataContract.DataEntry_2.HUM_AR));
                    pluv = cursor.getString(cursor.getColumnIndex(DbDataContract.DataEntry_2.PLUV));
                    data = cursor.getString(cursor.getColumnIndex(DbDataContract.DataEntry_2.DATA));

                    ListData listData = new ListData(id, temp, lum, humSolo, humAr, pluv, data);
                    publishProgress(listData);
                    row++;

                } while (row<3 && cursor.moveToPrevious()); // only the last 3 rows (most recent)
            }

            return "get_info_2";
        }

        else if(method.equals("last_info_2"))
        {
            SQLiteDatabase db = dbOperations.getReadableDatabase();
            Cursor cursor = dbOperations.getLastRow2(db);
            table = "Zona 2";

            // Check if data is available
            if(cursor.moveToLast())
            {
                lastTemp2 = cursor.getString(cursor.getColumnIndex(DbDataContract.DataEntry_2.TEMP));
                lastLum2 = cursor.getString(cursor.getColumnIndex(DbDataContract.DataEntry_2.LUM));
                lastHumSolo2 = cursor.getString(cursor.getColumnIndex(DbDataContract.DataEntry_2.HUM_SOLO));
                lastHumAr2 = cursor.getString(cursor.getColumnIndex(DbDataContract.DataEntry_2.HUM_AR));
                lastPluv2 = cursor.getString(cursor.getColumnIndex(DbDataContract.DataEntry_2.PLUV));
                lastData2 = cursor.getString(cursor.getColumnIndex(DbDataContract.DataEntry_2.DATA));
            }

            return "last_info_2";
        }

        else if(method.equals("delete_info_2"))
        {
            table = "Zona 2";
            SQLiteDatabase db = dbOperations.getWritableDatabase();
            dbOperations.deleteInfo2(db);

            return "delete_info_2";
        }

/*  --------- HISTORICO 3 --------- */
        else if (method.equals("add_info_3"))
        {
            String temp = params[1];
            String lum = params[2];
            String humSolo = params[3];
            String humAr = params[4];
            String pluv = params[5];
            String data = params[6];
            table = "Zona 3";

            SQLiteDatabase db = dbOperations.getWritableDatabase();
            dbOperations.addInfo3(temp, lum, humSolo, humAr, pluv, data, db);

            SharedPreferences prefs = ctx.getSharedPreferences("DataWarningsUI", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putBoolean("newValueSensor3", true);
            editor.putBoolean("newValueHist3", true);
            editor.commit();

            return "One Row Inserted (BackgroundTask) in table: " + DbDataContract.DataEntry_3.TABLE_NAME;
        }

        else if(method.equals("get_info_3"))
        {
            String id, temp, lum, humSolo, humAr, pluv, data;
            int row = 0;

            historicoListView = (ListView) activity.findViewById(R.id.historicoListView);

            listDataAdapter = new ListDataAdapter(ctx, R.layout.historico_list_item);

            SQLiteDatabase db = dbOperations.getReadableDatabase();
            Cursor cursor = dbOperations.getInfo3(db);

            // Check if data is available
            if(cursor.moveToLast())
            {
                do {
                    id = cursor.getString(cursor.getColumnIndex(DbDataContract.DataEntry_3.ID));
                    temp = cursor.getString(cursor.getColumnIndex(DbDataContract.DataEntry_3.TEMP));
                    lum = cursor.getString(cursor.getColumnIndex(DbDataContract.DataEntry_3.LUM));
                    humSolo = cursor.getString(cursor.getColumnIndex(DbDataContract.DataEntry_3.HUM_SOLO));
                    humAr = cursor.getString(cursor.getColumnIndex(DbDataContract.DataEntry_3.HUM_AR));
                    pluv = cursor.getString(cursor.getColumnIndex(DbDataContract.DataEntry_3.PLUV));
                    data = cursor.getString(cursor.getColumnIndex(DbDataContract.DataEntry_3.DATA));

                    ListData listData = new ListData(id, temp, lum, humSolo, humAr, pluv, data);
                    publishProgress(listData);
                    row++;

                } while (row<3 && cursor.moveToPrevious()); // only the last 3 rows (most recent)
            }

            return "get_info_3";
        }

        else if(method.equals("last_info_3"))
        {
            SQLiteDatabase db = dbOperations.getReadableDatabase();
            Cursor cursor = dbOperations.getLastRow3(db);
            table = "Zona 3";

            // Check if data is available
            if(cursor.moveToLast())
            {
                lastTemp3 = cursor.getString(cursor.getColumnIndex(DbDataContract.DataEntry_3.TEMP));
                lastLum3 = cursor.getString(cursor.getColumnIndex(DbDataContract.DataEntry_3.LUM));
                lastHumSolo3 = cursor.getString(cursor.getColumnIndex(DbDataContract.DataEntry_3.HUM_SOLO));
                lastHumAr3 = cursor.getString(cursor.getColumnIndex(DbDataContract.DataEntry_3.HUM_AR));
                lastPluv3 = cursor.getString(cursor.getColumnIndex(DbDataContract.DataEntry_3.PLUV));
                lastData3 = cursor.getString(cursor.getColumnIndex(DbDataContract.DataEntry_3.DATA));
            }

            return "last_info_3";
        }

        else if(method.equals("delete_info_3"))
        {
            table = "Zona 3";
            SQLiteDatabase db = dbOperations.getWritableDatabase();
            dbOperations.deleteInfo3(db);

            return "delete_info_3";
        }

        return null;
    }


    @Override
    protected void onProgressUpdate(ListData... values) {
        listDataAdapter.add(values[0]);
    }

    @Override
    protected void onPostExecute(String result) {

        if(result.equals("get_info_1") | result.equals("get_info_2") | result.equals("get_info_3"))
        {
            historicoListView.setAdapter(listDataAdapter);
        }
        else if(result.equals("get_new_1"))
        {
            Toast.makeText(ctx, result, Toast.LENGTH_SHORT).show();
        }
        else if(result.equals("last_info_1") | result.equals("last_info_2") | result.equals("last_info_3"))
        {
            if (table.equals("Zona 1")) {
                SharedPreferences prefs = ctx.getSharedPreferences("DataLastValues", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();
                editor.putString("lastTemp1", lastTemp1);
                editor.putString("lastLum1", lastLum1);
                editor.putString("lastHumSolo1", lastHumSolo1);
                editor.putString("lastHumAr1", lastHumAr1);
                editor.putString("lastPluv1", lastPluv1);
                editor.putString("lastData1", lastData1);
                editor.commit();
            }
            else if (table.equals("Zona 2")) {
                SharedPreferences prefs = ctx.getSharedPreferences("DataLastValues", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();
                editor.putString("lastTemp2", lastTemp2);
                editor.putString("lastLum2", lastLum2);
                editor.putString("lastHumSolo2", lastHumSolo2);
                editor.putString("lastHumAr2", lastHumAr2);
                editor.putString("lastPluv2", lastPluv2);
                editor.putString("lastData2", lastData2);
                editor.commit();
            }
            else if (table.equals("Zona 3")) {
                SharedPreferences prefs = ctx.getSharedPreferences("DataLastValues", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();
                editor.putString("lastTemp3", lastTemp3);
                editor.putString("lastLum3", lastLum3);
                editor.putString("lastHumSolo3", lastHumSolo3);
                editor.putString("lastHumAr3", lastHumAr3);
                editor.putString("lastPluv3", lastPluv3);
                editor.putString("lastData3", lastData3);
                editor.commit();
            }
        }
        else if(result.equals("delete_info_1") | result.equals("delete_info_2") | result.equals("delete_info_3"))
        {
            Toast.makeText(ctx, "Os registos do Histórico de '" +table+ "' foram apagados.", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(ctx, result, Toast.LENGTH_SHORT).show();
        }

    }
}
