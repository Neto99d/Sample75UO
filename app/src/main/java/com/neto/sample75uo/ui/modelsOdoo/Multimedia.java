package com.neto.sample75uo.ui.modelsOdoo;

public class Multimedia {
    String videos;
    String audios;
    String name;

    public Multimedia(String videos, String audios, String acerca_de) {
        this.videos = videos;
        this.audios = audios;
        this.name = acerca_de;
    }

    public Multimedia() {
    }

    public String getVideo() {
        return videos;
    }

    public void setVideo(String video) {
        this.videos = video;
    }

    public String getAudio() {
        return audios;
    }

    public void setAudio(String audio) {
        this.audios = audio;
    }

    public String getAcerca_de() {
        return name;
    }

    public void setAcerca_de(String acerca_de) {
        this.name = acerca_de;
    }
}
