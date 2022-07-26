package com.neto.sample75uo.ui.options.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.neto.sample75uo.R;
import com.neto.sample75uo.ui.modelsOdoo.Identidad;

import java.util.List;

public class IdentidadAdapter extends RecyclerView.Adapter<IdentidadAdapter.ViewHolder>{
    private List<Identidad> identidad;


    public IdentidadAdapter(List<Identidad> identidad) {
        this.identidad = identidad;
    }

    public List<Identidad> getIdentidad() {
        return identidad;
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
        Identidad identidad = this.identidad.get(position);
        holder.getText().setText(identidad.getContenido());
        holder.getImage().setImageBitmap(identidad.getImagen());
    }

    @Override
    public int getItemCount() {
        return this.identidad.size();
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
