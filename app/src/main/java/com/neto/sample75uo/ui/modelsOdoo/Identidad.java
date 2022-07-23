package com.neto.sample75uo.ui.modelsOdoo;

public class Identidad {
    String contenido;
    String imagen;

    public Identidad(String contenido, String imagen) {
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

    public String getImagen() {
        return imagen;
    }
}
