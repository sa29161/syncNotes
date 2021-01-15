package com.example.syncnotes;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

public class ListAdapter extends ArrayAdapter {

    private Activity mContext;
    List<StickyNote> notes;

    public ListAdapter(Activity mContext, List<StickyNote> notes){
        super(mContext,R.layout.list_item,notes);
        this.mContext = mContext;
        this.notes = notes;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = mContext.getLayoutInflater();
        View listItemView = inflater.inflate(R.layout.list_item,null,true);


        TextView titleName = listItemView.findViewById(R.id.titleName);
        StickyNote note = notes.get(position);

        titleName.setText(note.getDescription());

        return listItemView;
    }


}
