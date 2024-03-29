package com.dcomiUO75.netoduvalon_dev.ui.options;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dcomiUO75.netoduvalon_dev.R;
import com.dcomiUO75.netoduvalon_dev.ui.OkHttpUtil;
import com.dcomiUO75.netoduvalon_dev.ui.RestClient;
import com.dcomiUO75.netoduvalon_dev.ui.modelsOdoo.AccesOdoo;
import com.dcomiUO75.netoduvalon_dev.ui.modelsOdoo.AvisoEspecial;
import com.dcomiUO75.netoduvalon_dev.ui.modelsOdoo.Data;
import com.dcomiUO75.netoduvalon_dev.ui.options.Adapters.AvisoAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AvisoEspecialActivity extends AppCompatActivity {

    private AvisoAdapter nAdapter;
    private RecyclerView mRecyclerView;
    private ProgressBar progressBar;

    // Convertir a formato Fecha d-m-a
    public static String ParseFecha(String fecha) {
        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
        Date fechaDate = null;
        try {
            formato.setLenient(false);
            fechaDate = formato.parse(fecha);
            formato = new SimpleDateFormat("dd-MM-yyyy");
        } catch (ParseException ex) {
            System.out.println(ex);
        }
        return formato.format(fechaDate);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_aviso_especial);
        mRecyclerView = findViewById(R.id.recycler_aviso);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setHasFixedSize(true);
        progressBar = findViewById(R.id.progressBarAvisosEsp);
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

                    ArrayList<AvisoEspecial> avisoEspecials = new ArrayList<>();
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


                    Call<Data> callS = service.getAviso(params);
                    callS.enqueue(new Callback<Data>() {
                        @Override
                        public void onResponse(Call<Data> callS, Response<Data> response) {
                            //Codigo de respuesta
                            // // System.out.println("[Code: " + response.code() + "]");
                            if (response.isSuccessful()) {//si la peticion se completo con exito
                                Data data = response.body();
                                try {

                                    //  // System.out.println("Response:\n" + data.getData().get(0).get("contenido"));
                                    for (int i = 0; i < data.getData().size(); i++) {
                                        AvisoEspecial avisoEspecial = new AvisoEspecial();
                                        avisoEspecial.setContenido(data.getData().get(i).get("contenido").getAsString());
                                        avisoEspecial.setFecha(ParseFecha(data.getData().get(i).get("fecha").getAsString()));
                                        avisoEspecials.add(avisoEspecial);
                                    }
                                    Collections.reverse(avisoEspecials);
                                    if (avisoEspecials.isEmpty()) {
                                        progressBar.setVisibility(View.GONE);
                                        Toast toastData = Toast.makeText(getApplicationContext(), "No hay avisos en este momento", Toast.LENGTH_LONG);
                                        toastData.show();
                                    } else {
                                        progressBar.setVisibility(View.GONE);
                                    }

                                    nAdapter = new AvisoAdapter(avisoEspecials);
                                    mRecyclerView.setAdapter(nAdapter);
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
}

