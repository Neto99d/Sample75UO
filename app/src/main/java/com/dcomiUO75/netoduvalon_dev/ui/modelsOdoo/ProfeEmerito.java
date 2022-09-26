package com.dcomiUO75.netoduvalon_dev.ui.modelsOdoo;

import android.graphics.Bitmap;

public class ProfeEmerito {
    String contenido;
    Bitmap imagen;

    public ProfeEmerito(String contenido, Bitmap imagen) {
        this.contenido = contenido;
        this.imagen = imagen;
    }

    public ProfeEmerito() {
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
