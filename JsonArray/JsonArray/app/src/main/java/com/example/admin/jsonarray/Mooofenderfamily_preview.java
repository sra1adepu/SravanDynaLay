package com.example.admin.jsonarray;

import  android.support.v4.app.Fragment;

import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by admin on 19-05-2017.
 */

public class Mooofenderfamily_preview extends Fragment {
    TextView offendernametext,aliasestext,dobtext,agetext,mobilenotext,emailtext,psnametext,vehicledetailstext,
            currentactivitytext,presentaddressedit,permanentaddresstext,offencehnedit,dolaedit,psarrestededit,
            doredit,motext,hostorytext,lpctext;
    DatabaseHandler db;
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.mo_offender_detailspreview, container, false);

        offendernametext =(TextView)view.findViewById(R.id.offendernametext);
        aliasestext=(TextView)view.findViewById(R.id.aliasestext);
        dobtext=(TextView)view.findViewById(R.id.dobtext);
        agetext=(TextView)view.findViewById(R.id.agetext);
        mobilenotext=(TextView)view.findViewById(R.id.mobilenotext);
        emailtext=(TextView)view.findViewById(R.id.emailtext);
        psnametext=(TextView)view.findViewById(R.id.psnametext);
        vehicledetailstext=(TextView)view.findViewById(R.id.vehicledetailstext);
        currentactivitytext=(TextView)view.findViewById(R.id.currentactivitytext);
        presentaddressedit=(TextView)view.findViewById(R.id.presentaddressedit);
        permanentaddresstext=(TextView)view.findViewById(R.id.permanentaddresstext);
        offencehnedit=(TextView)view.findViewById(R.id.offencehnedit);
        dolaedit=(TextView)view.findViewById(R.id.dolaedit);
        psarrestededit=(TextView)view.findViewById(R.id.psarrestededit);
        doredit=(TextView)view.findViewById(R.id.doredit);
        motext=(TextView)view.findViewById(R.id.motext);
        hostorytext=(TextView)view.findViewById(R.id.hostorytext);
        lpctext=(TextView)view.findViewById(R.id.lpctext);
        db = new DatabaseHandler(getActivity());
        Cursor c1 = db.getAllpersonalinformation();
        while (c1.moveToNext()){
            offendernametext.setText((c1.getString(0)));
            aliasestext.setText((c1.getString(1)));
            dobtext.setText((c1.getString(2)));
            agetext.setText((c1.getString(3)));
            mobilenotext.setText((c1.getString(4)));
            emailtext.setText((c1.getString(5)));
            psnametext.setText((c1.getString(6)));
            vehicledetailstext.setText((c1.getString(7)));
            currentactivitytext.setText((c1.getString(8)));
            presentaddressedit.setText((c1.getString(9)));
            permanentaddresstext.setText((c1.getString(10)));
            offencehnedit.setText((c1.getString(11)));
            dolaedit.setText((c1.getString(12)));
            psarrestededit.setText((c1.getString(13)));
            doredit.setText((c1.getString(14)));
            motext.setText((c1.getString(15)));
            hostorytext.setText((c1.getString(16)));
            lpctext.setText((c1.getString(17)));
        }
        return view;

    }


}
