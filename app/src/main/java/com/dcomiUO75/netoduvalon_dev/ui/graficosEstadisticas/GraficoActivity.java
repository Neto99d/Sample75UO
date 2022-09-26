package com.dcomiUO75.netoduvalon_dev.ui.graficosEstadisticas;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.dcomiUO75.netoduvalon_dev.R;
import com.dcomiUO75.netoduvalon_dev.ui.OkHttpUtil;
import com.dcomiUO75.netoduvalon_dev.ui.RestClient;
import com.dcomiUO75.netoduvalon_dev.ui.modelsOdoo.AccesOdoo;
import com.dcomiUO75.netoduvalon_dev.ui.modelsOdoo.Data;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GraficoActivity extends AppCompatActivity {
    PieChart pastelPlantilla, pastelMatricula, pastelTrabajadores, pastelCategoriaCientifica;
    BarChart barraCategoriaDocente, barraInteres;
    TextView cantidadBecados;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grafico);

        pastelPlantilla = findViewById(R.id.pastelPlantilla);
        pastelMatricula = findViewById(R.id.pastelMatricula);
        pastelCategoriaCientifica = findViewById(R.id.pastelCategoriaCientifica);
        pastelTrabajadores = findViewById(R.id.pastelTrabajadores);
        barraCategoriaDocente = findViewById(R.id.barraCategoriaDocente);
        barraInteres = findViewById(R.id.barraInteres);
        cantidadBecados = findViewById(R.id.cantidadBecados);

        pastelPlantilla.getDescription().setEnabled(false);
        pastelPlantilla.getLegend().setWordWrapEnabled(true);
        pastelPlantilla.getLegend().setTextSize(14f);
        pastelPlantilla.getLegend().setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        pastelPlantilla.getLegend().setOrientation(Legend.LegendOrientation.HORIZONTAL);
        pastelPlantilla.setEntryLabelColor(Color.BLACK);
        pastelPlantilla.setEntryLabelTextSize(15f);
        pastelPlantilla.animate();

        pastelMatricula.getDescription().setEnabled(false);
        pastelMatricula.getLegend().setWordWrapEnabled(true);
        pastelMatricula.getLegend().setTextSize(14f);
        pastelMatricula.getLegend().setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        pastelMatricula.getLegend().setOrientation(Legend.LegendOrientation.HORIZONTAL);
        pastelMatricula.setEntryLabelColor(Color.BLACK);
        pastelMatricula.setEntryLabelTextSize(15f);
        pastelMatricula.animate();

        pastelCategoriaCientifica.getDescription().setEnabled(false);
        pastelCategoriaCientifica.getLegend().setWordWrapEnabled(true);
        pastelCategoriaCientifica.getLegend().setTextSize(14f);
        pastelCategoriaCientifica.getLegend().setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        pastelCategoriaCientifica.getLegend().setOrientation(Legend.LegendOrientation.HORIZONTAL);
        pastelCategoriaCientifica.setEntryLabelColor(Color.BLACK);
        pastelCategoriaCientifica.setEntryLabelTextSize(15f);
        pastelCategoriaCientifica.animate();

        pastelTrabajadores.getDescription().setEnabled(false);
        pastelTrabajadores.getLegend().setWordWrapEnabled(true);
        pastelTrabajadores.getLegend().setTextSize(14f);
        pastelTrabajadores.getLegend().setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        pastelTrabajadores.getLegend().setOrientation(Legend.LegendOrientation.HORIZONTAL);
        pastelTrabajadores.setEntryLabelColor(Color.BLACK);
        pastelTrabajadores.setEntryLabelTextSize(15f);
        pastelTrabajadores.animate();

        /// Llenando graficos Pastel
        setValuesPastel();


        barraCategoriaDocente.getDescription().setEnabled(false);
        barraCategoriaDocente.getLegend().setWordWrapEnabled(true);
        barraCategoriaDocente.getLegend().setTextSize(14f);
        barraCategoriaDocente.getLegend().setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        barraCategoriaDocente.getLegend().setOrientation(Legend.LegendOrientation.HORIZONTAL);
        barraCategoriaDocente.animate();

        barraInteres.getDescription().setEnabled(false);
        barraInteres.getLegend().setWordWrapEnabled(true);
        barraInteres.getLegend().setTextSize(14f);
        barraInteres.getLegend().setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        barraInteres.getLegend().setOrientation(Legend.LegendOrientation.HORIZONTAL);
        barraInteres.animate();

        /// Llenando graficos Barra
        setValuesBarras();
    }

    private void setValuesPastel() {
        /////////////////////////////////////////////////////////////////

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
            public void onResponse(Call<AccesOdoo> call, Response<AccesOdoo> response) {
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

                    Call<Data> callS = service.getEstadisticas(params);
                    //////////////////////////////
                    PieDataSet pastelDataPlantilla = new PieDataSet(null, null);
                    PieDataSet pastelDataMatricula = new PieDataSet(null, null);
                    PieDataSet pastelDataCategoriaCientifica = new PieDataSet(null, null);
                    PieDataSet pastelDataTrabajadores = new PieDataSet(null, null);

                    ArrayList<PieEntry> dataPlantilla = new ArrayList<>();
                    ArrayList<PieEntry> dataMatricula = new ArrayList<>();
                    ArrayList<PieEntry> dataCategoriaCientifica = new ArrayList<>();
                    ArrayList<PieEntry> dataTrabajadores = new ArrayList<>();
                    ///////////////////////////////

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

                    callS.enqueue(new Callback<Data>() {
                        @Override
                        public void onResponse(Call<Data> callS, Response<Data> response) {
                            //Codigo de respuesta
                            // System.out.println("[Code: " + response.code() + "]");
                            if (response.isSuccessful()) {//si la peticion se completo con exito
                                Data data = response.body();
                                try {
                                    int result = 0;
                                    // System.out.println("Response:\n" + data.getData().get(0).get("name"));

                                    dataPlantilla.add(new PieEntry(Integer.parseInt(data.getData().get(1).get("cantidad").getAsString()), data.getData().get(1).get("name").getAsString()));
                                    // Se resta palntilla total - plantilla cubierta y ese valor es el que se muestra (Plantilla no cubierta)
                                    dataPlantilla.add(new PieEntry(Integer.parseInt(data.getData().get(0).get("cantidad").getAsString()) - Integer.parseInt(data.getData().get(1).get("cantidad").getAsString()), "No Cubierta"));


                                    PieData pieDataPlantilla = new PieData(pastelDataPlantilla);
                                    pastelDataPlantilla.setColors(ColorTemplate.JOYFUL_COLORS);
                                    pastelDataPlantilla.setValueTextColor(Color.BLACK);
                                    pastelDataPlantilla.setValueTextSize(19f);
                                    pastelDataPlantilla.setValues(dataPlantilla);
                                    pastelDataPlantilla.setFormLineWidth(4);
                                    pastelPlantilla.setData(pieDataPlantilla);
                                    pastelPlantilla.setCenterTextSize(20f);
                                    pastelPlantilla.setCenterText("Total" + "\n" + data.getData().get(0).get("cantidad").getAsString()); // Plantilla Total
                                    pastelPlantilla.highlightValues(null);
                                    pastelPlantilla.invalidate();

                                    for (int i = 2; i < 4; i++) {
                                        dataTrabajadores.add(new PieEntry(Integer.parseInt(data.getData().get(i).get("cantidad").getAsString()), data.getData().get(i).get("name").getAsString()));
                                    }
                                    PieData pieDataTrabajadores = new PieData(pastelDataTrabajadores);
                                    pastelDataTrabajadores.setColors(ColorTemplate.LIBERTY_COLORS);
                                    pastelDataTrabajadores.setValueTextColor(Color.BLACK);
                                    pastelDataTrabajadores.setValueTextSize(19f);
                                    pastelDataTrabajadores.setValues(dataTrabajadores);
                                    pastelDataTrabajadores.setFormLineWidth(4);
                                    pastelTrabajadores.setData(pieDataTrabajadores);
                                    pastelTrabajadores.highlightValues(null);
                                    pastelTrabajadores.invalidate();

                                    for (int i = 4; i < 10; i++) {
                                        dataCategoriaCientifica.add(new PieEntry(Integer.parseInt(data.getData().get(i).get("cantidad").getAsString()), data.getData().get(i).get("name").getAsString()));
                                    }
                                    PieData pieDataCategoriaCientifica = new PieData(pastelDataCategoriaCientifica);
                                    pastelDataCategoriaCientifica.setColors(colors);
                                    pastelDataCategoriaCientifica.setValueTextColor(Color.BLACK);
                                    pastelDataCategoriaCientifica.setValueTextSize(19f);
                                    pastelDataCategoriaCientifica.setValues(dataCategoriaCientifica);
                                    pastelDataCategoriaCientifica.setFormLineWidth(4);
                                    pastelCategoriaCientifica.setData(pieDataCategoriaCientifica);
                                    pastelCategoriaCientifica.highlightValues(null);
                                    pastelCategoriaCientifica.invalidate();

                                    for (int i = 18; i < 22; i++) {
                                        dataMatricula.add(new PieEntry(Integer.parseInt(data.getData().get(i).get("cantidad").getAsString()), data.getData().get(i).get("name").getAsString()));
                                        result = result + Integer.parseInt(data.getData().get(i).get("cantidad").getAsString());
                                    }
                                    //////////////////
                                    PieData pieDataMatricula = new PieData(pastelDataMatricula);
                                    pastelDataMatricula.setColors(colors);
                                    pastelDataMatricula.setValueTextColor(Color.BLACK);
                                    pastelDataMatricula.setValueTextSize(19f);
                                    pastelDataMatricula.setValues(dataMatricula);
                                    pastelDataMatricula.setFormLineWidth(4);
                                    pastelMatricula.setData(pieDataMatricula);
                                    pastelMatricula.setCenterTextSize(20f);
                                    pastelMatricula.setCenterText("Total" + "\n" + result);
                                    pastelMatricula.highlightValues(null);
                                    pastelMatricula.invalidate();

                                    /// Cantidad e Becados
                                    cantidadBecados.setText(data.getData().get(22).get("cantidad").getAsString());

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

    private void setValuesBarras() {
        /////////////////////////////////////////////////////////////////

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
            public void onResponse(Call<AccesOdoo> call, Response<AccesOdoo> response) {
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

                    Call<Data> callS = service.getEstadisticas(params);
                    //////////////////////////////

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

                    BarDataSet DataInteres;
                    ArrayList<BarEntry> dataCategoriaDocente1 = new ArrayList<>();
                    ArrayList<BarEntry> dataCategoriaDocente2 = new ArrayList<>();
                    ArrayList<BarEntry> dataCategoriaDocente3 = new ArrayList<>();
                    ArrayList<BarEntry> dataCategoriaDocente4 = new ArrayList<>();
                    ArrayList<BarEntry> dataCategoriaDocente5 = new ArrayList<>();
                    ArrayList<BarEntry> dataInteres = new ArrayList<>();
                    ///////////////////////////////

                    callS.enqueue(new Callback<Data>() {
                        @Override
                        public void onResponse(Call<Data> callS, Response<Data> response) {
                            //Codigo de respuesta
                            // System.out.println("[Code: " + response.code() + "]");
                            if (response.isSuccessful()) {//si la peticion se completo con exito
                                Data data = response.body();
                                try {
                                    int result = 0;
                                    System.out.println("Response:\n" + data.getData().get(0).get("name"));

                                    dataCategoriaDocente1.add(new BarEntry(0, Float.parseFloat(data.getData().get(10).get("cantidad").getAsString())));
                                    dataCategoriaDocente2.add(new BarEntry(1, Float.parseFloat(data.getData().get(11).get("cantidad").getAsString())));
                                    dataCategoriaDocente3.add(new BarEntry(2, Float.parseFloat(data.getData().get(12).get("cantidad").getAsString())));
                                    dataCategoriaDocente4.add(new BarEntry(3, Float.parseFloat(data.getData().get(13).get("cantidad").getAsString())));
                                    dataCategoriaDocente5.add(new BarEntry(4, Float.parseFloat(data.getData().get(14).get("cantidad").getAsString())));

                                    BarDataSet DataCategoriaDocente1, DataCategoriaDocente2, DataCategoriaDocente3, DataCategoriaDocente4, DataCategoriaDocente5;

                                    DataCategoriaDocente1 = new BarDataSet(dataCategoriaDocente1, data.getData().get(10).get("name").getAsString());
                                    DataCategoriaDocente2 = new BarDataSet(dataCategoriaDocente2, data.getData().get(11).get("name").getAsString());
                                    DataCategoriaDocente3 = new BarDataSet(dataCategoriaDocente3, data.getData().get(12).get("name").getAsString());
                                    DataCategoriaDocente4 = new BarDataSet(dataCategoriaDocente4, data.getData().get(13).get("name").getAsString());
                                    DataCategoriaDocente5 = new BarDataSet(dataCategoriaDocente5, data.getData().get(14).get("name").getAsString());

                                    DataCategoriaDocente1.setValues(dataCategoriaDocente1);
                                    DataCategoriaDocente2.setValues(dataCategoriaDocente2);
                                    DataCategoriaDocente3.setValues(dataCategoriaDocente3);
                                    DataCategoriaDocente4.setValues(dataCategoriaDocente4);
                                    DataCategoriaDocente5.setValues(dataCategoriaDocente5);

                                    DataCategoriaDocente1.setColors(ColorTemplate.VORDIPLOM_COLORS);
                                    DataCategoriaDocente2.setColors(ColorTemplate.LIBERTY_COLORS);
                                    DataCategoriaDocente3.setColors(ColorTemplate.MATERIAL_COLORS);
                                    DataCategoriaDocente4.setColors(ColorTemplate.JOYFUL_COLORS);
                                    DataCategoriaDocente5.setColors(ColorTemplate.PASTEL_COLORS);

                                    BarData barraDataCatDocente = new BarData(DataCategoriaDocente1, DataCategoriaDocente2, DataCategoriaDocente3, DataCategoriaDocente4, DataCategoriaDocente5);
                                    barraDataCatDocente.setValueTextColor(Color.BLACK);
                                    barraDataCatDocente.setValueTextSize(19f);
                                    barraCategoriaDocente.setData(barraDataCatDocente);
                                    barraCategoriaDocente.getData().notifyDataChanged();
                                    barraCategoriaDocente.notifyDataSetChanged();
                                    barraCategoriaDocente.highlightValues(null);
                                    barraCategoriaDocente.invalidate();

                                    ArrayList<BarEntry> dataInteres1 = new ArrayList<>();
                                    ArrayList<BarEntry> dataInteres2 = new ArrayList<>();
                                    ArrayList<BarEntry> dataInteres3 = new ArrayList<>();

                                    dataInteres1.add(new BarEntry(0, Float.parseFloat(data.getData().get(15).get("cantidad").getAsString())));
                                    dataInteres2.add(new BarEntry(1, Float.parseFloat(data.getData().get(16).get("cantidad").getAsString())));
                                    dataInteres3.add(new BarEntry(2, Float.parseFloat(data.getData().get(17).get("cantidad").getAsString())));

                                    BarDataSet DataSetInteres1, DataSetInteres2, DataSetInteres3;

                                    DataSetInteres1 = new BarDataSet(dataInteres1, data.getData().get(15).get("name").getAsString());
                                    DataSetInteres2 = new BarDataSet(dataInteres2, data.getData().get(16).get("name").getAsString());
                                    DataSetInteres3 = new BarDataSet(dataInteres3, data.getData().get(17).get("name").getAsString());


                                    DataSetInteres1.setValues(dataInteres1);
                                    DataSetInteres2.setValues(dataInteres2);
                                    DataSetInteres3.setValues(dataInteres3);


                                    DataSetInteres1.setColors(ColorTemplate.VORDIPLOM_COLORS);
                                    DataSetInteres2.setColors(ColorTemplate.LIBERTY_COLORS);
                                    DataSetInteres3.setColors(ColorTemplate.MATERIAL_COLORS);


                                    BarData barraDataInteres = new BarData(DataSetInteres1, DataSetInteres2, DataSetInteres3);
                                    barraDataInteres.setValueTextColor(Color.BLACK);
                                    barraDataInteres.setValueTextSize(19f);
                                    barraInteres.setData(barraDataInteres);
                                    barraInteres.getData().notifyDataChanged();
                                    barraInteres.notifyDataSetChanged();
                                    barraInteres.highlightValues(null);
                                    barraInteres.invalidate();


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