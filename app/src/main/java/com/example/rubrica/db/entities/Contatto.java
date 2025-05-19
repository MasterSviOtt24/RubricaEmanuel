package com.example.rubrica.db.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class Contatto implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private long id;
    @ColumnInfo
    private String nome;
    @ColumnInfo
    private String cognome;
    @ColumnInfo
    private String numero;

    public Contatto(){}

    public Contatto(String nome, String cognome, String numero) {
        if(numero.length()!=10) {
            try {
                Integer.parseInt(numero);
            } catch (NumberFormatException e){
                return;
            }
        }
        this.numero = numero;
        this.nome = nome;
        this.cognome = cognome;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    @Override
    public String toString() {
        return "Contatto{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", cognome='" + cognome + '\'' +
                ", numero='" + numero + '\'' +
                '}';
    }
}
