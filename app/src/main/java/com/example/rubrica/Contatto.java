package com.example.rubrica;

import java.io.Serializable;

public class Contatto implements Serializable {

    private String nome;
    private String cognome;
    private String numero;

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
}
