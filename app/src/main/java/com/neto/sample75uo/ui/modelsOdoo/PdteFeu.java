package com.neto.sample75uo.ui.modelsOdoo;

public class PdteFeu {
    String contenido;
    String imagen;

    public PdteFeu(String contenido, String imagen) {
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

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }
}
