package com.example.chernobyl;

import com.example.chernobyl.classes.SubCategory;

import java.util.ArrayList;

public class MainSubCategory {
    int id;
    String description;
    ArrayList<SubCategory> subCategoryArrayList;

    public MainSubCategory( int id, String description, ArrayList<SubCategory> subCategoryArrayList){
        this.id=id;
        this.description=description;
        this.subCategoryArrayList = subCategoryArrayList;
    }
}
