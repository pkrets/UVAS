package com.setec2015.appsetec;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.text.Html;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
 * Created by Krets on 19/01/2016.
 */
public class BackgroundOnlineDbTask2 extends AsyncTask<String, Void, String> {

    Context ctx;

    Activity activity;

    Boolean LoginSuccess;
    String welcome;
    String table;

    ProgressDialog progress;

    BackgroundOnlineDbTask2(Context ctx) {
        this.ctx = ctx;
        activity = (Activity) ctx;
        this.progress = new ProgressDialog(ctx);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        this.progress.setMessage("A verificar credenciais de acesso ...");
        this.progress.show();
    }

    @Override
    protected String doInBackground(String... params) {

        String login_url = "http://androidsetec.netau.net/login.php";

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


        return null;
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(String result) {

        if(result.equals("Iniciar Sessão Online"))
        {
            if(LoginSuccess) {
                new android.app.AlertDialog.Builder(this.ctx)
                        .setTitle("Sessão Online")
                        .setIcon(R.mipmap.ic_login)
                        .setMessage(Html.fromHtml("<b>" + welcome + "</b>" +
                                "<br><br>Os registos do Histórico serão sincronizados com os registos da sua Base de Dados online e atualizados no seu telemóvel."))
                        .setCancelable(false)
                        .setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Intent intent = new Intent();
                                intent.setClass(ctx, MainActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // is there other way ?
                                ctx.startActivity(intent);
                            }
                        })
                        .show();


                // Local DB - get all rows from all tables (pandlet1_table, pandlet2_table, pandlet3_table)
                    BackgroundDbTask backgroundDbTaskA = new BackgroundDbTask(ctx);
                    backgroundDbTaskA.execute("get_new_1");

                    BackgroundDbTask backgroundDbTaskB = new BackgroundDbTask(ctx);
                    backgroundDbTaskB.execute("get_new_2");

                    BackgroundDbTask backgroundDbTaskC = new BackgroundDbTask(ctx);
                    backgroundDbTaskC.execute("get_new_3");


                SharedPreferences prefs = ctx.getSharedPreferences("LoginStatus", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();
                editor.putBoolean("isLogged", true);
                editor.commit();

                Boolean log = prefs.getBoolean("isLogged", false);
                Log.i("LOGGED STATUS 2", String.valueOf(log));
            }
            else if (!LoginSuccess){
                Toast.makeText(ctx, "Iniciar sessão online falhou!\n\nOs campos 'E-mail' e/ou 'Password' estão incorretos...", Toast.LENGTH_LONG).show();
            }
        }


        if (progress != null && progress.isShowing())
            progress.dismiss();
    }

}

