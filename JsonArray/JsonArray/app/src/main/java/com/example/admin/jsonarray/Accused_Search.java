package com.example.admin.jsonarray;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.icu.text.SimpleDateFormat;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Calendar;

import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.methods.HttpPost;
import cz.msebera.android.httpclient.entity.StringEntity;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;
import cz.msebera.android.httpclient.util.EntityUtils;

/**
 * Created by admin on 16-06-2017.
 */

public class Accused_Search extends Activity {
    EditText accusedname, accusedaliasname, fathername, agefrom, ageto,
            fromdate, todate, crimenumber, crimelocation;
    ListView accusedlist;
    Spinner psspinner;
    Accused_Adaptor accused_adaptor;
    ConnectionDetector cd;
    ArrayList psarraylist;
    RequestQueue requestQueue;
    Button buttonsearch;
    ArrayList<Accused_content> accused_List;
    Accused_content accused_content;
    String spin;
    SimpleDateFormat mdformatpresent;
     Calendar call;
    DatePickerDialog dpDialog,dpDialog1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.accused_search);
        accusedname = (EditText) findViewById(R.id.accusedname);
        accusedaliasname = (EditText) findViewById(R.id.accusedaliasname);
        fathername = (EditText) findViewById(R.id.fathername);
        agefrom = (EditText) findViewById(R.id.agefrom);
        ageto = (EditText) findViewById(R.id.ageto);
        fromdate = (EditText) findViewById(R.id.fromdate);
        todate = (EditText) findViewById(R.id.todate);
        crimenumber = (EditText) findViewById(R.id.crimenumber);
        crimelocation = (EditText) findViewById(R.id.crimelocation);
        psspinner = (Spinner) findViewById(R.id.psspinner);
        buttonsearch = (Button) findViewById(R.id.buttonsearch);
        cd = new ConnectionDetector(this);
        accusedlist = (ListView) findViewById(R.id.accusedlist);
        accused_content = new Accused_content();
        requestQueue = Volley.newRequestQueue(getApplicationContext());

        mdformatpresent = new SimpleDateFormat("dd-MMM-yyyy'T'HH:mm:ss'Z'");

        call = Calendar.getInstance();
        dpDialog=new DatePickerDialog(Accused_Search.this, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                call = Calendar.getInstance();
                call.set(year, monthOfYear, dayOfMonth);
                fromdate.setText(mdformatpresent.format(call.getTime()));
                Log.d("fromdate",fromdate.getText().toString());
            }
        }, call.get(Calendar.YEAR), call.get(Calendar.MONTH), call.get(Calendar.DAY_OF_MONTH));
        fromdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dpDialog.show();
            }
        });

        mdformatpresent = new SimpleDateFormat("dd-MMM-yyyy");
        call = Calendar.getInstance();
        dpDialog1=new DatePickerDialog(Accused_Search.this, new DatePickerDialog.OnDateSetListener(){

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                call = Calendar.getInstance();
                call.set(year, monthOfYear, dayOfMonth);
                todate.setText(mdformatpresent.format(call.getTime()));
            }
        }, call.get(Calendar.YEAR), call.get(Calendar.MONTH), call.get(Calendar.DAY_OF_MONTH));
        todate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dpDialog1.show();
            }
        });

        psspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(!parent.getSelectedItem().equals("Select ps")){
                    spin=(String)psspinner.getSelectedItem();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        if (cd.isConnectingToInternet()) {
            new Psdetails().execute();
        } else {
            Toast.makeText(getApplicationContext(), "No Internet Connection", Toast.LENGTH_SHORT).show();
        }

        buttonsearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("accusedname", (String) psspinner.getSelectedItem());
                if(cd.isConnectingToInternet()) {
                    new Accuses_Details().execute();
                }
                accusedname.setText("");
                accusedaliasname.setText("");
                fathername.setText("");
                agefrom.setText("");
                ageto.setText("");
                fromdate.setText("");
                todate.setText("");
                crimenumber.setText("");
                crimelocation.setText("");

            }
        });
    }

    private class Psdetails extends AsyncTask<String, String, String> {
        Boolean result;
        String s;
        ProgressDialog pd;

        @Override
        protected void onPreExecute() {
            pd=new ProgressDialog(Accused_Search.this);
            pd.setMessage("Please wait...");
            pd.show();
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                //obj = jsonparser.getJSONArrayFromUrl("http://policepatrolservice.logicshore.co.in/Service1.svc/FindPrisoner?SearchBy="+cat+"&SearchVal="+URLEncoder.encode(sc.getText().toString(), "UTF-8")+"&CrimeType="+URLEncoder.encode(searchval, "UTF-8")+"&Release="+URLEncoder.encode(typere, "UTF-8")+"&StartDate="+et.getText().toString()+"&EndDate="+to.getText().toString());
                HttpClient hs = new DefaultHttpClient();
                HttpPost ps = new HttpPost("http://220.225.38.123:8081//LogicShore.svc/GetAllPSdetails");
                JSONObject jsonobj = new JSONObject();
                JSONObject js = new JSONObject();

                js.put("Zone", "");
                js.put("Division", "");
                js.put("PoliceStation", "");
                js.put("CCODE","2023");
                jsonobj.put("request", js);
                String message;
                message = jsonobj.toString();
                Log.d("Main Object", js.toString());
                ps.setEntity(new StringEntity(message, "UTF8"));
                //	ps.setHeader("Accept", "application/json");
                ps.setHeader("Content-Type", "application/json");
                HttpResponse resp = hs.execute(ps);
                if (resp != null) {
                    if (resp.getStatusLine().getStatusCode() == 204)
                        result = true;
                    Log.d("Status line", "" + resp.getStatusLine().getStatusCode());
                    //	Log.d("Response",resp.toString());
                    HttpEntity hr = resp.getEntity();
                    s = EntityUtils.toString(hr);
                    Log.d("Response_String", s);
                    //  Toast.makeText(getApplicationContext(), resp.toString(), 10).show();
                }
            } catch (UnknownHostException e) {

            } catch (Exception e) {

            }
            return s;
        }

        protected void onPostExecute(String s) {
            psarraylist = new ArrayList<String>();
            psarraylist.add("Select ps");
            pd.dismiss();


            try {
                ArrayList<String> al = new ArrayList<>();
                JSONObject jstotal = new JSONObject(s);
                JSONArray pslist = jstotal.getJSONArray("Pslist");
                if (pslist.length() > 0) {
                    for (int i = 0; i < pslist.length(); i++) {
                        JSONObject policelist = pslist.getJSONObject(i);
                        String psname = policelist.getString("PSname");
                        psarraylist.add(psname);
                    }
                    ArrayAdapter psspinners = new ArrayAdapter(Accused_Search.this, android.R.layout.simple_spinner_item, psarraylist);
                    psspinners.setDropDownViewResource(android.R.layout.simple_spinner_item);
                    psspinner.setAdapter(psspinners);
                    pd.dismiss();                }

            } catch (JSONException e) {
                e.printStackTrace();
            }


        }
    }

    private class Accuses_Details extends AsyncTask<String, String, String> {
        private String s;
        Boolean result;
        private ProgressDialog Pd1;

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            Pd1 = new ProgressDialog(Accused_Search.this);
            Pd1.setMessage("Please Wait!!");
            Pd1.show();
            super.onPreExecute();

        }

        @Override
        protected String doInBackground(String... params) {
            try {

                HttpClient hs = new DefaultHttpClient();
                HttpPost ps = new HttpPost("http://tecdatum.net/Kiteeyeapi/api/Service/AccusedPersonSearch");
                JSONObject jsonobj = new JSONObject();
                JSONObject js = new JSONObject();
                js.put("UserId", "Kiteeye");
                js.put("Password", "a2l0ZUA2NjY=");
                js.put("Name", accusedname.getText().toString());
                js.put("AliasName", accusedaliasname.getText().toString());
                js.put("FatherName", fathername.getText().toString());
                js.put("Policestation", spin);
                js.put("location", crimelocation.getText().toString());
                js.put("crimenumber", crimenumber.getText().toString());
                js.put("Fromdate", fromdate.getText().toString());
                js.put("Todate", todate.getText().toString());
                js.put("Fromage", agefrom.getText().toString());
                js.put("Toage", ageto.getText().toString());
                //jsonobj.put("request", js);

                String message;
                message = js.toString();
                Log.d("Main Object", js.toString());
                ps.setEntity(new StringEntity(message, "UTF8"));
                //	ps.setHeader("Accept", "application/json");
                ps.setHeader("Content-Type", "application/json");
                HttpResponse resp = hs.execute(ps);

                if (resp != null) {
                    if (resp.getStatusLine().getStatusCode() == 204)
                        result = true;
                    Log.d("Status line", "" + resp.getStatusLine().getStatusCode());
                    cz.msebera.android.httpclient.HttpEntity hr = resp.getEntity();


                    s = EntityUtils.toString(hr);
                    Log.d("Response_String", s);
                }
            } catch (UnknownHostException e) {


            } catch (Exception e) {
                e.printStackTrace();

            }
            return s;

        }

        @Override
        protected void onPostExecute(String s) {


            try {

                JSONObject jsobj = new JSONObject(s);
                if (jsobj.getString("code").equals("1")&& jsobj.getString("Message").equals("Success")) {

                    JSONArray jsonarray = jsobj.getJSONArray("data");
                    Log.d("jsonarray", String.valueOf(jsonarray));
                    accused_List = new ArrayList<>();
                    if (jsonarray.length() > 0) {
                        for (int i = 0; i < jsonarray.length(); i++) {
                            accused_content = new Accused_content();
                            JSONObject accusedvalues = jsonarray.getJSONObject(i);
                            accused_content.setUname(accusedvalues.getString("Name"));
                            accused_content.setAliasname(accusedvalues.getString("AliasName"));
                            accused_content.setFathername(accusedvalues.getString("FatherName"));
                            accused_content.setAge(accusedvalues.getString("Age"));
                            accused_content.setCrimenumber(accusedvalues.getString("CrimeNumber"));
                            accused_content.setCrimedate(accusedvalues.getString("CrimeDate"));
                            accused_content.setLocation(accusedvalues.getString("Location"));
                            accused_content.setPsname(accusedvalues.getString("PS"));
                            accused_content.setMo(accusedvalues.getString("MO"));

                            accused_List.add(accused_content);
                            Log.d("Repsonse", accused_List.toString());
                        }
                        accused_adaptor = new Accused_Adaptor(accused_List, Accused_Search.this);
                        accusedlist.setAdapter(accused_adaptor);
                    }

                }


            } catch (JSONException e) {
                e.printStackTrace();
            }
            Pd1.dismiss();

        }
    }
}
