package com.example.syncnotes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

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
    FirebaseUser user;
    FirebaseAuth fAuth;
    ListAdapter adapter;
    String id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrieve_data);

        myListview = findViewById(R.id.myListView);
        notes = new ArrayList();
        user = FirebaseAuth.getInstance().getCurrentUser();
        db = FirebaseDatabase.
                getInstance("https://syncnotes-7daca-default-rtdb.firebaseio.com/").
                getReference("Users").child(user.getUid());


        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                notes.clear();

                for (DataSnapshot noteDatasnap : dataSnapshot.getChildren()) {
                    StickyNote note = noteDatasnap.getValue(StickyNote.class);
                    notes.add(note);
                }
                adapter = new ListAdapter(RetreiveData.this, notes);
                myListview.setAdapter(adapter);
                registerForContextMenu(myListview);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu,v,menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.context_menu,menu);


    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)
                item.getMenuInfo();
        switch (item.getItemId()){
            case R.id.delete_id:
                id = notes.get(info.position).getId();
                notes.remove(info.position);

                DatabaseReference dbRemove = FirebaseDatabase
                        .getInstance("https://syncnotes-7daca-default-rtdb.firebaseio.com/")
                        .getReference("Users")
                        .child(user.getUid())
                        .child(id);
                dbRemove.removeValue();
                adapter.notifyDataSetChanged();
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }
}