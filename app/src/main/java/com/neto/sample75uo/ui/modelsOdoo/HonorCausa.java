package com.neto.sample75uo.ui.modelsOdoo;

public class HonorCausa {
    String name;
    String contenido;
    String imagen;

    public HonorCausa(String name, String contenido, String imagen) {
        this.name = name;
        this.contenido = contenido;
        this.imagen = imagen;
    }

    public HonorCausa() {
    }

    public String getNombre() {
        return name;
    }

    public void setNombre(String nombre) {
        this.name = nombre;
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

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }
}
