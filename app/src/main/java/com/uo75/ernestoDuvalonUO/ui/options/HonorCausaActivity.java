package com.uo75.ernestoDuvalonUO.ui.options;

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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.glidebitmappool.GlideBitmapFactory;
import com.glidebitmappool.GlideBitmapPool;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.uo75.ernestoDuvalonUO.R;
import com.uo75.ernestoDuvalonUO.ui.OkHttpUtil;
import com.uo75.ernestoDuvalonUO.ui.RestClient;
import com.uo75.ernestoDuvalonUO.ui.modelsOdoo.AccesOdoo;
import com.uo75.ernestoDuvalonUO.ui.modelsOdoo.Data;
import com.uo75.ernestoDuvalonUO.ui.modelsOdoo.HonorCausa;
import com.uo75.ernestoDuvalonUO.ui.options.Adapters.HonorCausaAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HonorCausaActivity extends AppCompatActivity {
    public ImageView imageFull;
    public CardView fondoImageFull;
    Context mContext;
    private HonorCausaAdapter nAdapter;
    private RecyclerView mRecyclerView;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        mContext = this;
        setContentView(R.layout.activity_honor_causa);
        imageFull = findViewById(R.id.imageViewFullHonorCa);
        fondoImageFull = findViewById(R.id.cardViewFullImageHonorCa);
        mRecyclerView = findViewById(R.id.recycler_honorcausa);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setHasFixedSize(true);
        progressBar = findViewById(R.id.progressBarHonor);
        GlideBitmapPool.initialize(5 * 800 * 600); // 2mb max memory size
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
                // // System.out.println("[Code: " + response.code() + "]");
                if (response.isSuccessful()) {//si la peticion se completo con exito
                    AccesOdoo acceso = response.body();
                    //   // System.out.println("Response:\n" + acceso);
                    //// LLAMANDO A LAS API
                    ////////////////////////////////////////////////////////////////
                    //Definimos la URL base del API REST que utilizamos
                    String baseUrl = "https://dcomi.uo.edu.cu/ ";

                    ArrayList<HonorCausa> honor = new ArrayList<>();
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


                    Call<Data> callS = service.getHonorCausa(params);
                    callS.enqueue(new Callback<Data>() {
                        @Override
                        public void onResponse(Call<Data> callS, Response<Data> response) {
                            //Codigo de respuesta

                            //   // System.out.println("[Code: " + response.code() + "]");
                            if (response.isSuccessful()) {//si la peticion se completo con exito
                                Data data = response.body();
                                try {

                                    //        // System.out.println("Response:\n" + data.getData().get(0).get("contenido"));
                                    for (int i = 0; i < data.getData().size(); i++) {
                                        HonorCausa honorC = new HonorCausa();
                                        honorC.setContenido(data.getData().get(i).get("contenido").getAsString());
                                        /// Se coge el String a partir del primer caracter ' que llega desde el JSon
                                        honorC.setImagen(StringToBitMap(data.getData().get(i).get("imagen").getAsString().substring(data.getData().get(i).get("imagen").getAsString().indexOf("'") + 1)));
                                        honor.add(honorC);
                                    }
                                    if (honor.isEmpty()) {
                                        progressBar.setVisibility(View.GONE);
                                        Toast toastData = Toast.makeText(getApplicationContext(), R.string.No_info_ahora, Toast.LENGTH_LONG);
                                        toastData.show();
                                    } else {
                                        progressBar.setVisibility(View.GONE);
                                    }
                                    nAdapter = new HonorCausaAdapter(honor, mContext, imageFull, fondoImageFull);
                                    mRecyclerView.setAdapter(nAdapter);
                                    GlideBitmapPool.clearMemory();
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


    // Convertir String base64 a Imagen Bitmap
    public Bitmap StringToBitMap(String encodedString) {
        try {
            //ByteArrayOutputStream out = new ByteArrayOutputStream();
            byte[] encodeByte = Base64.decode(encodedString, Base64.DEFAULT);
            Bitmap foto = GlideBitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            //foto.compress(Bitmap.CompressFormat.WEBP, 100, out);
            //byte[] byteArray = out.toByteArray();
            //foto = GlideBitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
            return foto;
        } catch (Exception e) {
            e.getMessage();
            return null;
        }
    }

}