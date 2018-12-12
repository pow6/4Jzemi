package com.example.pow6.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    int counter=0;
    int sw=0;
    public void button_count(View v){
        counter++;
        ((TextView)findViewById(R.id.text)).setText(String.valueOf(counter));
    }
    public void button_viewCtrl(View v){
        if(sw==0){
            (findViewById(R.id.text)).setVisibility(View.INVISIBLE);
            sw=1;
        }else{
            (findViewById(R.id.text)).setVisibility(View.VISIBLE);
            sw=0;
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

}
