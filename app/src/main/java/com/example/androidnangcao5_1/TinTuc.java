package com.example.androidnangcao5_1;

public class TinTuc {
//    public String title;
//    public String description;
//    public String pubDate;
//    public String link;
//    public String guiid;
    String id;
    String title;
    String des;
    String pubDate;

    public TinTuc() {
    }

    public TinTuc(String id, String title, String des, String pubDate) {
        this.id = id;
        this.title = title;
        this.des = des;
        this.pubDate = pubDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public String getPubDate() {
        return pubDate;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }
}
