package com.uo75.ernestoDuvalonUO.ui.options;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.uo75.ernestoDuvalonUO.R;
import com.uo75.ernestoDuvalonUO.ui.RestClient;
import com.uo75.ernestoDuvalonUO.ui.modelsOdoo.AccesOdoo;
import com.uo75.ernestoDuvalonUO.ui.modelsOdoo.Data;
import com.uo75.ernestoDuvalonUO.ui.modelsOdoo.Patrimonio;
import com.uo75.ernestoDuvalonUO.ui.options.Adapters.PatrimonioAdapter;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PatrimonioActivity extends AppCompatActivity {
    private PatrimonioAdapter nAdapter;
    private RecyclerView mRecyclerView;
    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        mContext = this;
        setContentView(R.layout.activity_patrimonio);
        mRecyclerView = findViewById(R.id.recycler_patrimonio);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setHasFixedSize(true);

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

                    ArrayList<Patrimonio> patrim = new ArrayList<>();
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


                    Call<Data> callS = service.getPatrimonio(params);
                    callS.enqueue(new Callback<Data>() {
                        @Override
                        public void onResponse(Call<Data> callS, Response<Data> response) {
                            //Codigo de respuesta

                            System.out.println("[Code: " + response.code() + "]");
                            if (response.isSuccessful()) {//si la peticion se completo con exito
                                Data data = response.body();
                                try {

                                    System.out.println("Response:\n" + data.getData().get(0).get("contenido"));
                                    for (int i = 0; i < data.getData().size(); i++) {
                                        Patrimonio patrimonio = new Patrimonio();
                                        patrimonio.setContenido(data.getData().get(i).get("contenido").getAsString());
                                        /// Se coge el String a partir del primer caracter ' que llega desde el JSon
                                        patrimonio.setImagen(StringToBitMap(data.getData().get(i).get("imagen").getAsString().substring(data.getData().get(i).get("imagen").getAsString().indexOf("'") + 1)));
                                        patrim.add(patrimonio);
                                    }

                                    nAdapter = new PatrimonioAdapter(patrim, mContext);
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


    // Convertir String base64 a Imagen Bitmap
    public Bitmap StringToBitMap(String encodedString) {
        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            byte[] encodeByte = Base64.decode(encodedString, Base64.DEFAULT);
            Bitmap foto = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            foto.compress(Bitmap.CompressFormat.WEBP,100,out);
            byte[] byteArray = out.toByteArray();
            foto = BitmapFactory.decodeByteArray(byteArray,0,byteArray.length);
            return foto;
        } catch (Exception e) {
            e.getMessage();
            return null;
        }
    }

}