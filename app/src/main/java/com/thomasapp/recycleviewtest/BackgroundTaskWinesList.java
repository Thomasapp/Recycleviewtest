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
 * Created by thomasdechaseaux on 02/10/2017.
 */

public class BackgroundTaskWinesList extends AsyncTask<String, Void, String>{

    Context ctx;
    BackgroundTaskWinesList (Context ctx)
    {
        this.ctx =ctx;
    }
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }
    @Override
    protected String doInBackground(String... params) {
        String addwinetobdd_url = "http://192.168.1.12/winesbdd/addwine.php";
        String method = params[0];
        if (method.equals("addwinetobdd"))
        {
            String chateaux = params[1];
            //String cuvée = params[2];
            String commentaire = params[2];
            String type = params[3];
            String usermail = params[4];
            try {
                URL url = new URL(addwinetobdd_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                OutputStream OS = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(OS,"UTF-8"));
                String data = URLEncoder.encode("chateaux","UTF-8") +"="+URLEncoder.encode(chateaux,"UTF-8")+"&"+URLEncoder.encode("commentaire","UTF-8") +"="+URLEncoder.encode(commentaire,"UTF-8")+"&"+URLEncoder.encode("type","UTF-8") +"="+URLEncoder.encode(type,"UTF-8")
                        +"&"+URLEncoder.encode("usermail","UTF-8") +"="+URLEncoder.encode(usermail,"UTF-8");
                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                OS.close();
                InputStream IS =httpURLConnection.getInputStream();
                IS.close();
                return "Data added correctly..";

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
        //Toast.makeText(ctx,result,Toast.LENGTH_LONG).show();
    }
}
