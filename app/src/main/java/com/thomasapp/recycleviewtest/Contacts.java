package com.thomasapp.recycleviewtest;

import android.widget.Toast;

/**
 * Created by thomasdechaseaux on 16/11/2017.
 */

public class Contacts {

    public String usermail;

    public Contacts (String usermail)
    {
        this.setUsermail(usermail);
    }

    public String getUsermail() {
        return usermail;
    }

    public void setUsermail(String usermail) {
        this.usermail = usermail;
    }


}