package com.dcomiUO75.netoduvalon_dev.ui.modelsOdoo;

public class AccesOdoo {
    String access_token;

    public AccesOdoo(String accesToken) {
        this.access_token = accesToken;
    }

    public AccesOdoo() {
    }

    public String getAccesToken() {
        return access_token;
    }

    public void setAccesToken(String accesToken) {
        this.access_token = accesToken;
    }
}
