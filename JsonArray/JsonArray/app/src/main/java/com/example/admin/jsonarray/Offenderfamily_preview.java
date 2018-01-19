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

public class Offenderfamily_preview extends Fragment {
    TextView realtiontypetext,nametext,mobiletext,addresstext,occupationtext,anyprooftext;
    DatabaseHandler db;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View  view =inflater.inflate(R.layout.offenderfamilypreview,container,false);
        realtiontypetext=(TextView)view.findViewById(R.id.realtiontypetext);
        nametext=(TextView)view.findViewById(R.id.nametext);
        mobiletext=(TextView)view.findViewById(R.id.mobiletext);
        addresstext=(TextView)view.findViewById(R.id.addresstext);
        occupationtext=(TextView)view.findViewById(R.id.occupationtext);
        anyprooftext=(TextView)view.findViewById(R.id.anyprooftext);
        db=new DatabaseHandler(getActivity());
        Cursor c2 = db.getAllContacts();
        while (c2.moveToNext()){
            realtiontypetext.setText(c2.getString(0));
            nametext.setText(c2.getString(1));
            mobiletext.setText(c2.getString(2));
            addresstext.setText(c2.getString(3));
            occupationtext.setText(c2.getString(4));
            anyprooftext.setText(c2.getString(5));
        }

        return view;
    }
}
