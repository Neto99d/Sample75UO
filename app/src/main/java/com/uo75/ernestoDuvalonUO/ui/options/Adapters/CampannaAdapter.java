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
import com.uo75.ernestoDuvalonUO.ui.modelsOdoo.Campaña;

import java.util.List;

public class CampannaAdapter extends RecyclerView.Adapter<CampannaAdapter.ViewHolder>{

    private List<Campaña> campaña;
    Context conntext;

    public CampannaAdapter(List<Campaña> campaña, Context conntext) {
        this.campaña = campaña;
        this.conntext = conntext;
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
        return this.campaña.size();
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