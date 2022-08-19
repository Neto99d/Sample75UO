package com.uo75.ernestoDuvalonUO.ui.gallery;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.glidebitmappool.GlideBitmapFactory;
import com.glidebitmappool.GlideBitmapPool;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.uo75.ernestoDuvalonUO.R;
import com.uo75.ernestoDuvalonUO.ui.RestClient;
import com.uo75.ernestoDuvalonUO.ui.modelsOdoo.AccesOdoo;
import com.uo75.ernestoDuvalonUO.ui.modelsOdoo.Data;
import com.uo75.ernestoDuvalonUO.ui.modelsOdoo.Postales;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GalleryActivity extends AppCompatActivity {

    private GalleryAdaper nAdapter;
    private RecyclerView mRecyclerView;
    Context mContext;
    private ProgressBar progressBar;
    public ImageView imageFull;
    public CardView fondoImageFull;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
        mContext = this;
        imageFull = findViewById(R.id.imageViewFull);
        fondoImageFull = findViewById(R.id.cardViewFullImage);
        mRecyclerView = findViewById(R.id.recycler_gallery);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setHasFixedSize(true);
        progressBar = findViewById(R.id.progressBarGallery);
        GlideBitmapPool.initialize(5 * 800 * 600); // 2mb max memory size

        //Definimos la URL base del API REST que utilizamos
        String baseUrl = "http://192.168.1.2:8069/";

        //Instancia a GSON
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd HH:mm:ss")
                .create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        //Se crea el servicio
        RestClient service = retrofit.create(RestClient.class);
        //Se realiza la llamada
        Map<String, String> params = new HashMap<>();
        params.put("db", "odooDB");
        params.put("login", "admin@example.com");
        params.put("password", "admin");
        // ... as much as you need.

        Call<AccesOdoo> call = service.getAcceso(params);
        call.enqueue(new Callback<AccesOdoo>() {
            @Override
            public void onResponse(Call<AccesOdoo> call, Response<AccesOdoo> response) {
                //Codigo de respuesta
                System.out.println("[Code: " + response.code() + "]");
                if (response.isSuccessful()) {//si la peticion se completo con exito
                    AccesOdoo acceso = response.body();
                    System.out.println("Response:\n" + acceso);
                    //// LLAMANDO A LAS API
                    ////////////////////////////////////////////////////////////////
                    //Definimos la URL base del API REST que utilizamos
                    String baseUrl = "http://192.168.1.2:8069/";

                    ArrayList<Postales> postales = new ArrayList<>();
                    //Instancia a GSON
                    Gson gson = new GsonBuilder()
                            .setDateFormat("yyyy-MM-dd HH:mm:ss")
                            .create();
                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl(baseUrl)
                            .addConverterFactory(GsonConverterFactory.create(gson))
                            .build();
                    //Se crea el servicio
                    RestClient service = retrofit.create(RestClient.class);
                    //Se realiza la llamada
                    Map<String, String> params = new HashMap<>();
                    params.put("access_token", acceso.getAccesToken());


                    Call<Data> callS = service.getPostales(params);
                    callS.enqueue(new Callback<Data>() {
                        @Override
                        public void onResponse(Call<Data> callS, Response<Data> response) {
                            //Codigo de respuesta

                            System.out.println("[Code: " + response.code() + "]");
                            if (response.isSuccessful()) {//si la peticion se completo con exito
                                Data data = response.body();
                                try {
                                    for (int i = 0; i < data.getData().size(); i++) {
                                        Postales postales1 = new Postales();
                                        /// Se coge el String a partir del primer caracter ' que llega desde el JSon
                                        postales1.setImagen(StringToBitMap(data.getData().get(i).get("imagen").getAsString().substring(data.getData().get(i).get("imagen").getAsString().indexOf("'") + 1)));
                                        postales.add(postales1);
                                    }
                                    progressBar.setVisibility(View.GONE);
                                    nAdapter = new GalleryAdaper(postales, mContext, imageFull, fondoImageFull);
                                    mRecyclerView.setAdapter(nAdapter);
                                    GlideBitmapPool.clearMemory();
                                } catch (OutOfMemoryError e) {
                                    System.out.println("ERROR: " + e.getMessage());
                                    Toast toast = Toast.makeText(mContext, "Puede que algunas imágenes no salgan en su dispositivo por la alta resolución de estas.", Toast.LENGTH_LONG);
                                    toast.show();
                                    progressBar.setVisibility(View.GONE);
                                }

                            } else {//La peticion se realizo, pero ocurrio un error
                                System.out.println("ERROR: " + response.message());

                            }
                        }

                        @Override
                        public void onFailure(Call<Data> callS, Throwable t) {
                            System.out.println("Network Error :: " + t.getLocalizedMessage());
                        }
                    });


                } else {//La peticion se realizo, pero ocurrio un error
                    System.out.println("ERROR: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<AccesOdoo> call, Throwable t) {
                System.out.println("Network Error :: " + t.getLocalizedMessage());

            }
        });

    }

    // Convertir String base64 a Imagen Bitmap
    public Bitmap StringToBitMap(String encodedString) {

        try {
            //ByteArrayOutputStream out = new ByteArrayOutputStream();
            byte[] encodeByte = Base64.decode(encodedString, Base64.DEFAULT);
            Bitmap foto = GlideBitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            //foto.compress(Bitmap.CompressFormat.WEBP, 100, out);
            //byte[] byteArray = out.toByteArray();
            //foto = GlideBitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
            GlideBitmapPool.clearMemory();
            return foto;
        } catch (Exception e) {
            e.getMessage();
            return null;
        }
    }

    public ImageView getImageFull() {
        return imageFull;
    }
}