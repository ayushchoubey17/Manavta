package com.example.ayush.manavta;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class AfterServedActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.afterserverd);
        Intent in=getIntent();
        //int code=Integer.parseInt(String.valueOf(in.getStringArrayExtra("Code")));
    }
}
