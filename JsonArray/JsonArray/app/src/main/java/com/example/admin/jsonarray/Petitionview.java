package com.example.admin.jsonarray;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.icu.text.SimpleDateFormat;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.methods.HttpPost;
import cz.msebera.android.httpclient.entity.StringEntity;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;
import cz.msebera.android.httpclient.util.EntityUtils;


public class Petitionview extends Activity {
    Petition_Content petition_content;
    ArrayList<Petition_Content> petitionList;
    Petition_Adaptor petition_adaptor;
    ListView petitionlist1;
    private boolean isInternetPresent1;
    TextView petitionadvancedsearch,cancel;
    EditText petitionid,eoname,complainantname,petitionfrom,petitionto;
    Button search;
    String Eoname="";
    ConnectionDetector cd;
    int pagecount=0;
    Dialog adavanced_dialog;
    int pagenumber=1;
    int totalpages=-1;
    int pagenumberrequeusting=20;
    protected Dialog dialog1;
    SimpleDateFormat mdformatpresent;
    DatePickerDialog dpDialog,dpDialog1;
    String petfrm="",petto="",petid="";
    String Complaname="";
    Calendar call;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.petitionlistview);
        petitionlist1 = (ListView) findViewById(R.id.petitionlist1);
       // petitionadvancedsearch=(TextView)findViewById(R.id.petitionadvancedsearch);
        petitionList = new ArrayList<>();
        ConnectionDetector cd1= new ConnectionDetector(getApplicationContext());
        isInternetPresent1 = cd1.isConnectingToInternet();
        cd = new ConnectionDetector(getApplicationContext());
        pagenumber = 1;
        pagenumberrequeusting = 20;
        pagecount = 20;
        adavanced_dialog = new Dialog(Petitionview.this);
        if (cd.isConnectingToInternet()) {
            //new Petitiondatails().execute();

            petitionlist1.setOnScrollListener(new AbsListView.OnScrollListener() {

                @Override
                public void onScrollStateChanged(AbsListView view, int scrollState) {
                    Log.d("list onScroll", " entered");
                    int count = petitionlist1.getCount();
                    Log.d("count", String.valueOf(count));
                    int threshold = 1;
                    if (scrollState == SCROLL_STATE_IDLE) {
                        if (petitionlist1.getLastVisiblePosition() >= count - threshold) {
                            Log.d("get last position", "position");
                            if (pagenumber > totalpages) {
                                pagenumber = pagenumber + 1;
                                pagecount = pagecount + pagenumberrequeusting;
                                Log.d("pagecount", String.valueOf(pagecount));
                                pagenumberrequeusting = 20;
                               // new Petitiondatails().execute();
                            }
                        }
                    }
                }

                @Override
                public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

                }
            });

        }else
        {
            Toast.makeText(getApplicationContext(),"No Internet Connection Avaliable",Toast.LENGTH_SHORT).show();
        }

                petitionid=(EditText)findViewById(R.id.petitionid);

                eoname=(EditText)findViewById(R.id.eoname);


                complainantname=(EditText)findViewById(R.id.complainantname);

                petitionfrom=(EditText)findViewById(R.id.petitionfrom);
                petitionto=(EditText)findViewById(R.id.petitionto);
                search=(Button)findViewById(R.id.search);
                cancel=(TextView)findViewById(R.id.cancel);
                mdformatpresent = new SimpleDateFormat("dd-MMM-yyyy");
                call = Calendar.getInstance();

                dpDialog=new DatePickerDialog(Petitionview.this, new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear,
                                          int dayOfMonth) {
                        // TODO Auto-generated method stub
                        call = Calendar.getInstance();
                        call.set(year, monthOfYear, dayOfMonth);
                        petitionfrom.setText(mdformatpresent.format(call.getTime()));
                        petfrm=petitionfrom.getText().toString();
                    }
                }, call.get(Calendar.YEAR), call.get(Calendar.MONTH), call.get(Calendar.DAY_OF_MONTH));
                petitionfrom.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dpDialog.show();
                    }
                });


                mdformatpresent = new SimpleDateFormat("dd-MMM-yyyy");
                call = Calendar.getInstance();
                dpDialog1=new DatePickerDialog(Petitionview.this, new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear,
                                          int dayOfMonth) {
                        // TODO Auto-generated method stub
                        call = Calendar.getInstance();
                        call.set(year, monthOfYear, dayOfMonth);
                        petitionto.setText(mdformatpresent.format(call.getTime()));
                        petto=petitionto.getText().toString();
                        Log.d("weewewe",petitionto.getText().toString());
                    }
                }, call.get(Calendar.YEAR), call.get(Calendar.MONTH), call.get(Calendar.DAY_OF_MONTH));
                petitionto.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dpDialog1.show();
                    }
                });
                search.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        petid=petitionid.getText().toString();
                        Eoname= eoname.getText().toString();
                        petfrm=petitionfrom.getText().toString();
                        petto=petitionto.getText().toString();
                        Complaname=complainantname.getText().toString();
                        Log.d("eonames",Eoname);
                        if(isInternetPresent1)
                        {


                            petitionList.clear();
                            petitionlist1.setAdapter(null);
                            new advancesearch().execute();
                            clearText();
                        }

                        //cancel.setOnClickListener(cancel_button);
                    }
                });

cancel.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        //dialog1.dismiss();
    }
});


            }

    private void clearText() {
        petitionid.setText("");
        eoname.setText("");
        petitionfrom.setText("");
        petitionto.setText("");
        complainantname.setText("");
    }

    private class Petitiondatails extends AsyncTask<String,String,String>{
        private String s;
        Boolean result;
        private ProgressDialog Pd;

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            Pd=new ProgressDialog(Petitionview.this);
            Pd.setMessage("Please Wait!!");
            Pd.show();
            adavanced_dialog.dismiss();
            super.onPreExecute();
        }
        @Override
        protected String doInBackground(String... params) {
            try {
                Calendar c = Calendar.getInstance();
                SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
                String formattedDate = df.format(c.getTime());
                Log.d("date1",formattedDate);
                Date date = c.getTime();
                int day = c.get(Calendar.DATE);
                //Note: +1 the month for current month
                int month = c.get(Calendar.MONTH) + 1;
                int year = c.get(Calendar.YEAR);
                String fromdate="01-jan-"+year;
                Log.d("year", fromdate);

                HttpClient hs = new DefaultHttpClient();
                HttpPost ps = new HttpPost("http://220.225.38.123:8081/LogicShore.svc/PetitionDetailsNew");
                JSONObject jsonobj = new JSONObject();
                JSONObject js = new JSONObject();
                js.put("P_PET_ID","");
                js.put("P_PET_FROM_DT",fromdate);
                js.put("P_PET_TO_DT",formattedDate);
                js.put("P_LANG_CD","99");
                js.put("P_PET_PS_CD","2023001");
                js.put("P_COMP_NAME","");
                js.put("P_EO_NAME","");
                js.put("PageSize",pagenumberrequeusting);
                js.put("PageIndex",pagenumber);
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
            Pd.dismiss();

            try {

                JSONObject jsobj = new JSONObject(s);
                JSONArray jsonarray=jsobj.getJSONArray("Petition");
                Log.d("jsonarray", String.valueOf(jsonarray));

                if(jsonarray.length()>0){
                    for (int i=0;i<jsonarray.length();i++){
                        petition_content=new Petition_Content();
                        JSONObject petitionvalues = jsonarray.getJSONObject(i);
                        petition_content.setResult(String.valueOf(petitionvalues));
                        petition_content.setPettno(petitionvalues.getString("PET_ID"));
                        petition_content.setPettype(petitionvalues.getString("PETITION_TYPE"));
                        String petitionername=petitionvalues.getString("PETITIONERNAME");
                        petition_content.setPetnername(petitionername.replaceAll("[.,]",""));
                        petition_content.setPetdate(petitionvalues.getString("PET_DATE"));
                        petition_content.setPetstatus(petitionvalues.getString("PETITION_STATUS"));
                        petition_content.setPolicesta(petitionvalues.getString("UNIT_NAME"));
                        petition_content.setEoname(petitionvalues.getString("EO_NAME"));
                        petitionList.add(petition_content);
                        Log.d("Repsonse",petitionList.toString());
                    }
                    petition_adaptor =new Petition_Adaptor(petitionList, Petitionview.this) ;
                   // petitionlist1.setAdapter(petition_adaptor);
                    if(pagecount>1) {
                        Log.d("page count inner","");
                        int position = petitionlist1.getLastVisiblePosition();
                        Log.d("positionvalue", String.valueOf(position));
                        petitionlist1.setAdapter(petition_adaptor);
                        petitionlist1.setSelectionFromTop(position, 0);

                    }


                }


            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

    private class advancesearch extends AsyncTask<String,String,String>{
        private String s;
        Boolean result;

        private ProgressDialog Pd;


        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            Pd=new ProgressDialog(Petitionview.this);
            Pd.setMessage("Please Wait!!");
            Pd.show();
            adavanced_dialog.dismiss();
//            dialog1.dismiss();
            super.onPreExecute();
        }
        @Override
        protected String doInBackground(String... params) {
            try {

                HttpClient hs = new DefaultHttpClient();
                HttpPost ps = new HttpPost("http://220.225.38.123:8081/LogicShore.svc/PetitionDetailsNew");
                JSONObject jsonobj = new JSONObject();
                JSONObject js = new JSONObject();
                js.put("P_PET_ID",petid);
                js.put("P_PET_FROM_DT",petfrm);
                js.put("P_PET_TO_DT",petto);
                js.put("P_LANG_CD","99");
                js.put("P_PET_PS_CD","2023500");
                js.put("P_COMP_NAME",Complaname);
                js.put("P_EO_NAME",Eoname);
                js.put("PageSize","50");
                js.put("PageIndex","0");
                jsonobj.put("request", js);

                String message;
                message = jsonobj.toString();
                Log.d("Object", js.toString());
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
            Pd.dismiss();

            try {
                JSONObject jsobj = new JSONObject(s);
                JSONArray jsonarray=jsobj.getJSONArray("Petition");
                Log.d("jsonarray", String.valueOf(jsonarray));

                if(jsonarray.length()>0){
                    for (int i=0;i<jsonarray.length();i++){
                        petition_content=new Petition_Content();
                        JSONObject petitionvalues = jsonarray.getJSONObject(i);
                        petition_content.setResult(String.valueOf(petitionvalues));
                        petition_content.setPettno(petitionvalues.getString("PET_ID"));
                        petition_content.setPettype(petitionvalues.getString("PETITION_TYPE"));
                        String petitionername=petitionvalues.getString("PETITIONERNAME");
                        petition_content.setPetnername(petitionername.replaceAll("[.,]",""));
                        petition_content.setPetdate(petitionvalues.getString("PET_DATE"));
                        petition_content.setPetstatus(petitionvalues.getString("PETITION_STATUS"));
                        petition_content.setPolicesta(petitionvalues.getString("UNIT_NAME"));
                        petition_content.setEoname(petitionvalues.getString("EO_NAME"));
                        petitionList.add(petition_content);
                        Log.d("Repsonse",petitionList.toString());
                    }
                    petition_adaptor =new Petition_Adaptor(petitionList, Petitionview.this) ;
                    // petitionlist1.setAdapter(petition_adaptor);
                    if(pagecount>1) {
                        Log.d("page count inner","");
                        int position = petitionlist1.getLastVisiblePosition();
                        Log.d("positionvalue", String.valueOf(position));
                        petitionlist1.setAdapter(petition_adaptor);
                        petitionlist1.setSelectionFromTop(position, 0);
                    }

                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
//            petfrm="";
//            petto="";
//            petid="";
//            Eoname="";
//            Complaname="";
        }

    }

}
