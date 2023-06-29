package com.example.notes_upi.Database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.notes_upi.Models.Notes;

@Database(entities = Notes.class, version = 1, exportSchema = false)                //Moramo dodati entitete, nama su to bilješke
public abstract class RoomDB extends RoomDatabase {                                 //Definiramo klasu DB kao abstraktnu
    private static RoomDB database;                                                 //deklariranje
    private static String DATABASE_NAME = "NoteApp";                                //deklariranje imena baze podataka

    public synchronized static RoomDB getInstance(Context context){                 //inicijalizacija instance baze podataka
        if(database == null){                                                       // ako DB ne postoji, moramo ju izraditi
            database = Room.databaseBuilder(context.getApplicationContext(),
                    RoomDB.class, DATABASE_NAME)
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return database;                                                            //Ako baza već postoji, vrati instancu baze
    }
    public abstract MainDAO mainDAO();
}
