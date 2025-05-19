package com.example.rubrica;

import android.content.Intent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rubrica.databinding.RowContattoBinding;
import com.example.rubrica.db.entities.Contatto;

public class ContattoViewHolder extends RecyclerView.ViewHolder {

    Contatto contatto;
    private RowContattoBinding binding;
    public ContattoViewHolder(@NonNull View itemView) {
        super(itemView);
        binding = RowContattoBinding.bind(itemView);
    }

    public void setContatto(Contatto contatto){
        this.contatto = contatto;
        binding.nomeContatto.setText(contatto.getNome()+" "+contatto.getCognome());

        itemView.setOnClickListener(v->{
            Intent intent = new Intent(itemView.getContext(), DettagliContatto.class);
            intent.putExtra("contatto", contatto);
            itemView.getContext().startActivity(intent);
        });
    }

}
