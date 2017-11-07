package com.thomasapp.recycleviewtest;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by thomasdechaseaux on 24/10/2017.
 */

public class BackgroundTaskContactsLogin extends AsyncTask<String, Void, String> {

    Context ctx;
    BackgroundTaskContactsLogin (Context ctx)
    {
        this.ctx =ctx;
    }
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... params) {
        String logintobdd_url = "http://192.168.1.12/winesbdd/login.php";
        String method = params[0];
        if (method.equals("logintobdd"))
        {
            String mail = params[1];
            String name = params[2];
            try {
                URL url = new URL(logintobdd_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                OutputStream OS = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(OS,"UTF-8"));
                String data = URLEncoder.encode("mail","UTF-8") +"="+URLEncoder.encode(mail,"UTF-8")+"&"+URLEncoder.encode("name","UTF-8") +"="+URLEncoder.encode(name,"UTF-8");
                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                OS.close();
                InputStream IS =httpURLConnection.getInputStream();
                IS.close();
                return "Data added correctly..";


            } catch (MalformedURLException e){
                e.printStackTrace();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return "nopnop";
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(String result) {
        Toast.makeText(ctx,result,Toast.LENGTH_LONG).show();
    }
}
