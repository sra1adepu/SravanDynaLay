package com.example.admin.jsonarray;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
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
import java.util.Calendar;
import java.util.List;

/**
 * Created by admin on 09-05-2017.
 */

public class mooffernder_add_personal extends Activity {
    private static final String MY_PREFS_NAME = "MyPrefs" ; ;
    private int mYear, mMonth, mDay;
    EditText offendernameedit,aliasesedit,dobedit,ageedit,mobilenoedit,emailedit,vehicledetailsedit,
            currentactivityedit,presentaddressedit,permanentaddressedit,offencehnedit,dolaedit,
            psarrestededit,doredit,hostoryedit,lpcedit,reasonedit;
    AutoCompleteTextView psnameedit,moedit;
    List<String> psal,psidvalues;
    Button savebutton;
    SharedPreferences sharedpreferences;
    RequestQueue requestQueue;
    SimpleDateFormat mdformatpresent,mdformatpresent1;
    Calendar call;
    ProgressDialog progressDialog;
    DatePickerDialog dpDialog,dpDialog1,dpDialog2;
    String psidselected;
    ConnectionDetector cd;
    DatabaseHandler db;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mooffender_add_personal);
        cd=new ConnectionDetector(this);
        requestQueue = Volley.newRequestQueue(this);
        offendernameedit=(EditText)findViewById(R.id.offendernameedit);
        aliasesedit=(EditText)findViewById(R.id.aliasesedit);
        dobedit=(EditText)findViewById(R.id.dobedit);
        ageedit=(EditText)findViewById(R.id.ageedit);
        mobilenoedit=(EditText)findViewById(R.id.mobilenoedit);
        emailedit=(EditText)findViewById(R.id.emailedit);
        vehicledetailsedit=(EditText)findViewById(R.id.vehicledetailsedit);
        currentactivityedit=(EditText)findViewById(R.id.currentactivityedit);
        presentaddressedit=(EditText)findViewById(R.id.presentaddressedit);
        permanentaddressedit=(EditText)findViewById(R.id.permanentaddressedit);
        offencehnedit=(EditText)findViewById(R.id.offencehnedit);
        dolaedit=(EditText)findViewById(R.id.dolaedit);
        psarrestededit=(EditText)findViewById(R.id.psarrestededit);
        doredit=(EditText)findViewById(R.id.doredit);
        moedit=(AutoCompleteTextView) findViewById(R.id.moedit);
        hostoryedit=(EditText)findViewById(R.id.hostoryedit);
        lpcedit=(EditText)findViewById(R.id.lpcedit);
        reasonedit=(EditText)findViewById(R.id.reasonedit);
        psnameedit=(AutoCompleteTextView)findViewById(R.id.psnameedit);

if(cd.isConnectingToInternet()) {
    try {
        JSONObject jsoninner = new JSONObject();
        jsoninner.put("Zone", "");
        jsoninner.put("Division", "");
        jsoninner.put("PoliceStation", "");
        JSONObject jsonparent = new JSONObject();
        jsonparent.put("request", jsoninner);
//            Log.d("requestis",jsonparent.toString());
        //(Method.POST ,url, new JSONObject("request",jsoninner),

        String url1 = "http://220.225.38.123:8081//LogicShore.svc/GetAllPSdetails";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url1, jsonparent, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("psname", String.valueOf(response));

                try {
                    JSONArray pslist = response.getJSONArray("Pslist");
                    if (pslist.length() > 0) {
                        psal = new ArrayList<String>();
                        psidvalues = new ArrayList<String>();
                        for (int i = 0; i < pslist.length(); i++) {

                            JSONObject data = pslist.getJSONObject(i);
//                            String a = (String) data.get("PSname".toString());
//                            String a =pslist.get(Integer.parseInt("PScode")).toString();
                            String psname = data.getString("PSname");
                            String psid = data.getString("PSId");
                            psal.add(psname);
                            psidvalues.add(psid);


                        }
                        ArrayAdapter autopsname = new ArrayAdapter(mooffernder_add_personal.this, android.R.layout.simple_dropdown_item_1line, psal);
                        psnameedit.setAdapter(autopsname);
                        psnameedit.setThreshold(1);
                        Log.d("psal", String.valueOf(psal));

                    }
                } catch (JSONException e) {
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
    } catch (Exception e) {

    }
}
else{
    Toast.makeText(getApplicationContext(),"NO INTERNET CONNECTION",Toast.LENGTH_SHORT);
}
if(cd.isConnectingToInternet()) {
    String url = "http://220.225.38.123:8081//LogicShore.svc/GetOStateMOtypes";
    JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, url, null,
            new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    ArrayList<String> moarray = new ArrayList<String>();
                    try {
                        JSONArray motype = response.getJSONArray("MOlist");
                        for (int i = 0; i < motype.length(); i++) {
                            JSONObject motypelist = motype.getJSONObject(i);
                            String motypevalue = motypelist.getString("MOType");

                            moarray.add(motypevalue);
                            Log.d("moarray", String.valueOf(moarray));
                        }
                        ArrayAdapter autopsname1 = new ArrayAdapter(mooffernder_add_personal.this, android.R.layout.simple_dropdown_item_1line, moarray);
                        moedit.setAdapter(autopsname1);
                        moedit.setThreshold(1);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    // display response
                    Log.d("Response", response.toString());
                }
            },
            new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {


                    //Log.d("Error.Response", response);
                }
            }
    );
    requestQueue.add(getRequest);
}
else{
    Toast.makeText(getApplicationContext(),"NO INTERNET CONNECTION",Toast.LENGTH_SHORT);
}
        moedit.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                parent.getSelectedItem();
            }
        });

//
        savebutton=(Button)findViewById(R.id.savebutton);





        mdformatpresent = new SimpleDateFormat("dd-MM-yyyy");
        call = Calendar.getInstance();
        dpDialog=new DatePickerDialog(mooffernder_add_personal.this, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                // TODO Auto-generated method stub
                call = Calendar.getInstance();
                call.set(year, monthOfYear, dayOfMonth);
                doredit.setText(mdformatpresent.format(call.getTime()));
            }
        }, call.get(Calendar.YEAR), call.get(Calendar.MONTH), call.get(Calendar.DAY_OF_MONTH));
        doredit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dpDialog.show();
            }
        });


        mdformatpresent = new SimpleDateFormat("dd-MM-yyyy");
        call = Calendar.getInstance();
        dpDialog1=new DatePickerDialog(mooffernder_add_personal.this, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                call = Calendar.getInstance();
                call.set(year, monthOfYear, dayOfMonth);
                dobedit.setText(mdformatpresent.format(call.getTime()));
            }
        }, call.get(Calendar.YEAR), call.get(Calendar.MONTH), call.get(Calendar.DAY_OF_MONTH));
        dobedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dpDialog1.show();
            }
        });

        mdformatpresent = new SimpleDateFormat("dd-MM-yyyy");
        call = Calendar.getInstance();
        dpDialog2=new DatePickerDialog(mooffernder_add_personal.this, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                call = Calendar.getInstance();
                call.set(year, monthOfYear, dayOfMonth);
                dolaedit.setText(mdformatpresent.format(call.getTime()));
            }
        }, call.get(Calendar.YEAR), call.get(Calendar.MONTH), call.get(Calendar.DAY_OF_MONTH));
        dolaedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dpDialog2.show();
            }
        });


        savebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String getText=emailedit.getText().toString();
                String Expn =
                        "^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                                +"((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                                +"[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                                +"([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                                +"[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                                +"([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$";

                if(emailedit.getText().length()>0 && !getText.matches(Expn)) {
                    emailedit.setError("invalid email format");
                    emailedit.setFocusable(true);

                }
                else if(offendernameedit.getText().toString().length()==0){
                    offendernameedit.setError("This field is Mandatory");
                    offendernameedit.setFocusable(true);

                }
                else if(psnameedit.getText().toString().length()==0){
                    psnameedit.setError("This field is Mandatory");
                    psnameedit.setFocusable(true);

                }
                else if(presentaddressedit.getText().toString().length()==0 && permanentaddressedit.getText().toString().length()==0){
                        permanentaddressedit.setError("present or permanent address is Mandatory");
                        presentaddressedit.setError("present or permanent address is Mandatory");
                }
                else {
                    db = new DatabaseHandler(mooffernder_add_personal.this);

                            db.personalinsert(offendernameedit.getText().toString(), aliasesedit.getText().toString(),dobedit.getText().toString(),
                                    ageedit.getText().toString(),mobilenoedit.getText().toString(),emailedit.getText().toString(),
                                    psnameedit.getText().toString(),vehicledetailsedit.getText().toString(),currentactivityedit.getText().toString(),
                                    presentaddressedit.getText().toString(),permanentaddressedit.getText().toString(),offencehnedit.getText().toString(),
                                    dolaedit.getText().toString(),psarrestededit.getText().toString(),doredit.getText().toString(),moedit.getText().toString(),
                                    hostoryedit.getText().toString(),lpcedit.getText().toString(),reasonedit.getText().toString(),"associates");
                            Cursor c1 = db.getAllAsscoiaates();
                            while (c1.moveToNext()) {
                                Log.d("dude", c1.getString(0));
                            }

//
                            AssociatesAdaptor todoAdapter = new AssociatesAdaptor(mooffernder_add_personal.this, c1);

//                            listprinting.setAdapter(todoAdapter);
//                            clearText();
                            Intent i = new Intent(mooffernder_add_personal.this, Data.class);
                                        startActivity(i);

                        }

//                        private void clearText() {
//                            associatesnameedit.setText("");
//                            associatesaddressedit.setText("");
//                            associatesdescptedit.setText("");
//                            associatesmobileedit.setText("");
//                            associatesoccupationedit.setText("");
//                        }

                }

//                    if(cd.isConnectingToInternet()) {
//                        final ProgressDialog progressDialog = new ProgressDialog(mooffernder_add_personal.this);
//                        progressDialog.setMessage("Please wait data is Processing");
//                        progressDialog.show();
//
//                        try {
//                            JSONObject jsoninner = new JSONObject();
//                            jsoninner.put("OFFENDERNAME", offendernameedit.getText().toString());
//                            jsoninner.put("ALIASES", aliasesedit.getText().toString());
//                            jsoninner.put("DOB", dobedit.getText().toString());
//                            jsoninner.put("AGE", ageedit.getText().toString());
//                            jsoninner.put("MOBILENO", mobilenoedit.getText().toString());
//                            jsoninner.put("EMAIL", emailedit.getText().toString());
//                            jsoninner.put("PSID", psidselected);
//                            jsoninner.put("VEHCILEDETAILS", vehicledetailsedit.getText().toString());
//                            jsoninner.put("CURRENTACTIVITY", currentactivityedit.getText().toString());
//                            jsoninner.put("PRESENT_ADDRESS", presentaddressedit.getText().toString());
//                            jsoninner.put("PERMANENT_ADDRESS", permanentaddressedit.getText().toString());
//                            jsoninner.put("DATEOFLASTARREST", dolaedit.getText().toString());
//                            jsoninner.put("PSARRESTED", psarrestededit.getText().toString());
//                            jsoninner.put("DATEOFRELEASE", doredit.getText().toString());
//                            jsoninner.put("MODUSOPERENDI", moedit.getText().toString());
//                            jsoninner.put("HISTORYSHEET", hostoryedit.getText().toString());
//                            jsoninner.put("LATESTPHOTOGRAPHCOLLECTED", lpcedit.getText().toString());
//
//
//                            JSONObject jsonparent = new JSONObject();
//                            jsonparent.put("request", jsoninner);
////                            Log.d("submit",jsonparent.toString());
//                            //(Method.POST ,url, new JSONObject("request",jsoninner),
//                            Log.d("ravi", jsonparent.toString());
//                            String url = "http://crimeproneareaservice.logicshore.co.in//LogicShore.svc/AddOffendersDetails";
//                            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, jsonparent, new Response.Listener<JSONObject>() {
//                                @Override
//                                public void onResponse(JSONObject response) {
//                                    Log.d("data123", String.valueOf(response));
//                                    try {
//                                        String transId = response.getString("TransId");
//                                        Log.d("transid", transId);
//                                        SharedPreferences shared = getSharedPreferences("offender", 0);
//                                        SharedPreferences.Editor editor = shared.edit();
//                                        editor.clear();
//                                        editor.putInt("name", Integer.parseInt(transId));
//
//                                        editor.commit();
//
//                                        progressDialog.dismiss();
//                                        Intent i = new Intent(mooffernder_add_personal.this, Data.class);
//                                        startActivity(i);
//                                    } catch (JSONException e) {
//                                        e.printStackTrace();
//                                        progressDialog.dismiss();
//                                    }
//
//
//                                }
//                            },
//                                    new Response.ErrorListener() {
//                                        @Override
//                                        public void onErrorResponse(VolleyError error) {
//
//                                        }
//                                    });
//                            //add request to queue
//                            requestQueue.add(jsonObjectRequest);
//                        } catch (Exception e) {
//
//                        }
//
//                    }
//                    else {
//                        Toast.makeText(getApplicationContext(),"No internet connection",Toast.LENGTH_SHORT).show();
//                    }
//                }


        });


    }
}
