package com.thomasapp.recycleviewtest;

import android.content.Context;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by thomasdechaseaux on 03/11/2017.
 */

public class MySingleton {
    //private static final String REGISTER_REQUEST_URL = "http://192.168.1.12/winesbdd/register.php";
    private static MySingleton minstance;
    private RequestQueue requestQueue;
    private static Context mCtx;

    private MySingleton (Context context){
        mCtx = context;
        requestQueue = getRequestQueue();
    }

    public  RequestQueue getRequestQueue(){

        if (requestQueue==null){
            requestQueue = Volley.newRequestQueue(mCtx.getApplicationContext());
        }
        return requestQueue;
    }

    public static synchronized MySingleton getInstance(Context context){

        if (minstance==null){
            minstance= new MySingleton(context);
        }
        return minstance;
    }

    public <T>void addToRequestque(Request<T> request){
        requestQueue.add(request);
    }
}
