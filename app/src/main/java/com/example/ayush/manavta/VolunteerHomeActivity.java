package com.example.ayush.manavta;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class VolunteerHomeActivity extends AppCompatActivity {

    private TextView welcometxtview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volunteer_home);

        Intent in=getIntent();
        String name=in.getStringExtra("name");
        welcometxtview=findViewById(R.id.welcomevolunteerid);
        welcometxtview.setText("Welcome "+name);

    }

}
