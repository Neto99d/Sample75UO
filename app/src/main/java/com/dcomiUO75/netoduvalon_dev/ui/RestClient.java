package com.dcomiUO75.netoduvalon_dev.ui;

import com.dcomiUO75.netoduvalon_dev.ui.modelsOdoo.AccesOdoo;
import com.dcomiUO75.netoduvalon_dev.ui.modelsOdoo.Data;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;


// Interfaz de Peticiones Rest API
public interface RestClient {

    // Acces a Odoo obteniendo Token
    @GET("api/auth/token/")
    Call<AccesOdoo> getAcceso(@HeaderMap Map<String, String> params);

    // Mision
    @GET("api/mision.mision")
    Call<Data> getMision(@HeaderMap Map<String, String> params);

    // Objeto Social
    @GET("api/objeto.objeto")
    Call<Data> getObjeto(@HeaderMap Map<String, String> params);

    // Vision
    @GET("api/vision.vision")
    Call<Data> getVision(@HeaderMap Map<String, String> params);

    // Patrimonio
    @GET("api/patrimonio.patrimonio")
    Call<Data> getPatrimonio(@HeaderMap Map<String, String> params);

    // Estadisticas
    @GET("api/estadisticas.estadisticas")
    Call<Data> getEstadisticas(@HeaderMap Map<String, String> params);

    // Honoris Causa
    @GET("api/honorcausa.honorcausa")
    Call<Data> getHonorCausa(@HeaderMap Map<String, String> params);

    // Identidad
    @GET("api/identidad.identidad")
    Call<Data> getIdentidad(@HeaderMap Map<String, String> params);

    // Multimedia
    @GET("api/multimedia.multimedia")
    Call<Data> getMultimedia(@HeaderMap Map<String, String> params);

    // Postales
    @GET("api/postales.postales")
    Call<Data> getPostales(@HeaderMap Map<String, String> params);

    // Profe Emerito
    @GET("api/profeemerito.profeemerito")
    Call<Data> getProfeEmerito(@HeaderMap Map<String, String> params);

    // Presidente FEU
    @GET("api/pdtefeu.pdtefeu")
    Call<Data> getPteFeu(@HeaderMap Map<String, String> params);

    // Reseña
    @GET("api/resena.resena")
    Call<Data> getReseña(@HeaderMap Map<String, String> params);

    // Aviso
    @GET("api/aviso.aviso")
    Call<Data> getAviso(@HeaderMap Map<String, String> params);

    // Campaña
    @GET("api/campana.campana")
    Call<Data> getCampaña(@HeaderMap Map<String, String> params);

    // Efemerides
    @GET("api/efemerides.efemerides")
    Call<Data> getEfemerides(@HeaderMap Map<String, String> params);

    // Rectores
    @GET("api/rectores.rectores")
    Call<Data> getRectores(@HeaderMap Map<String, String> params);


}
