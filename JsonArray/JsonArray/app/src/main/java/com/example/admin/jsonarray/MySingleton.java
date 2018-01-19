package com.example.admin.jsonarray;

/**
 * Created by admin on 25-04-2017.
 */

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by admin on 25-04-2017.
 */

public class MySingleton {

    private static MySingleton mInstance;
    private static Context text;
    private RequestQueue requestQueue;

    private MySingleton(Context context){
        text = context;
        requestQueue= getRequestQueue();
    }


    public RequestQueue getRequestQueue() {
        if(requestQueue==null){
            requestQueue= Volley.newRequestQueue(text.getApplicationContext());
        }

        return requestQueue;
    }
    public static synchronized MySingleton getInstance(Context context)
    {
        if(mInstance==null){
            mInstance = new  MySingleton(context);
        }
        return mInstance;
    }
    public <T> void addToRequestQue(Request<T> request)
    {
        requestQueue.add(request);
    }
}
