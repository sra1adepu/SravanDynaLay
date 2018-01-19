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

public class Petition_Adaptor extends BaseAdapter {
    Context context;
    ArrayList<Petition_Content> petitionList;
    private List<Petition_Content> CriminalDetailslist = null;
    LayoutInflater inflater;

    public Petition_Adaptor(ArrayList<Petition_Content> petitionList2 , Context petition){
        context = petition;
        this.CriminalDetailslist=petitionList2;
        inflater = LayoutInflater.from(context);
        this.petitionList = new ArrayList<Petition_Content>();
        this.petitionList.addAll(petitionList2);
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

            view = inflater.inflate(R.layout.petition_view, null);
            nHolder=new ViewHolder(view);
            view.setTag(nHolder);

        }
        else
        {
            nHolder =(ViewHolder) view.getTag();
        }

        nHolder.petitionno.setText(CriminalDetailslist.get(position).getPettno());
        nHolder.petitiontype.setText(CriminalDetailslist.get(position).getPettype());
        nHolder.petitionername.setText(CriminalDetailslist.get(position).getPetnername());
        nHolder.petitiondate.setText(CriminalDetailslist.get(position).getPetdate());
        nHolder.petitionstatus.setText(CriminalDetailslist.get(position).getPetstatus());
        nHolder.policestation.setText(CriminalDetailslist.get(position).getPolicesta());
        nHolder.petitionresult.setText(CriminalDetailslist.get(position).getResult());
        nHolder.eoname.setText(CriminalDetailslist.get(position).getEoname());
        nHolder.imageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context,Petition_full_list.class);
                i.putExtra("petitiono",CriminalDetailslist.get(position).getPettno());
                Log.d("petitionnumber",CriminalDetailslist.get(position).getPettno());
                i.putExtra("petitiontype",CriminalDetailslist.get(position).getPettype());
                i.putExtra("petitionername",CriminalDetailslist.get(position).getPetnername());
                i.putExtra("petitiondate",CriminalDetailslist.get(position).getPetdate());
                i.putExtra("petitionstatus",CriminalDetailslist.get(position).getPetstatus());
                i.putExtra("policestation",CriminalDetailslist.get(position).getPolicesta());
                i.putExtra("totalresult", CriminalDetailslist.get(position).getResult());
                i.putExtra("eoname",CriminalDetailslist.get(position).getEoname());
                Log.d("totalresult",CriminalDetailslist.get(position).getResult());
                context.startActivity(i);
            }
        });


        return view;
    }
    class ViewHolder
    {
        TextView petitionno,petitiontype,petitionername,petitiondate,petitionstatus,policestation,petitionresult,eoname;
        ImageView imageView2;

        public ViewHolder(View view) {
            petitionno=(TextView)view.findViewById(R.id.petitionno);
            petitiontype=(TextView)view.findViewById(R.id.petitiontype);
            petitionername=(TextView)view.findViewById(R.id.petitionername);
            petitiondate=(TextView)view.findViewById(R.id.petitiondate);
            petitionstatus=(TextView)view.findViewById(R.id.petitionstatus);
            policestation=(TextView)view.findViewById(R.id.policestation);
            petitionresult=(TextView)view.findViewById(R.id.petitionresult);
            eoname=(TextView)view.findViewById(R.id.eoname);
            imageView2=(ImageView)view.findViewById(R.id.imageView2);

        }

    }
}
