package com.example.chernobyl;

import android.widget.AdapterView.OnItemClickListener;

import com.example.chernobyl.classes.MainCategory;

public class DrawerItem {

    private int id = 0;
    private String title = "";
    private int counter = 0;
    private boolean isGroupTitle = false;
    private int icon = 0;
    private String tagColor = null;
    private MainSubCategory mainCategory = null;

    public DrawerItem(int id, String title, MainSubCategory mainCategory, int counter) {
        this.id = id;
        this.title = title;
        this.counter = counter;
        this.mainCategory = mainCategory;
    }

    public DrawerItem(int id, String title, MainSubCategory mainCategory, int counter,
                      int icon) {
        this.id = id;
        this.title = title;
        this.counter = counter;
        this.icon = icon;
        this.mainCategory = mainCategory;
    }

    public DrawerItem(int id, String title, MainSubCategory mainCategory, int counter,
                      String tagColor) {
        this.id = id;
        this.title = title;
        this.counter = counter;
        this.tagColor = tagColor;
        this.mainCategory = mainCategory;
    }

    public DrawerItem(int id, String title, boolean isGroupTitle) {
        this.id = id;
        this.title = title;
        this.isGroupTitle = isGroupTitle;
    }

    public DrawerItem(int id, String title, int counter, boolean isGroupTitle) {
        this.id = id;
        this.title = title;
        this.counter = counter;
        this.isGroupTitle = isGroupTitle;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getCounter() {
        return counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }

    public void isGroupTitle(boolean flag) {
        this.isGroupTitle = flag;
    }

    public boolean isGroupTitle() {
        return this.isGroupTitle;
    }

    public void setIcon(int resource) {
        icon = resource;
    }

    public int getIcon() {
        return icon;
    }

    public void setTagColor(String tagColor) {
        this.tagColor = tagColor;
    }

    public String getTagColor() {
        return this.tagColor;
    }

    public MainSubCategory getMainCategory() {
        return mainCategory;
    }

    public void setMainCategory(MainSubCategory mainCategory) {
        this.mainCategory = mainCategory;
    }

    public interface DrawerItemClickListener extends OnItemClickListener {
    }
}