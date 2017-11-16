package com.thomasapp.recycleviewtest;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by thomasdechaseaux on 23/10/2017.
 */

public class AddContacts extends Activity {

    EditText ET_contacts;
    Button BT_addcontact;
    String contacttoadd, userwhoadd;

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
    }

    public void addcontactstobdd (View view){
        contacttoadd = ET_contacts.getText().toString();


       // Toast.makeText(this,userwhoadd, Toast.LENGTH_SHORT).show();
        String method ="addcontactstobdd";
        BackgroundTaskContactsAdd backgroundTask = new BackgroundTaskContactsAdd (this);
        backgroundTask.execute(method,contacttoadd,userwhoadd);
        finish();
    }
}
