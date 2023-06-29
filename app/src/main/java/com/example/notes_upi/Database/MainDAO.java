package com.example.notes_upi.Database;

import static androidx.room.OnConflictStrategy.REPLACE;

import android.provider.ContactsContract;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.notes_upi.Models.Notes;

import java.util.List;

@Dao
public interface MainDAO {

    @Insert(onConflict = REPLACE)
    void insert(Notes notes);               // metoda koja unosi našu bilješku u bazu

    @Query("SELECT * FROM notes ORDER BY id DESC")          //SQL naredba za dohvaćanje svih podataka iz baze
    List<Notes> getAll();                                   // metoda za dohvaćanje svih podataka iz baze

    @Query("UPDATE notes SET title = :title, notes= :notes WHERE ID = :id")         //SQL naredba za ažuriranje bilješke unutar baze
    void update(int id, String title, String notes);                                      //metoda za ažuriranje podataka unutar baze

    @Delete
    void delete(Notes notes);                                                       // metoda za brisanje određenje bilješke


    @Query("UPDATE notes SET pinned = :pin WHERE ID = :id")
    void pin(int id, boolean pin);                                              //metoda koda prikvači bilješku
}
