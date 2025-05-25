package com.example.rubrica;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.rubrica.databinding.ActivityMainBinding;
import com.example.rubrica.db.DBManager;
import com.example.rubrica.db.entities.Contatto;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

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

        MaterialToolbar toolbar = binding.topAppBar;
        toolbar.setOnClickListener(v->{
            Toast.makeText(this, "Ciao :)", Toast.LENGTH_SHORT).show();
        });

        toolbar.setOnMenuItemClickListener(item->{
            if(item.getItemId()==R.id.impostazioni){
                startActivity(new Intent(Settings.ACTION_SETTINGS));
            }
            return true;
        });

        toolbar.setNavigationOnClickListener(v->{
            Intent intent = new Intent(this, AggiungiContatto.class);
            intent.putExtra("idMainView", binding.main.getId());
            startActivity(intent);
        });

        TextView myTextView1 = binding.lastCall;
        registerForContextMenu(myTextView1);

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo){
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.my_menu2, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item){
        if(item.getItemId()==R.id.oo1){
            Toast.makeText(this, "oo1", Toast.LENGTH_SHORT).show();
            return true;
        } else if(item.getItemId() == R.id.oo2){
            Toast.makeText(this, "oo2", Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.onContextItemSelected(item);
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

//        binding.aggiungiContatto.setOnClickListener(v->{
//            Intent intent = new Intent(this, AggiungiContatto.class);
//            startActivity(intent);
//        });

        showLastNumberCalled();
    }

    public void showLastNumberCalled(){
        String lastNumberCalled = Preferences.leggiNumero(this);
        TextView lastNumberCalledTextView = binding.lastCall;
        if(lastNumberCalled != null){
            lastNumberCalledTextView.setText("Ultimo numero chiamato: +39 "+lastNumberCalled);
        }
    }

}