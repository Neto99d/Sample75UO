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
import com.neto.sample75uo.ui.modelsOdoo.Campaña;

import java.util.List;

public class CampannaAdapter extends RecyclerView.Adapter<CampannaAdapter.ViewHolder>{

    private List<Campaña> campaña;


    public CampannaAdapter(List<Campaña> campaña) {
        this.campaña = campaña;
    }

    public List<Campaña> getcampaña() {
        return campaña;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.rowlist_gen, parent, false);

        final ViewHolder viewHolder = new ViewHolder(vista);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CampannaAdapter.ViewHolder holder, int position) {
        Campaña campaña = this.campaña.get(position);
        holder.getText().setText(campaña.getContenido());
        holder.getImage().setImageBitmap(campaña.getImagen());
    }

    @Override
    public int getItemCount() {
        return this.campaña.size();
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
