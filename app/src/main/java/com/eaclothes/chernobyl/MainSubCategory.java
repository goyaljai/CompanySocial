package com.eaclothes.chernobyl;

import com.eaclothes.chernobyl.classes.SubCategory;

import java.io.Serializable;
import java.util.ArrayList;

public class MainSubCategory implements Serializable {
    int id;
    String description;
    ArrayList<SubCategory> subCategoryArrayList;

    public MainSubCategory(int id, String description, ArrayList<SubCategory> subCategoryArrayList) {
        this.id = id;
        this.description = description;
        this.subCategoryArrayList = subCategoryArrayList;
    }
}
