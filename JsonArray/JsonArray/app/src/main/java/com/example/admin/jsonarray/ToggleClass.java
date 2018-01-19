package com.example.admin.jsonarray;

/**
 * Created by admin on 21-11-2017.
 */
import android.app.Activity;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.ToggleButton;

public class ToggleClass extends Activity {
    ToggleButton tButton;
    TextView tvStateofToggleButton;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.toggle);

        tButton = (ToggleButton) findViewById(R.id.toggleButton1);
        tvStateofToggleButton=(TextView)findViewById(R.id.tvstate);
        tvStateofToggleButton.setText("OFF");
        tButton.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {

                if(isChecked){
                    tvStateofToggleButton.setText("ON");
                }else{
                    tvStateofToggleButton.setText("OFF");
                }

            }
        });
    }
}