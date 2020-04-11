package com.kk.dell.fidemo;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
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

public class DatabaeDemo extends AppCompatActivity {

    Button btn;
    String s1,s2;
    String TAG="DatabaeDemo";
    EditText ed1,ed2;
    FirebaseDatabase database;
    DatabaseReference myRef;
    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_databae_demo);
        btn=findViewById(R.id.btn4);
        ed1=findViewById(R.id.edsmartfone);
        ed2=findViewById(R.id.edfruit);
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("message");

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                s1=ed1.getText().toString().trim();
                s2=ed2.getText().toString().trim();
                if(!s1.isEmpty() && !s2.isEmpty() )
                {    pd=new ProgressDialog(DatabaeDemo.this);
                    pd.setTitle("processing...");
                    pd.setMessage("wait...");
                    pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                    pd.show();
                //    database = FirebaseDatabase.getInstance();
                //    myRef = database.getReference("message");
                    FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();
                    String userId=user.getUid();
                    myRef.child(userId).child("Gadget").child("SmartPhone").setValue(s1);
                    myRef.child(userId).child("Food").child("Fruit").setValue(s2);
                    pd.dismiss();
                }
                else
                {
                    toast("above field is empty");
                }
            }
        });




    }
    public void toast(String msg)
    {
        Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_LONG).show();
    }


/*
    public class AddToDataBase extends AsyncTask<String,Void,String>
    {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected String doInBackground(String... strings) {



            return null;
        }


        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);


        }
    }
 */
}
