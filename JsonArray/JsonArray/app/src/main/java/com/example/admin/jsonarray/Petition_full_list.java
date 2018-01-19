package com.example.admin.jsonarray;

import android.app.Activity;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.UnknownHostException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.methods.HttpPost;
import cz.msebera.android.httpclient.entity.StringEntity;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;
import cz.msebera.android.httpclient.util.EntityUtils;

/**
 * Created by admin on 08-06-2017.
 */
public class Petition_full_list extends Activity{
    TextView petitionno,petitiodate,petitiotype,petitiosubject,petitionername,fathername,applicationdate,petitiondescription,
            petitionertype,mobileno,teleoffice,teleresid,presentaddress,district,policestation,servicetype,petitionstatus,enquirytime;
    String petnumber;
    TableLayout table1;
    LinearLayout tableRow;
    ConnectionDetector cd;
    TextView textview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.petition_full_list);
        petitionno=(TextView)findViewById(R.id.petitionno);
        petitiodate=(TextView)findViewById(R.id.petitiodate);
        petitiotype=(TextView)findViewById(R.id.petitiotype);
        petitiosubject=(TextView)findViewById(R.id.petitiosubject);
        petitionername=(TextView)findViewById(R.id.petitionername);
        fathername=(TextView)findViewById(R.id.fathername);
        applicationdate=(TextView)findViewById(R.id.applicationdate);
        petitionertype=(TextView)findViewById(R.id.petitionertype);
        mobileno=(TextView)findViewById(R.id.mobileno);
        teleoffice=(TextView)findViewById(R.id.teleoffice);
        teleresid=(TextView)findViewById(R.id.teleresid);
        presentaddress=(TextView)findViewById(R.id.presentaddress);
        district=(TextView)findViewById(R.id.district);
        policestation=(TextView)findViewById(R.id.policestation);
        servicetype=(TextView)findViewById(R.id.servicetype);
        petitionstatus=(TextView)findViewById(R.id.petitionstatus);
        petitiondescription=(TextView)findViewById(R.id.petitiondescription);
        enquirytime=(TextView)findViewById(R.id.enquirytime);
        table1=(TableLayout)findViewById(R.id.table1);
        cd=new ConnectionDetector(getApplicationContext());
        petnumber = getIntent().getStringExtra("petitiono");
        if (cd.isConnectingToInternet()) {
            new Petition_Respose().execute();
        }
        else{
            Toast.makeText(getApplicationContext(),"No Internet Connection Avaliable",Toast.LENGTH_SHORT).show();
        }
        try {
            JSONObject jsonObject=new JSONObject(getIntent().getStringExtra("totalresult"));
            String applicationname=jsonObject.getString("PETITIONERNAME");
            policestation.setText(jsonObject.getString("UNIT_NAME"));
            petitionno.setText(jsonObject.getString("PET_ID"));
            petitiodate.setText(jsonObject.getString("PET_DATE"));
            petitiotype.setText(jsonObject.getString("PETITION_TYPE"));
            petitiosubject.setText(jsonObject.getString("PETITION_SUBJECT"));
           // petitiondescription.setText(jsonObject.getString(""));
            petitionertype.setText(jsonObject.getString("PETITIONER_TYPE"));
            String petitionernam=jsonObject.getString("PETITIONERNAME");
            String name=petitionernam.replaceAll("[.,]","");
            petitionername.setText(name);
            Log.d("petitionernam",petitionernam);
            fathername.setText(jsonObject.getString("FATHER_NAME"));
            applicationdate.setText(jsonObject.getString("APPLICATION_DATE"));
            mobileno.setText(jsonObject.getString("MOBILENO"));
            teleoffice.setText(jsonObject.getString("TELE_OFFICE"));
            teleresid.setText(jsonObject.getString("TELE_RESID"));
            presentaddress.setText(jsonObject.getString("PRESENT_ADDRESS"));
            district.setText(jsonObject.getString("district"));
            servicetype.setText(jsonObject.getString("SR_TYPE"));
            petitionstatus.setText(jsonObject.getString("PETITION_STATUS"));
            String description=jsonObject.getString("PET_DESCRIPTION");
            String s2 = description.replaceAll("[\n\r]", "");

            petitiondescription.setText(s2);

//            if(description.contains(remove)){
//                description = description.replaceAll(remove,"");
//                petitiondescription.setText(description);
//            }
//            else{
//                petitiondescription.setText(description);
//            }

            //petitiondescription.setText(jsonObject.getString("PET_DESCRIPTION"));

            Log.d("applicationname",applicationname);
            Log.d("jjjjjj", String.valueOf(jsonObject));
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


    private class Petition_Respose extends AsyncTask<String,String,String> {
        private String s;
        Boolean result;
        @Override
        protected String doInBackground(String... params) {
            try {
                HttpClient hs = new DefaultHttpClient();
                HttpPost ps = new HttpPost("http://220.225.38.123:8081/LogicShore.svc/PetSearchNew");
                JSONObject jsonobj = new JSONObject();
                JSONObject js = new JSONObject();
                js.put("P_PET_ID",petnumber);
                jsonobj.put("request", js);

                String message;
                message = jsonobj.toString();
                Log.d("Main", js.toString());
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
        protected void onPostExecute(String s) {

            try {

                JSONObject jsobj = new JSONObject(s);
                JSONArray jsonarray=jsobj.getJSONArray("Petlist");
                Log.d("jsonarraypetlist", String.valueOf(jsonarray));

                if(jsonarray.length()>0){
                    String fromdate=jsonarray.getJSONObject(0).getString("FROM_DATE_TIME");
                    String todate=jsonarray.getJSONObject(jsonarray.length()-1).getString("TO_DATE_TIME");
                    SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                    Date d1 = null;
                    Date d2 = null;
                    d1 = format.parse(fromdate);
                    d2 = format.parse(todate);
                    long diff = d2.getTime() - d1.getTime();
                    long diffSeconds = diff / 1000 % 60;
                    long diffMinutes = diff / (60 * 1000) % 60;
                    long diffHours = diff / (60 * 60 * 1000) % 24;
                    long diffDays = diff / (24 * 60 * 60 * 1000);
                    Log.d("diffSeconds", String.valueOf(diffSeconds));
                    Log.d("diffMinutes", String.valueOf(diffMinutes));
                    Log.d("diffHours", String.valueOf(diffHours));
                    Log.d("diffDays", String.valueOf(diffDays));
                    String timediffernce =String.valueOf(diffDays)+"d: "+String.valueOf(diffHours)+"hr: "+String.valueOf(diffMinutes)+"mi: "+String.valueOf(diffSeconds)+"ss";
                    Log.d("timediffernce",timediffernce);
                    enquirytime.setText(timediffernce);


                    LinearLayout.LayoutParams llhead_layoutParams1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
                    llhead_layoutParams1.setMargins(10, 10, 10, 10);
                    tableRow = new LinearLayout(Petition_full_list.this);
                    tableRow.setOrientation(LinearLayout.HORIZONTAL);
                    tableRow.setWeightSum(5);
                    tableRow.setBackgroundResource(R.drawable.table_border);
                    tableRow.setBackgroundColor(Color.parseColor("#135b96"));
                    tableRow.setLayoutParams(llhead_layoutParams1);

                    LinearLayout.LayoutParams layoutParams_t = new LinearLayout.LayoutParams(0,LinearLayout.LayoutParams.WRAP_CONTENT, 1);

                    layoutParams_t.setMargins(10, 10, 10, 10);
                    textview = new TextView(Petition_full_list.this);
                    textview.setLayoutParams(layoutParams_t);
                    textview.setTextColor(Color.parseColor("#ffffff"));
//                    textview.setGravity(Gravity.CENTER_HORIZONTAL);
                    textview.setText("Date");
                    textview.setTextSize(16);
                    //textview.setBackgroundColor(Color.parseColor("#135b96"));
                    tableRow.addView(textview);

                    View v = new View(Petition_full_list.this);
                    v.setLayoutParams(new TableRow.LayoutParams(2, TableRow.LayoutParams.MATCH_PARENT));
                    tableRow.addView(v);

                    LinearLayout.LayoutParams layoutParams_t21 = new LinearLayout.LayoutParams(0,LinearLayout.LayoutParams.WRAP_CONTENT, 1);

                    layoutParams_t21.setMargins(10, 10, 10, 10);
                    textview = new TextView(Petition_full_list.this);
                    textview.setLayoutParams(layoutParams_t21);
                    textview.setTextColor(Color.parseColor("#ffffff"));
//                    textview.setGravity(Gravity.CENTER_HORIZONTAL);
                    textview.setText("Time");
                    textview.setTextSize(16);
                    //textview.setBackgroundColor(Color.parseColor("#135b96"));
                    tableRow.addView(textview);
                    // In order to get the view we have to use the new view with text_layout in it

                    View v11 = new View(Petition_full_list.this);
                    v11.setLayoutParams(new TableRow.LayoutParams(2, TableRow.LayoutParams.MATCH_PARENT));
                    tableRow.addView(v11);

                    LinearLayout.LayoutParams layoutParams_t1 = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1);
                    layoutParams_t1.setMargins(10, 10, 10, 10);
                    textview = new TextView(Petition_full_list.this);
                    textview.setLayoutParams(layoutParams_t1);
                    textview.setTextColor(Color.parseColor("#ffffff"));
//                    textview.setGravity(Gravity.CENTER);
                    //textview.setBackgroundColor(Color.parseColor("#135b96"));
                    textview.setText("Actiontaken By");
                    textview.setTextSize(16);
                    tableRow.addView(textview);

                    View v1 = new View(Petition_full_list.this);
                    v1.setLayoutParams(new TableRow.LayoutParams(2, TableRow.LayoutParams.MATCH_PARENT));
                    tableRow.addView(v1);

                    LinearLayout.LayoutParams layoutParams_t2 = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1);
                    layoutParams_t2.setMargins(10, 10, 10, 10);
                    textview = new TextView(Petition_full_list.this);
                    textview.setLayoutParams(layoutParams_t2);
                    textview.setTextColor(Color.parseColor("#ffffff"));
//                    textview.setGravity(Gravity.CENTER);
                    textview.setText("Actiontaken");
                    textview.setTextSize(16);
                    //textview.setBackgroundColor(Color.parseColor("#135b96"));
                    tableRow.addView(textview);

                    View v2 = new View(Petition_full_list.this);
                    v2.setLayoutParams(new TableRow.LayoutParams(2, TableRow.LayoutParams.MATCH_PARENT));
                    tableRow.addView(v2);

                    LinearLayout.LayoutParams layoutParams_t3 = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1);
                    layoutParams_t3.setMargins(10, 10, 10, 10);
                    textview = new TextView(Petition_full_list.this);
                    textview.setLayoutParams(layoutParams_t3);
                    textview.setTextColor(Color.parseColor("#ffffff"));
//                    textview.setGravity(Gravity.CENTER);
                    textview.setText("Assigned To");
                    textview.setTextSize(16);
                   // textview.setBackgroundColor(Color.parseColor("#135b96"));
                    tableRow.addView(textview);

                    table1.addView(tableRow);

                    for (int i=0;i<jsonarray.length();i++){
                        JSONObject petitionlist = jsonarray.getJSONObject(i);
                        String date=petitionlist.getString("FROM_DATE_TIME");
                        String todate1=petitionlist.getString("TO_DATE_TIME");
                        String[] todateformat=todate1.split(" ");
                        String[] fromat=date.split(" ");
                            Log.d("todateformat", todateformat[0]);
                            Log.d("todateformat1", todateformat[1]);
                            Log.d("srsavaa0", fromat[0]);
                            Log.d("srsavaa1", fromat[1]);

                        LinearLayout.LayoutParams llhead_layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
                        llhead_layoutParams.setMargins(0, 10, 0, 10);
                        tableRow = new LinearLayout(Petition_full_list.this);
                        tableRow.setOrientation(LinearLayout.HORIZONTAL);
                        tableRow.setWeightSum(5);
                        tableRow.setBackgroundResource(R.drawable.table_border);
//                    tableRow.setBackgroundColor(Color.parseColor("#135b96"));
                        tableRow.setLayoutParams(llhead_layoutParams);

                        LinearLayout.LayoutParams layoutParams_tv = new LinearLayout.LayoutParams(0,LinearLayout.LayoutParams.WRAP_CONTENT, 1);
                        layoutParams_tv.setMargins(10, 10, 10, 10);
                        textview = new TextView(Petition_full_list.this);
                        textview.setLayoutParams(layoutParams_tv);
                        //android:textColor="#717272"
                        textview.setTextColor(Color.parseColor("#717272"));
//                        textview.setGravity(Gravity.CENTER_HORIZONTAL);
                        if(petitionlist.getString("PET_WF_SL_NO").equals("1")) {
                            textview.setText(fromat[0]);
                            textview.setTextSize(16);
                            tableRow.addView(textview);
                        }
                        else{
                            textview.setText(todateformat[0]);
                            textview.setTextSize(16);
                            tableRow.addView(textview);
                        }
                        View v0 = new View(Petition_full_list.this);
                        v0.setLayoutParams(new TableRow.LayoutParams(2, TableRow.LayoutParams.MATCH_PARENT));
                        tableRow.addView(v0);

                        LinearLayout.LayoutParams layoutParams_tv0 = new LinearLayout.LayoutParams(0,LinearLayout.LayoutParams.WRAP_CONTENT, 1);
                        layoutParams_tv0.setMargins(10, 10, 10, 10);
                        textview = new TextView(Petition_full_list.this);
                        textview.setLayoutParams(layoutParams_tv0);
                        textview.setTextColor(Color.parseColor("#717272"));
//                        textview.setGravity(Gravity.CENTER_HORIZONTAL);
                        if(petitionlist.getString("PET_WF_SL_NO").equals("1")) {
                            textview.setText(fromat[1]);
                            textview.setTextSize(16);
                            tableRow.addView(textview);
                        }
                        else{
                            textview.setText(todateformat[1]);
                            textview.setTextSize(16);
                            tableRow.addView(textview);
                        }
                        View v01 = new View(Petition_full_list.this);
                        v01.setLayoutParams(new TableRow.LayoutParams(2, TableRow.LayoutParams.MATCH_PARENT));
                        tableRow.addView(v01);

                        // In order to get the view we have to use the new view with text_layout in it

                        LinearLayout.LayoutParams layoutParams_tv1 = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1);
                        layoutParams_tv1.setMargins(10, 10, 10, 10);
                        textview = new TextView(Petition_full_list.this);
                        textview.setLayoutParams(layoutParams_tv1);
                        textview.setTextColor(Color.parseColor("#717272"));
//                        textview.setGravity(Gravity.CENTER);
                        textview.setText(petitionlist.getString("FROM_NAME_WITH_ROLE"));
                        textview.setTextSize(16);
                        tableRow.addView(textview);

                        View v10 = new View(Petition_full_list.this);
                        v10.setLayoutParams(new TableRow.LayoutParams(2, TableRow.LayoutParams.MATCH_PARENT));
                        tableRow.addView(v10);

                        LinearLayout.LayoutParams layoutParams_tv2 = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1);
                        layoutParams_tv2.setMargins(10, 10, 10, 10);
                        textview = new TextView(Petition_full_list.this);
                        textview.setLayoutParams(layoutParams_tv2);
                        textview.setTextColor(Color.parseColor("#717272"));
//                        textview.setGravity(Gravity.CENTER);
                        textview.setText(petitionlist.getString("PET_WF_REMARKS"));
                        textview.setTextSize(16);
                        tableRow.addView(textview);

                        View v20 = new View(Petition_full_list.this);
                        v20.setLayoutParams(new TableRow.LayoutParams(2, TableRow.LayoutParams.MATCH_PARENT));
                        tableRow.addView(v20);

                        LinearLayout.LayoutParams layoutParams_tv3 = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1);
                        layoutParams_tv3.setMargins(10, 10, 10, 10);
                        textview = new TextView(Petition_full_list.this);
                        textview.setLayoutParams(layoutParams_tv3);
                        textview.setTextColor(Color.parseColor("#717272"));
//                        textview.setGravity(Gravity.CENTER);
                        textview.setText(petitionlist.getString("TO_NAME_WITH_ROLE"));
                        textview.setTextSize(16);
                        tableRow.addView(textview);

                        table1.addView(tableRow);
                    }

                }

            } catch (JSONException e) {
                e.printStackTrace();
            } catch (ParseException e) {
                e.printStackTrace();
            }

        }
    }
}
