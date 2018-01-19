package com.example.admin.jsonarray;


import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by admin on 08-06-2017.
 */

public class Accused_Adaptor extends BaseAdapter {
    Context context;
    ArrayList<Accused_content> accused_List;
    private List<Accused_content> CriminalDetailslist = null;
    LayoutInflater inflater;

    public Accused_Adaptor(ArrayList<Accused_content> accused_list1 , Context petition){
        context = petition;
        this.CriminalDetailslist=accused_list1;
        inflater = LayoutInflater.from(context);
        this.accused_List = new ArrayList<Accused_content>();
        this.accused_List.addAll(accused_List);
    }

    public int getCount() {
        // TODO Auto-generated method stub
        return CriminalDetailslist.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return CriminalDetailslist.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        View view=convertView;
        final ViewHolder nHolder;
        if (view == null)
        {

            view = inflater.inflate(R.layout.accused_view, null);
            nHolder=new ViewHolder(view);
            view.setTag(nHolder);

        }
        else
        {
            nHolder =(ViewHolder) view.getTag();
        }

        nHolder.uname.setText(CriminalDetailslist.get(position).getUname());
        nHolder.aliasname.setText(CriminalDetailslist.get(position).getAliasname());
        nHolder.fathername.setText(CriminalDetailslist.get(position).getFathername());
        nHolder.age.setText(CriminalDetailslist.get(position).getAge());
        nHolder.crimenumber.setText(CriminalDetailslist.get(position).getCrimenumber());
        nHolder.crimedate.setText(CriminalDetailslist.get(position).getCrimedate());
        nHolder.mo.setText(CriminalDetailslist.get(position).getMo());
        nHolder.policestation.setText(CriminalDetailslist.get(position).getPsname());
        nHolder.location.setText(CriminalDetailslist.get(position).getLocation());


        nHolder.imageView2.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                //Toast.makeText(context,"dfdsf",Toast.LENGTH_SHORT).show();
                Intent i = new Intent(context,Accused_full_list.class);
                i.putExtra("name",CriminalDetailslist.get(position).getUname());
                Log.d("raviname",CriminalDetailslist.get(position).getUname());
//                i.putExtra("petitiontype",CriminalDetailslist.get(position).getPettype());
//                i.putExtra("petitionername",CriminalDetailslist.get(position).getPetnername());
//                i.putExtra("petitiondate",CriminalDetailslist.get(position).getPetdate());
//                i.putExtra("petitionstatus",CriminalDetailslist.get(position).getPetstatus());
//                i.putExtra("policestation",CriminalDetailslist.get(position).getPolicesta());
//                i.putExtra("totalresult", CriminalDetailslist.get(position).getResult());
//                Log.d("totalresult",CriminalDetailslist.get(position).getResult());
                context.startActivity(i);


            }
        });

//
      return view;
    }
    class ViewHolder
    {
        TextView uname,aliasname,fathername,age,crimenumber,crimedate,mo,policestation,location;
        ImageView imageView2;

        public ViewHolder(View view) {
            uname=(TextView)view.findViewById(R.id.uname);
            aliasname=(TextView)view.findViewById(R.id.aliasname);
            fathername=(TextView)view.findViewById(R.id.fathername);
            age=(TextView)view.findViewById(R.id.age);
            crimenumber=(TextView)view.findViewById(R.id.crimenumber);
            crimedate=(TextView)view.findViewById(R.id.crimedate);
            mo=(TextView)view.findViewById(R.id.mo);
            policestation=(TextView)view.findViewById(R.id.policestation);
            location=(TextView)view.findViewById(R.id.location);
            imageView2=(ImageView)view.findViewById(R.id.imageView2);


        }

    }
}
