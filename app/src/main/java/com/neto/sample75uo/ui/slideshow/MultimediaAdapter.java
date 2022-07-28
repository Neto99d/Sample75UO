package com.neto.sample75uo.ui.slideshow;

import android.content.Context;
import android.text.util.Linkify;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ceylonlabs.imageviewpopup.ImagePopup;
import com.neto.sample75uo.R;
import com.neto.sample75uo.ui.modelsOdoo.Multimedia;

import java.util.List;

public class MultimediaAdapter extends RecyclerView.Adapter<MultimediaAdapter.ViewHolder> {
    private List<Multimedia> multimedia;
    Context context;

    public MultimediaAdapter(List<Multimedia> multimedia, Context context) {
        this.multimedia = multimedia;
        this.context = context;
    }

    public List<Multimedia> getMultimedia() {
        return multimedia;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.rowlist_multimedia, parent, false);

        final ViewHolder viewHolder = new ViewHolder(vista);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Multimedia multimedia = this.multimedia.get(position);
        holder.getText().setText(multimedia.getUrl());
        Linkify.addLinks(holder.getText(), Linkify.WEB_URLS);
        holder.getImage().setImageBitmap(multimedia.getImage());
        final ImagePopup imagePopup = new ImagePopup(context);
        imagePopup.setWindowHeight(800); // Optional
        imagePopup.setWindowWidth(800); // Optional
        imagePopup.setFullScreen(true); // Optional
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
        return this.multimedia.size();
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
