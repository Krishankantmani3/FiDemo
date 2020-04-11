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
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class NewUser extends AppCompatActivity {

    EditText editName,editPassword1,editCnfPassword,editEmail;
    Button submit;
    FirebaseAuth mAuth;
    FirebaseAuth.AuthStateListener mAuthStateListener;
    String TAG="MainActivity";
    String s = "nothing bro";
    ProgressDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_user);
        editName=findViewById(R.id.editName);
        editPassword1=findViewById(R.id.editPassword);
        editCnfPassword=findViewById(R.id.editCnfPassword);
        editEmail=findViewById(R.id.editemail);
        submit=findViewById(R.id.btnSubmit);
        mAuth=FirebaseAuth.getInstance();
        mAuth.signOut();
        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser currentUser = mAuth.getCurrentUser();
                if (currentUser != null) {
                    Toast.makeText(getApplicationContext(),"succesfully created",Toast.LENGTH_LONG).show();
                    Intent i=new Intent(NewUser.this,ProfileDemo.class);
                    startActivity(i);
                }

            }
        };






        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name=editName.getText().toString().trim();
                String password=editPassword1.getText().toString().trim();
                String cnfpassword=editCnfPassword.getText().toString().trim();
                String email=editEmail.getText().toString().trim();

                dialog=new ProgressDialog(NewUser.this);
                dialog.setTitle("processing...");
                dialog.setMessage("please wait...");
                dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                dialog.show();

                if(!name.isEmpty() && !password.isEmpty() && !cnfpassword.isEmpty() && !email.isEmpty()) {
                    if (password.equals(cnfpassword)) {
                        mAuth.createUserWithEmailAndPassword(email, password)
                                .addOnCompleteListener(NewUser.this, new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {

                                        if (task.isSuccessful()) {
                                            // Sign in success, update UI with the signed-in user's information
                                            Log.d(TAG, "createUserWithEmail:success");
                                            FirebaseUser user = mAuth.getCurrentUser();
                                            Toast.makeText(getApplicationContext(),"succesfully completed",Toast.LENGTH_LONG).show();
                                            dialog.dismiss();
                                            finish();
                                        } else {
                                            // If sign in fails, display a message to the user.
                                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                            dialog.dismiss();
                                            Toast.makeText(getApplicationContext(),"Authentication failed.",Toast.LENGTH_LONG).show();
                                        }

                                        // ...
                                    }
                                });

                    } else {
                        Toast.makeText(getApplicationContext(), "password didn't match", Toast.LENGTH_LONG).show();
                    }
                }else{
                    Toast.makeText(getApplicationContext(),"fill the empty field",Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthStateListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(mAuthStateListener!=null)
        mAuth.removeAuthStateListener(mAuthStateListener);
    }


}
