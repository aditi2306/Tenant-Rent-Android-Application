package com.example.admin.sdl;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Select extends AppCompatActivity {
    private Button rentA,RentYour;
    private DatabaseReference dbref;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select);


        dbref= FirebaseDatabase.getInstance().getReference("Category");
        mAuth = FirebaseAuth.getInstance();
        if(mAuth.getCurrentUser() == null)
        {
            finish();
            startActivity(new Intent(this,MainActivity.class));
        }

        FirebaseUser user=mAuth.getCurrentUser();


        rentA=(Button)findViewById(R.id.rentappart);
        RentYour=(Button)findViewById(R.id.yourappt);


        rentA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ii=new Intent(getApplicationContext(),Home.class);
                startActivity(ii);

            }
        });

        RentYour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getApplicationContext(),Details.class);
                startActivity(i);
            }
        });

    }
}
