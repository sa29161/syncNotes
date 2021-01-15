package com.example.syncnotes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = null;
    Button mShowBtn;
    Button mLogoutBtn;
    FirebaseAuth fAuth;
    Button createBtn;
    DatabaseReference myRef;
    FirebaseUser user;
    FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
         database = FirebaseDatabase
                .getInstance("https://syncnotes-7daca-default-rtdb.firebaseio.com/");
        fAuth = FirebaseAuth.getInstance();
        user = fAuth.getCurrentUser();
        myRef = FirebaseDatabase.getInstance("https://syncnotes-7daca-default-rtdb.firebaseio.com/")
                .getReference().child("Users").child(user.getUid());
        mShowBtn = findViewById(R.id.showNotes);
        mLogoutBtn = findViewById(R.id.logoutBtn);

        createBtn = findViewById(R.id.create);

        mShowBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),RetreiveData.class));
            }
        });

        createBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),Note.class));
            }
        });

        mLogoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logout();
                startActivity(new Intent(getApplicationContext(),Login.class));
            }
        });
    }

   public void logout(){
       fAuth.getInstance().signOut();
    }

    public void readNotes(){
    myRef.addValueEventListener(new ValueEventListener() {

        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            // This method is called once with the initial value and again
            // whenever data at this location is updated.
//            ArrayAdapter adapter = new ArrayAdapter<String>(this,
            ////                    android.R.layout.simple_list_item_1,
            ////                    list);
            //
            //           // Log.d(TAG, "Value is: " + value);
        }

        @Override
        public void onCancelled(DatabaseError error) {
            // Failed to read value
            Log.w(TAG, "Failed to read value.", error.toException());
        }
    });}

}