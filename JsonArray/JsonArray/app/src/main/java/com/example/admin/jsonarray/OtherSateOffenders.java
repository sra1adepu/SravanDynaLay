package com.example.admin.jsonarray;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

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
import java.util.List;

/**
 * Created by admin on 14-06-2017.
 */

public class OtherSateOffenders extends Activity {
    Spinner zone,division;
    RequestQueue requestQueue;
    List<String> zonelist,psidvalues;
    JSONObject jsoninner;
    String pos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.offenders);
        zone=(Spinner)findViewById(R.id.division);
        division=(Spinner)findViewById(R.id.division);
        zonelist=new ArrayList<>();
        requestQueue = Volley.newRequestQueue(this);
//        try {
//            jsoninner = new JSONObject();
//            zone.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//                public void onItemSelected(AdapterView<?> parent, View view,
//                                           int position, long id) {
//                    // TODO Auto-generated method stub
//
//                    if(position==0){
//                        zone.get(position).toString();
//                        jsoninner = new JSONObject();
//                        try {
//                            jsoninner.put("Zone", "");
//                            jsoninner.put("Division", "");
//                            jsoninner.put("PoliceStation", "");
//                            new Divisiondata().execute();
//                        }
//                        catch (Exception e)
//                        {
//
//                        }
//
//                    }
//
//                    else{
//                        zoneselected = zonevaluearray.get(position).toString();
//                        try {
//                            JSONObject js = new JSONObject();
//                            js.put("Zone", zoneselected);
//                            js.put("Division", "");
//                            js.put("PoliceStation", "");
//                            // new Divisiondatawz().execute();
//                        }
//                        catch (Exception e)
//                        {
//
//                        }
//
//                    }
//
//
//                }
//
//                @Override
//                public void onNothingSelected(AdapterView<?> parent) {
//                    // TODO Auto-generated method stub
//
//                }
//            });

        try {
            zone.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        pos = (String) zone.getSelectedItem();
                    if(!pos.equals("All")){
                        JSONObject jsoninner = new JSONObject();
                        try {
                            jsoninner.put("Zone",pos);
                            jsoninner.put("Division","");
                            jsoninner.put("PoliceStation","");
                            JSONObject jsonparent =new JSONObject();
                            jsonparent.put("request",jsoninner);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                        Log.d("pos",pos);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
            JSONObject jsoninner = new JSONObject();
            jsoninner.put("Zone","");
            jsoninner.put("Division","");
            jsoninner.put("PoliceStation","");
            JSONObject jsonparent =new JSONObject();
            jsonparent.put("request",jsoninner);
//            Log.d("requestis",jsonparent.toString());
            //(Method.POST ,url, new JSONObject("request",jsoninner),

            String url1 = "http://220.225.38.123:8081//LogicShore.svc/GetAllPSdetails";
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url1,jsonparent ,  new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Log.d("psname", String.valueOf(response));

                    try {
                        JSONArray pslist = response.getJSONArray("zonelist");
                        JSONArray divlist = response.getJSONArray("divisionlist");
                        if(pslist.length()>0){
                            zonelist= new ArrayList<String>();
                            psidvalues= new ArrayList<String>();
                            for (int i = 0; i<pslist.length();i++) {
                                JSONObject data = pslist.getJSONObject(i);
                                JSONObject data1=divlist.getJSONObject(i);
                                String psname = data.getString("Zone");
                                String psid = data1.getString("Division");
                                zonelist.add(psname);
                                psidvalues.add(psid);
                            }
                                ArrayAdapter spinnerArrayAdapter = new ArrayAdapter(OtherSateOffenders.this, android.R.layout.simple_spinner_item, zonelist);
                                spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                zone.setAdapter(spinnerArrayAdapter);
                            ArrayAdapter spinnerArrayAdapter1 = new ArrayAdapter(OtherSateOffenders.this, android.R.layout.simple_spinner_item, psidvalues);
                            spinnerArrayAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            division.setAdapter(spinnerArrayAdapter1);
                        }
                    }
                    catch (JSONException e) {
                        e.printStackTrace();
                    }


//                    try {
//                        String transId = response.getString("TransId");
//                        Log.d("transid", transId);
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }


                }
            },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    });
            //add request to queue
            requestQueue.add(jsonObjectRequest);
        }
        catch(Exception e)
        {

        }
    }
}
