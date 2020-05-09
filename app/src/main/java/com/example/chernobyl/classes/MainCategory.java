package com.example.chernobyl.classes;

import com.example.chernobyl.MainSubCategory;

import java.util.ArrayList;
import java.util.List;

public class MainCategory {
    private int id;
    private String tabName;
    private List<MainSubCategoryData> mMainSubCategories;
    private List<MainSubCategory> mainUISubCategories;
    private ArrayList<String> subRandomImageList;

    public MainCategory(int id, String tabName, List<MainSubCategoryData> subCategories) {
        this.id = id;
        this.mMainSubCategories = subCategories;
        this.tabName = tabName;
    }

    public MainCategory(List<MainSubCategory> mainSubCategories, int id, String tabName, ArrayList<String> subRandomImageList) {
        this.id = id;
        this.mainUISubCategories = mainSubCategories;
        this.tabName = tabName;
        this.subRandomImageList = subRandomImageList;
    }

    public int getId() {
        return this.id;
    }

    public String getTitle() {
        return this.tabName;
    }

    public List<MainSubCategoryData> getmMainSubCategories() {
        return this.mMainSubCategories;
    }

    public List<MainSubCategory> getMainUISubCategories() {
        return this.mainUISubCategories;
    }

    public ArrayList<String> getRandomImageList() {
        return this.subRandomImageList;
    }
}