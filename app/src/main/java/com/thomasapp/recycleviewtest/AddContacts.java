package com.thomasapp.recycleviewtest;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by thomasdechaseaux on 23/10/2017.
 */

public class AddContacts extends Activity {

    EditText ET_contacts;
    Button BT_addcontact;
    String contacttoadd, userwhoadd;
    AlertDialog.Builder builder;
    String ADD_CONTACT_REQUEST_URL = "http://192.168.1.12/winesbdd/addcontacts.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_contacts);
        ET_contacts = (EditText)findViewById(R.id.finaledittexteaddcontacts);
        BT_addcontact = (Button)findViewById(R.id.finalbuttonaddcontacts);

        SharedPreferences sharedPreferences = getSharedPreferences("MYPREF", MODE_PRIVATE);
        String display = sharedPreferences.getString("display","");
        userwhoadd = display;
        Toast.makeText(this,display, Toast.LENGTH_SHORT).show();

        builder = new AlertDialog.Builder(AddContacts.this, R.style.MyDialogTheme);

        BT_addcontact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                contacttoadd = ET_contacts.getText().toString();

                if (contacttoadd.equals("")) {
                    builder.setTitle("SWW");
                    displayAlert("Please fill the field");

                } else {
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, ADD_CONTACT_REQUEST_URL,
                            new Response.Listener<String>() {

                                @Override
                                public void onResponse(String response) {
                                    try {
                                        JSONArray jsonArray = new JSONArray(response);
                                        JSONObject jsonObject = jsonArray.getJSONObject(0);
                                        String code = jsonObject.getString("code");
                                        if (code.equals("reg_false")) {
                                            builder.setTitle("Contact error...");
                                            displayAlert(jsonObject.getString("message"));
                                        } else {
                                            Toast.makeText(AddContacts.this,"Successful adding, now in your contacts ;)", Toast.LENGTH_SHORT).show();
                                            finish();
                                            Intent i = new Intent(AddContacts.this, MainActivity.class);
                                            startActivity(i);
                                        }

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }

                                }
                            }, new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(AddContacts.this, "Error", Toast.LENGTH_LONG).show();
                            error.printStackTrace();
                        }
                    }) {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> params = new HashMap<String, String>();
                            params.put("contacttoadd", contacttoadd);
                            params.put("userwhoadd", userwhoadd);

                            return params;
                        }
                    };
                    MySingleton.getInstance(AddContacts.this).addToRequestque(stringRequest);
                }
            }
        });
    }

    public void displayAlert(final String message) {
        builder.setMessage(message);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                ET_contacts.setText("");
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    //public void addcontactstobdd (View view){
        //contacttoadd = ET_contacts.getText().toString();

        //String method ="addcontactstobdd";
        //BackgroundTaskContactsAdd backgroundTask = new BackgroundTaskContactsAdd (this);
        //backgroundTask.execute(method,contacttoadd,userwhoadd);
        //finish();
        //Intent i = new Intent(AddContacts.this, MainActivity.class);
        //startActivity(i);
    //}
}
