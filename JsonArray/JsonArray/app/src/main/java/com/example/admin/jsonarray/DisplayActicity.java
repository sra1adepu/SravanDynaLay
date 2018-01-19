package com.example.admin.jsonarray;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;

public class DisplayActicity extends AppCompatActivity {
RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;
//    ArrayList<Content> arrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_acticity);
//        recyclerView=(RecyclerView)findViewById(R.id.recyclerview);
//        layoutManager=new LinearLayoutManager(this);
//        recyclerView.setLayoutManager(layoutManager);
//        recyclerView.setHasFixedSize(true);
//        BackgroundTask backgroundTask = new BackgroundTask(DisplayActicity.this);
//        arrayList = backgroundTask.getList();
//        Toast.makeText(getApplicationContext(),arrayList.size()+"",Toast.LENGTH_SHORT).show();
//       // adapter= new RecyclerAdapter(arrayList);
//        recyclerView.setAdapter(adapter);


    }
}
