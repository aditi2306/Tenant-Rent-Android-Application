package com.example.admin.sdl;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import android.Manifest;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

public class FlatDetail extends AppCompatActivity {
    TextView flat_FullName,flat_Email,flat_phoneNo;
    TextView flat_Rent,flat_Location,flat_streetName;
    TextView flat_AppartmentType,flat_BHK;
    ImageView flat_image;

    CollapsingToolbarLayout collapsingToolbarLayout;


    String CategoryId="";
    FirebaseDatabase database;
    DatabaseReference category,locality;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flat_detail);

        //init firebase
        database=FirebaseDatabase.getInstance();
        category=database.getReference("Category");
        locality=category.child("Locality");

        flat_FullName=(TextView)findViewById(R.id.flat_FullName);
        flat_Email=(TextView)findViewById(R.id.flat_Email);
        flat_phoneNo=(TextView)findViewById(R.id.flat_phoneNo);
        flat_Rent=(TextView)findViewById(R.id.flat_Rent);
        flat_Location=(TextView)findViewById(R.id.flat_Location);
        flat_streetName=(TextView)findViewById(R.id.flat_streetName);
        flat_AppartmentType=(TextView)findViewById(R.id.flat_AppartmentType);
        flat_BHK=(TextView)findViewById(R.id.flat_BHK);
        flat_image=(ImageView)findViewById(R.id.img);


        collapsingToolbarLayout=(CollapsingToolbarLayout)findViewById(R.id.collapsing);
        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.ExpandedAppBar);
        collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.CollapsedAppBar);

        //get food id
        if(getIntent()!=null)
            CategoryId=getIntent().getStringExtra("CategoryId");
        if(!CategoryId.isEmpty())
        {
            getDetailFlat(CategoryId);
            getDetailFlat2(CategoryId);
        }




    }


    private void getDetailFlat(String categoryId) {
        category.child(categoryId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Category cat = dataSnapshot.getValue(Category.class);

                Picasso.with(getBaseContext()).load(cat.getImage())
                        .into(flat_image);


                flat_FullName.setText(cat.getFullName());
                flat_Email.setText(cat.getEmail());
                flat_phoneNo.setText(cat.getPhoneNo());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void getDetailFlat2(String categoryId) {
        locality.child(categoryId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Category cat=dataSnapshot.getValue(Category.class);

                collapsingToolbarLayout.setTitle(cat.getAppartmentName());
                flat_AppartmentType.setText(cat.getAppartmentType());
                flat_BHK.setText(cat.getBHK());
                flat_Rent.setText(cat.getRent());
                flat_streetName.setText(cat.getStreetName());
                flat_Location.setText(cat.getLocation());

            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
