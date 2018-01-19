package com.example.admin.jsonarray;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by admin on 06-07-2017.
 */

public class Country extends Activity{
    Spinner gdplist,inrlist;
    CustomList gridviewAdapter;
    RequestQueue requestQueue;
EditText gbpedit,inredit;
    String from,to,spname;
    double d=0;

    ArrayList<String> topspinnerarray=new ArrayList<>();
    ArrayList<String> bottomspinner=new ArrayList<>();

    boolean oneFLG = false, twoFLG= false ;



    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.countries);
        gbpedit=(EditText)findViewById(R.id.gbpedit);
        inredit=(EditText)findViewById(R.id.inredit);
        gdplist=(Spinner)findViewById(R.id.gdplist);
        inrlist=(Spinner) findViewById(R.id.inrlist);
        requestQueue= Volley.newRequestQueue(Country.this);
        topspinnerarray.add("United States");

        topspinnerarray.add("India");
        topspinnerarray.add("China");
            gbpedit.setText("");
        inredit.setText("");
        oneFLG=true;
        gbpedit.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                oneFLG=true;
                twoFLG=false;
                return false;
            }
        });

        inredit.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                twoFLG=true;
                oneFLG=false;
                return false;
            }
        });

        gbpedit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            d=0.0;
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                Log.i("data", "" + s);
                if (oneFLG) {
                    if (!s.toString().equals("")) {
                        double data1 = cal(from, to, s.toString());
                        inredit.setText(data1 + "");
                    } else {
                        inredit.setText("");
                    }
                }
            }
        });

        inredit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            d=0.0;
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                Log.d("data", s.toString());
                if (twoFLG) {
                    if (!s.toString().equals("")) {
                        double data1 = cal1(to, from, s.toString());
                        gbpedit.setText(data1 + "");
                    } else {
                        gbpedit.setText("");
                    }
                }
            }
        });

        final ArrayList<Country_images> data = new ArrayList<Country_images>();
        String url ="http://www.androidbegin.com/tutorial/jsonparsetutorial.txt";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObj = new JSONObject(response);
                            JSONArray jsonArray = jsonObj.getJSONArray("worldpopulation");

                            for (int i = 0; i < jsonArray.length(); i++) {       
                                for (int j=0;j<topspinnerarray.size();j++) {
                                    Country_images item = new Country_images();
                                    JSONObject c = jsonArray.getJSONObject(i);
                                    if(c.getString("country").equals(topspinnerarray.get(j).toString())) {
                                        String icon = c.getString("flag");

                                        Log.i("Icon ::", "==>" + icon);
                                        item.setImagid(icon);
                                        item.setRank(c.getInt("rank"));
                                        item.setCountry(c.getString("country"));
                                        item.setPopulation(c.getString("population"));
                                        data.add(item);
                                    break;
                                    }
                                    }
                            }
                            gridviewAdapter = new CustomList(Country.this,data);
                            gdplist.setAdapter(gridviewAdapter);
                            gdplist.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    Country_images c=data.get(position);
                                    Toast.makeText(getApplicationContext(),c.getCountry()+"\n"+c.getRank()+"\n"+c.getPopulation(),Toast.LENGTH_SHORT).show();

                                    from=c.getCountry();
                                    spname="sp1";

                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {

                                }
                            });

                            inrlist.setAdapter(gridviewAdapter);
                            inrlist.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    Country_images c=data.get(position);
                                    Toast.makeText(getApplicationContext(),c.getCountry()+"\n"+c.getRank()+"\n"+c.getPopulation(),Toast.LENGTH_SHORT).show();
                                    to=c.getCountry();
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {

                                }
                            });

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(stringRequest);
    }



     private double cal(String from,String to,String s)
    {

        double i=Double.parseDouble(s);
        switch(from)
        {
            case "United  ":

                if(to.equals("India"))
                {
                    d=i*64;
                    break;
                }
                else if(to.equals("China"))
                {
                    d=i*6;
                    break;
                }
                else
                {
                    d=i;
                    break;
                }



            case "India":


                if(to.equals("United States"))
                {
                    d=i*0.015;
                    break;
                }
                else if(to.equals("China"))
                {
                    d=i*0.11;
                    break;
                }
                else
                {
                    d=i;
                    break;
                }


            case "China":

                if(to.equals("United States"))
                {
                    d=i*0.15;
                    break;
                }
                else if(to.equals("India"))
                {
                    d=i*9.51;
                    break;
                }
                else
                {
                    d=i;
                    break;
                }


                default:

                    break;
        }

        return d;
    }



    private double cal1(String from,String to,String s)
    {

        double i=Double.parseDouble(s);
        switch(from)
        {
            case "United States":
                if(to.equals("India"))
                {
                    d=i*64;
                    break;
                }
                else if(to.equals("China"))
                {
                    d=i*6;
                    break;
                }
                else
                {
                    d=i;
                    break;
                }



            case "India":


                if(to.equals("United States"))
                {
                    d=i*0.015;
                    break;
                }
                else if(to.equals("China"))
                {
                    d=i*0.11;
                    break;
                }
                else
                {
                    d=i;
                    break;
                }



            case "China":

                if(to.equals("United States"))
                {
                    d=i*0.15;
                    break;
                }
                else if(to.equals("India"))
                {
                    d=i*9.51;
                    break;
                }
                else
                {
                    d=i;
                    break;
                }


            default:

                break;
        }

        return d;
    }

}
