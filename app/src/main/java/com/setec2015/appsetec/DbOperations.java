package com.setec2015.appsetec;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by Krets on 11/12/2015.
 */
public class DbOperations extends SQLiteOpenHelper {

    private static final String DB_NAME = "uvas_data.db";
    /*
          >>> NOTE : every time the table structure or the queries are altered, the
          DATABASE_VERSION must be INCREMENTED in order to call the onUpgrade method.
              > Version 1 -- DB created with 3 tables including <create_table> & <drop_table> queries
                */
    private static final int DB_VERSION = 1;

    // SQL queries to create and drop Table 1 (pandlet1_table)
    private static final String CREATE_TABLE_1 =
            "CREATE TABLE "+DbDataContract.DataEntry_1.TABLE_NAME+"("+DbDataContract.DataEntry_1.ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+
                    DbDataContract.DataEntry_1.TEMP+" VARCHAR(255), "+ DbDataContract.DataEntry_1.LUM+" VARCHAR(255), "+
                    DbDataContract.DataEntry_1.HUM_SOLO+" VARCHAR(255), "+ DbDataContract.DataEntry_1.HUM_AR+" VARCHAR(255), "+
                    DbDataContract.DataEntry_1.PLUV+" VARCHAR(255), "+ DbDataContract.DataEntry_1.DATA+" VARCHAR(255));";
    private static final String DROP_TABLE_1 = "DROP TABLE IF EXISTS "+ DbDataContract.DataEntry_1.TABLE_NAME;

    // SQL query to create Table 2 (pandlet2_table)
    private static final String CREATE_TABLE_2 =
            "CREATE TABLE "+DbDataContract.DataEntry_2.TABLE_NAME+"("+DbDataContract.DataEntry_2.ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+
                    DbDataContract.DataEntry_2.TEMP+" VARCHAR(255), "+ DbDataContract.DataEntry_2.LUM+" VARCHAR(255), "+
                    DbDataContract.DataEntry_2.HUM_SOLO+" VARCHAR(255), "+ DbDataContract.DataEntry_2.HUM_AR+" VARCHAR(255), "+
                    DbDataContract.DataEntry_2.PLUV+" VARCHAR(255), "+ DbDataContract.DataEntry_2.DATA+" VARCHAR(255));";
    private static final String DROP_TABLE_2 = "DROP TABLE IF EXISTS "+ DbDataContract.DataEntry_2.TABLE_NAME;

    // SQL query to create Table 3 (pandlet3_table)
    private static final String CREATE_TABLE_3 =
            "CREATE TABLE "+DbDataContract.DataEntry_3.TABLE_NAME+"("+DbDataContract.DataEntry_3.ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+
                    DbDataContract.DataEntry_3.TEMP+" VARCHAR(255), "+ DbDataContract.DataEntry_3.LUM+" VARCHAR(255), "+
                    DbDataContract.DataEntry_3.HUM_SOLO+" VARCHAR(255), "+ DbDataContract.DataEntry_3.HUM_AR+" VARCHAR(255), "+
                    DbDataContract.DataEntry_3.PLUV+" VARCHAR(255), "+ DbDataContract.DataEntry_3.DATA+" VARCHAR(255));";
   private static final String DROP_TABLE_3 = "DROP TABLE IF EXISTS "+ DbDataContract.DataEntry_3.TABLE_NAME;



    DbOperations(Context ctx)
    {
        super(ctx, DB_NAME, null, DB_VERSION);
            Log.d("DATABASE OPERATIONS", "Database opened !");
    }

    // Called if DB created for the 1st time
        @Override
        public void onCreate(SQLiteDatabase db) {

            try {
                db.execSQL(CREATE_TABLE_1);
                    Log.d("DATABASE OPERATIONS", "onCreate called! Table created: " + DbDataContract.DataEntry_1.TABLE_NAME);
                db.execSQL(CREATE_TABLE_2);
                    Log.d("DATABASE OPERATIONS", "onCreate called! Table created: " + DbDataContract.DataEntry_2.TABLE_NAME);
                db.execSQL(CREATE_TABLE_3);
                    Log.d("DATABASE OPERATIONS", "onCreate called! Table created: " + DbDataContract.DataEntry_3.TABLE_NAME);

            }catch (SQLException e) {
                    Log.d("DATABASE OPERATIONS", "Error " + e);
            }
        }


//////////////////////////////////////////////////////////////////////////////

    // Add a Row to TABLE 1 in DB -- pandlet1_table
        public void addInfo1(String temp, String lum, String humSolo, String humAr, String pluv, String data, SQLiteDatabase db)
        {
            ContentValues contentValues = new ContentValues();
                contentValues.put(DbDataContract.DataEntry_1.TEMP, temp);
                contentValues.put(DbDataContract.DataEntry_1.LUM, lum);
                contentValues.put(DbDataContract.DataEntry_1.HUM_SOLO, humSolo);
                contentValues.put(DbDataContract.DataEntry_1.HUM_AR, humAr);
                contentValues.put(DbDataContract.DataEntry_1.PLUV, pluv);
                contentValues.put(DbDataContract.DataEntry_1.DATA, data);

            db.insert(DbDataContract.DataEntry_1.TABLE_NAME, null, contentValues);
                Log.d("DATABASE OPERATIONS", "One Row Inserted in Table: " + DbDataContract.DataEntry_1.TABLE_NAME);
        }

    // Get All Rows from TABLE 1 in DB -- pandlet1_table
        public Cursor getInfo1(SQLiteDatabase db)
        {
            Cursor cursor;

            String [] projections = {DbDataContract.DataEntry_1.ID, DbDataContract.DataEntry_1.TEMP,
                    DbDataContract.DataEntry_1.LUM, DbDataContract.DataEntry_1.HUM_SOLO,
                    DbDataContract.DataEntry_1.HUM_AR, DbDataContract.DataEntry_1.PLUV,
                    DbDataContract.DataEntry_1.DATA};

            cursor = db.query(DbDataContract.DataEntry_1.TABLE_NAME, projections, null, null, null, null, null);
                Log.d("DATABASE OPERATIONS", "Data retrieved from Table: " + DbDataContract.DataEntry_1.TABLE_NAME);

            return cursor;
        }


    // Add a Row to TABLE 2 in DB -- pandlet2_table
        public void addInfo2(String temp, String lum, String humSolo, String humAr, String pluv, String data, SQLiteDatabase db)
        {
            ContentValues contentValues = new ContentValues();
            contentValues.put(DbDataContract.DataEntry_2.TEMP, temp);
            contentValues.put(DbDataContract.DataEntry_2.LUM, lum);
            contentValues.put(DbDataContract.DataEntry_2.HUM_SOLO, humSolo);
            contentValues.put(DbDataContract.DataEntry_2.HUM_AR, humAr);
            contentValues.put(DbDataContract.DataEntry_2.PLUV, pluv);
            contentValues.put(DbDataContract.DataEntry_2.DATA, data);

            db.insert(DbDataContract.DataEntry_2.TABLE_NAME, null, contentValues);
                Log.d("DATABASE OPERATIONS", "One Row Inserted in Table: " + DbDataContract.DataEntry_2.TABLE_NAME);
        }

    // Get All Rows from TABLE 2 in DB -- pandlet2_table
        public Cursor getInfo2(SQLiteDatabase db)
        {
            Cursor cursor;

            String [] projections = {DbDataContract.DataEntry_2.ID, DbDataContract.DataEntry_2.TEMP,
                    DbDataContract.DataEntry_2.LUM, DbDataContract.DataEntry_2.HUM_SOLO,
                    DbDataContract.DataEntry_2.HUM_AR, DbDataContract.DataEntry_2.PLUV,
                    DbDataContract.DataEntry_2.DATA};

            cursor = db.query(DbDataContract.DataEntry_2.TABLE_NAME, projections, null, null, null, null, null);
            Log.d("DATABASE OPERATIONS", "Data retrieved from Table: " + DbDataContract.DataEntry_2.TABLE_NAME);

            return cursor;
        }


    // Add a Row to TABLE 3 in DB -- pandlet3_table
        public void addInfo3(String temp, String lum, String humSolo, String humAr, String pluv, String data, SQLiteDatabase db)
        {
            ContentValues contentValues = new ContentValues();
            contentValues.put(DbDataContract.DataEntry_3.TEMP, temp);
            contentValues.put(DbDataContract.DataEntry_3.LUM, lum);
            contentValues.put(DbDataContract.DataEntry_3.HUM_SOLO, humSolo);
            contentValues.put(DbDataContract.DataEntry_3.HUM_AR, humAr);
            contentValues.put(DbDataContract.DataEntry_3.PLUV, pluv);
            contentValues.put(DbDataContract.DataEntry_3.DATA, data);

            db.insert(DbDataContract.DataEntry_3.TABLE_NAME, null, contentValues);
            Log.d("DATABASE OPERATIONS", "One Row Inserted in Table: " + DbDataContract.DataEntry_3.TABLE_NAME);
        }

    // Get All Rows from TABLE 3 in DB -- pandlet3_table
        public Cursor getInfo3(SQLiteDatabase db)
        {
            Cursor cursor;

            String [] projections = {DbDataContract.DataEntry_3.ID, DbDataContract.DataEntry_3.TEMP,
                    DbDataContract.DataEntry_3.LUM, DbDataContract.DataEntry_3.HUM_SOLO,
                    DbDataContract.DataEntry_3.HUM_AR, DbDataContract.DataEntry_3.PLUV,
                    DbDataContract.DataEntry_3.DATA};

            cursor = db.query(DbDataContract.DataEntry_3.TABLE_NAME, projections, null, null, null, null, null);
            Log.d("DATABASE OPERATIONS", "Data retrieved from Table: " + DbDataContract.DataEntry_3.TABLE_NAME);

            return cursor;
        }

//////////////////////////////////////////////////////////////////////////////




    // Called when changes are implemented in the table/queries (DATABASE_VERSION must be incremented)
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

            try {
                db.execSQL(DROP_TABLE_1);
                    Log.d("DATABASE OPERATIONS", "onUpgrade called! Changes were performed in: " + DbDataContract.DataEntry_1.TABLE_NAME);
                db.execSQL(DROP_TABLE_2);
                    Log.d("DATABASE OPERATIONS", "onUpgrade called! Changes were performed in: " + DbDataContract.DataEntry_2.TABLE_NAME);
                db.execSQL(DROP_TABLE_3);
                    Log.d("DATABASE OPERATIONS", "onUpgrade called! Changes were performed in: " + DbDataContract.DataEntry_3.TABLE_NAME);
            }catch (SQLException e) {
                Log.d("DATABASE OPERATIONS", "Error " + e);
            }

        }
}
