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

public class BackgroundTaskContactsAdd extends AsyncTask<String, Void, String> {

    Context ctx;
    BackgroundTaskContactsAdd (Context ctx)
    {
        this.ctx =ctx;
    }
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }
    @Override
    protected String doInBackground(String... params) {
        String addcontactstobdd_url = "http://192.168.1.12/winesbdd/addcontacts.php";
        String method = params[0];
        if (method.equals("addcontactstobdd"))
        {
            String contacttoadd = params[1];
            String userwhoadd = params[2];
            try {
                URL url = new URL(addcontactstobdd_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                OutputStream OS = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(OS,"UTF-8"));
                String data = URLEncoder.encode("contacttoadd","UTF-8") +"="+URLEncoder.encode(contacttoadd,"UTF-8")+"&"+URLEncoder.encode("userwhoadd","UTF-8") +"="+URLEncoder.encode(userwhoadd,"UTF-8");
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
