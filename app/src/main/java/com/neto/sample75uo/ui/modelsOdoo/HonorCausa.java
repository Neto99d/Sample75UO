package com.neto.sample75uo.ui.modelsOdoo;

import android.graphics.Bitmap;

public class HonorCausa {

    String contenido;
    Bitmap imagen;

    public HonorCausa(String contenido, Bitmap imagen) {
        this.contenido = contenido;
        this.imagen = imagen;
    }

    public HonorCausa() {
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public Bitmap getImagen() {
        return imagen;
    }

    public void setImagen(Bitmap imagen) {
        this.imagen = imagen;
    }
}
