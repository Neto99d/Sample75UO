package com.uo75.ernestoDuvalonUO;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.uo75.ernestoDuvalonUO.ui.RestClient;
import com.uo75.ernestoDuvalonUO.ui.ServicioSearchAvisos;
import com.uo75.ernestoDuvalonUO.ui.modelsOdoo.AccesOdoo;
import com.uo75.ernestoDuvalonUO.ui.modelsOdoo.AvisoEspecial;
import com.uo75.ernestoDuvalonUO.ui.modelsOdoo.Data;
import com.uo75.ernestoDuvalonUO.ui.options.AvisoEspecialActivity;

import org.jetbrains.annotations.NotNull;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {


    private AppBarConfiguration mAppBarConfiguration;
    private PendingIntent pendingIntent;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); // Solo Orientacion Vertical
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /// Iniciando servicio de busqueda de avisos
        if (!isMyServiceRunning(ServicioSearchAvisos.class)) { // Si el servicio no esta en ejcucion
            System.out.println("SERVICIO INICIADO");
            startService(new Intent(this,
                    ServicioSearchAvisos.class));
            // Buscar aviso al abrir app
            metodoBuscarAviso();
        } else {      // Si el servicio esta en ejecucion
            System.out.println("SERVICIO YA ESTA EN EJECUCION");
        }

        /// Llamada a la funcion notificacion que inicia el canal
        createNotificationChannel();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_MOV)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        //Definimos la URL base del API REST que utilizamos
        String baseUrl = "https://dcomi.uo.edu.cu/";

        // Saltar verificacion de nombre de host, por problema de certificado
        OkHttpClient okHttpClient = new OkHttpClient.Builder().hostnameVerifier(new HostnameVerifier() {
            @Override
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        }).build();

        //Instancia a GSON
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd HH:mm:ss")
                .create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        //Se crea el servicio
        RestClient service = retrofit.create(RestClient.class);
        //Se realiza la llamada
        Map<String, String> params = new HashMap<>();
        params.put("db", "odoo_db");  // Nombre de base de Datos en el Sistema: odoo_db
        params.put("login", "uo75App@uo.cu"); // Usuario de la App en el Sitema: uo75App@uo.cu
        params.put("password", "app#75");     // Contraseña de la App en el Sistema: app#75
        // ... as much as you need.

        Call<AccesOdoo> call = service.getAcceso(params);
        call.enqueue(new Callback<AccesOdoo>() {
            @Override
            public void onResponse(Call<AccesOdoo> call, Response<AccesOdoo> response) {
                //Codigo de respuesta
                Toast toast = Toast.makeText(getApplicationContext(), "Conexión al servicio exitosa", Toast.LENGTH_LONG);
                toast.show();
                System.out.println("[Code: " + response.code() + "]");
                if (response.isSuccessful()) {//si la peticion se completo con exito
                    AccesOdoo acceso = response.body();
                    System.out.println("Response:\n" + acceso);


                } else {//La peticion se realizo, pero ocurrio un error
                    System.out.println("ERROR: " + response.message());
                    Toast toastData = Toast.makeText(getApplicationContext(), "Error al recibir datos, no hay respuesta", Toast.LENGTH_LONG);
                    toastData.show();
                }
            }

            @Override
            public void onFailure(Call<AccesOdoo> call, Throwable t) {
                System.out.println("Network Error :: " + t.getLocalizedMessage());
                Toast toast = Toast.makeText(getApplicationContext(), "Error de Conexión con el servicio", Toast.LENGTH_LONG);
                toast.show();
            }
        });

    }

    /// Fab Redes Sociales
    public void facebookFab(View view) {
        String url = "https://www.facebook.com/UOCuba";
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(intent);
    }

    public void instagramFab(View view) {
        String url = "https://www.instagram.com/uocuba_oficial/";
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(intent);
    }

    public void twitterFab(View view) {
        String url = "https://twitter.com/UOCuba";
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(intent);
    }

    public void linkedinFab(View view) {
        String url = "https://cu.linkedin.com/school/universidad-de-oriente-cuba/";
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(intent);
    }

    public void telegramFab(View view) {
        String url = "https://t.me/uo_cuba";
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(intent);
    }

    public void youtubeFab(View view) {
        String url = "https://youtube.com/c/UniversidaddeOrienteCuba";
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(intent);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    //// CREANDO CANAL PARA NOTIFICACIONES
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
                .setAutoCancel(true)
                .setVibrate(new long[]{100, 250, 100, 500})
                .setContentIntent(pendingIntent)
                .setSound(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.message_notification)) // Sonido agregado un audio mp3
                .setPriority(NotificationCompat.PRIORITY_HIGH);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);

        // notificationId is a unique int for each notification that you must define
        notificationManager.notify(notificationId, builder.build());

    }


    // Abir Activity Avisos desde la notificacion
    private void setPendingIntent(Class<?> clasActivity) {
        Intent intent = new Intent(this, clasActivity);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(clasActivity);
        stackBuilder.addNextIntent(intent);
        pendingIntent = stackBuilder.getPendingIntent(1, PendingIntent.FLAG_UPDATE_CURRENT);

    }

    // Pregunta  Si el servicio esta corriendo
    @RequiresApi(api = Build.VERSION_CODES.M)
    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    /// METODO PARA BUSCAR AVISO AL ABRIR LA APP
    private void metodoBuscarAviso() {
        createNotificationChannel();
        //Definimos la URL base del API REST que utilizamos
        String baseUrl = "https://dcomi.uo.edu.cu/";

        OkHttpClient okHttpClient = new OkHttpClient.Builder().hostnameVerifier(new HostnameVerifier() {
            @Override
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        }).build();
        //Instancia a GSON
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd HH:mm:ss")
                .create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(okHttpClient)
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

                    OkHttpClient okHttpClient = new OkHttpClient.Builder().hostnameVerifier(new HostnameVerifier() {
                        @Override
                        public boolean verify(String hostname, SSLSession session) {
                            return true;
                        }
                    }).build();
                    //Instancia a GSON
                    Gson gson = new GsonBuilder()
                            .setDateFormat("yyyy-MM-dd HH:mm:ss")
                            .create();
                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl(baseUrl)
                            .client(okHttpClient)
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
}

