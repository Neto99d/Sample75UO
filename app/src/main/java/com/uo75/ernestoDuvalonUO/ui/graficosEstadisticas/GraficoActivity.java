package com.uo75.ernestoDuvalonUO.ui.graficosEstadisticas;

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
    PieChart pieChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grafico);

        pieChart = findViewById(R.id.pastelChart);

        pieChart.getDescription().setEnabled(false);
        pieChart.getLegend().setWordWrapEnabled(true);
        pieChart.getLegend().setTextSize(14f);
        pieChart.getLegend().setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        pieChart.getLegend().setOrientation(Legend.LegendOrientation.HORIZONTAL);
        pieChart.setEntryLabelColor(Color.BLACK);
        pieChart.setEntryLabelTextSize(15f);
        pieChart.animate();
        setValues();

    }

   private void setValues(){
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
       params.put("db", "odoo_db");
       params.put("login", "uo75App@uo.cu");
       params.put("password", "app#75");
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
                   params.put("access-token", acceso.getAccesToken());

                   Call<Data> callS = service.getEstadisticas(params);
                   //////////////////////////////
                   PieDataSet pastelData = new PieDataSet(null, null);
                   ArrayList<PieEntry> dataGraficoGuardar = new ArrayList<>();
                   ///////////////////////////////

                   callS.enqueue(new Callback<Data>() {
                       @Override
                       public void onResponse(Call<Data> callS, Response<Data> response) {
                           //Codigo de respuesta
                           System.out.println("[Code: " + response.code() + "]");
                           if (response.isSuccessful()) {//si la peticion se completo con exito
                               Data data = response.body();
                               try {
                                   int result = 0;
                                   System.out.println("Response:\n" + data.getData().get(0).get("name"));
                                   for (int i = 0; i < data.getData().size(); i++) {
                                       dataGraficoGuardar.add(new PieEntry(Integer.parseInt(data.getData().get(i).get("cantidad").getAsString()), data.getData().get(i).get("name").getAsString()));
                                       result = result + Integer.parseInt(data.getData().get(i).get("cantidad").getAsString());
                                   }
                                   //////////////////
                                   PieData pieData = new PieData(pastelData);
                                   ArrayList<Integer> colors = new ArrayList<>();

                                   for (int c : ColorTemplate.VORDIPLOM_COLORS)
                                       colors.add(c);

                                   for (int c : ColorTemplate.JOYFUL_COLORS)
                                       colors.add(c);

                                   for (int c : ColorTemplate.COLORFUL_COLORS)
                                       colors.add(c);

                                   for (int c : ColorTemplate.LIBERTY_COLORS)
                                       colors.add(c);

                                   for (int c : ColorTemplate.PASTEL_COLORS)
                                       colors.add(c);

                                   colors.add(ColorTemplate.getHoloBlue());


                                   pastelData.setColors(colors);
                                   pastelData.setValueTextColor(Color.BLACK);
                                   pastelData.setValueTextSize(19f);
                                   pastelData.setValues(dataGraficoGuardar);
                                   pastelData.setFormLineWidth(4);

                                   pieChart.setData(pieData);
                                   pieChart.setCenterTextSize(20f);
                                   pieChart.setCenterText("Total" + "\n" + result);

                                   // undo all highlights
                                   pieChart.highlightValues(null);

                                   pieChart.invalidate();
                                   ///////////////////
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