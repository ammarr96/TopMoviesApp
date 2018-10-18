package com.example.amarlubovac.topmovies;

import java.io.Serializable;

public class TVShow implements Serializable {
    int id;
    String name;
    String posterpath;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPosterpath() {
        return posterpath;
    }

    public void setPosterpath(String posterpath) {
        this.posterpath = posterpath;
    }
}
