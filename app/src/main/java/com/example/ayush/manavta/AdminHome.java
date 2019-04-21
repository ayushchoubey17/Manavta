package com.example.ayush.manavta;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.TextView;

public class AdminHome extends AppCompatActivity implements UserListFragment.OnFragmentInteractionListener,
VolunteerListFragment.OnFragmentInteractionListener{

    private TextView mTextMessage;
    private FrameLayout frame;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    //mTextMessage.setText("Users");
                    FragmentManager manager=getSupportFragmentManager();
                    FragmentTransaction transaction=manager.beginTransaction();
                    transaction.replace(R.id.frame,new UserListFragment());
                    transaction.commit();
                    break;
                case R.id.navigation_dashboard:
                   // mTextMessage.setText("Volunteers");
                    FragmentManager manager1=getSupportFragmentManager();
                    FragmentTransaction transaction1=manager1.beginTransaction();
                    transaction1.replace(R.id.frame,new VolunteerListFragment());
                    transaction1.commit();

                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);

        frame=findViewById(R.id.frame);
        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
