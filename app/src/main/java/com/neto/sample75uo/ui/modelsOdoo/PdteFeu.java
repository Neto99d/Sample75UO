package com.neto.sample75uo.ui.modelsOdoo;

import android.graphics.Bitmap;

public class PdteFeu {
    String contenido;
    Bitmap imagen;

    public PdteFeu(String contenido, Bitmap imagen) {
        this.contenido = contenido;
        this.imagen = imagen;
    }

    public PdteFeu() {
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
