package com.example.admin.jsonarray;

import android.support.v4.app.Fragment;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * Created by admin on 22-05-2017.
 */

public class Photo_preview extends Fragment {
    ImageView idimage;
    DatabaseHandler db;
    Context context = null;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.photopreview,container,false);
        idimage=(ImageView)view.findViewById(R.id.idimage);
        db=new DatabaseHandler(getActivity());
        Cursor c5 = db.getphotoinsert();
        while (c5.moveToNext()) {
            ImageLoader is = new ImageLoader(context);
            is.DisplayImage(c5.getString(0), idimage);
        }
        return view;
    }
}
