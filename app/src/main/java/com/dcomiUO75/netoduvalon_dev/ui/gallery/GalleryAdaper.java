package com.dcomiUO75.netoduvalon_dev.ui.gallery;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.glidebitmappool.GlideBitmapPool;
import com.dcomiUO75.netoduvalon_dev.R;
import com.dcomiUO75.netoduvalon_dev.ui.modelsOdoo.Postales;

import java.util.List;

public class GalleryAdaper extends RecyclerView.Adapter<GalleryAdaper.ViewHolder> {

    Context conntext;
    ImageView image;
    CardView imageFull;
    private List<Postales> postales;

    public GalleryAdaper(List<Postales> postales, Context conntext, ImageView image, CardView imageFull) {
        this.postales = postales;
        this.conntext = conntext;
        this.image = image;
        this.imageFull = imageFull;
    }

    public List<Postales> getPostales() {
        return postales;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_gallery, parent, false);

        final ViewHolder viewHolder = new ViewHolder(vista);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Postales postales = this.postales.get(position);
        RequestOptions reqOptions = new RequestOptions()
                .fitCenter()
                .override(800, 600);
        Glide.with(conntext)
                .applyDefaultRequestOptions(reqOptions)
                .load(postales.getImagen())
                .into(holder.getImage());
        GlideBitmapPool.clearMemory();
        holder.getImage().setOnClickListener(new View.OnClickListener() {
            @SuppressLint("WrongConstant")
            @Override
            public void onClick(View view) {
                if (image.getVisibility() == 8) { // 8 GONE
                    image.setImageBitmap(postales.getImagen());
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
        return this.postales.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView image;


        ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.imageGallery);


        }

        ImageView getImage() {
            return image;
        }

    }


}
