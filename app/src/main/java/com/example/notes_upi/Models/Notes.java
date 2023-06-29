package com.example.notes_upi.Models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName="notes")          //Ime tablice koju ćemo koristiti

public class Notes implements Serializable {

    @PrimaryKey(autoGenerate = true)        // Primarni ključ za naše bilješke, samogenerirajući za svaki novu biljeršku
    int ID = 0;

    @ColumnInfo(name = "title")             // Naslov svake naše bilješke
    String title = "";

    @ColumnInfo(name = "notes")             // Glavni tekst svake naše bilješke
    String notes = "";

    @ColumnInfo(name = "date")              // Datum izrade svake naše bilješke
    String date = "";

    @ColumnInfo(name = "pinned")            // Boolean vrijednost da li je biljkeška prikvačena (nije implementirana u aplikaciji)
    boolean pinned = false;

    public int getID() {
        return ID;
    }           // getter metoda za naš primarni ključ

    public void setID(int ID) {
        this.ID = ID;
    }       //setter metoda za naš primarni ključ

    public String getTitle() {
        return title;
    }       // getter metoda za naslov bilješke

    public void setTitle(String title) {
        this.title = title;                             //setter metoda za naslov bilješke
    }

    public String getNotes() {
        return notes;
    }           //getter metoda za glavni tekst bilješke

    public void setNotes(String notes) {
        this.notes = notes;
    }           // setter metoda za glavni tekst naše bilješke

    public String getDate() {
        return date;
    }       // getter metoda za datum izrade bilješke

    public void setDate(String date) {
        this.date = date;
    }       // setter metoda za datum

    public boolean isPinned() {
        return pinned;
    }               // getter metoda za prikvačene bilješke

    public void setPinned(boolean pinned) {
        this.pinned = pinned;
    }       // setter metoda za prikvačene bilješke
}
