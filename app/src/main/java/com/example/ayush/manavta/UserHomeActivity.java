package com.example.ayush.manavta;

import android.content.ClipData;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Switch;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UserHomeActivity extends AppCompatActivity implements FoodDetailFragment.OnFragmentInteractionListener {

    private CardView servecardviwid;
    FirebaseAuth auth;
    private int SERVECODE=1;
    AlertDialog.Builder alertDialog;
    AlertDialog alert;
    UserBean userBean;
    private boolean i=true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_home);
        Intent in=getIntent();

        userBean=new UserBean();

        auth=FirebaseAuth.getInstance();
        servecardviwid=findViewById(R.id.servecardviewid);

        alertDialog=new AlertDialog.Builder(this);

        servecardviwid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if (auth.getCurrentUser() != null)
                {
                    Toast.makeText(UserHomeActivity.this, "ThankYou for Donating! your Request will be send to our nearest Volunteer", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(UserHomeActivity.this,FragmentActivity.class));
                } else
                    {

                        alertDialog=new AlertDialog.Builder(UserHomeActivity.this)
                                .setMessage("Please Login First!")

                                .setTitle("OOPss...Not Logged-In")
                                .setCancelable(false)

                                .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {

                                        startActivity(new Intent(UserHomeActivity.this,UserLoginActivity.class));
                                        alert.dismiss();
                                    }
                                })
                                .setNegativeButton("no", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        Toast.makeText(UserHomeActivity.this, "OK", Toast.LENGTH_SHORT).show();
                                        alert.dismiss();
                                    }
                                })
                                .setNeutralButton("cancel", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        alert.dismiss();
                                    }
                                })
                        ;
                        alert=alertDialog.create();
                        alert.show();
                    }


            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if(auth.getCurrentUser()!=null) {
            getMenuInflater().inflate(R.menu.usermenu, menu);
        }
        else
        {
            getMenuInflater().inflate(R.menu.volunteermenu,menu);
        }
            return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId())
        {
            case R.id.logoutid:
                auth.signOut();
                startActivity(new Intent(UserHomeActivity.this,UserLoginActivity.class));
                break;
            case R.id.volunteeropt:

                     Intent in = new Intent(UserHomeActivity.this, VolunteerCurrentActivity.class);
                     startActivity(in);
                     i=false;



                break;
            case R.id.volunteerid2:
                startActivity(new Intent(UserHomeActivity.this,VolunteerActivity.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
