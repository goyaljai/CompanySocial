package com.eaclothes.chernobyl.classes;

import com.squareup.moshi.Json;

import java.util.List;

public class MainSubCategoryData {
    @Json(name = "id")
    private int id;

    @Json(name = "description")
    private String description;

    @Json(name = "subcategories")
    private List<SubCategoryData> subcategories;

    public MainSubCategoryData(int id, String description, List<SubCategoryData> subcategories) {
        this.id = id;
        this.description = description;
        this.subcategories = subcategories;
    }

    public int getId() {
        return id;
    }
    public String getDescription(){return description;}
    public List<SubCategoryData> getSubcategories() {
        return subcategories;
    }

}