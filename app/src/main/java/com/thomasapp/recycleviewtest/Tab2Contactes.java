package com.thomasapp.recycleviewtest;

/**
 * Created by thomasdechaseaux on 11/09/2017.
 */

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

public class Tab2Contactes extends Fragment {

    TextView TV_backename, TV_backmail;
    EditText name, mail;
    RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.contacts, container, false);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view_contacts_list);
        newsupdate();

        Button T = (Button) rootView.findViewById(R.id.buttonlogintobdd);
        T.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), ContactsLogin.class);
                startActivity(i);
            }
        });

        ImageButton IM = (ImageButton) rootView.findViewById(R.id.addcontactstobdd);
        IM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), AddContacts.class);
                startActivity(i);
            }
        });

        Button buttonlogout = (Button) rootView.findViewById(R.id.buttonlogoutfrombdd);
        buttonlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MYPREF", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();
                editor.apply();
            }
        });

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MYPREF", MODE_PRIVATE);
        String display = sharedPreferences.getString("display","");
        //TV_backename = (TextView) rootView.findViewById(R.id.tvgetbacknamefrombdd);
        TV_backmail = (TextView) rootView.findViewById(R.id.tvgetbackmailfrombdd);
        TV_backmail.setText(display);

        return rootView;
    }

    public void newsupdate() {
        new Tab2Contactes.BackgroundTaskGetContacts(getActivity()).execute();
    }

    public class BackgroundTaskGetContacts extends AsyncTask<Void, Contacts, Void> {

        String json_string = "http://192.168.1.12/winesbdd/getcontactsfrombdd.php";

        Context ctx;
        Activity activity;
        RecyclerView.Adapter adapter;
        RecyclerView.LayoutManager layoutManager;
        ArrayList<Contacts> arrayList = new ArrayList<>();

        public BackgroundTaskGetContacts(Context ctx) {
            this.ctx = ctx;
            activity = (Activity) ctx;
        }

        @Override
        protected void onPreExecute() {

            layoutManager = new LinearLayoutManager(ctx);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setHasFixedSize(true);
            adapter = new RecyclerAdapterContactsList(arrayList);
            recyclerView.setAdapter(adapter);
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                URL url = new URL(json_string);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
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
                    Contacts contacts = new Contacts(JO.getString("contacttoadd"));
                    publishProgress(contacts);
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
        protected void onProgressUpdate(Contacts... values) {
            arrayList.add(values[0]);
            adapter.notifyDataSetChanged();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }

    }

}
