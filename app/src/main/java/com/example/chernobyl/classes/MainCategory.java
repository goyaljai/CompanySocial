package com.example.chernobyl.classes;

import java.util.ArrayList;

public class MainCategory {

    private String mName;
    public ArrayList<SubCategory> mCategories;

    public MainCategory(String name,ArrayList<SubCategory> subCategories) {
        mCategories = subCategories;
        mName = name;
    }
}