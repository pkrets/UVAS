package com.setec2015.appsetec;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.text.Html;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by Krets on 29/12/2015.
 */
public class BackgroundOnlineDbTask extends AsyncTask<String, Void, String> {

    Context ctx;

    Activity activity;

    String JSON_STRING;
    String json_string;
    JSONObject jsonObject;
    JSONArray jsonArray;

    String table;

    ProgressDialog progress;

    BackgroundOnlineDbTask(Context ctx) {
        this.ctx = ctx;
        activity = (Activity) ctx;
        this.progress = new ProgressDialog(ctx);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        this.progress.setMessage("Aguarde um momento ...");
        this.progress.show();
    }

    @Override
    protected String doInBackground(String... params) {

        String addInfo_pandlet1_url = "http://androidsetec.netau.net/write_pandlet1.php";
        String addInfo_pandlet2_url = "http://androidsetec.netau.net/write_pandlet2.php";
        String addInfo_pandlet3_url = "http://androidsetec.netau.net/write_pandlet3.php";
        String getJson_pandlet1_url = "http://androidsetec.netau.net/read_pandlet1_json.php";
        String getJson_pandlet2_url = "http://androidsetec.netau.net/read_pandlet2_json.php";
        String getJson_pandlet3_url = "http://androidsetec.netau.net/read_pandlet3_json.php";
        String update_pandlet1_url = "http://androidsetec.netau.net/update_pandlet1.php";
        String update_pandlet2_url = "http://androidsetec.netau.net/update_pandlet2.php";
        String update_pandlet3_url = "http://androidsetec.netau.net/update_pandlet3.php";

        String method = params[0];


/*  --------- HISTORICO 1 --------- */
        if (method.equals("add_info_1"))
        {
            String temp = params[1];
            String lum = params[2];
            String humSolo = params[3];
            String humAr = params[4];
            String pluv = params[5];
            String data = params[6];

            try {
                URL url = new URL(addInfo_pandlet1_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);

                OutputStream OS = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(OS, "UTF-8"));

                String data_string = URLEncoder.encode("temperature", "UTF-8") + "=" + URLEncoder.encode(temp, "UTF-8") + "&" +
                        URLEncoder.encode("luminosity", "UTF-8") + "=" + URLEncoder.encode(lum, "UTF-8") + "&" +
                        URLEncoder.encode("soil_moisture", "UTF-8") + "=" + URLEncoder.encode(humSolo, "UTF-8") + "&" +
                        URLEncoder.encode("relative_humidity", "UTF-8") + "=" + URLEncoder.encode(humAr, "UTF-8") + "&" +
                        URLEncoder.encode("pluviosity", "UTF-8") + "=" + URLEncoder.encode(pluv, "UTF-8") + "&" +
                        URLEncoder.encode("timestamp", "UTF-8") + "=" + URLEncoder.encode(data, "UTF-8");

                bufferedWriter.write(data_string);
                bufferedWriter.flush();
                bufferedWriter.close();
                OS.close();

                // Get response from the server
                InputStream IS = httpURLConnection.getInputStream();
                IS.close();

                // Terminate connection
                httpURLConnection.disconnect();

                return "add_info_1";

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        else if (method.equals("update_info_1"))
        {
            String temp = params[1];
            String lum = params[2];
            String humSolo = params[3];
            String humAr = params[4];
            String pluv = params[5];
            String data = params[6];

            try {
                URL url = new URL(update_pandlet1_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);

                OutputStream OS = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(OS, "UTF-8"));

                String data_string = URLEncoder.encode("temperature", "UTF-8") + "=" + URLEncoder.encode(temp, "UTF-8") + "&" +
                        URLEncoder.encode("luminosity", "UTF-8") + "=" + URLEncoder.encode(lum, "UTF-8") + "&" +
                        URLEncoder.encode("soil_moisture", "UTF-8") + "=" + URLEncoder.encode(humSolo, "UTF-8") + "&" +
                        URLEncoder.encode("relative_humidity", "UTF-8") + "=" + URLEncoder.encode(humAr, "UTF-8") + "&" +
                        URLEncoder.encode("pluviosity", "UTF-8") + "=" + URLEncoder.encode(pluv, "UTF-8") + "&" +
                        URLEncoder.encode("timestamp", "UTF-8") + "=" + URLEncoder.encode(data, "UTF-8");

                bufferedWriter.write(data_string);
                bufferedWriter.flush();
                bufferedWriter.close();
                OS.close();

                // Get response from the server
                InputStream IS = httpURLConnection.getInputStream();
                IS.close();

                // Terminate connection
                httpURLConnection.disconnect();

                return "update_info_1";

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        else if (method.equals("get_info_1"))
        {
            StringBuilder stringBuilder = new StringBuilder();
            table = "Zona 1";

            try {
                URL url = new URL(getJson_pandlet1_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

                InputStream IS = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(IS));

                while ((JSON_STRING = bufferedReader.readLine()) != null)
                {
                    stringBuilder.append(JSON_STRING + "\n");
                }

                bufferedReader.close();
                IS.close();

                // Terminate connection
                httpURLConnection.disconnect();

                return stringBuilder.toString().trim();

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }



/*  --------- HISTORICO 2 --------- */
        else if (method.equals("add_info_2"))
        {
            String temp = params[1];
            String lum = params[2];
            String humSolo = params[3];
            String humAr = params[4];
            String pluv = params[5];
            String data = params[6];

            try {
                URL url = new URL(addInfo_pandlet2_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);

                OutputStream OS = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(OS, "UTF-8"));

                String data_string = URLEncoder.encode("temperature", "UTF-8") + "=" + URLEncoder.encode(temp, "UTF-8") + "&" +
                        URLEncoder.encode("luminosity", "UTF-8") + "=" + URLEncoder.encode(lum, "UTF-8") + "&" +
                        URLEncoder.encode("soil_moisture", "UTF-8") + "=" + URLEncoder.encode(humSolo, "UTF-8") + "&" +
                        URLEncoder.encode("relative_humidity", "UTF-8") + "=" + URLEncoder.encode(humAr, "UTF-8") + "&" +
                        URLEncoder.encode("pluviosity", "UTF-8") + "=" + URLEncoder.encode(pluv, "UTF-8") + "&" +
                        URLEncoder.encode("timestamp", "UTF-8") + "=" + URLEncoder.encode(data, "UTF-8");

                bufferedWriter.write(data_string);
                bufferedWriter.flush();
                bufferedWriter.close();
                OS.close();

                // Get response from the server
                InputStream IS = httpURLConnection.getInputStream();
                IS.close();

                // Terminate connection
                httpURLConnection.disconnect();

                return "add_info_2";

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        else if (method.equals("update_info_2"))
        {
            String temp = params[1];
            String lum = params[2];
            String humSolo = params[3];
            String humAr = params[4];
            String pluv = params[5];
            String data = params[6];

            try {
                URL url = new URL(update_pandlet2_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);

                OutputStream OS = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(OS, "UTF-8"));

                String data_string = URLEncoder.encode("temperature", "UTF-8") + "=" + URLEncoder.encode(temp, "UTF-8") + "&" +
                        URLEncoder.encode("luminosity", "UTF-8") + "=" + URLEncoder.encode(lum, "UTF-8") + "&" +
                        URLEncoder.encode("soil_moisture", "UTF-8") + "=" + URLEncoder.encode(humSolo, "UTF-8") + "&" +
                        URLEncoder.encode("relative_humidity", "UTF-8") + "=" + URLEncoder.encode(humAr, "UTF-8") + "&" +
                        URLEncoder.encode("pluviosity", "UTF-8") + "=" + URLEncoder.encode(pluv, "UTF-8") + "&" +
                        URLEncoder.encode("timestamp", "UTF-8") + "=" + URLEncoder.encode(data, "UTF-8");

                bufferedWriter.write(data_string);
                bufferedWriter.flush();
                bufferedWriter.close();
                OS.close();

                // Get response from the server
                InputStream IS = httpURLConnection.getInputStream();
                IS.close();

                // Terminate connection
                httpURLConnection.disconnect();

                return "update_info_2";

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        else if (method.equals("get_info_2"))
        {
            StringBuilder stringBuilder = new StringBuilder();
            table = "Zona 2";

            try {
                URL url = new URL(getJson_pandlet2_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

                InputStream IS = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(IS));

                while ((JSON_STRING = bufferedReader.readLine()) != null)
                {
                    stringBuilder.append(JSON_STRING + "\n");
                }

                bufferedReader.close();
                IS.close();

                // Terminate connection
                httpURLConnection.disconnect();

                return stringBuilder.toString().trim();

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
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

            try {
                URL url = new URL(addInfo_pandlet3_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);

                OutputStream OS = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(OS, "UTF-8"));

                String data_string = URLEncoder.encode("temperature", "UTF-8") + "=" + URLEncoder.encode(temp, "UTF-8") + "&" +
                        URLEncoder.encode("luminosity", "UTF-8") + "=" + URLEncoder.encode(lum, "UTF-8") + "&" +
                        URLEncoder.encode("soil_moisture", "UTF-8") + "=" + URLEncoder.encode(humSolo, "UTF-8") + "&" +
                        URLEncoder.encode("relative_humidity", "UTF-8") + "=" + URLEncoder.encode(humAr, "UTF-8") + "&" +
                        URLEncoder.encode("pluviosity", "UTF-8") + "=" + URLEncoder.encode(pluv, "UTF-8") + "&" +
                        URLEncoder.encode("timestamp", "UTF-8") + "=" + URLEncoder.encode(data, "UTF-8");

                bufferedWriter.write(data_string);
                bufferedWriter.flush();
                bufferedWriter.close();
                OS.close();

                // Get response from the server
                InputStream IS = httpURLConnection.getInputStream();
                IS.close();

                // Terminate connection
                httpURLConnection.disconnect();

                return "add_info_3";

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        else if (method.equals("update_info_3"))
        {
            String temp = params[1];
            String lum = params[2];
            String humSolo = params[3];
            String humAr = params[4];
            String pluv = params[5];
            String data = params[6];

            try {
                URL url = new URL(update_pandlet3_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);

                OutputStream OS = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(OS, "UTF-8"));

                String data_string = URLEncoder.encode("temperature", "UTF-8") + "=" + URLEncoder.encode(temp, "UTF-8") + "&" +
                        URLEncoder.encode("luminosity", "UTF-8") + "=" + URLEncoder.encode(lum, "UTF-8") + "&" +
                        URLEncoder.encode("soil_moisture", "UTF-8") + "=" + URLEncoder.encode(humSolo, "UTF-8") + "&" +
                        URLEncoder.encode("relative_humidity", "UTF-8") + "=" + URLEncoder.encode(humAr, "UTF-8") + "&" +
                        URLEncoder.encode("pluviosity", "UTF-8") + "=" + URLEncoder.encode(pluv, "UTF-8") + "&" +
                        URLEncoder.encode("timestamp", "UTF-8") + "=" + URLEncoder.encode(data, "UTF-8");

                bufferedWriter.write(data_string);
                bufferedWriter.flush();
                bufferedWriter.close();
                OS.close();

                // Get response from the server
                InputStream IS = httpURLConnection.getInputStream();
                IS.close();

                // Terminate connection
                httpURLConnection.disconnect();

                return "update_info_3";

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        else if (method.equals("get_info_3"))
        {
            StringBuilder stringBuilder = new StringBuilder();
            table = "Zona 3";

            try {
                URL url = new URL(getJson_pandlet3_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

                InputStream IS = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(IS));

                while ((JSON_STRING = bufferedReader.readLine()) != null)
                {
                    stringBuilder.append(JSON_STRING + "\n");
                }

                bufferedReader.close();
                IS.close();

                // Terminate connection
                httpURLConnection.disconnect();

                return stringBuilder.toString().trim();

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(String result) {

        if(result.equals("add_info_1") | result.equals("add_info_2") | result.equals("add_info_3"))     //  NOT USED
        {
            //Toast.makeText(ctx, "Nova linha inserida na BD Online!", Toast.LENGTH_SHORT).show();
            Log.i("ONLINE DB", "add_info: Nova linha inserida na BD");
        }
        else if(result.equals("update_info_1") | result.equals("update_info_2") | result.equals("update_info_3"))
        {
            //Toast.makeText(ctx, "Sincronização: Nova linha inserida na BD Online!", Toast.LENGTH_SHORT).show();
            Log.i("ONLINE DB", "update_info: Nova linha inserida na BD Online");
        }
        else
        {
            // Local DB - first we delete the local tables (pandlet1_table, pandlet2_table, pandlet3_table)
                if(table.equals("Zona 1")) {
                    BackgroundDbTask backgroundDbTaskDelete = new BackgroundDbTask(ctx);
                    backgroundDbTaskDelete.execute("delete_info_1");
                }
                if(table.equals("Zona 2")) {
                    BackgroundDbTask backgroundDbTaskDelete = new BackgroundDbTask(ctx);
                    backgroundDbTaskDelete.execute("delete_info_2");
                }
                if(table.equals("Zona 3")) {
                    BackgroundDbTask backgroundDbTaskDelete = new BackgroundDbTask(ctx);
                    backgroundDbTaskDelete.execute("delete_info_3");
                }

            json_string = result; // get the string with JSON info

            try {
                jsonObject = new JSONObject(json_string);
                jsonArray = jsonObject.getJSONArray("server_response");
                String temp, lum, humSolo, humAr, pluv, data;
                int count = 0;

                while(count < jsonArray.length())
                {
                    JSONObject JO = jsonArray.getJSONObject(count); // return the JSON object on the first index (row)
                        temp = JO.getString("temperature");
                        lum = JO.getString("luminosity");
                        humSolo = JO.getString("soil_moisture");
                        humAr = JO.getString("relative_humidity");
                        pluv = JO.getString("pluviosity");
                        data = JO.getString("timestamp");
                    count++;

                    // Local DB - write all data received from Online DB in its correspondent table in Local BD
                        if(table.equals("Zona 1")) {
                            BackgroundDbTask backgroundDbTask = new BackgroundDbTask(ctx);
                            backgroundDbTask.execute("add_info_1", temp, lum, humSolo, humAr, pluv, data);
                        }
                        if(table.equals("Zona 2")) {
                            BackgroundDbTask backgroundDbTask = new BackgroundDbTask(ctx);
                            backgroundDbTask.execute("add_info_2", temp, lum, humSolo, humAr, pluv, data);
                        }
                        if(table.equals("Zona 3")) {
                            BackgroundDbTask backgroundDbTask = new BackgroundDbTask(ctx);
                            backgroundDbTask.execute("add_info_3", temp, lum, humSolo, humAr, pluv, data);
                        }
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }



        if (progress != null && progress.isShowing())
            progress.dismiss();



    }


}
