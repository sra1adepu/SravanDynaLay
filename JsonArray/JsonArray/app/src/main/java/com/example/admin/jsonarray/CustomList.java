package com.example.admin.jsonarray;

/**
 * Created by admin on 06-07-2017.
 */


import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

/**
 * Created by user on 06-07-2017.
 */

public class CustomList extends BaseAdapter {
    Context context;
    ArrayList<Country_images> data = new ArrayList<Country_images>();
    LayoutInflater inflter;

    public CustomList(Context applicationContext, ArrayList<Country_images> data) {
        this.context = applicationContext;
        this.data=data;
        inflter = (LayoutInflater.from(applicationContext));
    }


    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        view = inflter.inflate(R.layout.list_single, null);
        ImageView icon = (ImageView) view.findViewById(R.id.img);
       TextView txt=(TextView)view.findViewById(R.id.txt);
        txt.setText(data.get(i).getCountry());
        Log.d("Ravi",data.get(i).getImagid());
        Glide.with(context)
                .load(data.get(i).getImagid())
                .into(icon);

//        Country_images item = getItem(position);
        return view;
    }
}


        //extends ArrayAdapter<Country_images> {
//    Context context ;
//    int resourceId;
//    ArrayList<Country_images> data = new ArrayList<Country_images>();
//
//    public CustomList(Context context, int layoutResourceId, ArrayList<Country_images> data) {
//        super(context, layoutResourceId, data);
//        this.context = context;
//        this.resourceId = layoutResourceId;
//        this.data = data;
//    }
//
//
//    static class ViewHolder {
//        ImageView imageView;
//    }
//
//    public View getDropDownView(int position, View convertView, ViewGroup parent) {
//        ViewHolder holder = null;
//        Country_images rowItem = getItem(position);
//
//        LayoutInflater mInflater = (LayoutInflater) context
//                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
//        if (convertView == null) {
//            convertView = mInflater.inflate(R.layout.list_single, null);
//            holder = new ViewHolder();
//
//            holder.imageView = (ImageView) convertView.findViewById(R.id.img);
//            convertView.setTag(holder);
//        } else {
//            holder = (ViewHolder) convertView.getTag();
//        }
//
//        Glide.with(context)
//                .load(getItem(position).getImagid())
//                .into(holder.imageView);
//
//        Country_images item = getItem(position);
//
//        return convertView;
//    }
//
//}
