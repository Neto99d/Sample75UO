package com.dcomiUO75.netoduvalon_dev.ui.modelsOdoo;

import android.graphics.Bitmap;

public class Efemerides {
    String contenido;
    Bitmap imagen;
    String fecha;

    public Efemerides(String contenido, Bitmap imagen, String fecha) {
        this.contenido = contenido;
        this.imagen = imagen;
        this.fecha = fecha;
    }

    public Efemerides() {
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

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
}
