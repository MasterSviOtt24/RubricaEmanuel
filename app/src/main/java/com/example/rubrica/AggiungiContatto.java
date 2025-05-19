package com.example.rubrica;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.rubrica.databinding.ActivityAggiungiContattoBinding;
import com.example.rubrica.db.DBManager;
import com.example.rubrica.db.entities.Contatto;

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

        if (getIntent().getBooleanExtra("isModifica", false)) {
            binding.title.setText("Modifica contatto");
            binding.nome.setText(vecchioContatto.getNome());
            binding.cognome.setText(vecchioContatto.getCognome());
            binding.numero.setText((vecchioContatto.getNumero()));
        }

        binding.salva.setOnClickListener(v->{
            String nome = binding.nome.getText().toString();
            String cognome = binding.cognome.getText().toString();
            String numero = binding.numero.getText().toString();

            long idContatto = vecchioContatto.getId();

            if(!controllaValiditaNumero(nome,cognome,numero)) {
                numeroNonValido();
                return;
            };

            if (getIntent().getBooleanExtra("isModifica", false)) {
                Contatto contatto = db.getContattoDAO().getContattoById(idContatto);
                if(contatto==null) {
                    Log.e("Contatto", "Contatto null");
                    return;
                }
                contatto.setNome(nome);
                contatto.setCognome(cognome);
                contatto.setNumero(numero);
                Log.w("con2", contatto.toString());
                db.getContattoDAO().aggiornaContatto(contatto);
                return;
            }

            Contatto contatto = new Contatto(nome,cognome,numero);
            db.getContattoDAO().aggiungiContatto(contatto);
        });

    }

    private boolean controllaValiditaNumero(String nome,String cognome,String numero){
        if(nome.isBlank() || cognome.isBlank() || numero.isBlank()) return false;
        if(!numero.startsWith("3") || numero.length()!=10) return false;
        if (!numero.matches("\\d+")) return false;
        return true;
    }
    private boolean numeroNonValido(){
        Toast.makeText(this, "Contatto non aggiunto!", Toast.LENGTH_LONG).show();
        return false;
    }
}