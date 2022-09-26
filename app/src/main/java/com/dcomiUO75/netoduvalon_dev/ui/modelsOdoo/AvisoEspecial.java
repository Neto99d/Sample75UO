package com.dcomiUO75.netoduvalon_dev.ui.modelsOdoo;

public class AvisoEspecial {
    String contenido;
    String fecha;

    public AvisoEspecial(String contenido, String fecha) {
        this.contenido = contenido;
        this.fecha = fecha;
    }

    public AvisoEspecial() {
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
}
