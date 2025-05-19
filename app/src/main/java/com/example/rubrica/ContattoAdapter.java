package com.example.rubrica;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rubrica.db.entities.Contatto;

import java.util.List;

public class ContattoAdapter extends RecyclerView.Adapter<ContattoViewHolder> {

    private List<Contatto> contatti;

    public ContattoAdapter(List<Contatto> contatti){
        this.contatti = contatti;
    }

    @NonNull
    @Override
    public ContattoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_contatto,parent, false);
        return new ContattoViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ContattoViewHolder holder, int position) {
        holder.setContatto(contatti.get(position));
    }

    @Override
    public int getItemCount() {
        return contatti.size();
    }

}
