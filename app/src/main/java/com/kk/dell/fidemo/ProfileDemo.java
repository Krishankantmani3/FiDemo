package com.kk.dell.fidemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ProfileDemo extends AppCompatActivity {
    FirebaseAuth mAuth;
    Button btnsignedOut,btnAddToDatabase,btnRead,btnSaveImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_demo);
        btnsignedOut=findViewById(R.id.button2);
        mAuth=FirebaseAuth.getInstance();
        btnAddToDatabase=findViewById(R.id.btnAddToDatabase);
        btnRead=findViewById(R.id.btnread);
        btnSaveImage=findViewById(R.id.btnSaveImg);
        btnsignedOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                FirebaseUser currentUser= mAuth.getCurrentUser();
                if(currentUser==null) {
                    Toast.makeText(getApplicationContext(), "Signed Out", Toast.LENGTH_LONG).show();
                    finish();
                }
            }
        });


        btnAddToDatabase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ProfileDemo.this,DatabaeDemo.class);
                startActivity(intent);
            }
        });

        btnRead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =new Intent(ProfileDemo.this,ReadData.class);
                startActivity(i);
            }
        });


        btnSaveImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(ProfileDemo.this,UploadImage.class);
                startActivity(i);
            }
        });


    }



}
