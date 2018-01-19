package com.example.admin.jsonarray;

/**
 * Created by admin on 13-05-2017.
 */

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class OffenderDocumentAdaptor extends CursorAdapter {

    public OffenderDocumentAdaptor(Context context, Cursor cursor) {
        super(context,cursor,0);

    }
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.offenderdocumentitems, parent, false);
    }


    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        TextView prooftypetext = (TextView) view.findViewById(R.id.prooftypetext);
        TextView OffenceType=(TextView)view.findViewById(R.id.OffenceType);
        TextView proofvaluetext = (TextView) view.findViewById(R.id.proofvaluetext);
        TextView proofidtext = (TextView) view.findViewById(R.id.proofidtext);
        ImageView imag=(ImageView)view.findViewById(R.id.image);

        // Extract properties from cursor
        String prooftypetextstring = cursor.getString(1);
        OffenceType.setText(cursor.getString(1)+" No");
        String proofvaluetextstring = cursor.getString(2);
        String proofidtextstring = cursor.getString(3);

        if(cursor.getString(3).equals(""))
        {
            proofidtext.setVisibility(View.VISIBLE);
            imag.setVisibility(View.GONE);
            proofidtext.setText("N/A");
        }
        else
        {
            proofidtext.setVisibility(View.GONE);
            imag.setVisibility(View.VISIBLE);
            ImageLoader is = new ImageLoader(context);
            is.DisplayImage(cursor.getString(3), imag);
        }
        // Populate fields with extracted properties
        prooftypetext.setText(prooftypetextstring);
        proofvaluetext.setText(proofvaluetextstring);
        proofidtext.setText(proofidtextstring);


    }


}
