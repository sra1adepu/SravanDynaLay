package com.example.admin.jsonarray;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by admin on 01-05-2017.
 */

public class RecyclerAdaptor extends RecyclerView.Adapter<RecyclerAdaptor.RecyclerViewHolder> {
    String[]countries_list;String[]capitals_list;

    public RecyclerAdaptor(String[] countries_list,String[]capitals_list) {
        this.capitals_list=capitals_list;
        this.countries_list=countries_list;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item1,parent,false);

        RecyclerViewHolder recyclerViewHolder = new RecyclerViewHolder(view);
        return recyclerViewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        holder.countries.setText(countries_list[position]);
        holder.capitals.setText(capitals_list[position]);


    }

    @Override
    public int getItemCount() {
        return countries_list.length;
    }

    public static class  RecyclerViewHolder extends RecyclerView.ViewHolder{
        TextView countries,capitals;
        public RecyclerViewHolder(View view){
            super(view);
            countries=(TextView)view.findViewById(R.id.txt_countries);
            capitals=(TextView)view.findViewById(R.id.txt_capitals);

        }
    }

}
