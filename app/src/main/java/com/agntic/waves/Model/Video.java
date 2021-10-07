package com.agntic.waves.Model;

/**
 * Created by FURY on 12/1/2017.
 */

public class Video {

    String Name;
    String Url;
    String Numb;

    public Video(String name, String url,String numb) {
        Name = name;
        Url = url;
        Numb = numb;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getNumb() {
        return Numb;
    }

    public void setNumb(String numb) {
        Numb = numb;
    }

    public String getUrl() {
        return Url;
    }

    public void setUrl(String url) {
        Url = url;
    }
}
