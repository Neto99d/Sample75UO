package com.uo75.ernestoDuvalonUO.ui.modelsOdoo;

public class Estadisticas {
    String  cantidad;
    String name;

    public Estadisticas(String cantidad, String name) {
        this.cantidad = cantidad;
        this.name = name;
    }

    public Estadisticas() {
    }

    public String getCantidad() {
        return cantidad;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
