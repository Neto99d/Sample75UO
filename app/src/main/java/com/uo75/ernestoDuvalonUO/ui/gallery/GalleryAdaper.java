package com.uo75.ernestoDuvalonUO.ui.gallery;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.ceylonlabs.imageviewpopup.ImagePopup;
import com.uo75.ernestoDuvalonUO.R;
import com.uo75.ernestoDuvalonUO.ui.modelsOdoo.Postales;

import java.io.ByteArrayOutputStream;
import java.util.List;

public class GalleryAdaper extends RecyclerView.Adapter<GalleryAdaper.ViewHolder> {

    private List<Postales> postales;
    Context conntext;
    ImageView image;

    public GalleryAdaper(List<Postales> postales, Context conntext, ImageView image) {
        this.postales = postales;
        this.conntext = conntext;
        this.image = image;
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

        Glide.with(conntext)
                .load(postales.getImagen())
                .into(holder.getImage());
        /*holder.getImage().getController().getSettings()
                .setMaxZoom(2f)
                .setDoubleTapZoom(-1f) // Falls back to max zoom level
                .setPanEnabled(true)
                .setZoomEnabled(true)
                .setDoubleTapEnabled(true)
                .setRotationEnabled(false)
                .setRestrictRotation(false)
                .setOverscrollDistance(0f, 0f)
                .setOverzoomFactor(2f)
                .setFillViewport(false)
                .setFitMethod(Settings.Fit.INSIDE)
                .setGravity(Gravity.CENTER);*/
        /*final ImagePopup imagePopup = new ImagePopup(conntext);
        imagePopup.setWindowHeight(400); // Optional
        imagePopup.setWindowWidth(320); // Optional
        imagePopup.setFullScreen(false); // Optional
        imagePopup.setScaleType(ImageView.ScaleType.FIT_XY);
        imagePopup.setHideCloseIcon(true);  // Optional
        imagePopup.setImageOnClickClose(true);  // Optional*/


        holder.getImage().setOnClickListener(new View.OnClickListener() {
            @SuppressLint("WrongConstant")
            @Override
            public void onClick(View view) {
                if (image.getVisibility() == 8) { // 8 GONE
                    image.setImageBitmap(postales.getImagen());
                    image.setVisibility(View.VISIBLE);
                }

                else if(image.getVisibility() == 0){ // 0 VISIBLE
                    image.setVisibility(View.GONE);
                    image.setImageBitmap(null);
                }
                /** Initiate Popup view **/
                /*holder.getImage().setImageBitmap(postales.getImagen());
                imagePopup.initiatePopup(holder.getImage().getDrawable()); // Load Image from Drawable
                imagePopup.viewPopup();*/

            }
        });


        image.setOnClickListener(new View.OnClickListener() {
            @Override
            @SuppressLint("WrongConstant")
            public void onClick(View view) {
                if(image.getVisibility() == 0){ // 0 VISIBLE
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
