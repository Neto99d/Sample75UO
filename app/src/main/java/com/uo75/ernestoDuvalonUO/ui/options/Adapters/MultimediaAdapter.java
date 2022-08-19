package com.uo75.ernestoDuvalonUO.ui.options.Adapters;

import android.content.Context;
import android.text.util.Linkify;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ceylonlabs.imageviewpopup.ImagePopup;
import com.uo75.ernestoDuvalonUO.R;
import com.uo75.ernestoDuvalonUO.ui.modelsOdoo.Multimedia;

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
