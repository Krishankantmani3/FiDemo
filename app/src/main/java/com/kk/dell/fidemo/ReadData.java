package com.kk.dell.fidemo;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ReadData extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    final private String TAG="ReadData";
    TextView tv;
     ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_data);

        tv=findViewById(R.id.tv);
        mAuth=FirebaseAuth.getInstance();
        final FirebaseUser user=mAuth.getCurrentUser();
        database=FirebaseDatabase.getInstance();
        final String userId=user.getUid();
        DatabaseReference myRef=database.getReference();
        /*
        progressDialog=new ProgressDialog(ReadData.this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage("processing ...");
        */


        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                for (DataSnapshot ds:dataSnapshot.getChildren())
                {
                    UserInformation userInformation=new UserInformation();
                    userInformation.setFruit(ds.child(userId).child("Food").getValue(UserInformation.class).getFruit());
                    userInformation.setSmartPhone(ds.child(userId).child("Gadget").getValue(UserInformation.class).getSmartPhone());

                    tv.setText("smartphone :"+userInformation.SmartPhone+"/n"+"Fruit :"+userInformation.getFruit());
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
                Toast.makeText(getApplicationContext(),"erro occured",Toast.LENGTH_LONG).show();
            }
        });
      //  progressDialog.dismiss();
    }

}
