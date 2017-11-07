package com.thomasapp.recycleviewtest;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by thomasdechaseaux on 23/10/2017.
 */

public class ContactsLogin extends Activity {

    EditText ET_mail, ET_name;
    String mail, name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contacts_login);

        ET_mail = (EditText) findViewById(R.id.editTextmaillogin);
        ET_name = (EditText) findViewById(R.id.editTextpseudologin);
    }
    public void logintobdd (View view){

        mail = ET_mail.getText().toString();
        name = ET_name.getText().toString();

        String method = "logintobdd";
        if (mail.equals("") || name.equals("")) {

            Toast.makeText(getApplicationContext(),"Something W W", Toast.LENGTH_LONG).show();
        }
        else {

            BackgroundTaskContactsLogin backgroundTask = new BackgroundTaskContactsLogin (this);
            backgroundTask.execute(method,mail,name);
            finish();
        }
    }
}
