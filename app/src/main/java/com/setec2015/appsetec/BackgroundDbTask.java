package com.setec2015.appsetec;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.widget.ListView;
import android.widget.Toast;

/**
 * Created by Krets on 11/12/2015.
 */
public class BackgroundDbTask extends AsyncTask<String, ListData, String> {

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

            SQLiteDatabase db = dbOperations.getWritableDatabase();
            dbOperations.addInfo1(temp, lum, humSolo, humAr, pluv, data, db);

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

        else if(method.equals("last_info_1"))
        {
            SQLiteDatabase db = dbOperations.getReadableDatabase();
            Cursor cursor = dbOperations.getLastRow1(db);
            table = "zona1";

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
            SQLiteDatabase db = dbOperations.getWritableDatabase();
            dbOperations.deleteInfo1(db);

            return "All Rows Deleted (BackgroundTask) in table: " + DbDataContract.DataEntry_1.TABLE_NAME;
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

            SQLiteDatabase db = dbOperations.getWritableDatabase();
            dbOperations.addInfo2(temp, lum, humSolo, humAr, pluv, data, db);

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
            table = "zona2";

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
            SQLiteDatabase db = dbOperations.getWritableDatabase();
            dbOperations.deleteInfo2(db);

            return "All Rows Deleted (BackgroundTask) in table: " + DbDataContract.DataEntry_2.TABLE_NAME;
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

            SQLiteDatabase db = dbOperations.getWritableDatabase();
            dbOperations.addInfo3(temp, lum, humSolo, humAr, pluv, data, db);

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
            table = "zona3";

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
            SQLiteDatabase db = dbOperations.getWritableDatabase();
            dbOperations.deleteInfo3(db);

            return "All Rows Deleted (BackgroundTask) in table: " + DbDataContract.DataEntry_3.TABLE_NAME;
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
        else if(result.equals("last_info_1") | result.equals("last_info_2") | result.equals("last_info_3"))
        {
            if (table.equals("zona1")) {

                Toast.makeText(ctx, "Zona 1: " + lastTemp1 + " " + lastLum1 + " " + lastHumSolo1 + " "
                        + lastHumAr1 + " " + lastPluv1 + " " + lastData1, Toast.LENGTH_LONG).show();
            }
            else if (table.equals("zona2")) {

                Toast.makeText(ctx, "Zona 2: " + lastTemp2 + " " + lastLum2 + " " + lastHumSolo2 + " "
                        + lastHumAr2 + " " + lastPluv2 + " " + lastData2, Toast.LENGTH_LONG).show();
            }
            else if (table.equals("zona3")) {

                Toast.makeText(ctx, "Zona 3: " + lastTemp3 + " " + lastLum3 + " " + lastHumSolo3 + " "
                        + lastHumAr3 + " " + lastPluv3 + " " + lastData3, Toast.LENGTH_LONG).show();
            }

            //getLastTemp1(); getLastLum1(); getLastHumSolo1(); getLastHumAr1(); getLastPluv1(); getLastData1();
        }
        else
        {
            Toast.makeText(ctx, result, Toast.LENGTH_SHORT).show();
        }

    }


    /*
    private String getLastTemp1() { return lastTemp1; }
    private String getLastLum1() { return lastLum1; }
    private String getLastHumSolo1() { return lastHumSolo1; }
    private String getLastHumAr1() { return lastHumAr1; }
    private String getLastPluv1() { return lastPluv1; }
    private String getLastData1() { return lastData1; }
    */
}
