package com.uo75.ernestoDuvalonUO.ui.options;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.uo75.ernestoDuvalonUO.R;
import com.uo75.ernestoDuvalonUO.ui.RestClient;
import com.uo75.ernestoDuvalonUO.ui.modelsOdoo.AccesOdoo;
import com.uo75.ernestoDuvalonUO.ui.modelsOdoo.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GraficoActivity extends AppCompatActivity {

    PieDataSet pastelData = new PieDataSet(null, null);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grafico);

        Toast toast = Toast.makeText(this, "Toque la pantalla para mostrar el gráfico con los datos.", Toast.LENGTH_LONG);
        toast.show();
        PieChart pieChart = findViewById(R.id.pastelChart);

        /////////////////////////////////////////////
        pastelData.setColors(ColorTemplate.COLORFUL_COLORS);
        pastelData.setValueTextColor(Color.BLACK);
        pastelData.setValueTextSize(19f);

        pieChart.getDescription().setEnabled(false);
        pieChart.setCenterText("Estadísticas UO");
        pieChart.getLegend().setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        pieChart.getLegend().setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        pieChart.animate();
        pastelData.setFormLineWidth(4);

        //////////////////////////////////////////////////////


        /////////////////////////////////////////////////////////////////

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

                    ArrayList<PieEntry> dataGraficoGuardar = new ArrayList<>();

                    Call<Data> callS = service.getEstadisticas(params);
                    callS.enqueue(new Callback<Data>() {
                        @Override
                        public void onResponse(Call<Data> callS, Response<Data> response) {
                            //Codigo de respuesta
                            System.out.println("[Code: " + response.code() + "]");
                            if (response.isSuccessful()) {//si la peticion se completo con exito
                                Data data = response.body();
                                try {

                                    System.out.println("Response:\n" + data.getData().get(0).get("name"));
                                    for (int i = 0; i < data.getData().size(); i++) {
                                        dataGraficoGuardar.add(new PieEntry(Integer.parseInt(data.getData().get(i).get("cantidad").getAsString()), data.getData().get(i).get("name").getAsString()));
                                    }
                                    pastelData.setValues(dataGraficoGuardar);
                                    PieData pieData = new PieData(pastelData);
                                    pieChart.setData(pieData);
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
        /////////////////////////////////////////////////////////////

    }


}