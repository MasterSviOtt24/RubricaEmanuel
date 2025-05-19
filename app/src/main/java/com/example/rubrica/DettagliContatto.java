package com.example.rubrica;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.rubrica.databinding.ActivityDettagliContattoBinding;
import com.example.rubrica.db.entities.Contatto;

import org.jetbrains.annotations.NotNull;

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

        getPageLayout();

    }

    @Override
    protected void onResume(){
        super.onResume();
        getPageLayout();
    }

    void getPageLayout(){
        Contatto contatto = (Contatto) getIntent().getSerializableExtra("contatto");
        binding.textNumero.setText(contatto.getNumero());
        binding.titolo.setText(contatto.getNome()+" "+contatto.getCognome());

        binding.chiama.setOnClickListener(v->{
            if(ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE)== PackageManager.PERMISSION_GRANTED){
                String numero = binding.textNumero.getText().toString();
                Log.e("numeroz", ""+numero);
                Preferences.salvaNumero(numero, getApplicationContext());
                Intent intentChiamata = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+contatto.getNumero()));
                startActivity(intentChiamata);
            } else {
                requestPermissions(new String[]{Manifest.permission.CALL_PHONE}, 1);
            }
        });
        binding.messaggia.setOnClickListener(v->{
            if(ActivityCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED){
                Intent intentMessaggio = new Intent(Intent.ACTION_VIEW, Uri.parse("sms:"+contatto.getNumero()));
                startActivity(intentMessaggio);
            } else {
                requestPermissions(new String[]{Manifest.permission.SEND_SMS}, 2);
            }
        });
        binding.modifica.setOnClickListener(v->{
            Intent intent = new Intent(this, AggiungiContatto.class);
            intent.putExtra("isModifica", true);
            intent.putExtra("contatto", contatto);
            startActivity(intent);
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NotNull String[] permissions, @NotNull int[] grantResults, int deviceId){
        super.onRequestPermissionsResult(requestCode, permissions, grantResults, deviceId);

        Contatto contatto = (Contatto) getIntent().getSerializableExtra("contatto");
        binding.textNumero.setText(contatto.getNumero());

        if(requestCode==1){
            if(grantResults[0]==PackageManager.PERMISSION_GRANTED){
                String numero = binding.textNumero.getText().toString();
                Preferences.salvaNumero(numero, getApplicationContext());
                Intent intentChiamata = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+contatto.getNumero()));
                startActivity(intentChiamata);
            } else {
                Toast.makeText(this, "Devi concedere il permesso", Toast.LENGTH_LONG).show();
            }
        } else if (requestCode==2){
            if(grantResults[0]==PackageManager.PERMISSION_GRANTED){
                Intent intentMessaggio = new Intent(Intent.ACTION_VIEW, Uri.parse("sms:"+contatto.getNumero()));
                startActivity(intentMessaggio);
            } else {
                Toast.makeText(this, "Devi concedere il permesso", Toast.LENGTH_LONG).show();
            }
        }
    }

}