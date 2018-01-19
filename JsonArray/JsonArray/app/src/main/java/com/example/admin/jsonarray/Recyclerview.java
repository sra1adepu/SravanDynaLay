package com.example.admin.jsonarray;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

/**
 * Created by admin on 01-05-2017.
 */

public class Recyclerview extends Activity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    String[] country={"india","Australia","japan"};
    String[] capitals={"Delhi","Sydney","Tokyo"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recycler);
        recyclerView=(RecyclerView)findViewById(R.id.recyclerview);
        adapter= new RecyclerAdaptor(country,capitals);
        layoutManager= new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }
}
