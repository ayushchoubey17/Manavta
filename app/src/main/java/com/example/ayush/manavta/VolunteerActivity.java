package com.example.ayush.manavta;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class VolunteerActivity extends AppCompatActivity {

    private EditText volnameedt,volemailedt,volcontactedt,voladdressedt,volpassedt,volCpassedt;
    private Button volcnfbtn;
    private String name,contact,email,address,password,Cpassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volunteer);


        Intent in=getIntent();

        volnameedt=findViewById(R.id.volnameedt);
        volcontactedt=findViewById(R.id.volcontactedt);
        voladdressedt=findViewById(R.id.voladdressedt);
        volemailedt=findViewById(R.id.volaemailedt);
        volpassedt=findViewById(R.id.volpasswordedt);
        volCpassedt=findViewById(R.id.volCpasswordedt);
        volcnfbtn=findViewById(R.id.volcnfbtn);

        volcnfbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name=volnameedt.getText().toString();
                contact=volcontactedt.getText().toString();
                email=volemailedt.getText().toString();
                password=volpassedt.getText().toString();
                Cpassword=volCpassedt.getText().toString();
                address=voladdressedt.getText().toString();
                if(name.isEmpty()||contact.isEmpty()||email.isEmpty()||password.isEmpty()||Cpassword.isEmpty())
                {
                   volnameedt.setError("required");
                   volcontactedt.setError("required");
                   volemailedt.setError("required");
                   volCpassedt.setError("required");
                   volpassedt.setError("required");
                }
                if(password.equals(Cpassword))
                {
                    final FirebaseAuth auth=FirebaseAuth.getInstance();
                    auth.createUserWithEmailAndPassword(email,password)
                            .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                                @Override
                                public void onSuccess(AuthResult authResult) {
                                    VolunteerBean v=new VolunteerBean();
                                    v.setName(name);
                                    v.setEmail(email);
                                    v.setContact(contact);
                                    v.setAddress(address);

                                    FirebaseDatabase database=FirebaseDatabase.getInstance();
                                    DatabaseReference reference=database.getReference("Volunteers");
                                    reference.child(auth.getCurrentUser().getUid()).setValue(v);
                                    Toast.makeText(VolunteerActivity.this,"you are now a Volunteer",Toast.LENGTH_LONG).show();



                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {

                                }
                            });

                }


            }
        });




    }
}
