package com.uo75.ernestoDuvalonUO.ui.modelsOdoo;

import android.graphics.Bitmap;

public class Multimedia {
    String titulo;
    String contenido;
    Bitmap imagen;

    public Multimedia() {
    }

    public Multimedia(String url, String titulo, Bitmap image) {
        this.contenido = url;
        this.imagen = image;
        this.titulo = titulo;
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

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
}
