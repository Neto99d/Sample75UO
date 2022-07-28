package com.uo75.ernestoDuvalonUO.ui.modelsOdoo;

public class RedesSociales {
    String link_Facebook;
    String link_Instagram;
    String link_Linkedin;
    String link_Telegram;
    String link_Twitter ;
    String link_Youtube;

    public RedesSociales(String link_Facebook, String link_Instagram, String link_Linkedin, String link_Telegram, String link_Twitter, String link_Youtube) {
        this.link_Facebook = link_Facebook;
        this.link_Instagram = link_Instagram;
        this.link_Linkedin = link_Linkedin;
        this.link_Telegram = link_Telegram;
        this.link_Twitter = link_Twitter;
        this.link_Youtube = link_Youtube;
    }

    public RedesSociales() {
    }

    public String getLink_Facebook() {
        return link_Facebook;
    }

    public void setLink_Facebook(String link_Facebook) {
        this.link_Facebook = link_Facebook;
    }

    public String getLink_Instagram() {
        return link_Instagram;
    }

    public void setLink_Instagram(String link_Instagram) {
        this.link_Instagram = link_Instagram;
    }

    public String getLink_Linkedin() {
        return link_Linkedin;
    }

    public void setLink_Linkedin(String link_Linkedin) {
        this.link_Linkedin = link_Linkedin;
    }

    public String getLink_Telegram() {
        return link_Telegram;
    }

    public void setLink_Telegram(String link_Telegram) {
        this.link_Telegram = link_Telegram;
    }

    public String getLink_Twitter() {
        return link_Twitter;
    }

    public void setLink_Twitter(String link_Twitter) {
        this.link_Twitter = link_Twitter;
    }

    public String getLink_Youtube() {
        return link_Youtube;
    }

    public void setLink_Youtube(String link_Youtube) {
        this.link_Youtube = link_Youtube;
    }
}
