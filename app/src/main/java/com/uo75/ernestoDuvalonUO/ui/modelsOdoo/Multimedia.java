package com.uo75.ernestoDuvalonUO.ui.modelsOdoo;

import android.graphics.Bitmap;

public class Multimedia {
    String contenido;
    Bitmap imagen;

    public Multimedia() {
    }

    public Multimedia(String url, Bitmap image) {
        this.contenido = url;
        this.imagen = image;
    }

    public String getUrl() {
        return contenido;
    }

    public void setUrl(String url) {
        this.contenido = url;
    }

    public Bitmap getImage() {
        return imagen;
    }

    public void setImage(Bitmap image) {
        this.imagen = image;
    }
}
