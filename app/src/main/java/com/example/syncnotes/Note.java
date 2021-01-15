package com.example.syncnotes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class Note extends AppCompatActivity {

    Button mSaveBtn;
    EditText mNote;
    FirebaseDatabase database;
    DatabaseReference myRef;
    StickyNote note;
    FirebaseAuth fAuth;
    List<StickyNote> notes = new ArrayList<StickyNote>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        mSaveBtn = findViewById(R.id.save);
        mNote = findViewById(R.id.note);
        database = FirebaseDatabase
                .getInstance("https://syncnotes-7daca-default-rtdb.firebaseio.com/");
        fAuth = FirebaseAuth.getInstance();
        final FirebaseUser user = fAuth.getCurrentUser();
        myRef = database.getReference("Users");
        note = new StickyNote();

        mSaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String des = mNote.getText().toString().trim();
                note.setDescription(des);
                notes.add(note);
                myRef.child(user.getUid()).push().setValue(notes.get(notes.size()-1));
               // myRef.push().setValue(notes.get(notes.size()-1));

                startActivity(new Intent(getApplicationContext(),MainActivity.class));


            }
        });

    }

   public List<StickyNote> getNotes(){
        return this.notes;
    }
}