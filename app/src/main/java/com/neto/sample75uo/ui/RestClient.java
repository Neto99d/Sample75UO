package com.neto.sample75uo.ui;

import com.neto.sample75uo.ui.modelsOdoo.AccesOdoo;
import com.neto.sample75uo.ui.modelsOdoo.Data;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.QueryMap;


public interface RestClient {

    // Acces a Odoo obteniendo Token
    @GET("api/auth/token/")
    Call<AccesOdoo> getAcceso(@QueryMap Map<String, String> params);

    // Mision
    @GET("api/mision.mision")
    Call<Data> getMision(@HeaderMap Map<String, String> params);

}