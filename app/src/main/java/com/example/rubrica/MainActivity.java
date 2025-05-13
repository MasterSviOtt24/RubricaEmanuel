package com.example.rubrica;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.rubrica.databinding.ActivityMainBinding;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private static final Contatto contatto1 = new Contatto("Mario", "Bianchi", "3582756395");
    private static final Contatto contatto2 = new Contatto("Luca", "Rossi", "3683375693");
    private static final Contatto contatto3 = new Contatto("Alberto", "Neri", "3126745388");
    private static Contatto[] contatti = {contatto1, contatto2, contatto3};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        TextView[] textViews = {binding.contatto1, binding.contatto2, binding.contatto3};
        fillTextViews(textViews);

        binding.aggiungiContatto.setOnClickListener(v->{
            Intent intent = new Intent(this, AggiungiContatto.class);
            startActivity(intent);
        });

    }

    private void fillTextViews(TextView[] textViews){
        int i = 0;
        for(TextView tw : textViews){
            tw.setText(contatti[i].getNome()+" "+contatti[i].getCognome());
            int j = i;
            tw.setOnClickListener(v->{
                Intent intent = new Intent(this, DettagliContatto.class);
                intent.putExtra("contatto", contatti[j]);
                startActivity(intent);
            });
            i++;
        }
    }
}