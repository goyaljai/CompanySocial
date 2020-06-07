package com.eaclothes.chernobyl.classes;

import com.squareup.moshi.Json;

public class MainCategoryData {
    @Json(name = "id")
    private int id;

    @Json(name = "title")
    private String title;

    public MainCategoryData(int id, String title) {
        this.id = id;
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

}
