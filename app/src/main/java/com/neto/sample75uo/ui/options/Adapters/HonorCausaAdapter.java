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
import com.neto.sample75uo.ui.modelsOdoo.HonorCausa;

import java.util.List;

public class HonorCausaAdapter extends RecyclerView.Adapter<HonorCausaAdapter.ViewHolder>{
    private List<HonorCausa> honor;


    public HonorCausaAdapter(List<HonorCausa> honor) {
        this.honor = honor;
    }

    public List<HonorCausa> getHonorCausa() {
        return honor;
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
        HonorCausa honor = this.honor.get(position);
        holder.getText().setText(honor.getContenido());
        holder.getImage().setImageBitmap(honor.getImagen());
    }

    @Override
    public int getItemCount() {
        return this.honor.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView text;
        private PhotoView image;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            text = itemView.findViewById(R.id.textPatr);
            image = itemView.findViewById(R.id.imageProfile);


        }


        TextView getText() {
            return text;
        }

        PhotoView getImage() {
            return image;
        }
    }
}
