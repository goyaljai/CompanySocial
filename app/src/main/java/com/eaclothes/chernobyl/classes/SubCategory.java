package com.eaclothes.chernobyl.classes;

import java.io.Serializable;

public class SubCategory implements Serializable {

    public String name;
    public String image;
    public String summary;
    public String title;

    public SubCategory(String name, String image, String summary, String title) {
        this.name = name;
        this.image = image;
        this.summary = summary;
        this.title = title;
    }
}
