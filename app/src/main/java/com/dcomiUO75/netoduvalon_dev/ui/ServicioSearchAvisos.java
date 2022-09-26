package com.dcomiUO75.netoduvalon_dev.ui;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.dcomiUO75.netoduvalon_dev.R;
import com.dcomiUO75.netoduvalon_dev.ui.modelsOdoo.AccesOdoo;
import com.dcomiUO75.netoduvalon_dev.ui.modelsOdoo.AvisoEspecial;
import com.dcomiUO75.netoduvalon_dev.ui.modelsOdoo.Data;
import com.dcomiUO75.netoduvalon_dev.ui.options.AvisoEspecialActivity;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.jetbrains.annotations.NotNull;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ServicioSearchAvisos extends Service {
    private PendingIntent pendingIntent;


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intenc, int flags, int idArranque) {
        super.onStartCommand(intenc, flags, idArranque);
        ///////////// Ejecutar metodo para buscar avisos
        if (!isNetworkAvailable()) {
            System.out.print("No hay conexion");
        } else {
            ejecutar();
        }
        /////////////
        return Service.START_STICKY; // crear hilo
    }

    @Override
    public void onDestroy() {

    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Notificaciones Básicas";
            String id = "basic_channel";
            String description = "Notificaciones de Aviso Especial";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel(id, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    //// CREANDO LAS NOTIFICACIONES DE AVISOS
    public void showNotificacion(String contenido) {
        setPendingIntent(AvisoEspecialActivity.class);
        String id = "basic_channel";
        int notificationId = 0;
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, id)
                .setSmallIcon(R.drawable.escudo_color) // ICONO
                .setContentTitle("Aviso de Universidad de Oriente")
                .setContentText(contenido)
                .setAutoCancel(true) // Al tocar se Cierre o desaparezca
                .setVibrate(new long[]{100, 250, 100, 500})
                .setContentIntent(pendingIntent)
                .setSound(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.message_notification)) // Sonido agregado un audio mp3
                .setPriority(NotificationCompat.PRIORITY_HIGH);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);

        // notificationId is a unique int for each notification that you must define
        notificationManager.notify(notificationId, builder.build());

    }

    // Abir Activity desde la notificacion
    private void setPendingIntent(Class<?> clasActivity) {
        Intent intent = new Intent(this, clasActivity);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(clasActivity);
        stackBuilder.addNextIntent(intent);
        pendingIntent = stackBuilder.getPendingIntent(1, PendingIntent.FLAG_UPDATE_CURRENT);

    }

    //// Ejecutar funcion cada cierto tiempo (Buscar Avisos nuevos)
    private void ejecutar() {
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                metodoBuscarAviso();//llamamos nuestro metodo
                handler.postDelayed(this, 900000); // se ejecutara cada 15 minutos
            }
        }, 3000);//empezara a ejecutarse después de 3 segundos de iniciada la app
    }

    private void metodoBuscarAviso() {
        createNotificationChannel();
        //Definimos la URL base del API REST que utilizamos
        String baseUrl = "https://dcomi.uo.edu.cu/";

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
            public void onResponse(@NotNull Call<AccesOdoo> call, @NotNull Response<AccesOdoo> response) {
                //Codigo de respuesta
                // System.out.println("[Code: " + response.code() + "]");
                if (response.isSuccessful()) {//si la peticion se completo con exito
                    AccesOdoo acceso = response.body();
                    // System.out.println("Response:\n" + acceso);
                    //// LLAMANDO A LAS API
                    ////////////////////////////////////////////////////////////////
                    //Definimos la URL base del API REST que utilizamos
                    String baseUrl = "https://dcomi.uo.edu.cu/";

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
                        public void onResponse(@NotNull Call<Data> callS, @NotNull Response<Data> response) {
                            //Codigo de respuesta
                            long ahora = System.currentTimeMillis();
                            @SuppressLint("SimpleDateFormat") DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                            String salida = df.format(ahora);
                            //    System.out.println("[FECHA: " + salida);
                            //  // System.out.println("[Code: " + response.code() + "]");
                            if (response.isSuccessful()) {//si la peticion se completo con exito
                                Data data = response.body();
                                try {

                                    //  System.out.println("Response:\n" + data.getData().get(0).get("fecha"));
                                    for (int i = 0; i < data.getData().size(); i++) {
                                        AvisoEspecial avisoEspecial = new AvisoEspecial();
                                        String fecha;
                                        avisoEspecial.setContenido(data.getData().get(i).get("contenido").getAsString());
                                        avisoEspecial.setFecha(data.getData().get(i).get("fecha").getAsString());
                                        fecha = data.getData().get(i).get("fecha").getAsString();
                                        if (salida.equals(fecha)) {
                                            ///Mostrar notificacion y le paso el contenido.
                                            showNotificacion(data.getData().get(i).get("contenido").getAsString());
                                        }
                                    }

                                    //tText.setValue(patrimonio.getContenido());
                                } catch (Exception e) {
                                    System.out.println("ERROR: " + e.getMessage());
                                }

                            } else {//La peticion se realizo, pero ocurrio un error
                                System.out.println("ERROR: " + response.message());

                            }
                        }

                        @Override
                        public void onFailure(@NotNull Call<Data> callS, @NotNull Throwable t) {
                            System.out.println("Network Error :: " + t.getLocalizedMessage());
                        }
                    });


                } else {//La peticion se realizo, pero ocurrio un error
                    System.out.println("ERROR: " + response.message());
                }
            }

            @Override
            public void onFailure(@NotNull Call<AccesOdoo> call, @NotNull Throwable t) {
                System.out.println("Network Error :: " + t.getLocalizedMessage());
            }
        });
    }

    ///VER SI HAY O NO CONEXION a INTERNET
    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = null;
        if (connectivityManager != null) {
            activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        }
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }


}
