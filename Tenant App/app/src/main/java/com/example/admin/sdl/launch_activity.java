package com.example.admin.sdl;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class launch_activity extends AppCompatActivity {
    TextView t;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch_activity);





        Thread thread=new Thread()
        {
            @Override
            public void run()
            {
                try
                {
                    sleep(3000);
                    Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(intent);
                   // overridePendingTransition(R.anim.from_middle, R.anim.to_middle);
                    finish();
                }
                catch (InterruptedException e)
                {

                    e.printStackTrace();
                }
            }

        };
        thread.start();
    }
}

