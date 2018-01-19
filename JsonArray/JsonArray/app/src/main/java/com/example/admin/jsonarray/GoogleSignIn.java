package com.example.admin.jsonarray;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

public class GoogleSignIn extends   Activity {

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);


        String number =getMyPhoneNO();
        Toast.makeText(getApplicationContext(), "My Phone No is: "
                +number, Toast.LENGTH_SHORT).show();
        Log.v("Debug", number);
    }

    private String getMyPhoneNO(){
        TelephonyManager mTelephonyMgr;
        mTelephonyMgr = (TelephonyManager)GoogleSignIn.this.getSystemService
                (Context.TELEPHONY_SERVICE);

        String yourNumber = mTelephonyMgr.getLine1Number();
        return yourNumber;
    }
}
