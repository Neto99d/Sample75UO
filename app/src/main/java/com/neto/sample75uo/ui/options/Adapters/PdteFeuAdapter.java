package com.neto.sample75uo.ui.options.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.neto.sample75uo.R;
import com.neto.sample75uo.ui.modelsOdoo.PdteFeu;

import java.util.List;

public class PdteFeuAdapter extends RecyclerView.Adapter<PdteFeuAdapter.ViewHolder>{
    private List<PdteFeu> pdtefeu;


    public PdteFeuAdapter(List<PdteFeu> pdtefeu) {
        this.pdtefeu = pdtefeu;
    }

    public List<PdteFeu> getPdteFeu() {
        return pdtefeu;
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
        PdteFeu pdtefeu = this.pdtefeu.get(position);
        holder.getText().setText(pdtefeu.getContenido());
        holder.getImage().setImageBitmap(pdtefeu.getImagen());
    }

    @Override
    public int getItemCount() {
        return this.pdtefeu.size();
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
