package com.dcomiUO75.netoduvalon_dev.ui.modelsOdoo;

import android.graphics.Bitmap;

public class Postales {

    Bitmap imagen;

    public Postales(Bitmap imagen) {
        this.imagen = imagen;
    }

    public Postales() {
    }

    public Bitmap getImagen() {
        return imagen;
    }

    public void setImagen(Bitmap imagen) {
        this.imagen = imagen;
    }
}
