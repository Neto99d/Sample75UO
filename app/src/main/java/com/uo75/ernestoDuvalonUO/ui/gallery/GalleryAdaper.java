package com.uo75.ernestoDuvalonUO.ui.gallery;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.ceylonlabs.imageviewpopup.ImagePopup;
import com.uo75.ernestoDuvalonUO.ui.modelsOdoo.Postales;

import java.util.List;

public class GalleryAdaper extends BaseAdapter {

    private List<Postales> postales;
    Context conntext;

    public GalleryAdaper(List<Postales> postales, Context context) {
        this.postales = postales;
        this.conntext = context;
    }

    public GalleryAdaper(Context conntext) {
        this.conntext = conntext;
    }

    public List<Postales> getPostales() {
        return postales;
    }


    @Override
    public int getCount() {
        return this.postales.size();
    }

    @Override
    public Object getItem(int i) {
        return this.postales.get(i).getImagen();
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ImageView imageView = new ImageView(conntext);
        imageView.setImageBitmap(postales.get(i).getImagen());
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setLayoutParams( new GridView.LayoutParams( 340, 350 ));

        final ImagePopup imagePopup = new ImagePopup(conntext);
        imagePopup.setWindowHeight(800); // Optional
        imagePopup.setWindowWidth(800); // Optional
        imagePopup.setFullScreen(true); // Optional
        imagePopup.setHideCloseIcon(true);  // Optional
        imagePopup.setImageOnClickClose(true);  // Optional


        imagePopup.initiatePopup(imageView.getDrawable()); // Load Image from Drawable


        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /** Initiate Popup view **/
                imagePopup.viewPopup();

            }
        });

        return imageView;
    }
}
