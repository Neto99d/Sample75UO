package com.neto.sample75uo.ui.modelsOdoo;

public class ProfeEmerito {
    String contenido;
    String imagen;

    public ProfeEmerito(String contenido, String imagen) {
        this.contenido = contenido;
        this.imagen = imagen;
    }

    public ProfeEmerito() {
    }

    public String getDescripcion() {
        return contenido;
    }

    public void setDescripcion(String descripcion) {
        this.contenido = descripcion;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }
}
