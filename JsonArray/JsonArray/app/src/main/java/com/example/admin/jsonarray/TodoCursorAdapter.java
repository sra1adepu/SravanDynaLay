package com.example.admin.jsonarray;

/**
 * Created by admin on 11-05-2017.
 */

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class TodoCursorAdapter extends CursorAdapter {
    private LayoutInflater cursorInflater;
    public TodoCursorAdapter(Context context, Cursor cursor) {

      super(context,cursor,0);

    }


    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.item, parent, false);
    }


    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        // Find fields to populate in inflated template
        TextView realtiontypetext = (TextView) view.findViewById(R.id.realtiontypetext);
        TextView nametext = (TextView) view.findViewById(R.id.nametext);
        TextView mobiletext = (TextView) view.findViewById(R.id.mobiletext);
        TextView addresstext = (TextView) view.findViewById(R.id.addresstext);
        TextView occupationtext = (TextView) view.findViewById(R.id.occupationtext);
        TextView anyprooftext = (TextView) view.findViewById(R.id.anyprooftext);

        // Extract properties from cursor
        String relationstring = cursor.getString(1);
        String namestring = cursor.getString(2);
        String mobilestring = cursor.getString(3);
        String addressstring = cursor.getString(4);
        String occupationstring = cursor.getString(5);
        String anyproofstring = cursor.getString(6);
        // Populate fields with extracted properties
        realtiontypetext.setText(relationstring);
        nametext.setText(namestring);
        mobiletext.setText(mobilestring);
        addresstext.setText(addressstring);
        occupationtext.setText(occupationstring);
        anyprooftext.setText(anyproofstring);

    }


}