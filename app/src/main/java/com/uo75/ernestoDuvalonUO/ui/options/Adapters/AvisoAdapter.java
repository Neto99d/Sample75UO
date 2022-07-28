package com.uo75.ernestoDuvalonUO.ui.options.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.uo75.ernestoDuvalonUO.R;
import com.uo75.ernestoDuvalonUO.ui.modelsOdoo.AvisoEspecial;

import java.util.List;

public class AvisoAdapter extends RecyclerView.Adapter<AvisoAdapter.ViewHolder>{
    private List<AvisoEspecial> aviso;


    public AvisoAdapter(List<AvisoEspecial> aviso) {
        this.aviso = aviso;
    }

    public List<AvisoEspecial> getAviso() {
        return aviso;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.rowlist_avisos_especiales, parent, false);

        final ViewHolder viewHolder = new ViewHolder(vista);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        AvisoEspecial aviso = this.aviso.get(position);
        holder.getText().setText(aviso.getContenido());
        holder.getTextFecha().setText(aviso.getFecha());
    }

    @Override
    public int getItemCount() {
        return this.aviso.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView text;
        private TextView textFecha;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            text = itemView.findViewById(R.id.textAviso);
            textFecha = itemView.findViewById(R.id.textAvisoFecha);

        }

        TextView getText() {
            return text;
        }

        TextView getTextFecha() {
            return textFecha;
        }
    }
}
