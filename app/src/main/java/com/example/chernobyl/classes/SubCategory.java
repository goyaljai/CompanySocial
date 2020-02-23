package com.example.chernobyl.classes;

public class SubCategory {
    // array of items

    public String name;
    public int[] image;
    public String[] summary;
    public String[] title;

    public SubCategory(String name, int[] image, String[] summary, String[] title) {
        this.name = name;
        this.image = image;
        this.summary = summary;
        this.title = title;
    }
}
