package com.thomasapp.recycleviewtest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;


/**
 * Created by thomasdechaseaux on 19/09/2017.
 */

public class SplashScreen extends Activity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
        Thread timer = new Thread() {
            public void run(){
                try {
                    sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    Intent MainActivity = new Intent("com.thomasapp.MAINACTIVITY");
                    startActivity(MainActivity);
                }
            }
        };
        timer.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}