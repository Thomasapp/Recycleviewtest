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

public class ContactsLogin extends Activity {

    EditText ET_mail, ET_name;
    String mail, name;
    TextView TV_registeraccess;
    Button BT_login;
    AlertDialog.Builder builder;
    String LOGIN_REQUEST_URL = "http://192.168.1.12/winesbdd/login.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contacts_login);

        TV_registeraccess = (TextView) findViewById(R.id.TextViewregisteraccess);
        TV_registeraccess.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent i = new Intent(ContactsLogin.this, ContactsRegister.class);
                startActivity(i);
            }
        });

        ET_mail = (EditText) findViewById(R.id.editTextmaillogin);
        ET_name = (EditText) findViewById(R.id.editTextpseudologin);
        BT_login = (Button) findViewById(R.id.buttoncheckmailtobddlogin);
        builder = new AlertDialog.Builder(ContactsLogin.this, R.style.MyDialogTheme);

        BT_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mail = ET_mail.getText().toString();
                name = ET_name.getText().toString();

                if (name.equals("") || mail.equals("")) {
                    builder.setTitle("SWW");
                    displayAlert("Please fill with valid username and mail");

                } else {
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, LOGIN_REQUEST_URL,
                            new Response.Listener<String>() {

                                @Override
                                public void onResponse(String response) {
                                    try {
                                        JSONArray jsonArray = new JSONArray(response);
                                        JSONObject jsonObject = jsonArray.getJSONObject(0);
                                        String code = jsonObject.getString("code");
                                        if (code.equals("login_failed")) {
                                            builder.setTitle("Login error...");
                                            displayAlert(jsonObject.getString("message"));
                                        } else {
                                            SharedPreferences sharedpreferences = getSharedPreferences("MYPREF", MODE_PRIVATE);
                                            String usermail =sharedpreferences.getString(mail, mail);
                                            SharedPreferences.Editor editor =sharedpreferences.edit();
                                            editor.putString("display",usermail);
                                            editor.apply();
                                            finish();
                                            Intent i = new Intent(ContactsLogin.this, MainActivity.class);
                                            startActivity(i);
                                        }

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }

                                }
                            }, new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(ContactsLogin.this, "Error", Toast.LENGTH_LONG).show();
                            error.printStackTrace();
                        }
                    }) {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> params = new HashMap<String, String>();
                            params.put("name", name);
                            params.put("mail", mail);

                            return params;
                        }
                    };
                    MySingleton.getInstance(ContactsLogin.this).addToRequestque(stringRequest);
                }
            }
        });
    }

    public void displayAlert(final String message) {
        builder.setMessage(message);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                    ET_name.setText("");
                    ET_mail.setText("");
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}

