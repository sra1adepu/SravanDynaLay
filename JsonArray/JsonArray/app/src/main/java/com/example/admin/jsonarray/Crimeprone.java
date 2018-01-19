package com.example.admin.jsonarray;

import android.annotation.TargetApi;
import android.app.Activity;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TableRow;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by admin on 26-04-2017.
 */

public class Crimeprone extends Activity {

    private int mYear, mMonth, mDay;
    EditText fromdate,todateedit;
    String[] Accidenttype = {"All", "Fatal Injuries", "Grivous Injuries", "No Injuries", "Simple Injuries"};
    String[] classif = {"2", "3", "4", "5"};
    String[] percen = {"10", "20", "30", "40", "50", "60", "70", "80", "90", "100"};

    String[] crimetype = {"All", "Gender Offence", "Murder", "Property Offence", "SC/ST Atrocities"};
    String[] genderoffence = {"All", "Rape", "Dowry Harrasment", "Cruelty to Women", "Outranging Modesty", "Insulting Modesty", "Dowry Death", "Offence Realated to Marriage"};
    String[] murder = {"All", "Others", "Communel", "Sexual Jealously", "Faction", "Political", "Terroist", "Property Disputes"};
    String[] propertyoffence = {"All", "Dacoity", "Murder for Gain", "Diverting Attention", "HB-Day", "Ordinary Theft", "Snatching", "Pseudo Police", "Automobile Theft", "HB-Night", "Robbery"};
    String[] atrocities = {"All", "Verbal Abuse", "Physical Assault"};

    private RadioGroup rg1;
    Spinner edit, spinner1, percentage;
    TableRow tableRow1, tableRow2;
    ImageButton hide;
    LinearLayout linearLayout1, linearLayout2;
    Button search;
    //int i=0;
    boolean value = true;
    SimpleDateFormat simpleDateFormat;


    @TargetApi(Build.VERSION_CODES.N)
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.header);

        fromdate=(EditText)findViewById(R.id.fromdateedit);
        todateedit=(EditText)findViewById(R.id.todateedit);
        rg1 = (RadioGroup) findViewById(R.id.typeradio);


        tableRow1 = (TableRow) findViewById(R.id.tablerow3);
        tableRow2 = (TableRow) findViewById(R.id.tablerow4);
        edit = (Spinner) findViewById(R.id.Accedittypeedit);
        //actv(false);
        spinner1 = (Spinner) findViewById(R.id.classificationedit);
        percentage = (Spinner) findViewById(R.id.classificationper);
        hide = (ImageButton) findViewById(R.id.imageView1);
        linearLayout1 = (LinearLayout) findViewById(R.id.thematictotal);
        search = (Button) findViewById(R.id.button2);
        // linearLayout2=(LinearLayout)findViewById(R.id.linearsearchbutton);


        ArrayAdapter spinnerArrayAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, crimetype);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        edit.setAdapter(spinnerArrayAdapter);
        edit.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> parentView,
                                       View selectedItemView, int position, long id) {
                // Object item = parentView.getItemAtPosition(position);

                if (position == 1) {
                    ArrayAdapter spin = new ArrayAdapter(getApplication(), android.R.layout.simple_spinner_item, genderoffence);
                    spin.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                    spinner1.setAdapter(spin);
                }
                if (position == 2) {
                    ArrayAdapter spin = new ArrayAdapter(getApplication(), android.R.layout.simple_spinner_item, murder);
                    spin.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                    spinner1.setAdapter(spin);
                }
                if (position == 3) {
                    ArrayAdapter spin = new ArrayAdapter(getApplication(), android.R.layout.simple_spinner_item, propertyoffence);
                    spin.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                    spinner1.setAdapter(spin);
                }
                if (position == 4) {
                    ArrayAdapter spin = new ArrayAdapter(getApplication(), android.R.layout.simple_spinner_item, atrocities);
                    spin.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                    spinner1.setAdapter(spin);
                }


            }

            public void onNothingSelected(AdapterView<?> arg0) {// do nothing
            }

        });
//        fromdate.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // Process to get Current Date
//                final Calendar c = Calendar.getInstance();
//                mYear = c.get(Calendar.YEAR);
//                mMonth = c.get(Calendar.MONTH);
//                mDay = c.get(Calendar.DAY_OF_MONTH);
//
//                // Launch Date Picker Dialog
//                DatePickerDialog dpd = new DatePickerDialog(Crimeprone.this,
//                        new DatePickerDialog.OnDateSetListener() {
//
//                            @Override
//                            public void onDateSet(DatePicker view, int year,
//                                                  int monthOfYear, int dayOfMonth) {
//                                // Display Selected date in textbox
//                                fromdate.setText(dayOfMonth + "-"
//                                        + (monthOfYear + 1) + "-" + year);
//
//                            }
//                        }, mYear, mMonth, mDay);
//                dpd.show();
//            }
//        });

    //    DatePickerDialog date_dailog = new DatePickerDialog(this, datepickerlistenr, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
        //Date date=cal.getTime();
      //  date_dailog.setTitle("Select Date");
        SimpleDateFormat sdf=new SimpleDateFormat("dd-MM-yyyy");
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE,-30);
        Date previousdate =cal.getTime();

        String strDatepri= sdf.format(previousdate);
        Log.d("sravan",strDatepri);
        Calendar currentdate=Calendar.getInstance();
       Date presentdate= currentdate.getTime();

        String strDatepresent= sdf.format(presentdate);
        Log.d("sravan1",strDatepresent);


        // date_dailog.getDatePicker().setMinDate(cal.getTimeInMillis());
        //date_dailog.getDatePicker().setMaxDate(System.currentTimeMillis());
//        private OnDateSetListener  datepickerlistenr=new OnDateSetListener() {
//        DatePickerDialog dpd = new DatePickerDialog(Crimeprone.this,
//                        new DatePickerDialog.OnDateSetListener() {
//
//                            @Override
//                            public void onDateSet(DatePicker view, int year, int monthOfYear,
//                                                  int dayOfMonth) {
//                                // TODO Auto-generated method stub
//                                cal = Calendar.getInstance();
//
//                                cal.set(year, monthOfYear, dayOfMonth);
//
//                                daily_dt_et.setText(spf.format(cal.getTime()));
//
//                                selectdate = spf1.format(cal.getTime());
//                                displaydate = spf2.format(cal.getTime());
//
//                            }
//
//        };
//        todateedit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // Process to get Current Date
//                final Calendar c = Calendar.getInstance();
//                mYear = c.get(Calendar.YEAR);
//                mMonth = c.get(Calendar.MONTH);
//                mDay = c.get(Calendar.DAY_OF_MONTH);
//
//                // Launch Date Picker Dialog
//                DatePickerDialog dpd = new DatePickerDialog(Crimeprone.this,
//                        new DatePickerDialog.OnDateSetListener() {
//
//                            @Override
//                            public void onDateSet(DatePicker view, int year,
//                                                  int monthOfYear, int dayOfMonth) {
//                                // Display Selected date in textbox
//                                todateedit.setText(dayOfMonth + "-"
//                                        + (monthOfYear + 1) + "-" + year);
////                                todateedit.setText(simpleDateFormat.format(c));
//
//                            }
//                        }, mYear, mMonth, mDay);
//                dpd.show();
//            }
//        });
    }


}


