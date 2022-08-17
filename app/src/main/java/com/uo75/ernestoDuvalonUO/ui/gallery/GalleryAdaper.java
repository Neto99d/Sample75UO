package com.uo75.ernestoDuvalonUO.ui.gallery;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alexvasilkov.gestures.Settings;
import com.alexvasilkov.gestures.views.GestureImageView;
import com.ceylonlabs.imageviewpopup.ImagePopup;
import com.uo75.ernestoDuvalonUO.R;
import com.uo75.ernestoDuvalonUO.ui.modelsOdoo.Postales;

import java.util.List;

public class GalleryAdaper extends RecyclerView.Adapter<GalleryAdaper.ViewHolder> {

    private List<Postales> postales;
    Context conntext;
    public GalleryAdaper(List<Postales> postales, Context conntext) {
        this.postales = postales;
        this.conntext = conntext;
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
        holder.getImage().setImageBitmap(postales.getImagen());
        holder.getImage().getController().getSettings()
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
                .setGravity(Gravity.CENTER);
        final ImagePopup imagePopup = new ImagePopup(conntext);
        imagePopup.setWindowHeight(850); // Optional
        imagePopup.setWindowWidth(1024); // Optional
        imagePopup.setFullScreen(false); // Optional
        imagePopup.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imagePopup.setHideCloseIcon(true);  // Optional
        imagePopup.setImageOnClickClose(true);  // Optional


        imagePopup.initiatePopup(holder.getImage().getDrawable()); // Load Image from Drawable


        holder.getImage().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /** Initiate Popup view **/
                imagePopup.viewPopup();
            }
        });

    }

    @Override
    public int getItemCount() {
        return this.postales.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private GestureImageView image;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.imageGallery);


        }

        GestureImageView getImage() {
            return image;
        }
    }
}
