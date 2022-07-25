package com.neto.sample75uo.ui.options.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.neto.sample75uo.R;
import com.neto.sample75uo.ui.modelsOdoo.Patrimonio;

import java.util.List;

public class PatrimonioAdapter extends RecyclerView.Adapter<PatrimonioAdapter.ViewHolder>{

    private List<Patrimonio> patrimonio;


    public PatrimonioAdapter(List<Patrimonio> patrimonio) {
        this.patrimonio = patrimonio;
    }

    public List<Patrimonio> getPatrimonio() {
        return patrimonio;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.rowlist_gen, parent, false);

        final ViewHolder viewHolder = new ViewHolder(vista);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Patrimonio patrimonio = this.patrimonio.get(position);
        holder.getText().setText(patrimonio.getContenido());
        holder.getImage().setImageBitmap(patrimonio.getImagen());
    }

    @Override
    public int getItemCount() {
        return this.patrimonio.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView text;
        private ImageView image;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            text = itemView.findViewById(R.id.textPatr);
            image = itemView.findViewById(R.id.imageProfile);


        }

        TextView getText() {
            return text;
        }

        ImageView getImage() {
            return image;
        }
    }
}
