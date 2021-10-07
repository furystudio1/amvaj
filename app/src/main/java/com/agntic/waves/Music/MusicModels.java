package com.agntic.waves.Music;

/**
 * Created by root on 2/3/16.
 */
public class MusicModels {

    private String Name;
    private String Url_img;
    private String artist;
    private String author;
    //int Position;

    public MusicModels(String name, String url_img, String artist, String author) {
        Name = name;
        Url_img = url_img;
        this.artist = artist;
        this.author = author;
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

    public String getAuthor() {
        return author;
    }

    public void setauthor(String numb) {
        author = numb;
    }



}
