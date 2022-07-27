package com.neto.sample75uo.ui.options.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.github.chrisbanes.photoview.PhotoView;
import com.neto.sample75uo.R;
import com.neto.sample75uo.ui.modelsOdoo.ProfeEmerito;

import java.util.List;

public class ProfeEmeritoAdapter extends RecyclerView.Adapter<ProfeEmeritoAdapter.ViewHolder>{
    private List<ProfeEmerito> proemerito;


    public ProfeEmeritoAdapter(List<ProfeEmerito> proemerito) {
        this.proemerito = proemerito;
    }

    public List<ProfeEmerito> getProfeEmerito() {
        return proemerito;
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
        ProfeEmerito proemerito = this.proemerito.get(position);
        holder.getText().setText(proemerito.getContenido());
        holder.getImage().setImageBitmap(proemerito.getImagen());
    }

    @Override
    public int getItemCount() {
        return this.proemerito.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView text;
        private PhotoView image;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            text = itemView.findViewById(R.id.textPatr);
            image = itemView.findViewById(R.id.imageProfile);


        }


        TextView getText() {
            return text;
        }

        PhotoView getImage() {
            return image;
        }
    }
}
