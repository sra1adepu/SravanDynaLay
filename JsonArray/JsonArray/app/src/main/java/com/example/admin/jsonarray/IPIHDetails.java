package com.example.admin.jsonarray;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.icu.text.SimpleDateFormat;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import net.danlew.android.joda.JodaTimeAndroid;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.XML;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.methods.HttpPost;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;
import cz.msebera.android.httpclient.util.EntityUtils;


/**
 * Created by LENOVO on 01-06-2017.
 */

public class IPIHDetails extends Activity {
    TextView dob,name,fathername,pancardno,voterid,aadharcard,drivinglicenceno,agevalue;
    TableLayout table1;
    LinearLayout tableRow;
    TextView textview;
    int age = 0;
    int age2=0;
    int value;
    SimpleDateFormat formatter;
    int year; int month; int day;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ipih1);
        JodaTimeAndroid.init(this);
        dob=(TextView)findViewById(R.id.dob);
        name=(TextView)findViewById(R.id.name);
        fathername=(TextView)findViewById(R.id.fathername);
        pancardno=(TextView)findViewById(R.id.pancardno);
        voterid=(TextView)findViewById(R.id.voterid);
        aadharcard=(TextView)findViewById(R.id.aadharcard);
        drivinglicenceno=(TextView)findViewById(R.id.drivinglicenceno);
        table1=(TableLayout)findViewById(R.id.table1);
        agevalue=(TextView)findViewById(R.id.agevalue);
        formatter = new SimpleDateFormat("MM-dd-yyyy");
        new details().execute();

    }

    private  class  details extends AsyncTask<String,String,String> {

        private String st;
        private ProgressDialog progressDialogzone;
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            progressDialogzone = new ProgressDialog(IPIHDetails.this);
            progressDialogzone.setMessage("Filtering");
            progressDialogzone.show();

            super.onPreExecute();
        }

        protected String doInBackground(String... params) {

            //do yo
            try
            {
                String url="http://220.225.38.123:8081//LogicShore.svc/GetIPIHserviceAddressPsIdex?psxId=108232289,108237627,108244504";

                HttpClient httpclient = new DefaultHttpClient();
                HttpPost httppost = new HttpPost(url);

                HttpResponse response = httpclient.execute(httppost);
                Log.d("http response",response.toString());
                st= EntityUtils.toString(response.getEntity());
                Log.d("HTTP status",st);

            }catch(Exception e)
            {
                e.printStackTrace();

            }
            return st;



        }

        @Override
        protected void onPostExecute(String st) {
            progressDialogzone.dismiss();

            String s=st.replace("\\","");

            s=s.substring(1,(s.length()-1));
            LayoutInflater layoutInflater = getLayoutInflater();
            View view;
            try {
                JSONObject jsonObj = XML.toJSONObject(s);
                Log.d("response",jsonObj.toString());

                JSONObject jsonObject1=jsonObj.getJSONObject("soap:Envelope");
                JSONObject jsonObject2=jsonObject1.getJSONObject("soap:Body");
                JSONObject jsonObject3=jsonObject2.getJSONObject("ns2:Result");

                String ddad =jsonObject3.getString("dob");
                Log.d("sravan",jsonObject3.getString("dob"));
                Log.d("name",jsonObject3.getString("voter-id"));

                String replace=ddad.replace("00:00:00.0","");
                Log.d("ddad",replace);

                String deliverydate=replace;

                Calendar c = Calendar.getInstance();
                SimpleDateFormat df = new SimpleDateFormat("MM-dd-yyyy");
                String formattedDate = df.format(c.getTime());
                Date date1=df.parse(formattedDate);

                Log.d("formattedDate",formattedDate);


                //String date = "2011/11/12 16:05:06";
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");
                Date testDate = null;
                try { testDate = sdf.parse(ddad);
                    Log.d("testDate", String.valueOf(testDate));
                }
                catch(Exception ex)
                { ex.printStackTrace();
                }

                String newFormat = formatter.format(testDate);
                Date resposedate=formatter.parse(newFormat);
                Log.d("Date1", newFormat);


                Calendar cal1 = new GregorianCalendar();
                Calendar cal2 = new GregorianCalendar();

                int factor = 0;
                Date date3 = new SimpleDateFormat("MM-dd-yyyy").parse(newFormat);
                Date date2 = new SimpleDateFormat("MM-dd-yyyy").parse(formattedDate);
                cal1.setTime(date3);
                cal2.setTime(date2);
                if(cal2.get(Calendar.DAY_OF_YEAR) < cal1.get(Calendar.DAY_OF_YEAR)) {
                    factor = -1;
                }
                age = cal2.get(Calendar.YEAR) - cal1.get(Calendar.YEAR) + factor;
               Log.d("Your", String.valueOf(age));

                 agevalue.setText(String.valueOf(age));

                name.setText(jsonObject3.getString("name"));
                fathername.setText(jsonObject3.getString("father-name"));
                dob.setText(replace);
                if(jsonObject3.getString("pancard-no").equals("")){
                    pancardno.setText("N/A");
                }
                else
                {
                    pancardno.setText(jsonObject3.getString("pancard-no"));
                }
                if(jsonObject3.getString("voter-id").equals("")){
                    voterid.setText("N/A");
                }
                else{
                    voterid.setText(jsonObject3.getString("voter-id"));
                }
               if(jsonObject3.getString("aadhar-no").equals("")){
                   aadharcard.setText("N/A");
               }
                else {
                   aadharcard.setText(jsonObject3.getString("aadhar-no"));
               }
                if(jsonObject3.getString("driving-licn").equals("")){
                    drivinglicenceno.setText("N/A");
                }
                else {
                    drivinglicenceno.setText(jsonObject3.getString("driving-licn"));
                }
                List<String> stockList = new ArrayList<String>();
                stockList.add("stock1");
                stockList.add("stock2");

                String array[] = new String[stockList.size()];
                for(int j =0;j<stockList.size();j++){
                    array[j] = stockList.get(j);
                }

		/*Displaying Array elements*/
                for(String k: array)
                {
                   Log.d("jjj",k);
                }

//                LinearLayout.LayoutParams llhead_layoutParams1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
//                llhead_layoutParams1.setMargins(0, 10, 0, 10);
//
//                tableRow = new LinearLayout(IPIHDetails.this);
//                tableRow.setOrientation(LinearLayout.HORIZONTAL);
//                tableRow.setWeightSum(5);
//                tableRow.setBackgroundResource(R.drawable.table_border);
//                tableRow.setBackgroundColor(Color.parseColor("#135b96"));
//                tableRow.setLayoutParams(llhead_layoutParams1);
//
//                LinearLayout.LayoutParams layoutParams_t = new LinearLayout.LayoutParams(0,LinearLayout.LayoutParams.WRAP_CONTENT, 1);
//
//                layoutParams_t.setMargins(0, 10, 0, 10);
//                textview = new TextView(IPIHDetails.this);
//                textview.setLayoutParams(layoutParams_t);
//                textview.setTextColor(Color.parseColor("#ffffff"));
//                textview.setGravity(Gravity.CENTER_HORIZONTAL);
//                textview.setText("Relation");
//                textview.setTextSize(16);
//                //textview.setBackgroundColor(Color.parseColor("#135b96"));
//                tableRow.addView(textview);
//
//                View v = new View(IPIHDetails.this);
//                v.setLayoutParams(new TableRow.LayoutParams(2, TableRow.LayoutParams.MATCH_PARENT,.0001f));
//                v.setBackgroundColor(Color.parseColor("#ffffff"));
//                tableRow.addView(v);
//
//                LinearLayout.LayoutParams layoutParams_t21 = new LinearLayout.LayoutParams(0,LinearLayout.LayoutParams.WRAP_CONTENT, 1);
//
//                layoutParams_t21.setMargins(0, 10, 0, 10);
//                textview = new TextView(IPIHDetails.this);
//                textview.setLayoutParams(layoutParams_t21);
//                textview.setTextColor(Color.parseColor("#ffffff"));
//                textview.setGravity(Gravity.CENTER_HORIZONTAL);
//                textview.setText("Name");
//                textview.setTextSize(16);
//                //textview.setBackgroundColor(Color.parseColor("#135b96"));
//                tableRow.addView(textview);
//                // In order to get the view we have to use the new view with text_layout in it
//
//                View v11 = new View(IPIHDetails.this);
//                v11.setLayoutParams(new TableRow.LayoutParams(2, TableRow.LayoutParams.MATCH_PARENT));
//                v11.setBackgroundColor(Color.parseColor("#ffffff"));
//                tableRow.addView(v11);
//
//                LinearLayout.LayoutParams layoutParams_t1 = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1);
//                layoutParams_t1.setMargins(0, 10, 0, 10);
//                textview = new TextView(IPIHDetails.this);
//                textview.setLayoutParams(layoutParams_t1);
//                textview.setTextColor(Color.parseColor("#ffffff"));
//                textview.setGravity(Gravity.CENTER);
//                //textview.setBackgroundColor(Color.parseColor("#135b96"));
//                textview.setText("Age");
//                textview.setTextSize(16);
//                tableRow.addView(textview);
//
//                View v1 = new View(IPIHDetails.this);
//                v1.setLayoutParams(new TableRow.LayoutParams(2, TableRow.LayoutParams.MATCH_PARENT));
//                v1.setBackgroundColor(Color.parseColor("#ffffff"));
//                tableRow.addView(v1);
//
//                LinearLayout.LayoutParams layoutParams_t2 = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1);
//                layoutParams_t2.setMargins(0, 10, 0, 10);
//                textview = new TextView(IPIHDetails.this);
//                textview.setLayoutParams(layoutParams_t2);
//                textview.setTextColor(Color.parseColor("#ffffff"));
//                textview.setGravity(Gravity.CENTER);
//                textview.setText("DOB");
//                textview.setTextSize(16);
//                //textview.setBackgroundColor(Color.parseColor("#135b96"));
//                tableRow.addView(textview);
//
//                View v2 = new View(IPIHDetails.this);
//                v2.setLayoutParams(new TableRow.LayoutParams(2, TableRow.LayoutParams.MATCH_PARENT));
//                v2.setBackgroundColor(Color.parseColor("#ffffff"));
//                tableRow.addView(v2);
//
//                LinearLayout.LayoutParams layoutParams_t3 = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1);
//                layoutParams_t3.setMargins(0, 10, 0, 10);
//                textview = new TextView(IPIHDetails.this);
//                textview.setLayoutParams(layoutParams_t3);
//                textview.setTextColor(Color.parseColor("#ffffff"));
//                textview.setGravity(Gravity.CENTER);
//                textview.setText("Address");
//                textview.setTextSize(16);
//                tableRow.addView(textview);
//                table1.addView(tableRow);

                JSONObject jsonObject4= jsonObject3.getJSONObject("family-results");
                JSONArray jsonArray=jsonObject4.getJSONArray("family-matches");
                Calendar c1 = Calendar.getInstance();
                SimpleDateFormat df1 = new SimpleDateFormat("MM/dd/yyyy");
                String formattedDate1 = df1.format(c1.getTime());
                int year= c1.get(Calendar.YEAR);
                for(int i=0;i<jsonArray.length();i++){
                    JSONObject data = jsonArray.getJSONObject(i);
                    String family=data.getString("relation");
                    String name=data.getString("name");
                    String dob=data.getString("dob");
                    Log.d("dob", dob);
                    String address= data.getString("address");
                    LinearLayout.LayoutParams llhead_layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
                    llhead_layoutParams.setMargins(0, 0, 0, 0);
                    tableRow = new LinearLayout(IPIHDetails.this);
                    tableRow.setOrientation(LinearLayout.HORIZONTAL);
                    tableRow.setWeightSum(5);
                    tableRow.setBackgroundResource(R.drawable.table_border);
                    tableRow.setLayoutParams(llhead_layoutParams);

                    LinearLayout.LayoutParams layoutParams_tv = new LinearLayout.LayoutParams(0,LinearLayout.LayoutParams.WRAP_CONTENT, 1);
                    layoutParams_tv.setMargins(0, 0, 0, 0);
                    textview = new TextView(IPIHDetails.this);
                    textview.setLayoutParams(layoutParams_tv);
                    textview.setTextColor(Color.parseColor("#000000"));
                    textview.setGravity(Gravity.CENTER_HORIZONTAL);
                    textview.setText(family);
                    tableRow.addView(textview);

                    View vv1 = new View(IPIHDetails.this);
                    vv1.setLayoutParams(new TableRow.LayoutParams(2, TableRow.LayoutParams.MATCH_PARENT));
                    vv1.setBackgroundColor(Color.parseColor("#135b97"));
                    tableRow.addView(vv1);



                    LinearLayout.LayoutParams layoutParams_tv1 = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1);
                    layoutParams_tv1.setMargins(0, 0, 0, 0);
                    textview = new TextView(IPIHDetails.this);
                    textview.setLayoutParams(layoutParams_tv);
                    textview.setTextColor(Color.parseColor("#000000"));
                    textview.setGravity(Gravity.CENTER);
                    textview.setText(name);
                    tableRow.addView(textview);

                    View vv2 = new View(IPIHDetails.this);
                    vv2.setLayoutParams(new TableRow.LayoutParams(2, TableRow.LayoutParams.MATCH_PARENT));
                    vv2.setBackgroundColor(Color.parseColor("#135b97"));
                    tableRow.addView(vv2);

                    LinearLayout.LayoutParams layoutParams_tv2 = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1);
                    layoutParams_tv2.setMargins(0, 0, 0, 0);
                    textview = new TextView(IPIHDetails.this);
                    textview.setLayoutParams(layoutParams_tv);
                    textview.setTextColor(Color.parseColor("#000000"));
                    textview.setGravity(Gravity.CENTER);
                    if (!"".equals(dob)) {
                        String[] words = dob.split("/");
                        value=Integer.parseInt(words[2].toString());
                        age2 = year-value;

                        textview.setText(String.valueOf(age2));
                        Log.d("w3", words[2].toString());
                        for (String w : words) {
                            Log.d("w", w);
                        }
                    }else{
                        dob="NA";
                        textview.setText("N/A");
                    }



                    tableRow.addView(textview);

                    Log.d("jsonArray",family);

                    View vv3 = new View(IPIHDetails.this);
                    vv3.setLayoutParams(new TableRow.LayoutParams(2, TableRow.LayoutParams.MATCH_PARENT));
                    vv3.setBackgroundColor(Color.parseColor("#135b97"));
                    tableRow.addView(vv3);

                    LinearLayout.LayoutParams layoutParams_tv3 = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1);
                    layoutParams_tv3.setMargins(0, 0, 0, 0);
                    textview = new TextView(IPIHDetails.this);
                    textview.setLayoutParams(layoutParams_tv3);
                    textview.setTextColor(Color.parseColor("#000000"));
                    textview.setGravity(Gravity.CENTER);
                    if(!dob.equals("")){
                        textview.setText(dob);
                    }
                    else{
                        textview.setText("N/A");
                    }

                    tableRow.addView(textview);
                    Log.d("jsonArray",family);

                    View vv4 = new View(IPIHDetails.this);
                    vv4.setLayoutParams(new TableRow.LayoutParams(2, TableRow.LayoutParams.MATCH_PARENT));
                    vv4.setBackgroundColor(Color.parseColor("#135b97"));
                    tableRow.addView(vv4);

                    LinearLayout.LayoutParams layoutParams_tv21 = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1);
                    layoutParams_tv21.setMargins(0, 0, 0, 0);
                    textview = new TextView(IPIHDetails.this);
                    textview.setLayoutParams(layoutParams_tv21);
                    textview.setTextColor(Color.parseColor("#000000"));
                    textview.setGravity(Gravity.CENTER);
                    textview.setText(address);
                    tableRow.addView(textview);
                    table1.addView(tableRow);
                    Log.d("jsonArray1",address);
                }
            } catch (Exception e) {
               Log.e("JSON exception", e.getMessage());
                e.printStackTrace();
            }

        }




    }
}
