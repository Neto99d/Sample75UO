package com.dcomiUO75.netoduvalon_dev.ui.options;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.dcomiUO75.netoduvalon_dev.R;
import com.dcomiUO75.netoduvalon_dev.ui.OkHttpUtil;
import com.dcomiUO75.netoduvalon_dev.ui.RestClient;
import com.dcomiUO75.netoduvalon_dev.ui.modelsOdoo.AccesOdoo;
import com.dcomiUO75.netoduvalon_dev.ui.modelsOdoo.Data;
import com.dcomiUO75.netoduvalon_dev.ui.modelsOdoo.Multimedia;
import com.dcomiUO75.netoduvalon_dev.ui.options.Adapters.MultimediaAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import cn.jzvd.Jzvd;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MultimediaActivity extends AppCompatActivity {

    Context mContext;
    private MultimediaAdapter nAdapter;
    private RecyclerView mRecyclerView;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        mContext = this;
        setContentView(R.layout.activity_multimedia);
        mRecyclerView = findViewById(R.id.recycler_multimedia);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setHasFixedSize(true);
        progressBar = findViewById(R.id.progressBarMultimedia);
        //Definimos la URL base del API REST que utilizamos
        String baseUrl = "https://dcomi.uo.edu.cu/ ";

        try {
            OkHttpUtil.verificar();
        } catch (Exception e) {
            e.printStackTrace();
        }

        //Instancia a GSON
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd HH:mm:ss")
                .create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(OkHttpUtil.getClient())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        //Se crea el servicio
        RestClient service = retrofit.create(RestClient.class);
        //Se realiza la llamada
        Map<String, String> params = new HashMap<>();
        params.put("db", "odoo_db");
        params.put("login", "uo75App@uo.cu");
        params.put("password", "app#75");
        // ... as much as you need.

        Call<AccesOdoo> call = service.getAcceso(params);
        call.enqueue(new Callback<AccesOdoo>() {
            @Override
            public void onResponse(Call<AccesOdoo> call, Response<AccesOdoo> response) {
                //Codigo de respuesta
                // System.out.println("[Code: " + response.code() + "]");
                if (response.isSuccessful()) {//si la peticion se completo con exito
                    AccesOdoo acceso = response.body();
                    // System.out.println("Response:\n" + acceso);
                    //// LLAMANDO A LAS API
                    ////////////////////////////////////////////////////////////////
                    //Definimos la URL base del API REST que utilizamos
                    String baseUrl = "https://dcomi.uo.edu.cu/ ";

                    ArrayList<Multimedia> multimedias = new ArrayList<>();
                    try {
                        OkHttpUtil.verificar();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    //Instancia a GSON
                    Gson gson = new GsonBuilder()
                            .setDateFormat("yyyy-MM-dd HH:mm:ss")
                            .create();
                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl(baseUrl)
                            .client(OkHttpUtil.getClient())
                            .addConverterFactory(GsonConverterFactory.create(gson))
                            .build();
                    //Se crea el servicio
                    RestClient service = retrofit.create(RestClient.class);
                    //Se realiza la llamada
                    Map<String, String> params = new HashMap<>();
                    params.put("access-token", acceso.getAccesToken());


                    Call<Data> callS = service.getMultimedia(params);
                    callS.enqueue(new Callback<Data>() {
                        @Override
                        public void onResponse(Call<Data> callS, Response<Data> response) {
                            //Codigo de respuesta

                            // System.out.println("[Code: " + response.code() + "]");
                            if (response.isSuccessful()) {//si la peticion se completo con exito
                                Data data = response.body();
                                try {

                                    // System.out.println("Response:\n" + data.getData().get(0).get("contenido"));
                                    for (int i = 0; i < data.getData().size(); i++) {
                                        Multimedia multimedia1 = new Multimedia();
                                        multimedia1.setTitulo(data.getData().get(i).get("name").getAsString());
                                        multimedia1.setUrl(data.getData().get(i).get("contenido").getAsString());
                                        /// Se coge el String a partir del primer caracter ' que llega desde el JSon
                                        multimedia1.setImage(StringToBitMap(data.getData().get(i).get("imagen").getAsString().substring(data.getData().get(i).get("imagen").getAsString().indexOf("'") + 1)));
                                        multimedias.add(multimedia1);
                                    }
                                    if (multimedias.isEmpty()) {
                                        progressBar.setVisibility(View.GONE);
                                        Toast toastData = Toast.makeText(getApplicationContext(), "No hay multimedia en este momento", Toast.LENGTH_LONG);
                                        toastData.show();
                                    } else {
                                        progressBar.setVisibility(View.GONE);
                                    }
                                    nAdapter = new MultimediaAdapter(multimedias, mContext);
                                    mRecyclerView.setAdapter(nAdapter);
                                    //tText.setValue(patrimonio.getContenido());
                                } catch (Exception e) {
                                    System.out.println("ERROR: " + e.getMessage());
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

    @Override
    public void onBackPressed() {
        if (Jzvd.backPress()) {
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Jzvd.releaseAllVideos();
    }

    // Convertir String base64 a Imagen Bitmap
    public Bitmap StringToBitMap(String encodedString) {
        try {
            byte[] encodeByte = Base64.decode(encodedString, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        } catch (Exception e) {
            e.getMessage();
            return null;
        }
    }
}