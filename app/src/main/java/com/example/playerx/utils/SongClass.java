package com.example.playerx.utils;

import android.widget.ImageView;
import android.widget.TextView;

public class SongClass {

    String  song;
    String info;

    public SongClass(String song, String info) {
        this.song = song;
        this.info = info;
    }

    public SongClass() {
    }

    public String getSong() {
        return song;
    }

    public void setSong(String song) {
        this.song = song;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
