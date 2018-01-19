package com.example.admin.jsonarray;

import android.app.Activity;
import android.app.Dialog;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by admin on 13-07-2017.
 */

public class Accused_full_list extends Activity {
    TextView name,aliasname,fathername;
    TableLayout crimelist,assosiatelist;
    ConnectionDetector cd;
    RequestQueue requestQueue;
    LinearLayout tableRow;
    TextView textview;
    TableLayout listviewpopup;
    ArrayList<TextView> assosiatename;
    int textsize=0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.accused_full_list);
       

        int screenSize = getResources().getConfiguration().screenLayout &
                Configuration.SCREENLAYOUT_SIZE_MASK;

        String toastMsg;
        switch(screenSize) {
            case Configuration.SCREENLAYOUT_SIZE_LARGE:
                toastMsg = "Large screen";
                textsize=14;
                break;
            case Configuration.SCREENLAYOUT_SIZE_NORMAL:
                toastMsg = "Normal screen";
                textsize=12;
                break;
            case Configuration.SCREENLAYOUT_SIZE_SMALL:
                toastMsg = "Small screen";
                textsize=12;
                break;
            case Configuration.SCREENLAYOUT_SIZE_XLARGE:
                textsize=16;
            default:
                toastMsg = "Screen size is neither large, normal or small";
        }
        cd=new ConnectionDetector(this);
        assosiatename=new ArrayList<>();
        name=(TextView)findViewById(R.id.name);
        aliasname=(TextView)findViewById(R.id.aliasname);
        fathername=(TextView)findViewById(R.id.fathername);
        crimelist=(TableLayout)findViewById(R.id.crimelist);
        assosiatelist=(TableLayout)findViewById(R.id.assosiatelist);
        requestQueue= Volley.newRequestQueue(this);
        if(cd.isConnectingToInternet()) {
            try {
                JSONObject jsoninner = new JSONObject();
                jsoninner.put("UserId", "Kiteeye");
                jsoninner.put("Password", "a2l0ZUA2NjY=");
                jsoninner.put("Name", getIntent().getStringExtra("name"));
                jsoninner.put("AliasName", "");
                jsoninner.put("FatherName", "");
                jsoninner.put("PoliceStation", "");
                jsoninner.put("CrimeNumber", "");
                jsoninner.put("Locality", "");
//                JSONObject jsonparent = new JSONObject();
//                jsonparent.put("request", jsoninner);
//            Log.d("requestis",jsonparent.toString());
                //(Method.POST ,url, new JSONObject("request",jsoninner),

                String url1 = "http://tecdatum.net/kiteeyeapi/api/Service/AccusedPersonCrimeno";
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url1, jsoninner, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("psname", String.valueOf(response));

                        try {
                            String code =response.getString("Code");
                            if (code.equals("1")){
                                JSONArray jsonArray =response.getJSONArray("data");
                                if(jsonArray.length()>0){
                                    for(int i=0;i<jsonArray.length();i++){
                                       JSONObject data= jsonArray.getJSONObject(i);
                                        name.setText(data.getString("Name"));
                                        aliasname.setText(data.getString("AliasName"));
                                        fathername.setText(data.getString("FatherName"));
                                    }
                                }
                                JSONArray jsonArray1 =response.getJSONArray("data1");
                                if (jsonArray1.length()>0){
                                    for(int i=0;i<jsonArray1.length();i++){
                                        JSONObject data1=jsonArray1.getJSONObject(i);

                                        LinearLayout.LayoutParams llhead_layoutParamst = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
                                        llhead_layoutParamst.setMargins(10, 0, 0, 10);
                                        tableRow = new LinearLayout(Accused_full_list.this);
                                        tableRow.setOrientation(LinearLayout.HORIZONTAL);
                                        tableRow.setWeightSum(1);
                                        tableRow.setBackgroundResource(R.drawable.table_border);
                                        tableRow.setLayoutParams(llhead_layoutParamst);

                                        LinearLayout.LayoutParams layoutParams_tvt1 = new LinearLayout.LayoutParams(0,LinearLayout.LayoutParams.WRAP_CONTENT, .3f);
                                        layoutParams_tvt1.setMargins(10, 0, 0, 0);
                                        textview = new TextView(Accused_full_list.this);
                                        textview.setLayoutParams(layoutParams_tvt1);
                                        textview.setTextColor(Color.parseColor("#000000"));
                                        textview.setText("Crime"+"   "+(i+1));
                                        textview.setTextSize(textsize);
                                        tableRow.addView(textview);
                                        crimelist.addView(tableRow);

                                        LinearLayout.LayoutParams llhead_layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
                                        llhead_layoutParams.setMargins(0, 0, 0, 0);
                                        tableRow = new LinearLayout(Accused_full_list.this);
                                        tableRow.setOrientation(LinearLayout.HORIZONTAL);
                                        tableRow.setWeightSum(1);
                                        tableRow.setBackgroundResource(R.drawable.table_border);
                                        tableRow.setLayoutParams(llhead_layoutParams);

                                        LinearLayout.LayoutParams layoutParams_tvn = new LinearLayout.LayoutParams(0,LinearLayout.LayoutParams.WRAP_CONTENT, .3f);
                                        layoutParams_tvn.setMargins(10, 0, 0, 0);
                                        textview = new TextView(Accused_full_list.this);
                                        textview.setLayoutParams(layoutParams_tvn);
                                        // textview.setTextColor(Color.parseColor("#000000"));
//                                        textview.setGravity(Gravity.CENTER);
                                        textview.setText("Crime No");
                                        textview.setTextSize(textsize);
                                        tableRow.addView(textview);
                                        LinearLayout.LayoutParams layoutParams_tv1 = new LinearLayout.LayoutParams(0,LinearLayout.LayoutParams.WRAP_CONTENT, .2f);
                                        layoutParams_tv1.setMargins(0, 0, 0, 0);
                                        textview = new TextView(Accused_full_list.this);
                                        textview.setLayoutParams(layoutParams_tv1);
//                                        textview.setTextColor(Color.parseColor("#000000"));
                                        textview.setGravity(Gravity.CENTER);
                                        textview.setText(":");
                                        textview.setTextSize(textsize);
                                        tableRow.addView(textview);
                                        LinearLayout.LayoutParams layoutParams_tv2 = new LinearLayout.LayoutParams(0,LinearLayout.LayoutParams.WRAP_CONTENT, .5f);
                                        layoutParams_tv2.setMargins(0, 0, 0, 0);
                                        textview = new TextView(Accused_full_list.this);
                                        textview.setLayoutParams(layoutParams_tv2);
                                        textview.setTextColor(Color.parseColor("#135b97"));
                                        textview.setTextSize(textsize);
//                                        textview.setGravity(Gravity.CENTER_HORIZONTAL);
                                        textview.setText(data1.getString("CrimeNumber"));
                                        tableRow.addView(textview);
                                        crimelist.addView(tableRow);



                                        LinearLayout.LayoutParams llhead_layoutParams1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
                                        llhead_layoutParams1.setMargins(0, 0, 0, 0);
                                        tableRow = new LinearLayout(Accused_full_list.this);
                                        tableRow.setOrientation(LinearLayout.HORIZONTAL);
                                        tableRow.setWeightSum(1);
                                        tableRow.setBackgroundResource(R.drawable.table_border);
                                        tableRow.setLayoutParams(llhead_layoutParams1);

                                        LinearLayout.LayoutParams layoutParams_tvd = new LinearLayout.LayoutParams(0,LinearLayout.LayoutParams.WRAP_CONTENT, .3f);
                                        layoutParams_tvd.setMargins(10, 0, 0, 0);
                                        textview = new TextView(Accused_full_list.this);
                                        textview.setLayoutParams(layoutParams_tvd);
                                       // textview.setTextColor(Color.parseColor("#000000"));
//                                        textview.setGravity(Gravity.CENTER);
                                        textview.setText("Crime Date");
                                        tableRow.addView(textview);
                                        textview.setTextSize(textsize);
                                        LinearLayout.LayoutParams layoutParams_tvd1 = new LinearLayout.LayoutParams(0,LinearLayout.LayoutParams.WRAP_CONTENT, .2f);
                                        layoutParams_tvd1.setMargins(0, 0, 0, 0);
                                        textview = new TextView(Accused_full_list.this);
                                        textview.setLayoutParams(layoutParams_tvd1);
//                                        textview.setTextColor(Color.parseColor("#000000"));
                                        textview.setGravity(Gravity.CENTER);
                                        textview.setText(":");
                                        textview.setTextSize(textsize);
                                        tableRow.addView(textview);
                                        LinearLayout.LayoutParams layoutParams_tvd2 = new LinearLayout.LayoutParams(0,LinearLayout.LayoutParams.WRAP_CONTENT, .5f);
                                        layoutParams_tvd2.setMargins(0, 0, 0, 0);
                                        textview = new TextView(Accused_full_list.this);
                                        textview.setLayoutParams(layoutParams_tvd2);
                                        textview.setTextColor(Color.parseColor("#135b97"));
                                        textview.setTextSize(textsize);
//                                        textview.setGravity(Gravity.CENTER_HORIZONTAL);
                                        textview.setText(data1.getString("CrimeDate"));
                                        tableRow.addView(textview);
                                        crimelist.addView(tableRow);


                                        LinearLayout.LayoutParams llhead_layoutParamsp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
                                        llhead_layoutParamsp.setMargins(0, 0, 0, 0);
                                        tableRow = new LinearLayout(Accused_full_list.this);
                                        tableRow.setOrientation(LinearLayout.HORIZONTAL);
                                        tableRow.setWeightSum(1);
                                        tableRow.setBackgroundResource(R.drawable.table_border);
                                        tableRow.setLayoutParams(llhead_layoutParamsp);

                                        LinearLayout.LayoutParams layoutParams_tvps = new LinearLayout.LayoutParams(0,LinearLayout.LayoutParams.WRAP_CONTENT, .3f);
                                        layoutParams_tvps.setMargins(10, 0, 0, 0);
                                        textview = new TextView(Accused_full_list.this);
                                        textview.setLayoutParams(layoutParams_tvps);
//                                        textview.setTextColor(Color.parseColor("#000000"));
//                                        textview.setGravity(Gravity.CENTER_HORIZONTAL);
                                        textview.setText("PS");
                                        textview.setTextSize(textsize);
                                        tableRow.addView(textview);
                                        LinearLayout.LayoutParams layoutParams_tvps1 = new LinearLayout.LayoutParams(0,LinearLayout.LayoutParams.WRAP_CONTENT, .2f);
                                        layoutParams_tvps1.setMargins(0, 0, 0, 0);
                                        textview = new TextView(Accused_full_list.this);
                                        textview.setLayoutParams(layoutParams_tvps1);
//                                        textview.setTextColor(Color.parseColor("#135b97"));
                                        textview.setGravity(Gravity.CENTER);
                                        textview.setText(":");
                                        tableRow.addView(textview);
                                        textview.setTextSize(textsize);
                                        LinearLayout.LayoutParams layoutParams_tvps2 = new LinearLayout.LayoutParams(0,LinearLayout.LayoutParams.WRAP_CONTENT, .5f);
                                        layoutParams_tvps2.setMargins(0, 0, 0, 0);
                                        textview = new TextView(Accused_full_list.this);
                                        textview.setLayoutParams(layoutParams_tvps2);
                                        textview.setTextColor(Color.parseColor("#135b97"));
//                                        textview.setGravity(Gravity.CENTER_HORIZONTAL);
                                        textview.setText(data1.getString("PoliceStation"));
                                        textview.setTextSize(textsize);
                                        tableRow.addView(textview);
                                        crimelist.addView(tableRow);

                                        LinearLayout.LayoutParams llhead_layoutParamsm1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
                                        llhead_layoutParams1.setMargins(0, 0, 0, 0);
                                        tableRow = new LinearLayout(Accused_full_list.this);
                                        tableRow.setOrientation(LinearLayout.HORIZONTAL);
                                        tableRow.setWeightSum(1);
                                        tableRow.setBackgroundResource(R.drawable.table_border);
                                        tableRow.setLayoutParams(llhead_layoutParamsm1);

                                        LinearLayout.LayoutParams layoutParams_tvmo = new LinearLayout.LayoutParams(0,LinearLayout.LayoutParams.WRAP_CONTENT, .3f);
                                        layoutParams_tvmo.setMargins(10, 0, 0, 0);
                                        textview = new TextView(Accused_full_list.this);
                                        textview.setLayoutParams(layoutParams_tvmo);
//                                        textview.setTextColor(Color.parseColor("#000000"));
//                                        textview.setGravity(Gravity.CENTER_HORIZONTAL);
                                        textview.setText("MO Offender");
                                        textview.setTextSize(textsize);
                                        tableRow.addView(textview);
                                        LinearLayout.LayoutParams layoutParams_tvmo1 = new LinearLayout.LayoutParams(0,LinearLayout.LayoutParams.WRAP_CONTENT, .2f);
                                        layoutParams_tvmo1.setMargins(0, 0, 0, 0);
                                        textview = new TextView(Accused_full_list.this);
                                        textview.setLayoutParams(layoutParams_tvmo1);
//                                        textview.setTextColor(Color.parseColor("#000000"));
                                        textview.setGravity(Gravity.CENTER);
                                        textview.setText(":");
                                        textview.setTextSize(textsize);
                                        tableRow.addView(textview);
                                        LinearLayout.LayoutParams layoutParams_tvmo2 = new LinearLayout.LayoutParams(0,LinearLayout.LayoutParams.WRAP_CONTENT, .5f);
                                        layoutParams_tvmo2.setMargins(0, 0, 0, 0);
                                        textview = new TextView(Accused_full_list.this);
                                        textview.setLayoutParams(layoutParams_tvmo2);
                                        textview.setTextColor(Color.parseColor("#135b97"));
//                                        textview.setGravity(Gravity.CENTER);
                                        textview.setText(data1.getString("MO"));
                                        textview.setTextSize(textsize);
                                        tableRow.addView(textview);
                                        crimelist.addView(tableRow);


                                        LinearLayout.LayoutParams llhead_layoutParamsml1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
                                        llhead_layoutParamsml1.setMargins(0, 0, 0, 0);
                                        tableRow = new LinearLayout(Accused_full_list.this);
                                        tableRow.setOrientation(LinearLayout.HORIZONTAL);
                                        tableRow.setWeightSum(1);
                                        tableRow.setBackgroundResource(R.drawable.table_border);
                                        tableRow.setLayoutParams(llhead_layoutParamsml1);

                                        LinearLayout.LayoutParams layoutParams_tvlo = new LinearLayout.LayoutParams(0,LinearLayout.LayoutParams.WRAP_CONTENT, .3f);
                                        layoutParams_tvlo.setMargins(10, 0, 0, 0);
                                        textview = new TextView(Accused_full_list.this);
                                        textview.setLayoutParams(layoutParams_tvlo);
//                                        textview.setTextColor(Color.parseColor("#000000"));
//                                        textview.setGravity(Gravity.CENTER_HORIZONTAL);
                                        textview.setText("Location");
                                        textview.setTextSize(textsize);
                                        tableRow.addView(textview);
                                        LinearLayout.LayoutParams layoutParams_tvlo1 = new LinearLayout.LayoutParams(0,LinearLayout.LayoutParams.WRAP_CONTENT, .2f);
                                        layoutParams_tvlo1.setMargins(0, 0, 0, 0);
                                        textview = new TextView(Accused_full_list.this);
                                        textview.setLayoutParams(layoutParams_tvlo1);
//                                        textview.setTextColor(Color.parseColor("#000000"));
                                        textview.setGravity(Gravity.CENTER);
                                        textview.setText(":");
                                        textview.setTextSize(textsize);
                                        tableRow.addView(textview);
                                        LinearLayout.LayoutParams layoutParams_tvlo2 = new LinearLayout.LayoutParams(0,LinearLayout.LayoutParams.WRAP_CONTENT, .5f);
                                        layoutParams_tvlo2.setMargins(0, 0, 0, 0);
                                        textview = new TextView(Accused_full_list.this);
                                        textview.setLayoutParams(layoutParams_tvlo2);
                                        textview.setTextColor(Color.parseColor("#135b97"));
//                                        textview.setGravity(Gravity.CENTER);
                                        textview.setText(data1.getString("Location"));
                                        textview.setTextSize(textsize);
                                        tableRow.addView(textview);
                                        crimelist.addView(tableRow);

//
                                    }
                                }
                                JSONArray jsonArray2 =response.getJSONArray("data2");
                                Log.d("sravan",jsonArray2.toString());
                                if (jsonArray2.length()>0){
                                    for(int i=0;i<jsonArray2.length();i++){

                                        JSONObject data2=jsonArray2.getJSONObject(i);
                                        LinearLayout.LayoutParams llhead_layoutParamst = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
                                        llhead_layoutParamst.setMargins(10, 0, 0, 10);
                                        tableRow = new LinearLayout(Accused_full_list.this);
                                        tableRow.setOrientation(LinearLayout.HORIZONTAL);
                                        tableRow.setWeightSum(1);
                                        tableRow.setBackgroundResource(R.drawable.table_border);
                                        tableRow.setLayoutParams(llhead_layoutParamst);

                                        LinearLayout.LayoutParams layoutParams_tvt1 = new LinearLayout.LayoutParams(0,LinearLayout.LayoutParams.WRAP_CONTENT, .3f);
                                        layoutParams_tvt1.setMargins(10, 0, 0, 0);
                                        textview = new TextView(Accused_full_list.this);
                                        textview.setLayoutParams(layoutParams_tvt1);
                                        textview.setTextColor(Color.parseColor("#000000"));
                                        textview.setText("Associated"+"   "+ (i+1));
                                        textview.setTextSize(textsize);
                                        tableRow.addView(textview);
                                        assosiatelist.addView(tableRow);

                                        LinearLayout.LayoutParams llhead_layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
                                        llhead_layoutParams.setMargins(0, 0, 0, 0);
                                        tableRow = new LinearLayout(Accused_full_list.this);
                                        tableRow.setOrientation(LinearLayout.HORIZONTAL);
                                        tableRow.setWeightSum(1);
                                        tableRow.setBackgroundResource(R.drawable.table_border);
                                        tableRow.setLayoutParams(llhead_layoutParams);

                                        LinearLayout.LayoutParams layoutParams_tvn = new LinearLayout.LayoutParams(0,LinearLayout.LayoutParams.WRAP_CONTENT, .3f);
                                        layoutParams_tvn.setMargins(10, 0, 0, 0);
                                        textview = new TextView(Accused_full_list.this);
                                        textview.setLayoutParams(layoutParams_tvn);
                                        // textview.setTextColor(Color.parseColor("#000000"));
//                                        textview.setGravity(Gravity.CENTER);
                                        textview.setText("Crime No");
                                        textview.setTextSize(textsize);
                                        tableRow.addView(textview);
                                        LinearLayout.LayoutParams layoutParams_tv1 = new LinearLayout.LayoutParams(0,LinearLayout.LayoutParams.WRAP_CONTENT, .2f);
                                        layoutParams_tv1.setMargins(0, 0, 0, 0);
                                        textview = new TextView(Accused_full_list.this);
                                        textview.setLayoutParams(layoutParams_tv1);
//                                        textview.setTextColor(Color.parseColor("#000000"));
                                        textview.setGravity(Gravity.CENTER);
                                        textview.setText(":");
                                        textview.setTextSize(textsize);
                                        tableRow.addView(textview);
                                        LinearLayout.LayoutParams layoutParams_tv2 = new LinearLayout.LayoutParams(0,LinearLayout.LayoutParams.WRAP_CONTENT, .5f);
                                        layoutParams_tv2.setMargins(0, 0, 0, 0);
                                        textview = new TextView(Accused_full_list.this);
                                        textview.setLayoutParams(layoutParams_tv2);
                                        textview.setTextColor(Color.parseColor("#135b97"));
//                                        textview.setGravity(Gravity.CENTER_HORIZONTAL);
                                        textview.setText(data2.getString("CrimeNumber"));
                                        textview.setTextSize(textsize);
                                        tableRow.addView(textview);
                                        assosiatelist.addView(tableRow);



                                        LinearLayout.LayoutParams llhead_layoutParams1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
                                        llhead_layoutParams1.setMargins(0, 0, 0, 0);
                                        tableRow = new LinearLayout(Accused_full_list.this);
                                        tableRow.setOrientation(LinearLayout.HORIZONTAL);
                                        tableRow.setWeightSum(1);
                                        tableRow.setBackgroundResource(R.drawable.table_border);
                                        tableRow.setLayoutParams(llhead_layoutParams1);

                                        LinearLayout.LayoutParams layoutParams_tvd = new LinearLayout.LayoutParams(0,LinearLayout.LayoutParams.WRAP_CONTENT, .3f);
                                        layoutParams_tvd.setMargins(10, 0, 0, 0);
                                        textview = new TextView(Accused_full_list.this);
                                        textview.setLayoutParams(layoutParams_tvd);
                                        // textview.setTextColor(Color.parseColor("#000000"));
//                                        textview.setGravity(Gravity.CENTER);
                                        textview.setText("Name");
                                        tableRow.addView(textview);
                                        textview.setTextSize(textsize);
                                        LinearLayout.LayoutParams layoutParams_tvd1 = new LinearLayout.LayoutParams(0,LinearLayout.LayoutParams.WRAP_CONTENT, .2f);
                                        layoutParams_tvd1.setMargins(0, 0, 0, 0);
                                        textview = new TextView(Accused_full_list.this);
                                        textview.setLayoutParams(layoutParams_tvd1);
//                                        textview.setTextColor(Color.parseColor("#000000"));
                                        textview.setGravity(Gravity.CENTER);
                                        textview.setText(":");
                                        textview.setTextSize(textsize);
                                        tableRow.addView(textview);
                                        LinearLayout.LayoutParams layoutParams_tvd2 = new LinearLayout.LayoutParams(0,LinearLayout.LayoutParams.WRAP_CONTENT, .5f);
                                        layoutParams_tvd2.setMargins(0, 0, 0, 0);
                                        textview = new TextView(Accused_full_list.this);
                                        textview.setLayoutParams(layoutParams_tvd2);
                                        textview.setId(i);
                                        textview.setTextColor(Color.parseColor("#E1326E"));
//                                        textview.setGravity(Gravity.CENTER_HORIZONTAL);
                                        textview.setText(data2.getString("Name"));
                                        textview.setTextSize(textsize);
                                        tableRow.addView(textview);
                                        assosiatename.add(textview);
                                        assosiatelist.addView(tableRow);

                                        textview.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                Dialog d=new Dialog(Accused_full_list.this);
                                                d.setContentView(R.layout.accused_popup);
                                                listviewpopup=(TableLayout)d.findViewById(R.id.listviewpopup);
                                                d.show();
                                                String result = assosiatename.get(v.getId()).getText().toString();
                                                Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
                                                if(cd.isConnectingToInternet()) {
                                                    try {
                                                        JSONObject jsoninner = new JSONObject();
                                                        jsoninner.put("UserId", "Kiteeye");
                                                        jsoninner.put("Password", "a2l0ZUA2NjY=");
                                                        jsoninner.put("Name", result);
                                                        jsoninner.put("AliasName", "");
                                                        jsoninner.put("FatherName", "");
                                                        jsoninner.put("PoliceStation", "");
                                                        jsoninner.put("CrimeNumber", "");
                                                        jsoninner.put("Locality", "");


                                                        String url1 = "http://tecdatum.net/kiteeyeapi/api/Service/AccusedPersonCrimeno";
                                                        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url1, jsoninner, new Response.Listener<JSONObject>() {
                                                            @Override
                                                            public void onResponse(JSONObject response) {
                                                                Log.d("psname", String.valueOf(response));

                                                                try {
                                                                    String code =response.getString("Code");
                                                                    if (code.equals("1")){

                                                                        JSONArray jsonArray1 =response.getJSONArray("data1");
                                                                        LinearLayout.LayoutParams llhead_layoutParamstt = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
                                                                        llhead_layoutParamstt.setMargins(10, 0, 0, 10);
                                                                        tableRow = new LinearLayout(Accused_full_list.this);
                                                                        tableRow.setOrientation(LinearLayout.HORIZONTAL);
                                                                        tableRow.setWeightSum(1);
                                                                        tableRow.setBackgroundResource(R.drawable.table_border);
                                                                        tableRow.setLayoutParams(llhead_layoutParamstt);

                                                                        LinearLayout.LayoutParams layoutParams_tvt1t = new LinearLayout.LayoutParams(0,LinearLayout.LayoutParams.WRAP_CONTENT, .9f);
                                                                        layoutParams_tvt1t.setMargins(10, 0, 0, 0);
                                                                        textview = new TextView(Accused_full_list.this);
                                                                        textview.setLayoutParams(layoutParams_tvt1t);
                                                                        textview.setTextColor(Color.parseColor("#000000"));
                                                                        textview.setText("Total Crime"+"      "+jsonArray1.length());
                                                                        textview.setTextSize(textsize);
                                                                        textview.setGravity(Gravity.RIGHT);
                                                                        tableRow.addView(textview);
                                                                        listviewpopup.addView(tableRow);
                                                                        if (jsonArray1.length()>0){
                                                                            for(int i=0;i<jsonArray1.length();i++){
                                                                                JSONObject data1=jsonArray1.getJSONObject(i);
                                                                                LinearLayout.LayoutParams llhead_layoutParamst = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
                                                                                llhead_layoutParamst.setMargins(10, 0, 0, 10);
                                                                                tableRow = new LinearLayout(Accused_full_list.this);
                                                                                tableRow.setOrientation(LinearLayout.HORIZONTAL);
                                                                                tableRow.setWeightSum(1);
                                                                                tableRow.setBackgroundResource(R.drawable.table_border);
                                                                                tableRow.setLayoutParams(llhead_layoutParamst);

                                                                                LinearLayout.LayoutParams layoutParams_tvt1 = new LinearLayout.LayoutParams(0,LinearLayout.LayoutParams.WRAP_CONTENT, .3f);
                                                                                layoutParams_tvt1.setMargins(10, 0, 0, 0);
                                                                                textview = new TextView(Accused_full_list.this);
                                                                                textview.setLayoutParams(layoutParams_tvt1);
                                                                                textview.setTextColor(Color.parseColor("#000000"));
                                                                                textview.setText("Crime"+"   "+(i+1));
                                                                                textview.setTextSize(textsize);
                                                                                tableRow.addView(textview);
                                                                                listviewpopup.addView(tableRow);

                                                                                LinearLayout.LayoutParams llhead_layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
                                                                                llhead_layoutParams.setMargins(0, 0, 0, 0);
                                                                                tableRow = new LinearLayout(Accused_full_list.this);
                                                                                tableRow.setOrientation(LinearLayout.HORIZONTAL);
                                                                                tableRow.setWeightSum(1);
                                                                                tableRow.setBackgroundResource(R.drawable.table_border);
                                                                                tableRow.setLayoutParams(llhead_layoutParams);

                                                                                LinearLayout.LayoutParams layoutParams_tvn = new LinearLayout.LayoutParams(0,LinearLayout.LayoutParams.WRAP_CONTENT, .3f);
                                                                                layoutParams_tvn.setMargins(10, 0, 0, 0);
                                                                                textview = new TextView(Accused_full_list.this);
                                                                                textview.setLayoutParams(layoutParams_tvn);
                                                                                // textview.setTextColor(Color.parseColor("#000000"));
//                                        textview.setGravity(Gravity.CENTER);
                                                                                textview.setText("Crime No");
                                                                                textview.setTextSize(textsize);
                                                                                tableRow.addView(textview);
                                                                                LinearLayout.LayoutParams layoutParams_tv1 = new LinearLayout.LayoutParams(0,LinearLayout.LayoutParams.WRAP_CONTENT, .2f);
                                                                                layoutParams_tv1.setMargins(0, 0, 0, 0);
                                                                                textview = new TextView(Accused_full_list.this);
                                                                                textview.setLayoutParams(layoutParams_tv1);
//                                        textview.setTextColor(Color.parseColor("#000000"));
                                                                                textview.setGravity(Gravity.CENTER);
                                                                                textview.setText(":");
                                                                                tableRow.addView(textview);
                                                                                textview.setTextSize(textsize);
                                                                                LinearLayout.LayoutParams layoutParams_tv2 = new LinearLayout.LayoutParams(0,LinearLayout.LayoutParams.WRAP_CONTENT, .5f);
                                                                                layoutParams_tv2.setMargins(0, 0, 0, 0);
                                                                                textview = new TextView(Accused_full_list.this);
                                                                                textview.setLayoutParams(layoutParams_tv2);
                                                                                textview.setTextColor(Color.parseColor("#135b97"));
//                                        textview.setGravity(Gravity.CENTER_HORIZONTAL);
                                                                                textview.setText(data1.getString("CrimeNumber"));
                                                                                textview.setTextSize(textsize);
                                                                                tableRow.addView(textview);
                                                                                listviewpopup.addView(tableRow);



                                                                                LinearLayout.LayoutParams llhead_layoutParams1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
                                                                                llhead_layoutParams1.setMargins(0, 0, 0, 0);
                                                                                tableRow = new LinearLayout(Accused_full_list.this);
                                                                                tableRow.setOrientation(LinearLayout.HORIZONTAL);
                                                                                tableRow.setWeightSum(1);
                                                                                tableRow.setBackgroundResource(R.drawable.table_border);
                                                                                tableRow.setLayoutParams(llhead_layoutParams1);

                                                                                LinearLayout.LayoutParams layoutParams_tvd = new LinearLayout.LayoutParams(0,LinearLayout.LayoutParams.WRAP_CONTENT, .3f);
                                                                                layoutParams_tvd.setMargins(10, 0, 0, 0);
                                                                                textview = new TextView(Accused_full_list.this);
                                                                                textview.setLayoutParams(layoutParams_tvd);
                                                                                // textview.setTextColor(Color.parseColor("#000000"));
//                                        textview.setGravity(Gravity.CENTER);
                                                                                textview.setText("Crime Date");
                                                                                textview.setTextSize(textsize);
                                                                                tableRow.addView(textview);
                                                                                LinearLayout.LayoutParams layoutParams_tvd1 = new LinearLayout.LayoutParams(0,LinearLayout.LayoutParams.WRAP_CONTENT, .2f);
                                                                                layoutParams_tvd1.setMargins(0, 0, 0, 0);
                                                                                textview = new TextView(Accused_full_list.this);
                                                                                textview.setLayoutParams(layoutParams_tvd1);
//                                        textview.setTextColor(Color.parseColor("#000000"));
                                                                                textview.setGravity(Gravity.CENTER);
                                                                                textview.setText(":");
                                                                                textview.setTextSize(textsize);
                                                                                tableRow.addView(textview);
                                                                                LinearLayout.LayoutParams layoutParams_tvd2 = new LinearLayout.LayoutParams(0,LinearLayout.LayoutParams.WRAP_CONTENT, .5f);
                                                                                layoutParams_tvd2.setMargins(0, 0, 0, 0);
                                                                                textview = new TextView(Accused_full_list.this);
                                                                                textview.setLayoutParams(layoutParams_tvd2);
                                                                                textview.setTextColor(Color.parseColor("#135b97"));
//                                        textview.setGravity(Gravity.CENTER_HORIZONTAL);
                                                                                textview.setText(data1.getString("CrimeDate"));
                                                                                textview.setTextSize(textsize);
                                                                                tableRow.addView(textview);
                                                                                listviewpopup.addView(tableRow);


                                                                                LinearLayout.LayoutParams llhead_layoutParamsp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
                                                                                llhead_layoutParamsp.setMargins(0, 0, 0, 0);
                                                                                tableRow = new LinearLayout(Accused_full_list.this);
                                                                                tableRow.setOrientation(LinearLayout.HORIZONTAL);
                                                                                tableRow.setWeightSum(1);
                                                                                tableRow.setBackgroundResource(R.drawable.table_border);
                                                                                tableRow.setLayoutParams(llhead_layoutParamsp);

                                                                                LinearLayout.LayoutParams layoutParams_tvps = new LinearLayout.LayoutParams(0,LinearLayout.LayoutParams.WRAP_CONTENT, .3f);
                                                                                layoutParams_tvps.setMargins(10, 0, 0, 0);
                                                                                textview = new TextView(Accused_full_list.this);
                                                                                textview.setLayoutParams(layoutParams_tvps);
//                                        textview.setTextColor(Color.parseColor("#000000"));
//                                        textview.setGravity(Gravity.CENTER_HORIZONTAL);
                                                                                textview.setText("PS");
                                                                                textview.setTextSize(textsize);
                                                                                tableRow.addView(textview);
                                                                                LinearLayout.LayoutParams layoutParams_tvps1 = new LinearLayout.LayoutParams(0,LinearLayout.LayoutParams.WRAP_CONTENT, .2f);
                                                                                layoutParams_tvps1.setMargins(0, 0, 0, 0);
                                                                                textview = new TextView(Accused_full_list.this);
                                                                                textview.setLayoutParams(layoutParams_tvps1);
//                                        textview.setTextColor(Color.parseColor("#135b97"));
                                                                                textview.setGravity(Gravity.CENTER);
                                                                                textview.setText(":");
                                                                                textview.setTextSize(textsize);
                                                                                tableRow.addView(textview);
                                                                                LinearLayout.LayoutParams layoutParams_tvps2 = new LinearLayout.LayoutParams(0,LinearLayout.LayoutParams.WRAP_CONTENT, .5f);
                                                                                layoutParams_tvps2.setMargins(0, 0, 0, 0);
                                                                                textview = new TextView(Accused_full_list.this);
                                                                                textview.setLayoutParams(layoutParams_tvps2);
                                                                                textview.setTextColor(Color.parseColor("#135b97"));
                                                                                textview.setTextSize(textsize);
//                                        textview.setGravity(Gravity.CENTER_HORIZONTAL);
                                                                                textview.setText(data1.getString("PoliceStation"));
                                                                                tableRow.addView(textview);
                                                                                listviewpopup.addView(tableRow);

                                                                                LinearLayout.LayoutParams llhead_layoutParamsm1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
                                                                                llhead_layoutParams1.setMargins(0, 0, 0, 0);
                                                                                tableRow = new LinearLayout(Accused_full_list.this);
                                                                                tableRow.setOrientation(LinearLayout.HORIZONTAL);
                                                                                tableRow.setWeightSum(1);
                                                                                tableRow.setBackgroundResource(R.drawable.table_border);
                                                                                tableRow.setLayoutParams(llhead_layoutParamsm1);

                                                                                LinearLayout.LayoutParams layoutParams_tvmo = new LinearLayout.LayoutParams(0,LinearLayout.LayoutParams.WRAP_CONTENT, .3f);
                                                                                layoutParams_tvmo.setMargins(10, 0, 0, 0);
                                                                                textview = new TextView(Accused_full_list.this);
                                                                                textview.setLayoutParams(layoutParams_tvmo);
//                                        textview.setTextColor(Color.parseColor("#000000"));
//                                        textview.setGravity(Gravity.CENTER_HORIZONTAL);
                                                                                textview.setText("MO Offender");
                                                                                textview.setTextSize(textsize);
                                                                                tableRow.addView(textview);
                                                                                LinearLayout.LayoutParams layoutParams_tvmo1 = new LinearLayout.LayoutParams(0,LinearLayout.LayoutParams.WRAP_CONTENT, .2f);
                                                                                layoutParams_tvmo1.setMargins(0, 0, 0, 0);
                                                                                textview = new TextView(Accused_full_list.this);
                                                                                textview.setLayoutParams(layoutParams_tvmo1);
//                                        textview.setTextColor(Color.parseColor("#000000"));
                                                                                textview.setGravity(Gravity.CENTER);
                                                                                textview.setText(":");
                                                                                textview.setTextSize(textsize);
                                                                                tableRow.addView(textview);
                                                                                LinearLayout.LayoutParams layoutParams_tvmo2 = new LinearLayout.LayoutParams(0,LinearLayout.LayoutParams.WRAP_CONTENT, .5f);
                                                                                layoutParams_tvmo2.setMargins(0, 0, 0, 0);
                                                                                textview = new TextView(Accused_full_list.this);
                                                                                textview.setLayoutParams(layoutParams_tvmo2);
                                                                                textview.setTextColor(Color.parseColor("#135b97"));
//                                        textview.setGravity(Gravity.CENTER);
                                                                                textview.setText(data1.getString("MO"));
                                                                                textview.setTextSize(textsize);
                                                                                tableRow.addView(textview);
                                                                                listviewpopup.addView(tableRow);


                                                                                LinearLayout.LayoutParams llhead_layoutParamsml1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
                                                                                llhead_layoutParamsml1.setMargins(0, 0, 0, 0);
                                                                                tableRow = new LinearLayout(Accused_full_list.this);
                                                                                tableRow.setOrientation(LinearLayout.HORIZONTAL);
                                                                                tableRow.setWeightSum(1);
                                                                                tableRow.setBackgroundResource(R.drawable.table_border);
                                                                                tableRow.setLayoutParams(llhead_layoutParamsml1);


                                                                                LinearLayout.LayoutParams layoutParams_tvlo = new LinearLayout.LayoutParams(0,LinearLayout.LayoutParams.WRAP_CONTENT, .3f);
                                                                                layoutParams_tvlo.setMargins(10, 0, 0, 0);
                                                                                textview = new TextView(Accused_full_list.this);
                                                                                textview.setLayoutParams(layoutParams_tvlo);
//                                        textview.setTextColor(Color.parseColor("#000000"));
//                                        textview.setGravity(Gravity.CENTER_HORIZONTAL);
                                                                                textview.setText("Location");
                                                                                textview.setTextSize(textsize);
                                                                                tableRow.addView(textview);
                                                                                LinearLayout.LayoutParams layoutParams_tvlo1 = new LinearLayout.LayoutParams(0,LinearLayout.LayoutParams.WRAP_CONTENT, .2f);
                                                                                layoutParams_tvlo1.setMargins(0, 0, 0, 0);
                                                                                textview = new TextView(Accused_full_list.this);
                                                                                textview.setLayoutParams(layoutParams_tvlo1);
//                                        textview.setTextColor(Color.parseColor("#000000"));
                                                                                textview.setGravity(Gravity.CENTER);
                                                                                textview.setText(":");
                                                                                textview.setTextSize(textsize);
                                                                                tableRow.addView(textview);
                                                                                LinearLayout.LayoutParams layoutParams_tvlo2 = new LinearLayout.LayoutParams(0,LinearLayout.LayoutParams.WRAP_CONTENT, .5f);
                                                                                layoutParams_tvlo2.setMargins(0, 0, 0, 0);
                                                                                textview = new TextView(Accused_full_list.this);
                                                                                textview.setLayoutParams(layoutParams_tvlo2);
                                                                                textview.setTextColor(Color.parseColor("#135b97"));
//                                        textview.setGravity(Gravity.CENTER);
                                                                                textview.setText(data1.getString("Location"));
                                                                                textview.setTextSize(textsize);
                                                                                tableRow.addView(textview);
                                                                                listviewpopup.addView(tableRow);

//
                                                                            }
                                                                        }



                                                                    }
                                                                    else{
                                                                        Toast.makeText(getApplication(),response.getString("Message"),Toast.LENGTH_SHORT).show();
                                                                    }
                                                                } catch (JSONException e) {
                                                                    e.printStackTrace();
                                                                }

//
                                                            }
                                                        },
                                                                new Response.ErrorListener() {
                                                                    @Override
                                                                    public void onErrorResponse(VolleyError error) {

                                                                    }
                                                                });
                                                        //add request to queue
                                                        requestQueue.add(jsonObjectRequest);
                                                    } catch (Exception e) {

                                                    }
                                                }
                                                else{
                                                    Toast.makeText(getApplicationContext(),"NO INTERNET CONNECTION",Toast.LENGTH_SHORT);
                                                }







                                            }
                                        });





                                        LinearLayout.LayoutParams llhead_layoutParamsp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
                                        llhead_layoutParamsp.setMargins(0, 0, 0, 0);
                                        tableRow = new LinearLayout(Accused_full_list.this);
                                        tableRow.setOrientation(LinearLayout.HORIZONTAL);
                                        tableRow.setWeightSum(1);
                                        tableRow.setBackgroundResource(R.drawable.table_border);
                                        tableRow.setLayoutParams(llhead_layoutParamsp);

                                        LinearLayout.LayoutParams layoutParams_tvps = new LinearLayout.LayoutParams(0,LinearLayout.LayoutParams.WRAP_CONTENT, .3f);
                                        layoutParams_tvps.setMargins(10, 0, 0, 0);
                                        textview = new TextView(Accused_full_list.this);
                                        textview.setLayoutParams(layoutParams_tvps);
//                                        textview.setTextColor(Color.parseColor("#000000"));
//                                        textview.setGravity(Gravity.CENTER_HORIZONTAL);
                                        textview.setText("Alias Name");
                                        textview.setTextSize(textsize);
                                        tableRow.addView(textview);
                                        LinearLayout.LayoutParams layoutParams_tvps1 = new LinearLayout.LayoutParams(0,LinearLayout.LayoutParams.WRAP_CONTENT, .2f);
                                        layoutParams_tvps1.setMargins(0, 0, 0, 0);
                                        textview = new TextView(Accused_full_list.this);
                                        textview.setLayoutParams(layoutParams_tvps1);
//                                        textview.setTextColor(Color.parseColor("#135b97"));
                                        textview.setGravity(Gravity.CENTER);
                                        textview.setText(":");
                                        textview.setTextSize(textsize);
                                        tableRow.addView(textview);
                                        LinearLayout.LayoutParams layoutParams_tvps2 = new LinearLayout.LayoutParams(0,LinearLayout.LayoutParams.WRAP_CONTENT, .5f);
                                        layoutParams_tvps2.setMargins(0, 0, 0, 0);
                                        textview = new TextView(Accused_full_list.this);
                                        textview.setLayoutParams(layoutParams_tvps2);
                                        textview.setTextColor(Color.parseColor("#135b97"));
//                                        textview.setGravity(Gravity.CENTER_HORIZONTAL);
                                        textview.setText(data2.getString("AliasName"));
                                        textview.setTextSize(textsize);
                                        tableRow.addView(textview);
                                        assosiatelist.addView(tableRow);

                                        LinearLayout.LayoutParams llhead_layoutParamsm1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
                                        llhead_layoutParams1.setMargins(0, 0, 0, 0);
                                        tableRow = new LinearLayout(Accused_full_list.this);
                                        tableRow.setOrientation(LinearLayout.HORIZONTAL);
                                        tableRow.setWeightSum(1);
                                        tableRow.setBackgroundResource(R.drawable.table_border);
                                        tableRow.setLayoutParams(llhead_layoutParamsm1);

                                        LinearLayout.LayoutParams layoutParams_tvmo = new LinearLayout.LayoutParams(0,LinearLayout.LayoutParams.WRAP_CONTENT, .3f);
                                        layoutParams_tvmo.setMargins(10, 0, 0, 0);
                                        textview = new TextView(Accused_full_list.this);
                                        textview.setLayoutParams(layoutParams_tvmo);
//                                        textview.setTextColor(Color.parseColor("#000000"));
//                                        textview.setGravity(Gravity.CENTER_HORIZONTAL);
                                        textview.setText("Father Name");
                                        textview.setTextSize(textsize);
                                        tableRow.addView(textview);
                                        LinearLayout.LayoutParams layoutParams_tvmo1 = new LinearLayout.LayoutParams(0,LinearLayout.LayoutParams.WRAP_CONTENT, .2f);
                                        layoutParams_tvmo1.setMargins(0, 0, 0, 0);
                                        textview = new TextView(Accused_full_list.this);
                                        textview.setLayoutParams(layoutParams_tvmo1);
//                                        textview.setTextColor(Color.parseColor("#000000"));
                                        textview.setGravity(Gravity.CENTER);
                                        textview.setText(":");
                                        textview.setTextSize(textsize);
                                        tableRow.addView(textview);
                                        LinearLayout.LayoutParams layoutParams_tvmo2 = new LinearLayout.LayoutParams(0,LinearLayout.LayoutParams.WRAP_CONTENT, .5f);
                                        layoutParams_tvmo2.setMargins(0, 0, 0, 0);
                                        textview = new TextView(Accused_full_list.this);
                                        textview.setLayoutParams(layoutParams_tvmo2);
                                        textview.setTextColor(Color.parseColor("#135b97"));
//                                        textview.setGravity(Gravity.CENTER);
                                        textview.setText(data2.getString("FatherName"));
                                        textview.setTextSize(textsize);
                                        tableRow.addView(textview);
                                        assosiatelist.addView(tableRow);


                                        LinearLayout.LayoutParams llhead_layoutParamsml1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
                                        llhead_layoutParamsml1.setMargins(0, 0, 0, 0);
                                        tableRow = new LinearLayout(Accused_full_list.this);
                                        tableRow.setOrientation(LinearLayout.HORIZONTAL);
                                        tableRow.setWeightSum(1);
                                        tableRow.setBackgroundResource(R.drawable.table_border);
                                        tableRow.setLayoutParams(llhead_layoutParamsml1);

                                        LinearLayout.LayoutParams layoutParams_tvlo = new LinearLayout.LayoutParams(0,LinearLayout.LayoutParams.WRAP_CONTENT, .3f);
                                        layoutParams_tvlo.setMargins(10, 0, 0, 0);
                                        textview = new TextView(Accused_full_list.this);
                                        textview.setLayoutParams(layoutParams_tvlo);
//                                        textview.setTextColor(Color.parseColor("#000000"));
//                                        textview.setGravity(Gravity.CENTER_HORIZONTAL);
                                        textview.setText("Address");
                                        textview.setTextSize(textsize);
                                        tableRow.addView(textview);
                                        LinearLayout.LayoutParams layoutParams_tvlo1 = new LinearLayout.LayoutParams(0,LinearLayout.LayoutParams.WRAP_CONTENT, .2f);
                                        layoutParams_tvlo1.setMargins(0, 0, 0, 0);
                                        textview = new TextView(Accused_full_list.this);
                                        textview.setLayoutParams(layoutParams_tvlo1);
//                                        textview.setTextColor(Color.parseColor("#000000"));
                                        textview.setGravity(Gravity.CENTER);
                                        textview.setText(":");
                                        textview.setTextSize(textsize);
                                        tableRow.addView(textview);
                                        LinearLayout.LayoutParams layoutParams_tvlo2 = new LinearLayout.LayoutParams(0,LinearLayout.LayoutParams.WRAP_CONTENT, .5f);
                                        layoutParams_tvlo2.setMargins(0, 0, 0, 0);
                                        textview = new TextView(Accused_full_list.this);
                                        textview.setLayoutParams(layoutParams_tvlo2);
                                        textview.setTextColor(Color.parseColor("#135b97"));
//                                        textview.setGravity(Gravity.CENTER);

                                        textview.setText(data2.getString("Address"));
                                        textview.setTextSize(textsize);
                                        tableRow.addView(textview);
                                        assosiatelist.addView(tableRow);
                       }
                                }


                            }
                            else{
                                Toast.makeText(getApplication(),response.getString("Message"),Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

//

                    }
                },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                            }
                        });
                //add request to queue
                requestQueue.add(jsonObjectRequest);
            } catch (Exception e) {

            }
        }
        else{
            Toast.makeText(getApplicationContext(),"NO INTERNET CONNECTION",Toast.LENGTH_SHORT);
        }



    }
}
