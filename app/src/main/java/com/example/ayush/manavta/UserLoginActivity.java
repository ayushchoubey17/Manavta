package com.example.ayush.manavta;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class UserLoginActivity extends AppCompatActivity {

private EditText login_email,login_password;
private String email,password;
private TextView signup_text;
private CardView logincardview;
private FirebaseAuth auth;
private TextView userskiptext;

private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);


        auth=FirebaseAuth.getInstance();

        login_email=findViewById(R.id.login_email);
        login_password=findViewById(R.id.login_password);
        signup_text=findViewById(R.id.signup_text);
        logincardview=findViewById(R.id.logincardview);
        progressDialog=new ProgressDialog(this);
        userskiptext=findViewById(R.id.skiptextid);

        logincardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                progressDialog.setMessage("Logging In..");
                progressDialog.show();
                email=login_email.getText().toString();
                password=login_password.getText().toString();
                 if(email.isEmpty()||password.isEmpty())
                 {
                     login_email.setError("required");
                     login_password.setError("required");
                 }
                 else {
                     auth.signInWithEmailAndPassword(email, password)
                             .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                         @Override
                         public void onComplete(@NonNull Task<AuthResult> task) {
                             if(task.isSuccessful())
                             {progressDialog.cancel();
                               Toast.makeText(UserLoginActivity.this,"Successful",Toast.LENGTH_LONG).show();
                               startActivity(new Intent(UserLoginActivity.this,UserHomeActivity.class));
                             }
                             else
                             {Toast.makeText(UserLoginActivity.this,"Unsuccessful",Toast.LENGTH_LONG).show();
                             }
                         }
                     });

                 }


            }
        });

        signup_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

              startActivity(new Intent(UserLoginActivity.this,UserSignUpActivity.class));
            }
        });
        userskiptext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserLoginActivity.this,UserHomeActivity.class));
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.adminmenu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.loginasadminid:
                startActivity(new Intent(UserLoginActivity.this,AdminLogin.class));
                break;
            case R.id.loginasvolunteer:
                startActivity(new Intent(UserLoginActivity.this,VolunteerActivity.class));

        }
        return super.onOptionsItemSelected(item);
    }
}
