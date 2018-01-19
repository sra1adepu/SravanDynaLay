package com.example.admin.jsonarray;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatDialogFragment;

import com.viewpagerindicator.CirclePageIndicator;

/**
 * Created by admin on 22-05-2017.
 */

public class PageViewer extends FragmentActivity {
    private CirclePageIndicator mIndicator;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.previewtab);
        ViewPager pager=(ViewPager)findViewById(R.id.pager);
        FragmentManager fragmentmangeer=getSupportFragmentManager();
        pager.setAdapter(new MyAdapter(fragmentmangeer));

        mIndicator = (CirclePageIndicator)findViewById(R.id.indicator);
        mIndicator.setViewPager(pager);
    }
}
class MyAdapter extends FragmentPagerAdapter {

    public MyAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
   Fragment fragment=null;
        if(i==0){
            fragment= new Mooofenderfamily_preview();
        }
        if(i==1){
            fragment= new Offenderfamily_preview();
        }
        if(i==2){
            fragment= new Involvedcases_preview();
        }
        if(i==3){
            fragment= new OffernderDocuments_preview();
        }
        if(i==4){
            fragment= new Photo_preview();
        }

        return fragment;
    }

    @Override
    public int getCount() {
        return 5;
    }

}


