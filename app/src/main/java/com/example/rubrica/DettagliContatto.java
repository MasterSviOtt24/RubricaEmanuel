package com.example.rubrica;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.rubrica.databinding.ActivityDettagliContattoBinding;
import com.example.rubrica.databinding.ActivityMainBinding;

public class DettagliContatto extends AppCompatActivity {

    private ActivityDettagliContattoBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = ActivityDettagliContattoBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Contatto contatto = (Contatto) getIntent().getSerializableExtra("contatto");
        binding.textNumero.setText(contatto.getNumero());

        binding.chiama.setOnClickListener(v->{
            Intent intentChiamata = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+contatto.getNumero()));
            startActivity(intentChiamata);
        });
        binding.messaggia.setOnClickListener(v->{
            Intent intentMessaggio = new Intent(Intent.ACTION_VIEW, Uri.parse("sms:"+contatto.getNumero()));
            startActivity(intentMessaggio);
        });
        binding.modifica.setOnClickListener(v->{
            Intent intent = new Intent(this, AggiungiContatto.class);
            intent.putExtra("isModifica", true);
            intent.putExtra("numero", contatto.getNumero());
            startActivity(intent);
        });

    }
}