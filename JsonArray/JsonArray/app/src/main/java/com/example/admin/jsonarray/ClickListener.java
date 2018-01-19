package com.example.admin.jsonarray;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TableRow;

/**
 * Created by admin on 26-04-2017.
 */

public class ClickListener extends Activity implements RadioGroup.OnCheckedChangeListener
{


    String[] Accidenttype = {"All","Fatal Injuries", "Grivous Injuries", "No Injuries", "Simple Injuries"};
    String[] classif={"2", "3", "4", "5"};
    String[] percen={"10","20","30","40","50","60","70","80","90","100"};
    String  location ="tataji bhought (50) apples (23.565326 76.983298) rupees";

    String[] crimetype={"All","Gender Offence","Murder","Property Offence","SC/ST Atrocities"};
    String[] genderoffence={"All","Rape","Dowry Harrasment","Cruelty to Women","Outranging Modesty","Insulting Modesty","Dowry Death","Offence Realated to Marriage"};
    String[] murder={"All","Others","Communel","Sexual Jealously","Faction","Political","Terroist","Property Disputes"};
    String[] propertyoffence={"All","Dacoity","Murder for Gain","Diverting Attention","HB-Day","Ordinary Theft","Snatching","Pseudo Police","Automobile Theft","HB-Night","Robbery"};
   String[] atrocities ={"All","Verbal Abuse","Physical Assault"};

    private RadioGroup rg1;
  Spinner edit,spinner1,percentage;
    TableRow tableRow1,tableRow2;
    ImageButton hide;
    LinearLayout linearLayout1,linearLayout2;
    Button search;
    //int i=0;
  boolean value =true;


    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.header);

        rg1 = (RadioGroup) findViewById(R.id.typeradio);
        rg1.setOnCheckedChangeListener(this);



        tableRow1=(TableRow)findViewById(R.id.tablerow3);
        tableRow2=(TableRow)findViewById(R.id.tablerow4);
        edit = (Spinner)findViewById(R.id.Accedittypeedit);
        //actv(false);
        spinner1=(Spinner)findViewById(R.id.classificationedit);
        percentage=(Spinner)findViewById(R.id.classificationper);
        hide=(ImageButton) findViewById(R.id.imageView1);
        linearLayout1=(LinearLayout) findViewById(R.id.thematictotal);
        search=(Button)findViewById(R.id.button2);
        // linearLayout2=(LinearLayout)findViewById(R.id.linearsearchbutton);


        ArrayAdapter spinnerArrayAdapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item,Accidenttype);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        edit.setAdapter(spinnerArrayAdapter);

        ArrayAdapter spin = new ArrayAdapter(this,android.R.layout.simple_spinner_item,classif);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner1.setAdapter(spin);


        ArrayAdapter spin1 = new ArrayAdapter(this,android.R.layout.simple_spinner_item,percen);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        percentage.setAdapter(spin1);


        hide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                String[] words=location.split("\\s");
//                Log.d("arraydatagetvalue0",words[0]+"");
//                Log.d("arraydatagetvalue1",words[1]+"");
//                Log.d("arraydatagetvalue2",words[2]+"");
//                Log.d("arraydatagetvalue3",words[3]+"");
//                Log.d("arraydatagetvalue4",words[4]+"");
//                Log.d("arraydatagetvalue5",words[5]+"");
//
//                String jj = words[2];
//                String jj1 = words[4];
//                String jj2 = words[5];
//
//                String parts1 = jj.replaceFirst(".*?(\\d+)", "$1");
//               String parts11= parts1.replace(")","");
//                Log.d("sravan",parts11);
//                String parts2 = jj1.replaceFirst(".*?(\\d+)", "$1");
//                Log.d("sravan1",parts2+"");
//                String parts3 = jj2.replaceFirst(".*?(\\d+)", "$1");
//                String part33=parts3.replace(")","");
//                Log.d("sravan2",part33+"");

                String firstNumber = location.replaceFirst(".*?(\\d+).*", "$1");
                Log.d("sravan",firstNumber);
                if(value)
                {
                    linearLayout1.setVisibility(View.GONE);
                    search.setVisibility(View.INVISIBLE);
                    value=false;
                    hide.setBackgroundResource(R.drawable.arrow40);
                }
                else {
                    linearLayout1.setVisibility(View.VISIBLE);
                    search.setVisibility(View.VISIBLE);
                    value = true;
                    hide.setBackgroundResource(R.drawable.hide);

                }
            }
        });

    }

    public void onCheckedChanged(RadioGroup group, int checkedId)
    {

        if(checkedId==R.id.classificationradio)
        {
            tableRow1.setVisibility(View.VISIBLE);
            tableRow2.setVisibility(View.GONE);
        }
        else if(checkedId==R.id.percentage)
        {
            tableRow2.setVisibility(View.VISIBLE);
            tableRow1.setVisibility(View.GONE);
        }
    }

    }


