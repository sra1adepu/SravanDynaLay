package com.example.admin.jsonarray;

import android.app.Fragment;
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

public class Associates_preview extends Fragment {
TextView nametext,typetext,descriptiontext,mobilenotext,occupationtext,addresstext;
    DatabaseHandler db;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.associatespreview,container,false);
        nametext=(TextView)view.findViewById(R.id.nametext);
        typetext=(TextView)view.findViewById(R.id.typetext);
        descriptiontext=(TextView)view.findViewById(R.id.descriptiontext);
        mobilenotext=(TextView)view.findViewById(R.id.mobilenotext);
        occupationtext=(TextView)view.findViewById(R.id.occupationtext);
        addresstext=(TextView)view.findViewById(R.id.addresstext);
        db= new DatabaseHandler(getActivity());
        Cursor c4=db.getAllAsscoiaates();
        nametext.setText(c4.getString(0));
        typetext.setText(c4.getString(1));
        descriptiontext.setText(c4.getString(2));
        mobilenotext.setText(c4.getString(3));
        occupationtext.setText(c4.getString(4));
        addresstext.setText(c4.getString(5));
        return view;
    }
}
