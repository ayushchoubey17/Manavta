package com.example.ayush.manavta;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class UserSignUpActivity extends AppCompatActivity {


    private LocationManager locationManager;
    private LocationListener locationListener;
    private double latitude,longitude;
    private String currentLocation="";
    private EditText ename,eemail,econtact,epass,ecpass,eaddress;
    private Button signupbtn;
    private String name,email,contact,pass,cpass,place,address;
    private TextView skiptext;
    private Spinner spinner;
    private List<Address> addressList;
    private ArrayList<String> places=new ArrayList<>(Arrays.asList(new String[]{"Home", "Hostel/pg", "Hotel"}));
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_sign_up);
        locationManager= (LocationManager) this.getSystemService(LOCATION_SERVICE);
        locationListener=new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {

                latitude=location.getLatitude();
                longitude=location.getLongitude();
                Geocoder geocoder=new Geocoder(getApplicationContext(), Locale.getDefault());
                try {
                    addressList=geocoder.getFromLocation(location.getLatitude(),location.getLongitude(),1);
                    if(addressList.size()>0&&addressList!=null)
                    {
                        if(addressList.get(0).getSubAdminArea()!=null)
                        {
                            currentLocation=addressList.get(0).getSubAdminArea();
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };

        ename=findViewById(R.id.ename);
        eemail=findViewById(R.id.eemail);
        econtact=findViewById(R.id.econtact);
        epass=findViewById(R.id.epass);
        ecpass=findViewById(R.id.ecpass);
        eaddress=findViewById(R.id.eaddress);
        signupbtn=findViewById(R.id.signupbtn);
        spinner=findViewById(R.id.spinner);

        if(Build.VERSION.SDK_INT<23)
        {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,2,0,locationListener);
        }
        else
        {
            if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED)
            {
                ActivityCompat.requestPermissions(UserSignUpActivity.this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},1);
            }
            else
            {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,2,0,locationListener);
            }
        }


        ArrayAdapter arrayAdapter=new ArrayAdapter(getBaseContext(),android.R.layout.simple_spinner_dropdown_item,places);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String s=parent.getItemAtPosition(position).toString();
                switch (s)
                {
                    case "Home":
                        place="home";
                        break;
                    case "Hostel/pg":
                        place="hostel";
                        break;
                    case "Hotel":
                        place="hotel";
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        signupbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name=ename.getText().toString();
                email=eemail.getText().toString();
                contact=econtact.getText().toString();
                pass=epass.getText().toString();
                cpass=ecpass.getText().toString();
                address=eaddress.getText().toString();
                if(name.isEmpty())
                {
                    ename.setError("required");
                }else if(email.isEmpty())
                {
                    eemail.setError("required");
                }else if(contact.isEmpty())
                {
                    econtact.setError("required");
                }else if(pass.isEmpty())
                {
                    epass.setError("required");
                }else if(cpass.isEmpty())
                {
                    ecpass.setError("required");
                }else if(address.equals(""))
                {
                    eaddress.setError("required");
                }
                else if(pass.equals(cpass))
                {

                    final FirebaseAuth auth=FirebaseAuth.getInstance();
                    auth.createUserWithEmailAndPassword(email,pass)
                            .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                                @Override
                                public void onSuccess(AuthResult authResult) {
                                    Toast.makeText(UserSignUpActivity.this, "Signed up successfully..", Toast.LENGTH_SHORT).show();
                                    FirebaseDatabase database=FirebaseDatabase.getInstance();
                                    DatabaseReference reference=database.getReference("user");
                                    UserBean user=new UserBean();
                                    user.setName(name);
                                    user.setContact(contact);
                                    user.setEmail(email);
                                    user.setAddress(address);
                                    user.setPlace(place);
                                    user.setLatitude(latitude);
                                    user.setLongitude(longitude);
                                    user.setLocation(currentLocation);
                                    reference.child(auth.getCurrentUser().getUid()).setValue(user);
                                    Toast.makeText(UserSignUpActivity.this, "records added", Toast.LENGTH_SHORT).show();
                                    Intent intent=new Intent(UserSignUpActivity.this,UserHomeActivity.class);

                                    startActivity(intent);
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(UserSignUpActivity.this, "Try again..", Toast.LENGTH_SHORT).show();
                                }
                            });
                }else {
                    Toast.makeText(UserSignUpActivity.this, "Password not matched..", Toast.LENGTH_SHORT).show();
                    epass.setText("");
                    ecpass.setText("");
                }
            }
        });
        skiptext=findViewById(R.id.skiptext);
        skiptext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(UserSignUpActivity.this,UserHomeActivity.class);
                startActivity(intent);
            }
        });



    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==1)
        {
            if(grantResults.length>0&&grantResults[0]==PackageManager.PERMISSION_GRANTED)
            {
                if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)==PackageManager.PERMISSION_GRANTED)
                {
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,2,0,locationListener);
                }
            }
        }
    }
}
