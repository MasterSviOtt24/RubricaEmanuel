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

        if (getIntent().getBooleanExtra("isModifica", false)) {
            binding.title.setText("Modifica contatto");
            binding.numero.setText(getIntent().getStringExtra("numero"));
        }

        binding.salva.setOnClickListener(v->{
            String nome = binding.nome.getText().toString();
            String cognome = binding.cognome.getText().toString();
            String numero = binding.numero.getText().toString();

            if(controllaValiditaNumero(nome,cognome,numero)) aggiungiContatto(nome,cognome,numero);

        });

    }

    private void aggiungiContatto(String nome,String cognome,String numero){
        Contatto contatto = new Contatto(nome,cognome,numero);
        Toast.makeText(this, "Contatto aggiunto!", Toast.LENGTH_LONG).show();
        Log.e("Contatto: ", contatto.getNome()+" "+contatto.getCognome());
        // Altro codice per db
    }

    private boolean controllaValiditaNumero(String nome,String cognome,String numero){
        if(nome.isBlank() || cognome.isBlank() || numero.isBlank()) numeroNonValido();
        if(!numero.startsWith("3") || numero.length()!=10) numeroNonValido();
        if (!numero.matches("\\d+")) numeroNonValido();
        return true;
    }
    private boolean numeroNonValido(){
        Toast.makeText(this, "Contatto non aggiunto!", Toast.LENGTH_LONG).show();
        return false;
    }
}