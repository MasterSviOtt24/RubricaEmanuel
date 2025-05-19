package com.example.rubrica.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.rubrica.db.dao.ContattoDAO;
import com.example.rubrica.db.entities.Contatto;

@Database(entities = {Contatto.class}, version = 1)
public abstract class DBManager extends RoomDatabase {

    private static DBManager instance;

    public static DBManager getInstance(Context c){
        if(instance == null){
            instance = Room.databaseBuilder(c, DBManager.class, "rubrica_db").allowMainThreadQueries().build();
        }
        return instance;
    }

    public abstract ContattoDAO getContattoDAO();

}
