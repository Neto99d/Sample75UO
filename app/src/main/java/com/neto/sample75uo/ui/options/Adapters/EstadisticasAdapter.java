package com.neto.sample75uo.ui.options.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.neto.sample75uo.R;
import com.neto.sample75uo.ui.modelsOdoo.AvisoEspecial;
import com.neto.sample75uo.ui.modelsOdoo.Estadisticas;

import java.util.List;

public class EstadisticasAdapter extends RecyclerView.Adapter<EstadisticasAdapter.ViewHolder> {

    private List<Estadisticas> estadisticas;


    public EstadisticasAdapter(List<Estadisticas> estadisticas) {
        this.estadisticas = estadisticas;
    }

    public List<Estadisticas> getEstadisticas() {
        return estadisticas;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.rowlist_estadisticas, parent, false);

        final ViewHolder viewHolder = new ViewHolder(vista);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull EstadisticasAdapter.ViewHolder holder, int position) {
        Estadisticas estadisticas = this.estadisticas.get(position);
        holder.getText().setText(estadisticas.getName());
        holder.getTextCant().setText(estadisticas.getCantidad());
    }

    @Override
    public int getItemCount() {
        return this.estadisticas.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView text;
        private TextView textCant;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            text = itemView.findViewById(R.id.textEstadistica);
            textCant = itemView.findViewById(R.id.textEstadCantidad);

        }

        TextView getText() {
            return text;
        }

        TextView getTextCant() {
            return textCant;
        }
    }
}
