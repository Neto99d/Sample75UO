package com.neto.sample75uo.ui.modelsOdoo;

import com.google.gson.JsonArray;

public class Data {
    JsonArray data;

    public Data(JsonArray  data) {
        this.data = data;
    }

    public Data() {
    }


    public JsonArray  getData() {
        return data;
    }

    public void setData(JsonArray  data) {
        this.data = data;
    }
}
