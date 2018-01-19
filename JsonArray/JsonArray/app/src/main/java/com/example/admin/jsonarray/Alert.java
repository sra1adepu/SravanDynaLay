package com.example.admin.jsonarray;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Alert extends Activity {
    /** Called when the activity is first created. */

    // this context will use when we work with Alert Dialog
    final Context context = this;

    // just for test, when we click this button, we will see the alert dialog.
    private Button button;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alert);

        button = (Button) findViewById(R.id.button_show_alert_dialog);

        //Create onclick listener class
        button.setOnClickListener(new View.OnClickListener() {
            //When you click the button, Alert dialog will be showed
            public void onClick(View v) {
       /* Alert Dialog Code Start*/
                AlertDialog.Builder alert = new AlertDialog.Builder(context);
                alert.setMessage("Are you sure, You wanted to make decision");
                alert.setMessage("Enter Your Password"); //Message here
                alert.setTitle("Do You want Continue this app ?");
                // Set an EditText view to get user input
                final EditText input = new EditText(context);
                alert.setView(input);

                alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        String srt = input.getEditableText().toString();
                        if(srt.equals("ganesh2k17")){
                            SharedPreferences.Editor editor=getSharedPreferences("MyPref",Context.MODE_PRIVATE).edit();
                            editor.putString("passwrd",srt);
                            editor.commit();
                            dialog.cancel();
                        }
                        else{


                        }

                        //Toast.makeText(context,srt,Toast.LENGTH_LONG).show();
                    } // End of onClick(DialogInterface dialog, int whichButton)
                }); //End of alert.setPositiveButton
                alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        // Canceled.
                        //dialog.cancel();
                    }
                }); //End of alert.setNegativeButton
                AlertDialog alertDialog = alert.create();
                alertDialog.show();
                alertDialog.setCancelable(false);
       /* Alert Dialog Code End*/
            }// End of onClick(View v)
        }); //button.setOnClickListener
    }//End of onCreate
}//class AlertDialogExample extends Activity
