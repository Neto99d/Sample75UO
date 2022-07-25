package com.neto.sample75uo.ui.modelsOdoo;

import android.graphics.Bitmap;

public class Patrimonio {
    String contenido;
    Bitmap imagen;

    public Patrimonio(String contenido, Bitmap imagen) {
        this.contenido = contenido;
        this.imagen = imagen;
    }

    public Patrimonio() {
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
