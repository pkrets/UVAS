package com.setec2015.appsetec;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
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
public class BackgroundOnlineDbTask extends AsyncTask<String, ListData, String> {

    Context ctx;

    Activity activity;

    ListDataAdapter listDataAdapter;

    ListView historicoListView;

    String JSON_STRING;
    String json_string;
    JSONObject jsonObject;
    JSONArray jsonArray;

    Boolean LoginSuccess;
    String welcome;

    //AlertDialog alertDialog;

    BackgroundOnlineDbTask(Context ctx) {
        this.ctx = ctx;
        activity = (Activity) ctx;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        //alertDialog = new AlertDialog.Builder(ctx.getApplicationContext()).create();
        //alertDialog.setTitle("Login Information");
    }

    @Override
    protected String doInBackground(String... params) {

        String login_url = "http://androidsetec.netau.net/login.php";
        String addInfo_pandlet1_url = "http://androidsetec.netau.net/write_pandlet1.php";
        String addInfo_pandlet2_url = "http://androidsetec.netau.net/write_pandlet2.php";
        String addInfo_pandlet3_url = "http://androidsetec.netau.net/write_pandlet3.php";
        String getJson_pandlet1_url = "http://androidsetec.netau.net/read_pandlet1_json.php";
        String getJson_pandlet2_url = "http://androidsetec.netau.net/read_pandlet2_json.php";
        String getJson_pandlet3_url = "http://androidsetec.netau.net/read_pandlet3_json.php";

        String method = params[0];

/*  --------- LOGIN --------- */
        if (method.equals("login"))
        {
            String email = params[1];
            String password = params[2];

            try {
                URL url = new URL(login_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);

                OutputStream OS = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(OS, "UTF-8"));

                String data = URLEncoder.encode("email", "UTF-8") +  "=" + URLEncoder.encode(email, "UTF-8") + "&" +
                        URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(password, "UTF-8");

                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                OS.close();

                // Get response from the server
                InputStream IS = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(IS, "iso-8859-1"));

                String response = "";
                String line = "";
                while ((line = bufferedReader.readLine()) != null) {
                    response += line;
                }
                bufferedReader.close();
                IS.close();

                // Terminate connection
                httpURLConnection.disconnect();

                Log.d("LOGIN", "response = " + response);

                if(response.equals("Login Falhou!")) {
                    LoginSuccess = false;
                }
                else {
                    LoginSuccess = true;
                    welcome = response;
                }

                return "Iniciar Sessão Online";

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

/*  --------- HISTORICO 1 --------- */
        else if (method.equals("add_info_1"))
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

                return "Nova linha inserida na BD!";

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        else if (method.equals("get_info_1"))
        {
            StringBuilder stringBuilder = new StringBuilder();

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

                return "Nova linha inserida na BD!";

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

                return "Nova linha inserida na BD!";

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    @Override
    protected void onProgressUpdate(ListData... values) {
        listDataAdapter.add(values[0]);
    }

    @Override
    protected void onPostExecute(String result) {

        if(result.equals("Iniciar Sessão Online"))
        {
            if(LoginSuccess) {
                Toast.makeText(ctx, "Sessão online iniciada com sucesso!\n\n" + welcome, Toast.LENGTH_LONG).show();

                SharedPreferences prefs = ctx.getSharedPreferences("LoginStatus", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();
                editor.putBoolean("isLogged", true);
                editor.commit();

                    Boolean log = prefs.getBoolean("isLogged", false);
                    Log.i("LOGGED STATUS 2", String.valueOf(log));

                Intent intent = new Intent();
                intent.setClass(ctx, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // is there other way ?
                ctx.startActivity(intent);
            }
            else if (!LoginSuccess){
                Toast.makeText(ctx, "Iniciar sessão online falhou!\n\nOs campos 'E-mail' e/ou 'Password' estão incorrectos...", Toast.LENGTH_LONG).show();
            }
        }
        else if(result.equals("Nova linha inserida na BD!"))
        {
            Toast.makeText(ctx, result, Toast.LENGTH_LONG).show();
        }
        else
        {
            json_string = result;
                Log.i("READ ONLINE DB", result);

            try {
                jsonObject = new JSONObject(json_string);
                jsonArray = jsonObject.getJSONArray("server_response");
                    Log.i("JSON ARRAY", jsonArray.toString());
                String temp, lum, humSolo, humAr, pluv, data;

                int count = 0;
                    Log.i("LENGTH OF JSON ARRAY", String.valueOf(jsonArray.length()));

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

                    // Local DB - write all data received from Online DB - Zona 1
                        BackgroundDbTask backgroundDbTask = new BackgroundDbTask(ctx);
                        backgroundDbTask.execute("add_info_1", temp, lum, humSolo, humAr, pluv, data);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

}
