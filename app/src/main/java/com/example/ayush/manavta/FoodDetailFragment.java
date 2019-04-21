package com.example.ayush.manavta;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.List;
import java.util.Locale;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FoodDetailFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FoodDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FoodDetailFragment extends Fragment
{
     private EditText foodtype,foodquantity,fooddescription;
     private CardView fooddetailsubmit;
     private  String type,quantity,description;
     private String name,contact,address;
     private LocationManager locationManager;
     private LocationListener locationListener;
     private double latitude,longitude;
     DataSnapshot dataSnapshot;
     private List<Address> addresslist;
     // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public FoodDetailFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FoodDetailFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FoodDetailFragment newInstance(String param1, String param2) {
        FoodDetailFragment fragment = new FoodDetailFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_food_detail, container, false);
        foodtype =v.findViewById(R.id.foodtypeid);
        fooddescription=v.findViewById(R.id.fooddescriptionid);
        foodquantity=v.findViewById(R.id.foodquantityid);
        fooddetailsubmit=v.findViewById(R.id.fooddetailsubmitid);
        locationManager= (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);
        locationListener=new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {

                latitude=location.getLatitude();
                longitude=location.getLongitude();
                Geocoder geocoder=new Geocoder(getContext(), Locale.getDefault());
                try {
                    addresslist=geocoder.getFromLocation(location.getLatitude(),location.getLongitude(),1);
                    if(addresslist.size()>0&&addresslist!=null)
                    {
                        if(addresslist.get(0).getSubAdminArea()!=null)
                        {
                            address=addresslist.get(0).getSubAdminArea();
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


        fooddetailsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               type=foodtype.getText().toString();
               quantity=foodquantity.getText().toString();
               description=fooddescription.getText().toString();
               if(type.isEmpty()||quantity.isEmpty()||description.isEmpty()) {
                   fooddescription.setError("Required");
                   foodquantity.setError("Required");
                   foodtype.setError("Required");
               }
               else
               {
                   FoodDetails details=new FoodDetails();
                   final FirebaseAuth auth=FirebaseAuth.getInstance();
                   final String Uid=auth.getCurrentUser().getUid();
                   final FirebaseDatabase database=FirebaseDatabase.getInstance();
                   DatabaseReference reference=database.getReference("Food Details");
                   DatabaseReference reference1=database.getReference("user");
                   reference1.addValueEventListener(new ValueEventListener() {
                       @Override
                       public void onDataChange(DataSnapshot dataSnapshot) {
                        name=dataSnapshot.child(auth.getCurrentUser().getUid()).child("name").getValue().toString();
                        contact=dataSnapshot.child(auth.getCurrentUser().getUid()).child("contact").getValue().toString();
                       }

                       @Override
                       public void onCancelled(DatabaseError databaseError) {

                       }
                   });
                  details.setName(name);
                  details.setContact(contact);
                   details.setAddress(address);
                   details.setFoodtype(type);
                   details.setFoodquantity(quantity);
                   details.setFooddescription(description);
                   reference.child(Uid).setValue(details);
                   Toast.makeText(getContext(), "Details Sent", Toast.LENGTH_LONG).show();

               }

            }
        });

        // Inflate the layout for this fragment
        return v;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
