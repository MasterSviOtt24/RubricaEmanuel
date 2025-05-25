package com.example.rubrica;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.rubrica.databinding.ActivityAggiungiContattoBinding;
import com.example.rubrica.db.DBManager;
import com.example.rubrica.db.entities.Contatto;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.MaterialAutoCompleteTextView;
import com.google.android.material.textfield.TextInputLayout;

public class AggiungiContatto extends AppCompatActivity {

    private ActivityAggiungiContattoBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = ActivityAggiungiContattoBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        DBManager db = DBManager.getInstance(getApplicationContext());
        Contatto vecchioContatto = (Contatto) getIntent().getSerializableExtra("contatto");
        int idMainView = getIntent().getIntExtra("idMainView", 0);

        if (getIntent().getBooleanExtra("isModifica", false)) {
            binding.title.setText("Modifica contatto");
            binding.nome.getEditText().setText(vecchioContatto.getNome());
            binding.cognome.getEditText().setText(vecchioContatto.getCognome());
            binding.numero.getEditText().setText((vecchioContatto.getNumero()));
        }

        binding.salva.setOnClickListener(v->{
            String nome = binding.nome.getEditText().getText().toString();
            String cognome = binding.cognome.getEditText().getText().toString();
            String numero = binding.numero.getEditText().getText().toString();

            long idContatto = 0;
            try{
                idContatto = vecchioContatto.getId();
            } catch (NullPointerException e) {
                Log.e("debug", "NullPointer");
            }

            if(!controllaValiditaNumero(nome,cognome,numero)) {
                numeroNonValido();
                Log.e("debug", "Numero non valido");
                return;
            };

            if (getIntent().getBooleanExtra("isModifica", false)&&idContatto!=0) {
                Contatto contatto = db.getContattoDAO().getContattoById(idContatto);

                contatto.setNome(nome);
                contatto.setCognome(cognome);
                contatto.setNumero(numero);
                db.getContattoDAO().aggiornaContatto(contatto);
                Toast.makeText(this, "Contatto aggiornato", Toast.LENGTH_LONG).show();
                startActivity(new Intent(this, MainActivity.class));
                return;
            }

            Contatto contatto = new Contatto(nome,cognome,numero);
            db.getContattoDAO().aggiungiContatto(contatto);
//            View mainView = findViewById(idMainView);
//            Snackbar.make(mainView, "Contatto aggiunto", Snackbar.LENGTH_LONG).show();
            startActivity(new Intent(this, MainActivity.class));
        });

    }

    private boolean controllaValiditaNumero(String nome,String cognome,String numero){
        if(nome.isBlank() || cognome.isBlank() || numero.isBlank()) return false;
        if(!numero.startsWith("3") || numero.length()!=10 || nome.length()>20 || cognome.length()>20) return false;
        if (!numero.matches("\\d+")) return false;
        return true;
    }
    private boolean numeroNonValido(){
        Snackbar.make(binding.main, "Contatto non aggiunto", Snackbar.LENGTH_LONG).show();
        return false;
    }
}