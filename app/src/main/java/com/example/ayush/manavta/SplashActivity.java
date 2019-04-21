package com.example.ayush.manavta;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SplashActivity extends AppCompatActivity {
DataSnapshot dataSnapshot;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
         final FirebaseAuth auth=FirebaseAuth.getInstance();
        FirebaseDatabase database=FirebaseDatabase.getInstance();
        final DatabaseReference reference=database.getReference("Volunteers");
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(auth.getCurrentUser()!=null) {
                    //String strchld=dataSnapshot.child(auth.getCurrentUser().getUid()).child("v"+"contact").getValue().toString();


                    //startActivity(new Intent(SplashActivity.this,VolunteerHomeActivity.class));

                         startActivity(new Intent(SplashActivity.this,UserHomeActivity.class));


                }
                else
                {
                    startActivity(new Intent(SplashActivity.this,FactsActivity.class));
                }
            }
        },4000);
    }
    }

