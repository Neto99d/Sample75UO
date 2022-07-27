package com.neto.sample75uo.ui.options;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.neto.sample75uo.R;
import com.neto.sample75uo.ui.options.Adapters.PatrimonioAdapter;

public class FullImageScreenActivity extends AppCompatActivity {

    ImageView imageViewDetalles;
    PatrimonioAdapter imagenesAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_image_screen);
        imageViewDetalles = (ImageView) findViewById(R.id.imagen_detalle);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Imagen ampliada");

        Intent i = getIntent();
        int posicion = i.getExtras().getInt("idimagen");
        imageViewDetalles.setImageBitmap(imagenesAdapter.getPatrimonio().get(posicion).getImagen());


    }
}