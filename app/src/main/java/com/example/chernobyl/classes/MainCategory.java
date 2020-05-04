package com.example.chernobyl.classes;

import com.example.chernobyl.MainSubCategory;

import java.util.ArrayList;

public class MainCategory {

    private String tabName;
    public ArrayList<MainSubCategory> mMainSubCategories;

    public MainCategory(String tabName,ArrayList<MainSubCategory> subCategories) {
         this.mMainSubCategories = subCategories;
        this.tabName = tabName;
    }
}