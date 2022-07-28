package com.neto.sample75uo.ui.options.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ceylonlabs.imageviewpopup.ImagePopup;
import com.neto.sample75uo.R;
import com.neto.sample75uo.ui.modelsOdoo.Efemerides;

import java.util.List;

public class EfemeridesAdapter extends RecyclerView.Adapter<EfemeridesAdapter.ViewHolder>{
    private List<Efemerides> efemerides;
    Context conntext;

    public EfemeridesAdapter(List<Efemerides> efemerides, Context conntext) {
        this.efemerides = efemerides;
        this.conntext = conntext;
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
        holder.getImage().setImageBitmap(efemerides.getImagen());
        final ImagePopup imagePopup = new ImagePopup(conntext);
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
