package com.example.admin.jsonarray;

/**
 * Created by admin on 12-05-2017.
 */

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class InvolvedAdaptor extends CursorAdapter {

    public InvolvedAdaptor(Context context, Cursor cursor) {
        super(context,cursor,0);

    }


    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.involveditems, parent, false);
    }


    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        // Find fields to populate in inflated template
        TextView crimenotext = (TextView) view.findViewById(R.id.crimenotext);
        TextView policestationtext = (TextView) view.findViewById(R.id.policestationtext);
        TextView dateofarresttext = (TextView) view.findViewById(R.id.dateofarresttext);
        TextView statusofcrimetext = (TextView) view.findViewById(R.id.statusofcrimetext);


        // Extract properties from cursor
        String crimetextstring = cursor.getString(1);
        String policestationtextstring = cursor.getString(2);
        String dateofarresttextstring = cursor.getString(3);
        String statussofcrimetextstring = cursor.getString(4);

        // Populate fields with extracted properties
        crimenotext.setText(crimetextstring);
        policestationtext.setText(policestationtextstring);
        dateofarresttext.setText(dateofarresttextstring);
        statusofcrimetext.setText(statussofcrimetextstring);


    }


}