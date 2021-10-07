package com.agntic.waves.ListVOD.models;

/**
 * Created by root on 2/3/16.
 */
public class Mobile {

    String Name;
    String Url_img;
    String Url_video;
    String Dis;
    String Cat;
    String thumbnailBG;
    int TotalCat;
    //int Position;

    public Mobile(String name, String url_img,String url_video,String dis,String cat,int totalCat,String thumbnailbG) {
        Name = name;
        Url_img = url_img;
        Url_video = url_video;
        Dis = dis;
        Cat = cat;
        TotalCat = totalCat;
        thumbnailBG = thumbnailbG;
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

    public String getUrl_video() {
        return Url_video;
    }

    public void setUrl_video(String url) {
        Url_video = url;
    }

    public String getDis() {
        return Dis;
    }

    public void setDis(String url) {
        Dis = url;
    }

    public String getCat() {
        return Cat;
    }

    public void setCat(String url) {
        Cat = url;
    }

    public int getTotalCat() {
        return TotalCat;
    }

    public void setTotalCat(int url) {
        TotalCat = url;
    }

    public String getthumbnailBG() {
        return thumbnailBG;
    }

    public void setthumbnailBG(String position) {
        thumbnailBG = position;
    }

}
