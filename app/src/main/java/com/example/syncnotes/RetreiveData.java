package com.example.syncnotes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.EventListener;
import java.util.List;

public class RetreiveData extends AppCompatActivity {

    ListView myListview;
    List<StickyNote> notes;
    DatabaseReference db;
    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrieve_data);

        myListview = findViewById(R.id.myListView);
        notes = new ArrayList();
        fAuth = FirebaseAuth.getInstance();
        final FirebaseUser user = fAuth.getCurrentUser();
        db = FirebaseDatabase.
                getInstance("https://syncnotes-7daca-default-rtdb.firebaseio.com/").
                getReference("Users").child(user.getUid());


        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                notes.clear();

                for(DataSnapshot noteDatasnap : dataSnapshot.getChildren()){
                    StickyNote note = noteDatasnap.getValue(StickyNote.class);
                    notes.add(note);
                }
                ListAdapter adapter = new ListAdapter(RetreiveData.this,notes);
                myListview.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}