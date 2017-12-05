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
import java.util.Arrays;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;
import static com.thomasapp.recycleviewtest.R.layout.contacts;

public class Tab2Contactes extends Fragment {

    RecyclerView recyclerView;
    AlertDialog.Builder builder;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(contacts, container, false);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view_contacts_list);
        newsupdate();


        //Toast.makeText(getActivity(), (CharSequence), Toast.LENGTH_LONG).show();
        //SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MYPREFF", MODE_PRIVATE);
        //final String users = sharedPreferences.getString("displayy","");

        Button T = (Button) rootView.findViewById(R.id.buttonlogintobdd);
        T.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MYPREF", MODE_PRIVATE);
                final String username = sharedPreferences.getString("display","");
                if(!username.equals("")){
                    builder.setTitle("");
                    builder.setMessage("You are already connected:)");
                    displayAlertlogin();
                }else {
                    Intent i = new Intent(getActivity(), ContactsLogin.class);
                    startActivity(i);
                }
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

        builder = new AlertDialog.Builder(getActivity(), R.style.MyDialogTheme);
        Button buttonlogout = (Button) rootView.findViewById(R.id.buttonlogoutfrombdd);
        buttonlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MYPREF", MODE_PRIVATE);
                final String username = sharedPreferences.getString("display","");
                if(!username.equals("")){
                    builder.setTitle("");
                    builder.setMessage("Are you sure you want to logout?");
                    displayAlert();
                }else {
                    builder.setTitle("");
                    builder.setMessage("You are not connected, no ways to logout;)");
                    displayAlertlogin();
                }
            }
        });
        return rootView;
    }

    public void newsupdate() {
        new Tab2Contactes.BackgroundTaskGetContacts(getActivity()).execute();
    }

    public class BackgroundTaskGetContacts extends AsyncTask<String, Contacts, String> {

        String json_string = "http://192.168.1.12/winesbdd/getcontactsfrombdd.php";
        Context ctx;
        Activity activity;
        RecyclerView.Adapter adapter;
        RecyclerView.LayoutManager layoutManager;
        ArrayList<Contacts> arrayList = new ArrayList<>();
        ArrayList<String> listtest = new ArrayList<>();

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
        protected String doInBackground(String... params) {
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
                final JSONObject jsonObject = new JSONObject(json_string);
                final JSONArray jsonArray = jsonObject.getJSONArray("server_response");


                //int count = 0;
                for (int i = 0; i < jsonArray.length(); i++) {
                    final JSONObject JO = jsonArray.getJSONObject(i);
                    //count++;
                    Contacts contacts = new Contacts(JO.getString("contacttoadd"));
                    listtest.add(contacts.getUsermail());
                    publishProgress(contacts);
                }

                activity.runOnUiThread(new Runnable() {
                    public void run() {

                        String resultuserstofilter = listtest.toString();

                        //Toast.makeText(getActivity(),listtest.toString(), Toast.LENGTH_LONG).show();
                        SharedPreferences.Editor editor = getActivity().getSharedPreferences("MYPREFF", MODE_PRIVATE).edit();
                        editor.putString("displayy", resultuserstofilter);
                        editor.apply();

                        /*SharedPreferences sharedpreferences = getActivity().getSharedPreferences("MYPREFF", MODE_PRIVATE);
                        String users = sharedpreferences.getString(jsonArray.toString(), jsonArray.toString());
                        SharedPreferences.Editor editor =sharedpreferences.edit();
                        editor.putStringSet("displayy",users);
                        editor.apply();*/
                    }
                });

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
        protected void onPostExecute(String result) {
            //super.onPostExecute(result);
            //Toast.makeText(getActivity(),, Toast.LENGTH_LONG).show();
        }
    }

    public void displayAlert() {
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MYPREF", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();
                editor.apply();
                //need app actualization
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
    public void displayAlertlogin() {
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

}
