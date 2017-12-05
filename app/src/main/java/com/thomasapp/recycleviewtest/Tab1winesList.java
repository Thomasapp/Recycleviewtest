package com.thomasapp.recycleviewtest;

/**
 * Created by thomasdechaseaux on 11/09/2017.
 */


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;


public class Tab1winesList extends Fragment {

    RecyclerView recyclerView;
    AlertDialog.Builder builder;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.wines_list, container, false);

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MYPREF", MODE_PRIVATE);
        String username = sharedPreferences.getString("display","");
        builder = new AlertDialog.Builder(getActivity(), R.style.MyDialogTheme);

            if (username.equals("")) {
                builder.setTitle("Welcome to Wineforfriends;)");
                builder.setMessage("First you need to login;)");
                displayAlert();
            } else {
                recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view_wines_list);
                newsupdate();

                ImageButton T = (ImageButton) rootView.findViewById(R.id.addanewwine);
                T.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(getActivity(), AddWine.class);
                        startActivity(i);
                    }
                });
            }
        return rootView;
    }

    public void newsupdate() {
        new BackgroundTaskGetWines(getActivity()).execute();
    }

    public class BackgroundTaskGetWines extends AsyncTask<String, Wines, Void> {

        String json_string = "http://192.168.1.12/winesbdd/getwinefrombdd.php";

        Context ctx;
        Activity activity;
        RecyclerView.Adapter adapter;
        RecyclerView.LayoutManager layoutManager;
        ArrayList<Wines> arrayList = new ArrayList<>();

        public BackgroundTaskGetWines(Context ctx) {
            this.ctx = ctx;
            activity = (Activity) ctx;
        }

        @Override
        protected void onPreExecute() {

            layoutManager = new LinearLayoutManager(ctx);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setHasFixedSize(true);
            adapter = new RecyclerAdapterWinesList(arrayList);
            recyclerView.setAdapter(adapter);
        }

        @Override
        protected Void doInBackground(String... params) {

            SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MYPREF", MODE_PRIVATE);
            String usermail = sharedPreferences.getString("display","");
            //SharedPreferences sharedPreferencess = getActivity().getSharedPreferences("MYPREFF", MODE_PRIVATE);
            //String usermails = sharedPreferencess.getString("displayy","");

            //usermail = params[0];

            try {
                URL url = new URL(json_string);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                OutputStream OS = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(OS,"UTF-8"));
                String data = URLEncoder.encode("usermail","UTF-8") +"="+URLEncoder.encode(usermail,"UTF-8");//+"&"+URLEncoder.encode("usermails","UTF-8") +"="+URLEncoder.encode(usermails,"UTF-8");
                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                OS.close();
                InputStream IS = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(IS));
                StringBuilder stringBuilder = new StringBuilder();
                String line;

                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line).append("\n");
                }

                httpURLConnection.disconnect();
                String json_string = stringBuilder.toString().trim();
                JSONObject jsonObject = new JSONObject(json_string);
                JSONArray jsonArray = jsonObject.getJSONArray("server_response");
                int count = 0;
                while (count < jsonArray.length()) {
                    JSONObject JO = jsonArray.getJSONObject(count);
                    count++;
                    Wines wines = new Wines(JO.getString("chateaux"), JO.getString("cuvée"),JO.getString("commentaire"), JO.getString("type"));
                    publishProgress(wines);
                }
                Log.d("JSON STRING", json_string);


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onProgressUpdate(Wines... values) {
            arrayList.add(values[0]);
            adapter.notifyDataSetChanged();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }

    }

    public void displayAlert() {
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent i = new Intent(getActivity(), ContactsLogin.class);
                startActivity(i);
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

}






