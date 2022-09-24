package com.uo75.ernestoDuvalonUO;

import android.app.ActivityManager;
import android.app.PendingIntent;
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
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.uo75.ernestoDuvalonUO.ui.OkHttpUtil;
import com.uo75.ernestoDuvalonUO.ui.RestClient;
import com.uo75.ernestoDuvalonUO.ui.ServicioSearchAvisos;
import com.uo75.ernestoDuvalonUO.ui.modelsOdoo.AccesOdoo;

import java.util.HashMap;
import java.util.Map;

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
        // Iniciando servicio de busqueda de avisos para Android 5 y 5.1
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.LOLLIPOP_MR1) {
            startService(new Intent(this,
                    ServicioSearchAvisos.class));
        }

        /// Iniciando servicio de busqueda de avisos Android >= 6
        if (!isMyServiceRunning(ServicioSearchAvisos.class)) { // Si el servicio no esta en ejcucion
            System.out.println("SERVICIO INICIADO");
            // Buscar aviso al abrir app ::: En algunas versiones anteriores de Android (como la 6 y quiza 7, 8) una vez instalada, la app mostrará la
            // notificación una vez y luego la mostrará cada 15 min y no cada vez que abre la app ya que el servicio de busqueda de avisos
            // esta en ejecucion permanentemente.
            startService(new Intent(this,
                    ServicioSearchAvisos.class));

        } else {      // Si el servicio esta en ejecucion
            System.out.println("SERVICIO YA ESTA EN EJECUCION");
        }

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

        // Iniciando Clase OkHttpUtil para ignorar validez del certificado
        try {
            OkHttpUtil.init(true);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //Instancia a GSON
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd HH:mm:ss")
                .create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(OkHttpUtil.getClient()) // Obteniedno el client de Clase OkHttpUtil
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        //Se crea el servicio
        RestClient service = retrofit.create(RestClient.class);
        // se pasa los parametros y Se realiza la llamada
        Map<String, String> params = new HashMap<>();
        params.put("db", "odoo_db");  // Nombre de base de Datos en el Sistema: odoo_db
        params.put("login", "uo75App@uo.cu"); // Usuario de la App en el Sitema: uo75App@uo.cu
        params.put("password", "app#75");     // Contraseña de la App en el Sistema: app#75
        // ... as much as you need.

        Call<AccesOdoo> call = service.getAcceso(params);
        call.enqueue(new Callback<AccesOdoo>() {
            @Override
            public void onResponse(Call<AccesOdoo> call, Response<AccesOdoo> response) {
                // Codigo de respuesta 200 OK
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

            // Error al comunicar con el servicio o la peticion no se realizo
            @Override
            public void onFailure(Call<AccesOdoo> call, Throwable t) {
                System.out.println("Network Error :: " + t.getLocalizedMessage());
                Toast toast = Toast.makeText(getApplicationContext(), "Error de Conexión al servicio, además verifique su conexión a internet", Toast.LENGTH_LONG);
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

    // Pregunta  Si el servicio esta corriendo
    @RequiresApi(api = Build.VERSION_CODES.M) // Android >= 6
    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }


}

