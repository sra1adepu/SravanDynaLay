package com.example.admin.jsonarray;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.methods.HttpGet;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;
import cz.msebera.android.httpclient.util.EntityUtils;


/**
 * Created by admin on 11-05-2017.
 */

public class Associates extends Activity{
   Spinner associatesedittype;
    RequestQueue requestQueue;
    EditText associatesdescptedit,associatesmobileedit,associatesoccupationedit,associatesnameedit,associatesaddressedit;
    Button associatesaddnew,associatessavebutton,previous;

    DatabaseHandler db;
    ListView listprinting;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.associates);
        requestQueue = Volley.newRequestQueue(this);
        associatesnameedit = (EditText) findViewById(R.id.associatesnameedit);
        associatesaddressedit = (EditText) findViewById(R.id.associatesaddressedit);
        associatesedittype = (Spinner) findViewById(R.id.associatesedittype);
        associatesdescptedit = (EditText) findViewById(R.id.associatesdescptedit);
        associatesmobileedit = (EditText) findViewById(R.id.associatesmobileedit);
        associatesoccupationedit = (EditText) findViewById(R.id.associatesoccupationedit);
        associatesaddnew = (Button) findViewById(R.id.associatesaddnew);
        associatessavebutton=(Button)findViewById(R.id.associatessavebutton);
        listprinting=(ListView)findViewById(R.id.listprinting);

        SharedPreferences sharedPreferences= getSharedPreferences("offender",0);
        final int transid=sharedPreferences.getInt("name",0);
        Log.d("transid3", String.valueOf(transid));


//        String url="http://crimeproneareaservice.logicshore.co.in//LogicShore.svc/MOspinner";
//        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, url, null,
//                new Response.Listener<JSONObject>()
//                {
//                    @Override
//                    public void onResponse(JSONObject response) {
//                        ArrayList<String> moarray1=new ArrayList<String >();
//                        moarray1.add("Select");
//                        try {
//                            JSONArray mospinner=response.getJSONArray("MOspinner");
//                            for(int i=0;i<mospinner.length();i++){
//                                JSONObject motypelist = mospinner.getJSONObject(i);
//                                String motypevalue=motypelist.getString("SubId");
//                                if(motypevalue.equals("2")){
//                                    //Master_Value
//                                    moarray1.add( motypelist.getString("Master_Value"));
//
//                                }
//
//
//                                Log.d("moarray", String.valueOf(moarray1));
//                            }
//                            ArrayAdapter autopsname1 = new ArrayAdapter(Associates.this,android.R.layout.simple_spinner_item, moarray1);
//                            autopsname1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//
//                            associatesedittype.setAdapter(autopsname1);
//
//
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                        // display response
//                        Log.d("Response", response.toString());
//                    }
//                },
//                new Response.ErrorListener()
//                {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//
//
//                        //Log.d("Error.Response", response);
//                    }
//                }
//        );
//        requestQueue.add(getRequest);


//        ArrayAdapter spinnerArrayAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, type);
//        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//
//        associatesedittype.setAdapter(spinnerArrayAdapter);
        db = new DatabaseHandler(this);
        associatesaddnew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (associatesedittype.getSelectedItem().equals("Select")) {
                    TextView errorText = (TextView) associatesedittype.getSelectedView();
                    errorText.setError("please select value");
                    errorText.setFocusable(true);
                    associatesedittype.setFocusable(true);
                }
                else {
                    db.associatesinsert(1, associatesnameedit.getText().toString(), associatesaddressedit.getText().toString(), String.valueOf(associatesedittype.getSelectedItem()), associatesdescptedit.getText().toString(), associatesmobileedit.getText().toString(), associatesoccupationedit.getText().toString(), "associates");
                    Cursor c1 = db.getAllAsscoiaates();
                    while (c1.moveToNext()) {
                        Log.d("dude", c1.getString(0));
                    }

                    AssociatesAdaptor todoAdapter = new AssociatesAdaptor(Associates.this, c1);
                    listprinting.setAdapter(todoAdapter);
                    clearText();

                }
            }

            private void clearText() {
                associatesnameedit.setText("");
                associatesaddressedit.setText("");
                associatesdescptedit.setText("");
                associatesmobileedit.setText("");
                associatesoccupationedit.setText("");
            }
        });
//        View view = this.getCurrentFocus();
//        if (view != null) {
//            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
//            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
//        }

        associatessavebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Associates.this, InvolvedCases.class);
                startActivity(i);
            }
        });
//        previous.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finish();
//            }
//        });
        new GetAssociates().execute();
    }
    private class GetAssociates extends AsyncTask<String, String, String> {

        ProgressDialog pd;
        String st;
        @Override
        protected void onPreExecute() {
            pd=new ProgressDialog(Associates.this);
            pd.setMessage("Please wait");
            pd.show();
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {

            try {
//                HttpClient httpClient = new DefaultHttpClient();
//                HttpGet httpGet = new HttpGet("http://crimeproneareaservice.logicshore.co.in//LogicShore.svc/MOspinner");
//                HttpResponse httpResponse=httpClient.execute(httpGet);
//                Log.d("http response",httpResponse.toString());
//                st= EntityUtils.toString(httpResponse.getEntity());
//                Log.d("Http Status",st);
                HttpClient httpClient= new DefaultHttpClient();
                HttpGet httpGet= new HttpGet("http://crimeproneareaservice.logicshore.co.in//LogicShore.svc/MOspinner");
                HttpResponse httpResponse=httpClient.execute(httpGet);
                // Log.d("http response",httpResponse.toString());
                st= EntityUtils.toString(httpResponse.getEntity());
                Log.d("Http Status",st);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            return st;

        }
        protected void onPostExecute(String result) {
            pd.dismiss();
            Log.d("result", result);
            ArrayList<String> moarray = new ArrayList<String>();
            moarray.add("select");
            try {
                JSONObject json = new JSONObject(result);
                JSONArray mospinner = json.getJSONArray("MOspinner");
                for (int i = 0; i < mospinner.length(); i++) {
                    JSONObject motypelist = mospinner.getJSONObject(i);
                    String motypevalue = motypelist.getString("SubId");
                    if (motypevalue.equals("2")) {
                        //Master_Value
                        moarray.add(motypelist.getString("Master_Value"));

                    }

                    Log.d("moarray", String.valueOf(moarray));
                }

                ArrayAdapter<String> autopsname1 = new ArrayAdapter<String>(Associates.this,android.R.layout.simple_spinner_item, moarray);
                autopsname1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                associatesedittype.setAdapter(autopsname1);

            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            // Toast.makeText(getApplicationContext(),"MOspinner:"+moarray,Toast.LENGTH_LONG).show();
            Log.d("result is",result);
            super.onPostExecute(result);
        }


    }
    @Override
    protected void onResume() {
        super.onResume();
        Cursor c1 = db.getAllAsscoiaates();
        AssociatesAdaptor todoAdapter = new AssociatesAdaptor(Associates.this, c1);

        listprinting.setAdapter(todoAdapter);
    }
    }