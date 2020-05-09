package com.example.chernobyl.classes;

import com.example.chernobyl.MainSubCategory;
import java.util.List;

public class MainCategory {
    private int id;
    private String tabName;
    private List<MainSubCategoryData> mMainSubCategories;
    private List<MainSubCategory> mainUISubCategories;

    public MainCategory(int id, String tabName,List<MainSubCategoryData> subCategories) {
        this.id = id;
        this.mMainSubCategories = subCategories;
        this.tabName = tabName;
    }

    public MainCategory(List<MainSubCategory> mainSubCategories, int id, String tabName) {
        this.id = id;
        this.mainUISubCategories = mainSubCategories;
        this.tabName = tabName;
    }

    public int getId(){
        return this.id;
    }
    public  String getTitle(){
        return this.tabName;
    }

    public List<MainSubCategoryData> getmMainSubCategories() {
        return this.mMainSubCategories;
    }

    public List<MainSubCategory> getMainUISubCategories(){
        return this.mainUISubCategories;
    }
}