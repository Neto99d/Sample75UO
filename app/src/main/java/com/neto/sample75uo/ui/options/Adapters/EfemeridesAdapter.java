package com.neto.sample75uo.ui.options.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.github.chrisbanes.photoview.PhotoView;
import com.neto.sample75uo.R;
import com.neto.sample75uo.ui.modelsOdoo.Efemerides;

import java.util.List;

public class EfemeridesAdapter extends RecyclerView.Adapter<EfemeridesAdapter.ViewHolder>{
    private List<Efemerides> efemerides;


    public EfemeridesAdapter(List<Efemerides> efemerides) {
        this.efemerides = efemerides;
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
    }

    @Override
    public int getItemCount() {
        return this.efemerides.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView text;
        private TextView textFecha;
        private PhotoView image;

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

        PhotoView getImage() {
            return image;
        }
    }
}
