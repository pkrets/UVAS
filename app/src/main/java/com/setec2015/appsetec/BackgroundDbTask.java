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
            String temp1 = params[1];
            String lum1 = params[2];
            String humSolo1 = params[3];
            String humAr1 = params[4];
            String pluv1 = params[5];
            String data1 = params[6];

            SQLiteDatabase db1 = dbOperations.getWritableDatabase();
            dbOperations.addInfo1(temp1, lum1, humSolo1, humAr1, pluv1, data1, db1);

            return "One Row Inserted (BackgroundTask) in table: " + DbDataContract.DataEntry_1.TABLE_NAME;
        }

        else if(method.equals("get_info_1"))
        {
            String id1, temp1, lum1, humSolo1, humAr1, pluv1, data1;
            int row = 0;

            historicoListView = (ListView) activity.findViewById(R.id.historicoListView);

            listDataAdapter = new ListDataAdapter(ctx, R.layout.historico_list_item);

            SQLiteDatabase db1 = dbOperations.getReadableDatabase();
            Cursor cursor = dbOperations.getInfo1(db1);

            // Check if data is available
            if(cursor.moveToLast())
            {
                do {
                    id1 = cursor.getString(cursor.getColumnIndex(DbDataContract.DataEntry_1.ID));
                    temp1 = cursor.getString(cursor.getColumnIndex(DbDataContract.DataEntry_1.TEMP));
                    lum1 = cursor.getString(cursor.getColumnIndex(DbDataContract.DataEntry_1.LUM));
                    humSolo1 = cursor.getString(cursor.getColumnIndex(DbDataContract.DataEntry_1.HUM_SOLO));
                    humAr1 = cursor.getString(cursor.getColumnIndex(DbDataContract.DataEntry_1.HUM_AR));
                    pluv1 = cursor.getString(cursor.getColumnIndex(DbDataContract.DataEntry_1.PLUV));
                    data1 = cursor.getString(cursor.getColumnIndex(DbDataContract.DataEntry_1.DATA));

                    ListData listData = new ListData(id1, temp1, lum1, humSolo1, humAr1, pluv1, data1);
                    publishProgress(listData);
                    row++;

                } while (row<3 && cursor.moveToPrevious()); // only the last 3 rows (most recent)
            }

        return "get_info_1";
        }

        else if(method.equals("last_info_1"))
        {
            SQLiteDatabase db1 = dbOperations.getReadableDatabase();
            Cursor cursor = dbOperations.getLastRow1(db1);

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
            String temp2 = params[1];
            String lum2 = params[2];
            String humSolo2 = params[3];
            String humAr2 = params[4];
            String pluv2 = params[5];
            String data2 = params[6];

            SQLiteDatabase db2 = dbOperations.getWritableDatabase();
            dbOperations.addInfo2(temp2, lum2, humSolo2, humAr2, pluv2, data2, db2);

            return "One Row Inserted (BackgroundTask) in table: " + DbDataContract.DataEntry_2.TABLE_NAME;
        }

        else if(method.equals("get_info_2"))
        {
            String id2, temp2, lum2, humSolo2, humAr2, pluv2, data2;
            int row = 0;

            historicoListView = (ListView) activity.findViewById(R.id.historicoListView);

            listDataAdapter = new ListDataAdapter(ctx, R.layout.historico_list_item);

            SQLiteDatabase db2 = dbOperations.getReadableDatabase();
            Cursor cursor = dbOperations.getInfo2(db2);

            // Check if data is available
            if(cursor.moveToLast())
            {
                do {
                    id2 = cursor.getString(cursor.getColumnIndex(DbDataContract.DataEntry_2.ID));
                    temp2 = cursor.getString(cursor.getColumnIndex(DbDataContract.DataEntry_2.TEMP));
                    lum2 = cursor.getString(cursor.getColumnIndex(DbDataContract.DataEntry_2.LUM));
                    humSolo2 = cursor.getString(cursor.getColumnIndex(DbDataContract.DataEntry_2.HUM_SOLO));
                    humAr2 = cursor.getString(cursor.getColumnIndex(DbDataContract.DataEntry_2.HUM_AR));
                    pluv2 = cursor.getString(cursor.getColumnIndex(DbDataContract.DataEntry_2.PLUV));
                    data2 = cursor.getString(cursor.getColumnIndex(DbDataContract.DataEntry_2.DATA));

                    ListData listData = new ListData(id2, temp2, lum2, humSolo2, humAr2, pluv2, data2);
                    publishProgress(listData);
                    row++;

                } while (row<3 && cursor.moveToPrevious()); // only the last 3 rows (most recent)
            }

            return "get_info_2";
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

        else if(method.equals("get_info_3"))
        {
            String id3, temp3, lum3, humSolo3, humAr3, pluv3, data3;
            int row = 0;

            historicoListView = (ListView) activity.findViewById(R.id.historicoListView);

            listDataAdapter = new ListDataAdapter(ctx, R.layout.historico_list_item);

            SQLiteDatabase db3 = dbOperations.getReadableDatabase();
            Cursor cursor = dbOperations.getInfo3(db3);

            // Check if data is available
            if(cursor.moveToLast())
            {
                do {
                    id3 = cursor.getString(cursor.getColumnIndex(DbDataContract.DataEntry_3.ID));
                    temp3 = cursor.getString(cursor.getColumnIndex(DbDataContract.DataEntry_3.TEMP));
                    lum3 = cursor.getString(cursor.getColumnIndex(DbDataContract.DataEntry_3.LUM));
                    humSolo3 = cursor.getString(cursor.getColumnIndex(DbDataContract.DataEntry_3.HUM_SOLO));
                    humAr3 = cursor.getString(cursor.getColumnIndex(DbDataContract.DataEntry_3.HUM_AR));
                    pluv3 = cursor.getString(cursor.getColumnIndex(DbDataContract.DataEntry_3.PLUV));
                    data3 = cursor.getString(cursor.getColumnIndex(DbDataContract.DataEntry_3.DATA));

                    ListData listData = new ListData(id3, temp3, lum3, humSolo3, humAr3, pluv3, data3);
                    publishProgress(listData);
                    row++;

                } while (row<3 && cursor.moveToPrevious()); // only the last 3 rows (most recent)
            }

            return "get_info_3";
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
        else if(result.equals("last_info_1"))
        {
            getLastTemp1(); getLastLum1(); getLastHumSolo1(); getLastHumAr1(); getLastPluv1(); getLastData1();
        }
        else
        {
            Toast.makeText(ctx, result, Toast.LENGTH_LONG).show();
        }

    }

    private String getLastTemp1() { return lastTemp1; }
    private String getLastLum1() { return lastLum1; }
    private String getLastHumSolo1() { return lastHumSolo1; }
    private String getLastHumAr1() { return lastHumAr1; }
    private String getLastPluv1() { return lastTemp1; }
    private String getLastData1() { return lastData1; }

}
