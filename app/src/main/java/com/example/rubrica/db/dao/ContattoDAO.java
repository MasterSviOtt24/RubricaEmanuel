package com.example.rubrica.db.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.rubrica.db.entities.Contatto;

import java.util.List;

@Dao
public interface ContattoDAO {

    @Insert
    long aggiungiContatto(Contatto c);
    @Update
    void aggiornaContatto(Contatto c);
    @Delete
    void eliminaContatto(Contatto c);
    @Query("SELECT * from Contatto")
    List<Contatto> getAll();
    @Query("Delete from Contatto")
    void deleteAll();
    @Query("SELECT * From Contatto WHERE id = :id")
    Contatto getContattoById(long id);

}
