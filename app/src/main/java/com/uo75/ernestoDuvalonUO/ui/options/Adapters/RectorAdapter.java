package com.uo75.ernestoDuvalonUO.ui.options.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ceylonlabs.imageviewpopup.ImagePopup;
import com.uo75.ernestoDuvalonUO.R;
import com.uo75.ernestoDuvalonUO.ui.modelsOdoo.Rectores;

import java.util.List;

public class RectorAdapter extends RecyclerView.Adapter<RectorAdapter.ViewHolder>{
    private List<Rectores> rectores;
    Context conntext;

    public RectorAdapter(List<Rectores> rectores, Context conntext) {
        this.rectores = rectores;
        this.conntext = conntext;
    }

    public List<Rectores> getRectores() {
        return rectores;
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
        Rectores rectores = this.rectores.get(position);
        holder.getText().setText(rectores.getContenido());
        holder.getImage().setImageBitmap(rectores.getImagen());
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
        return this.rectores.size();
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
