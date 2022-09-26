package com.dcomiUO75.netoduvalon_dev.ui.options.Adapters;

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
import com.dcomiUO75.netoduvalon_dev.R;
import com.dcomiUO75.netoduvalon_dev.ui.modelsOdoo.Efemerides;

import java.util.List;

public class EfemeridesAdapter extends RecyclerView.Adapter<EfemeridesAdapter.ViewHolder> {
    Context conntext;
    ImageView image;
    CardView imageFull;
    private List<Efemerides> efemerides;

    public EfemeridesAdapter(List<Efemerides> efemerides, Context conntext, ImageView image, CardView imageFull) {
        this.efemerides = efemerides;
        this.conntext = conntext;
        this.image = image;
        this.imageFull = imageFull;
    }

    public List<Efemerides> getEfemerides() {
        return efemerides;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.rowlist_efemerides, parent, false);

        final ViewHolder viewHolder = new ViewHolder(vista);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Efemerides efemerides = this.efemerides.get(position);
        holder.getText().setText(efemerides.getContenido());
        holder.getTextFecha().setText(efemerides.getFecha());
        Glide.with(conntext)
                .load(efemerides.getImagen())
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .into(holder.getImage());

        holder.getImage().setOnClickListener(new View.OnClickListener() {
            @SuppressLint("WrongConstant")
            @Override
            public void onClick(View view) {
                if (image.getVisibility() == 8) { // 8 GONE
                    image.setImageBitmap(efemerides.getImagen());
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
        return this.efemerides.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView text;
        private TextView textFecha;
        private ImageView image;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            text = itemView.findViewById(R.id.textEfemeride);
            textFecha = itemView.findViewById(R.id.textViewFecha);
            image = itemView.findViewById(R.id.imageEfemeride);

        }

        TextView getText() {
            return text;
        }

        TextView getTextFecha() {
            return textFecha;
        }

        ImageView getImage() {
            return image;
        }
    }
}
