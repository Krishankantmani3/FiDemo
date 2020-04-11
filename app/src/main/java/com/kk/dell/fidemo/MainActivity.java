package com.kk.dell.fidemo;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import kotlin.jvm.Synchronized;

public class MainActivity extends AppCompatActivity {


    LinearLayout linearLayout;
    TextView tvStatus;
    EditText editEmailId,editPassword;
    Button btnlogin,btnNew,btnsignOut;
    private final String Tag="MainActivity";
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListner;
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editEmailId = findViewById(R.id.editEmail);
        editPassword = findViewById(R.id.editPassword);
        btnlogin = findViewById(R.id.btnLogin);
        btnNew = findViewById(R.id.btnNewUser);
        tvStatus=findViewById(R.id.tvstatus);
        mAuth = FirebaseAuth.getInstance();
        btnsignOut=findViewById(R.id.btnsigneOut);




        mAuthListner = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser currentUser = mAuth.getCurrentUser();
                if (currentUser != null) {
                    tvStatus.setText("signed in");
                    Toast.makeText(getApplicationContext(),"succesfully ",Toast.LENGTH_LONG).show();
                } else {
                    tvStatus.setText("signed out");
                }
            }
        };


        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog=new ProgressDialog(MainActivity.this);
                dialog.setTitle("processing...");
                dialog.setMessage("waiting...");
                dialog.setCancelable(false);
                dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                dialog.show();
                String email = editEmailId.getText().toString();
                String password = editPassword.getText().toString();
                if (!email.isEmpty() && !password.isEmpty() ){
                    mAuth.signInWithEmailAndPassword(email,password)
                            .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {

                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // Sign in success, update UI with the signed-in user's information
                                        Log.d(Tag, "signInWithEmail:success");
                                        FirebaseUser user = mAuth.getCurrentUser();
                                        dialog.dismiss();
                                        //
                                        toast("successfully signed in");
                                        Intent i=new Intent(MainActivity.this,ProfileDemo.class);
                                        startActivity(i);

                                    } else {
                                        dialog.dismiss();
                                        // If sign in fails, display a message to the user.
                                        Log.w(Tag, "signInWithEmail:failure", task.getException());
                                        toast("failed in sign in ");
                                    }
                                    // ...
                                }
                            });
                }
                else {
                    dialog.dismiss();
                    Toast.makeText(getApplicationContext(), "fill the email or password", Toast.LENGTH_LONG).show();
                }

            }
        });


        btnsignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                FirebaseUser user=mAuth.getInstance().getCurrentUser();
                if (user!=null)
                {
                    editEmailId.setText("");
                    editPassword.setText("");
                  toast("signed out");
                }
            }
        });


        btnNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(MainActivity.this,NewUser.class);
                startActivity(i);
            }
        });



    }



    @Override
    protected void onStart() {
        super.onStart();



        mAuth.addAuthStateListener(mAuthListner);

    }

    @Override
    protected void onStop() {
        super.onStop();
        if(mAuthListner!=null)
        { mAuth.removeAuthStateListener(mAuthListner);}
    }



               public void toast(String s){
        Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
               }
}
