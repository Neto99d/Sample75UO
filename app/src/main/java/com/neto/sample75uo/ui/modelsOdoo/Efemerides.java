package com.neto.sample75uo.ui.modelsOdoo;

public class Efemerides {
    String contenido;
    String imagen;
    String fecha;

    public Efemerides(String contenido, String imagen, String fecha) {
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

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
}
