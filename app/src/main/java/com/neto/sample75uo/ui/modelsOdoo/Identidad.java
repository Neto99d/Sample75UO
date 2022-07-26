package com.neto.sample75uo.ui.modelsOdoo;

import android.graphics.Bitmap;

public class Identidad {
    String contenido;
    Bitmap imagen;

    public Identidad(String contenido, Bitmap imagen) {
        this.contenido = contenido;
        this.imagen = imagen;
    }

    public Identidad() {
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
