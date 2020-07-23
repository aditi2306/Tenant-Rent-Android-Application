package com.example.admin.sdl;

import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.provider.SyncStateContract;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class Locality extends AppCompatActivity implements View.OnClickListener{
     Button save;
     EditText location,appartment,street,aptype,Rent,howbhk;
     FirebaseDatabase fdb;
     private DatabaseReference dbref,dbref2;
     FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_locality);


        dbref= FirebaseDatabase.getInstance().getReference("Category");
        dbref2=dbref.child("Locality");
        mAuth=FirebaseAuth.getInstance();
        if(mAuth.getCurrentUser()==null)
        {
            finish();
            startActivity(new Intent(this,MainActivity.class));
        }




        /*Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.fragment);
        mapFragment.getMapAsync(this);*/

        location=(EditText)findViewById(R.id.locality);
        appartment=(EditText)findViewById(R.id.appart);
        street=(EditText)findViewById(R.id.streett);
        aptype=(EditText)findViewById(R.id.apptype);
        Rent=(EditText)findViewById(R.id.Rent);
        howbhk=(EditText)findViewById(R.id.bhk);

       save=(Button)findViewById(R.id.button2);
       save.setOnClickListener(Locality.this);

       /* button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent next=new Intent(Locality.this,AboutAppartment.class);
                startActivity(next);
            }
        });*/
    }


    private  void saveDetails()
    {
        final String loc=location.getText().toString();
        final String appt=appartment.getText().toString();
        final String str=street.getText().toString();
        final String apptype=aptype.getText().toString();
        final String bhk=howbhk.getText().toString();
        final String rent=Rent.getText().toString();

        final DetailsPart2 dp2=new DetailsPart2(loc,appt,str,apptype,bhk,rent);
        FirebaseUser user=mAuth.getCurrentUser();


        Map<String,String> map=new HashMap<>();
        map.put("Location",loc);
        map.put("AppartmentName",appt);
        map.put("streetName",str);
        map.put("AppartmentType",apptype);
        map.put("BHK",bhk);
        map.put("Rent",rent);
        //dbref.child(user.getUid()).setValue(map);

        dbref2.child(user.getUid()).setValue(map);
       // dbref.push().setValue(map);
        Toast.makeText(this,"Data inserted",Toast.LENGTH_LONG).show();


    }
    @Override
  /*  public void onMapReady(GoogleMap googleMap) {
        GoogleMap mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng Pune = new LatLng(18, 73);
        mMap.addMarker(new MarkerOptions().position(Pune).title("Marker in Pune"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(Pune));
    }
*/

    public void onClick(View view) {
        final String loc=location.getText().toString();
        final String appt=appartment.getText().toString();
        final String str=street.getText().toString();

        if (loc.length() == 0)
        {
            location.requestFocus();
            location.setError("Field Cannot Be Empty");
        }
        else if (appt.length() == 0)
        {
            appartment.requestFocus();
            appartment.setError("Field Cannot Be Empty");
        }
        else if(str.length() == 0)
        {
            street.requestFocus();
            street.setError("Fiend Cannot Be Empty");
        }
        else
        {
            saveDetails();
            Intent intent=new Intent(this,Select.class);
            startActivity(intent);
        }
    }
}
