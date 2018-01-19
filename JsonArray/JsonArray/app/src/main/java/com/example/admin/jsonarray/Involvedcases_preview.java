package com.example.admin.jsonarray;

import android.support.v4.app.Fragment;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by admin on 22-05-2017.
 */

public class Involvedcases_preview extends Fragment {
    TextView involvedcasestext,crimenotext,policestationtext,dateofarresttext,statusofcrimetext;
    DatabaseHandler db;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
      View view=inflater.inflate(R.layout.involvedcasespreview,container,false);
        involvedcasestext=(TextView)view.findViewById(R.id.involvedcasestext);
        crimenotext=(TextView)view.findViewById(R.id.crimenotext);
        policestationtext=(TextView)view.findViewById(R.id.policestationtext);
        dateofarresttext=(TextView)view.findViewById(R.id.dateofarresttext);
        statusofcrimetext=(TextView)view.findViewById(R.id.statusofcrimetext);
        db= new DatabaseHandler(getActivity());
        Cursor c3 = db.getAllInvolved();
        while (c3.moveToNext()){
           // involvedcasestext.setText(c3.getColumnCount());
            crimenotext.setText(c3.getString(1));
            policestationtext.setText(c3.getString(2));
            dateofarresttext.setText(c3.getString(3));
            statusofcrimetext.setText(c3.getString(4));
        }
        return view;
    }
}
