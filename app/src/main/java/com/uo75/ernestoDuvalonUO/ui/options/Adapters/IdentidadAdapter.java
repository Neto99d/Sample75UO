package com.uo75.ernestoDuvalonUO.ui.options.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.uo75.ernestoDuvalonUO.R;
import com.uo75.ernestoDuvalonUO.ui.modelsOdoo.Identidad;

import java.util.List;

public class IdentidadAdapter extends RecyclerView.Adapter<IdentidadAdapter.ViewHolder> {
    private List<Identidad> identidad;
    Context conntext;
    ImageView image;
    CardView imageFull;

    public IdentidadAdapter(List<Identidad> identidad, Context conntext, ImageView image, CardView imageFull) {
        this.identidad = identidad;
        this.conntext = conntext;
        this.image = image;
        this.imageFull = imageFull;
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
        Glide.with(conntext)
                .load(identidad.getImagen())
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .into(holder.getImage());

        holder.getImage().setOnClickListener(new View.OnClickListener() {
            @SuppressLint("WrongConstant")
            @Override
            public void onClick(View view) {
                if (image.getVisibility() == 8) { // 8 GONE
                    image.setImageBitmap(identidad.getImagen());
                    imageFull.setVisibility(View.VISIBLE);
                    image.setVisibility(View.VISIBLE);
                } else if (image.getVisibility() == 0) { // 0 VISIBLE
                    imageFull.setVisibility(View.GONE);
                    image.setVisibility(View.GONE);
                    image.setImageBitmap(null);
                }
            }
        });


        image.setOnClickListener(new View.OnClickListener() {
            @Override
            @SuppressLint("WrongConstant")
            public void onClick(View view) {
                if (image.getVisibility() == 0) { // 0 VISIBLE
                    imageFull.setVisibility(View.GONE);
                    image.setVisibility(View.GONE);
                    image.setImageBitmap(null);
                }
            }
        });
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
