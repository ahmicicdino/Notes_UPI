package com.example.notes_upi;

import androidx.cardview.widget.CardView;

import com.example.notes_upi.Models.Notes;

public interface NotesClickListener {
    void onClick(Notes notes);
    void onLongClick(Notes notes, CardView cardView);
}
