package com.example.ayush.manavta;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class VolunteerCurrentActivity extends AppCompatActivity {

    private TextView nametv,contacttv,emailtv,addresstv;
    FirebaseAuth auth;
    private CardView volsubmitid;
    String name,contact,email,address;
    private boolean i=true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volunteer_current);

        auth= FirebaseAuth.getInstance();
        final String Uid=auth.getCurrentUser().getUid();
        final FirebaseDatabase database=FirebaseDatabase.getInstance();
        final DatabaseReference reference=database.getReference("user");
        Intent in=getIntent();
        nametv=findViewById(R.id.nametv);
        contacttv=findViewById(R.id.contacttv);
        emailtv=findViewById(R.id.emailv);
        addresstv=findViewById(R.id.addresstv);
        volsubmitid=findViewById(R.id.volsubmitid);

       reference.addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(DataSnapshot dataSnapshot) {
               name=dataSnapshot.child(Uid).child("name").getValue().toString();
               email=dataSnapshot.child(Uid).child("email").getValue().toString();
               address=dataSnapshot.child(Uid).child("address").getValue().toString();
               contact=dataSnapshot.child(Uid).child("contact").getValue().toString();
               nametv.setText(name);
               emailtv.setText(email);
               contacttv.setText(contact);
               addresstv.setText(address);
           }

           @Override
           public void onCancelled(DatabaseError databaseError) {

           }
       });
       volsubmitid.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               if(i) {
                   VolunteerBean volunteerBean = new VolunteerBean();
                   volunteerBean.setName(name);
                   volunteerBean.setAddress(address);
                   volunteerBean.setContact(contact);
                   volunteerBean.setEmail(email);
                   FirebaseDatabase database1 = FirebaseDatabase.getInstance();
                   DatabaseReference reference1 = database1.getReference("Volunteers");
                   reference1.child(auth.getCurrentUser().getUid()).setValue(volunteerBean);
                   Intent in = new Intent(VolunteerCurrentActivity.this, VolunteerHomeActivity.class);
                   in.putExtra("name", name);
                   startActivity(in);
                   i=false;
               }
               else if(i==false)
               {
                   Toast.makeText(VolunteerCurrentActivity.this,"Hey You are alredy a Volunteer",Toast.LENGTH_LONG).show();
               }
               else
               {
                   Toast.makeText(VolunteerCurrentActivity.this,"Technical Error!",Toast.LENGTH_SHORT).show();
               }
           }
       });

    }
}
