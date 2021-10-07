package com.agntic.waves.Music;

/**
 * Created by root on 2/3/16.
 */
public class CatModels {

    String Name;
    String Url_img;
    String artist;
    //int Position;

    public CatModels(String name, String url_img, String artist) {
        Name = name;
        Url_img = url_img;
        this.artist = artist;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getUrl_img() {
        return Url_img;
    }

    public void setUrl_img(String numb) {
        Url_img = numb;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String numb) {
        artist = numb;
    }

}
