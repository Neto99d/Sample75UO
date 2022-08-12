package com.uo75.ernestoDuvalonUO.ui.modelsOdoo;


import com.google.gson.JsonObject;

import java.util.ArrayList;

public class Data {
    ArrayList<JsonObject> data;

    public Data(ArrayList<JsonObject> data) {
        this.data = data;
    }

    public Data() {
    }

    public ArrayList<JsonObject> getData() {
        return data;
    }

    public void setData(ArrayList<JsonObject> data) {
        this.data = data;
    }
}
