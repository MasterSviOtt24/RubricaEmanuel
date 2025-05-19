package com.example.rubrica;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.rubrica.databinding.ActivityMainBinding;
import com.example.rubrica.db.DBManager;
import com.example.rubrica.db.entities.Contatto;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
//    private static final Contatto contatto1 = new Contatto("Mario", "Bianchi", "3582756395");
//    private static final Contatto contatto2 = new Contatto("Luca", "Rossi", "3683375693");
//    private static final Contatto contatto3 = new Contatto("Alberto", "Neri", "3126745388");

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

        getPageLayout();

    }

    @Override
    protected void onResume(){
        super.onResume();
        getPageLayout();
        showLastNumberCalled();
    }

    public void getPageLayout(){
        DBManager db = DBManager.getInstance(this);

        ContattoAdapter adapter = new ContattoAdapter(db.getContattoDAO().getAll());
        binding.myRecycler.setAdapter(adapter);
        LinearLayoutManager layout = new LinearLayoutManager(this);
        binding.myRecycler.setLayoutManager(layout);

        binding.aggiungiContatto.setOnClickListener(v->{
            Intent intent = new Intent(this, AggiungiContatto.class);
            startActivity(intent);
        });

        showLastNumberCalled();
    }

    public void showLastNumberCalled(){
        String lastNumberCalled = Preferences.leggiNumero(this);
        TextView lastNumberCalledTextView = binding.lastCall;
        if(lastNumberCalled != null){
            lastNumberCalledTextView.setText("Last number called: "+lastNumberCalled);
        }
    }

}