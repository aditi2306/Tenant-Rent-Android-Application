package com.example.admin.sdl;


import android.content.Intent;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.internal.api.FirebaseNoSignedInUserException;

import java.util.HashMap;


public class Details extends AppCompatActivity implements  View.OnClickListener {


    private DatabaseReference dbref;
    private Button save;
    private EditText emailInput,Name,PhoneNo;
    FirebaseAuth mAuth;
    TextView upload;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

    dbref= FirebaseDatabase.getInstance().getReference("Category");
        mAuth = FirebaseAuth.getInstance();
       if(mAuth.getCurrentUser() == null)
       {
           finish();
           startActivity(new Intent(this,MainActivity.class));
       }

       FirebaseUser user=mAuth.getCurrentUser();


        emailInput=(EditText)findViewById(R.id.Email);
        Name=(EditText)findViewById(R.id.name);
        PhoneNo=(EditText)findViewById(R.id.Phone);



        save=(Button)findViewById(R.id.rya);
        save.setOnClickListener(Details.this);


      /*  save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent next=new Intent(Details.this,Locality.class);
                startActivity(next);
            }
        });*/


    }

    private  void saveDetails()
    {
        final String Email = emailInput.getText().toString();
        final String Nameclient=Name.getText().toString();
        final String phno= PhoneNo.getText().toString();

        DetailsPart1 dp1=new DetailsPart1(Nameclient,Email,phno);

        FirebaseUser user=mAuth.getCurrentUser();
        //databaseReference.child(user.getUid()).push().setValue(markerInfo);
        dbref.child(user.getUid()).setValue(dp1);

        //dbref.updateChildren();
        Toast.makeText(this,"Data inserted",Toast.LENGTH_LONG).show();

    }
    @Override
    public void onClick(View view) {
        final String Email = emailInput.getText().toString();
        final String Nameclient=Name.getText().toString();
        final String phno= PhoneNo.getText().toString();

        if (Email.length() == 0)
        {
            emailInput.requestFocus();
            emailInput.setError("Field Cannot Be Empty");
        }
        else if (!Email.matches( "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +

                "\\@" +

                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +

                "(" +

                "\\." +

                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +

                ")+")) {
            emailInput.requestFocus();
            emailInput.setError("Enter valid Email");
        }
        else if(Nameclient.length() == 0)
        {
            Name.requestFocus();
            Name.setError("Field Cannot Be Empty");
        }
        else if (!Nameclient.matches("[a-zA-Z ]+"))
        {
            Name.requestFocus();
            Name.setError("Enter Only Alphabetical Character");
        }
        else if(phno.length()== 0)
        {
            PhoneNo.requestFocus();
            PhoneNo.setError("Field Cannot Be Empty");
        }
        else if (phno.length()<10 || phno.length()>10)
        {
            PhoneNo.requestFocus();
            PhoneNo.setError("Field length should equal to 10");
        }


        else
        {
            saveDetails();
            Intent intent=new Intent(this,Locality.class);
            startActivity(intent);
        }
    }
}
