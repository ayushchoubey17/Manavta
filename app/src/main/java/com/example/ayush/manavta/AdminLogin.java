package com.example.ayush.manavta;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AdminLogin extends AppCompatActivity {
    private EditText adminemail,adminpassword;
    private Button adminloginbtn;
    private String email,password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);
        adminemail=findViewById(R.id.adminemail);
        adminpassword=findViewById(R.id.adminpassword);
        adminloginbtn=findViewById(R.id.adminloginbtn);

        email="ayushchoubey267@gmail.com";
        password="msdhoni17";

        adminloginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(adminemail.getText().toString().equals(email)&&adminpassword.getText().toString().equals(password))
                {
                    Toast.makeText(AdminLogin.this,"Success!",Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(AdminLogin.this,AdminHome.class));
                }
            }
        });
    }
}
