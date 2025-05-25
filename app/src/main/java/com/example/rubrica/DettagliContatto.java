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
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.rubrica.databinding.ActivityDettagliContattoBinding;
import com.example.rubrica.db.DBManager;
import com.example.rubrica.db.entities.Contatto;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.snackbar.Snackbar;

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
        binding.textNumero.setText("Numero: +39 " + contatto.getNumero());
        binding.toolBar.setText(contatto.getNome()+" "+contatto.getCognome());

        binding.chiama.setOnClickListener(v->{
            if(ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE)== PackageManager.PERMISSION_GRANTED){
                String numero = binding.textNumero.getText().toString().substring(12);
                Preferences.salvaNumero(numero, getApplicationContext());
                Intent intentChiamata = new Intent(Intent.ACTION_CALL, Uri.parse("tel: +39"+contatto.getNumero()));
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
        binding.elimina.setOnClickListener(v->{
            new MaterialAlertDialogBuilder(this)
                    .setMessage("Sei sicuro di voler eliminare "+contatto.getNome()+" "+contatto.getCognome()+" dai contatti?")
                    .setCancelable(false)
                    .setNeutralButton("Annulla", (dialog, position) -> {
                        return;
                    })
                    .setNegativeButton("Elimina", (dialog, position) -> {
                        DBManager.getInstance(getApplicationContext()).getContattoDAO().eliminaContatto(contatto);
                        Toast.makeText(getApplicationContext(), "Contatto eliminato", Toast.LENGTH_LONG).show();
                        startActivity(new Intent(this, MainActivity.class));
                    })
                    .show();
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NotNull String[] permissions, @NotNull int[] grantResults, int deviceId){
        super.onRequestPermissionsResult(requestCode, permissions, grantResults, deviceId);

        Contatto contatto = (Contatto) getIntent().getSerializableExtra("contatto");
        binding.textNumero.setText(contatto.getNumero());
        ConstraintLayout main = findViewById(R.id.main);

        if(requestCode==1){
            if(grantResults[0]==PackageManager.PERMISSION_GRANTED){
                String numero = binding.textNumero.getText().toString();
                Preferences.salvaNumero(numero, getApplicationContext());
                Intent intentChiamata = new Intent(Intent.ACTION_CALL, Uri.parse("tel: +39"+contatto.getNumero()));
                startActivity(intentChiamata);
            } else {
                Snackbar.make(main, "Devi concedere il permesso", Snackbar.LENGTH_LONG).setAction("Concedi", v->{
                    requestPermissions(new String[]{Manifest.permission.CALL_PHONE}, 1);
                }).show();
            }
        } else if (requestCode==2){
            if(grantResults[0]==PackageManager.PERMISSION_GRANTED){
                Intent intentMessaggio = new Intent(Intent.ACTION_VIEW, Uri.parse("sms:"+contatto.getNumero()));
                startActivity(intentMessaggio);
            } else {
                Snackbar.make(main, "Devi concedere il permesso", Snackbar.LENGTH_LONG).setAction("Concedi", v->{
                    requestPermissions(new String[]{Manifest.permission.SEND_SMS}, 2);
                }).show();
            }
        }
    }

}