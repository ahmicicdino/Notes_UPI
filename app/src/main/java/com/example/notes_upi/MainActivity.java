package com.example.notes_upi;

//import static com.example.notes_upi.R.id.pin;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.example.notes_upi.Adapters.NotesListAdapter;
import com.example.notes_upi.Database.RoomDB;
import com.example.notes_upi.Models.Notes;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener{

    RecyclerView recyclerView;
    NotesListAdapter notesListAdapter;
    List<Notes> notes= new ArrayList<>();
    RoomDB database;
    FloatingActionButton fab_add;

    Notes selectedNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recycler_home);
        fab_add = findViewById(R.id.fab_add);

        database = RoomDB.getInstance(this);
        notes = database.mainDAO().getAll();

        updateRecycler(notes);

        fab_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, NotesTakerActivity.class);
                startActivityForResult(intent, 101);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {       //Metoda koja upisuje nove bilješke u bazu
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==101){                                   //Ako je pitanje o novoj bilješci
            if(resultCode == Activity.RESULT_OK){
                Notes new_notes = (Notes) data.getSerializableExtra("note");
                database.mainDAO().insert(new_notes);           //Unosi se nova bilješka unutar baze podataka
                notes.clear();
                notes.addAll(database.mainDAO().getAll());

                notesListAdapter.notifyDataSetChanged();
            }
        }
        else if(requestCode == 102){                                //Ako se odnosi na ažuriranje već postojeće bilješke
            if(resultCode==Activity.RESULT_OK){
                Notes new_notes = (Notes) data.getSerializableExtra("note");
                database.mainDAO().update(new_notes.getID(), new_notes.getTitle(), new_notes.getNotes());       //Ažuriraj bilješku prema ID
                notes.clear();
                notes.addAll(database.mainDAO().getAll());
                notesListAdapter.notifyDataSetChanged();
            }
        }
    }

    private void updateRecycler(List<Notes> notes) {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL));
        notesListAdapter = new NotesListAdapter(MainActivity.this, notes, notesClickListener);
        recyclerView.setAdapter(notesListAdapter);
    }

    private final NotesClickListener notesClickListener = new NotesClickListener() {
        @Override
        public void onClick(Notes notes) {
            Intent intent = new Intent(MainActivity.this, NotesTakerActivity.class);
            intent.putExtra("old_note", notes);
            startActivityForResult(intent, 102);
        }

        @Override
        public void onLongClick(Notes notes, CardView cardView) {           // Podiže se ova metoda ako korisnik izvede jekdan long click na bilješku
            selectedNote = new Notes();
            selectedNote = notes;
            showPopup(cardView);                                            //poziva se showpopup metoda
        }
    };

    private void showPopup(CardView cardView) {                         //Nakon long click na bilješku, podiže se ova metoda koja ponudi korisniku opciju brisanja bilješke
        PopupMenu popupMenu = new PopupMenu(this, cardView);
        popupMenu.setOnMenuItemClickListener(this);
        popupMenu.inflate(R.menu.popup_menu);
        popupMenu.show();
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {             // Provjerava da li je odabrana metoda za brisanje bilješke

        int itemId = item.getItemId();

        if (itemId == R.id.delete) {
            database.mainDAO().delete(selectedNote);
            notes.remove(selectedNote);
            notesListAdapter.notifyDataSetChanged();
            Toast.makeText(MainActivity.this, "Bilješka obrisana", Toast.LENGTH_SHORT).show();      //poruka nakon brisanja poruke
            return true;
        }
        return false;
    }
}