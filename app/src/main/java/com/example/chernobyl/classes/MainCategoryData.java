package com.example.chernobyl.classes;

public class MainCategoryData {
    private int id;
    private String title;
    private String imagesUrl;
    private String imagesTitle;
    private String imagesDescription;

    public MainCategoryData(int id, String title, String imagesUrl, String imagesTitle, String imagesDescription) {
        this.id = id;
        this.title = title;
        this.imagesUrl = imagesUrl;
        this.imagesTitle = imagesTitle;
        this.imagesDescription = imagesDescription;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
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
