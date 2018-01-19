package com.example.admin.jsonarray;

import android.support.v4.app.Fragment;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by admin on 19-05-2017.
 */

public class OffernderDocuments_preview extends Fragment {
    TextView offenderdocuments,prooftypepreviewtext,proofvaluepreviewtext;
    DatabaseHandler db;
    ImageView image;
    Context context = null;
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.offenderdocumentpreview, container, false);
        offenderdocuments=(TextView)view.findViewById(R.id.offenderdocuments);
        prooftypepreviewtext=(TextView)view.findViewById(R.id.prooftypepreviewtext);
        proofvaluepreviewtext=(TextView)view.findViewById(R.id.proofvaluepreviewtext);
        image=(ImageView)view.findViewById(R.id.image);
        db= new DatabaseHandler(getActivity());
        Cursor c1 = db.getAllDocuments();
        while (c1.moveToNext()){
           // offenderdocuments.setText(c1.getColumnCount());
            prooftypepreviewtext.setText(c1.getString(1));
            proofvaluepreviewtext.setText(c1.getString(2));
            ImageLoader is = new ImageLoader(context);
            is.DisplayImage(c1.getString(3), image);

        }

        return view;
    }
}
