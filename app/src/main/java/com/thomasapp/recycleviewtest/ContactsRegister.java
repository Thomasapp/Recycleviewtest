package com.thomasapp.recycleviewtest;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static android.R.id.message;

/**
 * Created by thomasdechaseaux on 24/10/2017.
 */

public class ContactsRegister extends Activity{

    EditText ET_mail, ET_name, ET_mailbis, ET_firstname;
    Button B_Register;
    String mail, mailbis, name, firstname;
    AlertDialog.Builder builder;
    String REGISTER_REQUEST_URL = "http://192.168.1.12/winesbdd/register.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contacts_register);

        ET_mail = (EditText) findViewById(R.id.editTextmailregister);
        ET_mailbis = (EditText) findViewById(R.id.editTextmailbisregister);
        ET_name = (EditText) findViewById(R.id.editTextpseudoregister);
        ET_firstname = (EditText) findViewById(R.id.editTextpseudobisregister);
        B_Register = (Button) findViewById(R.id.buttonaddmailtobddregister);
        builder = new AlertDialog.Builder(ContactsRegister.this, R.style.MyDialogTheme);

        B_Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mail = ET_mail.getText().toString();
                mailbis = ET_mailbis.getText().toString();
                name = ET_name.getText().toString();
                firstname = ET_firstname.getText().toString();

                if (name.equals("") || mail.equals("")|| firstname.equals("")|| mailbis.equals("")) {
                    builder.setTitle("SOMTHING IS MISSING :)");
                    builder.setMessage("Please fill all the fields");
                    displayAlert("input_error");
                }

                else if (!mail.equals(mailbis)) {
                    builder.setTitle("SOMETHING IS MISSING :)");
                    builder.setMessage("Your mails are not matching");
                    displayAlert("input_error");
                }

                else{
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, REGISTER_REQUEST_URL,
                            new Response.Listener<String>() {

                                @Override
                                public void onResponse(String response) {
                                    try {
                                        JSONArray jsonArray = new JSONArray(response);
                                        JSONObject jsonObject = jsonArray.getJSONObject(0);
                                        String code = jsonObject.getString("code");
                                        String message = jsonObject.getString("message");
                                        builder.setTitle("Server Response...");
                                        builder.setMessage(message);
                                        displayAlert(code);

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }, new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {
                        }
                    }) {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> params = new HashMap<String, String>();
                            params.put("name", name);
                            params.put("mail", mail);
                            params.put("firstname", firstname);

                            return params;
                        }
                    };
                    MySingleton.getInstance(ContactsRegister.this).addToRequestque(stringRequest);
                }
            }
        });
    }

    public void displayAlert(final String code) {

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                if (code.equals("input_error")){

                    ET_name.setText("");
                    ET_mail.setText("");
                    ET_mailbis.setText("");
                    ET_firstname.setText("");
                }
                else if (code.equals("reg_success")){
                    finish();
                }
                else {
                    ET_name.setText("");
                    ET_mail.setText("");
                    ET_mailbis.setText("");
                    ET_firstname.setText("");
                }
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}

