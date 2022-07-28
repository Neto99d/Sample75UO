package com.uo75.ernestoDuvalonUO.ui.modelsOdoo;

import android.graphics.Bitmap;

public class Campaña {
    String contenido;
    Bitmap imagen;

    public Campaña(String contenido, Bitmap imagen) {
        this.contenido = contenido;
        this.imagen = imagen;
    }

    public Campaña() {
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
