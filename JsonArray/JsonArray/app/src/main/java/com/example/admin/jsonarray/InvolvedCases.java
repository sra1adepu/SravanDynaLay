package com.example.admin.jsonarray;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import java.util.Calendar;
import java.util.List;

/**
 * Created by admin on 12-05-2017.
 */

public class InvolvedCases extends Activity {
EditText involvedpsspinner,involvedsocrimespinner;
    EditText involvedcrimeedit,involveddateofarrestedit;
    Button involvedaddnew,involvedsavebutton;
    RequestQueue requestQueue;
    SimpleDateFormat mdformatpresent;
    //String[] pstype={"a","b","c","d"};
    DatePickerDialog dpDialog;
    List<String> psal;
    String[] socrime={"select","Detected","Undetected"};
    DatabaseHandler db;
    ListView listprinting;
    Calendar call;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.involvedcases);
        requestQueue = Volley.newRequestQueue(this);
        involvedpsspinner=(EditText)findViewById(R.id.involvedpsspinner);
        involvedsocrimespinner=(EditText) findViewById(R.id.involvedsocrimespinner);
        involvedcrimeedit=(EditText)findViewById(R.id.involvedcrimeedit);
        involveddateofarrestedit=(EditText)findViewById(R.id.involveddateofarrestedit);
        involvedaddnew=(Button)findViewById(R.id.involvedaddnew);
        involvedsavebutton=(Button)findViewById(R.id.involvedsavebutton);
        listprinting=(ListView)findViewById(R.id.listprinting);


        mdformatpresent = new SimpleDateFormat("dd-MM-yyyy");
        call = Calendar.getInstance();
        dpDialog=new DatePickerDialog(InvolvedCases.this, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                call = Calendar.getInstance();
                call.set(year, monthOfYear, dayOfMonth);
                involveddateofarrestedit.setText(mdformatpresent.format(call.getTime()));
            }
        }, call.get(Calendar.YEAR), call.get(Calendar.MONTH), call.get(Calendar.DAY_OF_MONTH));
        involveddateofarrestedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dpDialog.show();
            }
        });
        db = new DatabaseHandler(this);
        involvedaddnew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.involvedinsert(1,involvedcrimeedit.getText().toString(),String.valueOf(involvedpsspinner.getText().toString()), involveddateofarrestedit.getText().toString(), String.valueOf(involvedsocrimespinner.getText().toString()), "involved");
                Cursor c2 = db.getAllInvolved();
                while (c2.moveToNext()) {
                    Log.d("involve", c2.getString(0));
                }

//
                InvolvedAdaptor todoAdapter = new InvolvedAdaptor(InvolvedCases.this, c2);

                listprinting.setAdapter(todoAdapter);
                clearText();
                involvedsavebutton.setVisibility(View.VISIBLE);

            }

            private void clearText() {
                involvedcrimeedit.setText("");
                involveddateofarrestedit.setText("");

            }
        });
//        View view = this.getCurrentFocus();
//        if (view != null) {
//            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
//            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
//        }
        involvedsavebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(InvolvedCases.this, OffernderDocument.class);
                i.putExtra("JSHD","V");
                startActivity(i);
            }
        });

    }

    protected void onResume() {
        super.onResume();
        Cursor c2 = db.getAllInvolved();

//
        InvolvedAdaptor todoAdapter = new InvolvedAdaptor(InvolvedCases.this, c2);

        listprinting.setAdapter(todoAdapter);
        clearText();
    }
    private void clearText() {
        involvedpsspinner.setText("");
        involvedsocrimespinner.setText("");
        involvedcrimeedit.setText("");
        involveddateofarrestedit.setText("");

    }
}