package com.example.chernobyl.classes;

import com.squareup.moshi.Json;

public class AppData {

    @Json(name = "id")
    private int id;

    @Json(name = "title")
    private String title;

    @Json(name = "appUrl")
    private String appUrl;

    @Json(name = "appTitle")
    private String appTitle;

    @Json(name = "appMainImage")
    private String appMainImage;

    public AppData(int id, String title, String appUrl, String appTitle, String appMainImage) {
        this.id = id;
        this.title = title;
        this.appUrl = appUrl;
        this.appTitle = appTitle;
        this.appMainImage = appMainImage;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getAppUrl() {
        return appUrl;
    }

    public String getAppTitle() {
        return appTitle;
    }

    public String getAppMainImage() {
        return appMainImage;
    }
}
