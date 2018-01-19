package com.example.admin.jsonarray;

/**
 * Created by admin on 11-05-2017.
 */

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
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.methods.HttpGet;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;
import cz.msebera.android.httpclient.util.EntityUtils;

public class Data extends Activity {
    Spinner offendernamespinner;
    EditText nameedit,mobilenoedit,addressedit,occupationedit,anyproofspinner;
    Button addnew,savebutton;
//    RequestQueue requestQueue;
    DatabaseHandler db;
    ListView listprinting;
    ProgressDialog pDialog;
    //String[] offendername = {"","father","mother", "sister", "brother"};

    private SimpleCursorAdapter dataAdapter;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.offenderfamily);
//        requestQueue = Volley.newRequestQueue(this);
        offendernamespinner = (Spinner) findViewById(R.id.offendernamespinner);
        anyproofspinner = (EditText) findViewById(R.id.anyproofspinner);
        nameedit = (EditText) findViewById(R.id.nameedit);
        mobilenoedit = (EditText) findViewById(R.id.mobilenoedit);
        addressedit = (EditText) findViewById(R.id.addressedit);
        occupationedit = (EditText) findViewById(R.id.occupationedit);
        listprinting=(ListView)findViewById(R.id.listprinting);

        addnew = (Button) findViewById(R.id.addnew);
        savebutton=(Button)findViewById(R.id.savebutton);

        SharedPreferences sharedPreferences= getSharedPreferences("offender",0);
        final int transid=sharedPreferences.getInt("name",0);


        Log.d("transid2", String.valueOf(transid));


//        String url="http://crimeproneareaservice.logicshore.co.in//LogicShore.svc/MOspinner";
//        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, url, null,
//                new Response.Listener<JSONObject>()
//                {
//                    @Override
//                    public void onResponse(JSONObject response) {
//                        ArrayList<String> moarray=new ArrayList<String >();
//                        moarray.add("select");
//                        try {
//                            JSONArray mospinner=response.getJSONArray("MOspinner");
//                            for(int i=0;i<mospinner.length();i++){
//                                JSONObject motypelist = mospinner.getJSONObject(i);
//                                String motypevalue=motypelist.getString("SubId");
//                                if(motypevalue.equals("1")){
//                                    //Master_Value
//                                    moarray.add( motypelist.getString("Master_Value"));
//
//                                }
//
//
//                                Log.d("moarray", String.valueOf(moarray));
//                            }
//                            ArrayAdapter autopsname1 = new ArrayAdapter(Data.this,android.R.layout.simple_spinner_item, moarray);
//                            autopsname1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//
//                            offendernamespinner.setAdapter(autopsname1);
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


         db = new DatabaseHandler(this);
        addnew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(offendernamespinner.getSelectedItem().equals("select")){
                    TextView errorText = (TextView)offendernamespinner.getSelectedView();
                    errorText.setError("please select value");
                    errorText.setFocusable(true);

                    offendernamespinner.setFocusable(true);
                }
                else if(nameedit.getText().toString().length()==0){
                    nameedit.setError("This field is Mandatory");
                    nameedit.setFocusable(true);

                }
                //int id, String relationtype, String name, String mobileno, String address, String occupation,String anyproof
                //int id, String relationtype, String name, String mobileno, String address, String occupation,String anyproof
                else {
                    db.addContact(new Contact(1, String.valueOf(offendernamespinner.getSelectedItem()), nameedit.getText().toString(), mobilenoedit.getText().toString(), addressedit.getText().toString(), occupationedit.getText().toString(), anyproofspinner.getText().toString(), "OffenderFamily"));

                    Cursor c = db.getAllContacts();
                    while (c.moveToNext()) {
                        Log.d("idvalues", c.getString(0));
                    }

                    TodoCursorAdapter todoAdapter = new TodoCursorAdapter(Data.this, c);
//                   if(offendernamespinner.getSelectedItem().equals("select")){
//                       Toast.makeText(getApplicationContext(),"please select Relation Type",Toast.LENGTH_SHORT).show();
//                   }
//                else {
                    listprinting.setAdapter(todoAdapter);
//                   }
                    clearText();
                }
            }

            private void clearText() {
                nameedit.setText("");
                mobilenoedit.setText("");
                addressedit.setText("");
                occupationedit.setText("");
                anyproofspinner.setText("");
            }
        });
//        View view = this.getCurrentFocus();
//        if (view != null) {
//            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
//            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
//        }

        savebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Data.this, Associates.class);
                startActivity(i);

            }
        });
        new GetContacts().execute();
    }
    private class GetContacts extends AsyncTask<String, String, String> {

        ProgressDialog pd;
        String st;
        @Override
        protected void onPreExecute() {
            pd=new ProgressDialog(Data.this);
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
                    if (motypevalue.equals("1")) {
                        //Master_Value
                        moarray.add(motypelist.getString("Master_Value"));

                    }

                    Log.d("moarray", String.valueOf(moarray));
                }

                ArrayAdapter<String> autopsname1 = new ArrayAdapter<String>(Data.this,android.R.layout.simple_spinner_item, moarray);
                autopsname1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                offendernamespinner.setAdapter(autopsname1);

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
        Cursor c = db.getAllContacts();
        TodoCursorAdapter todoAdapter = new TodoCursorAdapter(Data.this, c);
        listprinting.setAdapter(todoAdapter);
    }
}