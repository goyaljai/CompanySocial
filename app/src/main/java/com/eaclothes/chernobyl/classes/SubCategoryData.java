package com.eaclothes.chernobyl.classes;

import com.squareup.moshi.Json;

public class SubCategoryData {
    @Json(name = "id")
    private int id;

    @Json(name = "imagesUrl")
    private String imagesUrl;

    @Json(name = "imagesTitle")
    private String imagesTitle;

    @Json(name = "imagesDescription")
    private String imagesDescription;

    public SubCategoryData(int id, String imagesUrl, String imagesTitle, String imagesDescription) {
        this.id = id;
        this.imagesUrl = imagesUrl;
        this.imagesTitle = imagesTitle;
        this.imagesDescription = imagesDescription;
    }

    public int getId() {
        return id;
    }

    public String getUrl() {
        return imagesUrl;
    }

    public String getImgtitle() {
        return imagesTitle;
    }

    public String getImgdesc() {
        return imagesDescription;
    }
}
