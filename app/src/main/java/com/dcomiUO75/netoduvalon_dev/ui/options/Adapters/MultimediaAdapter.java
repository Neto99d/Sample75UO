package com.dcomiUO75.netoduvalon_dev.ui.options.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dcomiUO75.netoduvalon_dev.R;
import com.dcomiUO75.netoduvalon_dev.ui.modelsOdoo.Multimedia;

import java.util.List;

import cn.jzvd.JzvdStd;

public class MultimediaAdapter extends RecyclerView.Adapter<MultimediaAdapter.ViewHolder> {
    Context context;
    private List<Multimedia> multimedia;

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
        holder.getvideo().setUp(multimedia.getUrl(), multimedia.getTitulo());
        holder.getvideo().posterImageView.setImageBitmap(multimedia.getImage());

    }

    @Override
    public int getItemCount() {
        return this.multimedia.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private JzvdStd video;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            video = itemView.findViewById(R.id.jz_video);

        }


        JzvdStd getvideo() {
            return video;
        }
    }

}
