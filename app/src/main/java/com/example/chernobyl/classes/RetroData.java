package com.example.chernobyl.classes;

public class RetroData {
    private String name;
    private String[] image_url;
    private String[] summary;
    private String[] title;
    public RetroData(String name, String[] image_url, String[] summary, String[] title){
        this.name= name;
        this.image_url=image_url;
        this.summary=summary;
        this.title=title;
    }

    public String getName() {
        return name;
    }

    public String[] getImageUrl() {
        return image_url;
    }

    public String[] getSummary() {
        return summary;
    }

    public String[] getTitle() {
        return title;
    }
}
