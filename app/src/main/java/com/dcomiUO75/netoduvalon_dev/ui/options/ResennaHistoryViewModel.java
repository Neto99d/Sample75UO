package com.dcomiUO75.netoduvalon_dev.ui.options;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.dcomiUO75.netoduvalon_dev.ui.OkHttpUtil;
import com.dcomiUO75.netoduvalon_dev.ui.RestClient;
import com.dcomiUO75.netoduvalon_dev.ui.modelsOdoo.AccesOdoo;
import com.dcomiUO75.netoduvalon_dev.ui.modelsOdoo.Data;
import com.dcomiUO75.netoduvalon_dev.ui.modelsOdoo.Reseña;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ResennaHistoryViewModel extends ViewModel {
    private MutableLiveData<String> mText;
    private MutableLiveData<Boolean> status; // Estado de conexion al servicio
//In your network successfull response

    public ResennaHistoryViewModel() {
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
                status.setValue(true);
                // System.out.println("[Code: " + response.code() + "]");
                if (response.isSuccessful()) {//si la peticion se completo con exito
                    AccesOdoo acceso = response.body();
                    // System.out.println("Response:\n" + acceso);
                    //// LLAMANDO A LAS API
                    getReseña(acceso.getAccesToken());


                } else {//La peticion se realizo, pero ocurrio un error
                    System.out.println("ERROR: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<AccesOdoo> call, Throwable t) {
                System.out.println("Network Error :: " + t.getLocalizedMessage());
                status.setValue(false);
            }
        });

        mText = new MutableLiveData<>();
        status = new MutableLiveData<>();

    }

    public Reseña getReseña(String token) {
        //Definimos la URL base del API REST que utilizamos
        String baseUrl = "https://dcomi.uo.edu.cu/ ";
        Reseña reseña = new Reseña();
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
        params.put("access-token", token);


        Call<Data> call = service.getReseña(params);
        call.enqueue(new Callback<Data>() {
            @Override
            public void onResponse(Call<Data> call, Response<Data> response) {
                //Codigo de respuesta
                status.setValue(true);
                // System.out.println("[Code: " + response.code() + "]");
                if (response.isSuccessful()) {//si la peticion se completo con exito
                    Data data = response.body();
                    try {
                        // System.out.println("Response:\n" + data.getData().get(0).get("contenido"));
                        for (int i = 0; i < 1; i++) {
                            reseña.setContenido(data.getData().get(i).get("contenido").getAsString());
                        }
                        mText.setValue(reseña.getContenido());
                    } catch (Exception e) {
                        mText.setValue("No hay datos que mostrar");
                    }

                } else {//La peticion se realizo, pero ocurrio un error
                    System.out.println("ERROR: " + response.message());
                    status.setValue(false);
                }
            }

            @Override
            public void onFailure(Call<Data> call, Throwable t) {
                System.out.println("Network Error :: " + t.getLocalizedMessage());
                status.setValue(false);
            }
        });
        return reseña;
    }


    public LiveData<String> getText() {
        return mText;
    }

    public LiveData<Boolean> getStatus() {
        return status;
    }
}