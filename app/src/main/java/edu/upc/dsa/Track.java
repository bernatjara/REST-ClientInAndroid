package edu.upc.dsa;


import java.io.Serializable;

public class Track implements Serializable {
    String id;
    String singer;
    String title;

    public Track(String singer, String title){
        this.singer=singer;
        this.title=title;
    }
    public Track(){}

    public String getSinger(){
        return singer;
    }

    public void setSinger(String singer) {
        this.singer = singer;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
