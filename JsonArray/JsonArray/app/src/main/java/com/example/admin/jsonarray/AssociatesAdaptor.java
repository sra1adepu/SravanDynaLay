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

public class AssociatesAdaptor extends CursorAdapter {

    public AssociatesAdaptor(Context context, Cursor cursor) {
        super(context,cursor,0);

    }


    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.associatesitem, parent, false);
    }


    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        // Find fields to populate in inflated template
        TextView nametext = (TextView) view.findViewById(R.id.nametext);
        TextView typetext = (TextView) view.findViewById(R.id.typetext);
        TextView descriptiontext = (TextView) view.findViewById(R.id.descriptiontext);
        TextView mobilenotext = (TextView) view.findViewById(R.id.mobilenotext);
        TextView occupationtext = (TextView) view.findViewById(R.id.occupationtext);
        TextView addresstext = (TextView) view.findViewById(R.id.addresstext);

        // Extract properties from cursor
        String nametextstring = cursor.getString(1);
        String typetextstring = cursor.getString(2);
        String descriptiontextstring = cursor.getString(3);
        String mobilenotextstring = cursor.getString(4);
        String occupationtextstring = cursor.getString(5);
        String addresstextstring = cursor.getString(6);
        // Populate fields with extracted properties
        nametext.setText(nametextstring);
        typetext.setText(typetextstring);
        descriptiontext.setText(descriptiontextstring);
        mobilenotext.setText(mobilenotextstring);
        occupationtext.setText(occupationtextstring);
        addresstext.setText(addresstextstring);

    }


}