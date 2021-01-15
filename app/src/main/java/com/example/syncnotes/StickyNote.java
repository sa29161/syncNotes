package com.example.syncnotes;

public class StickyNote {
private String description;
private String id;

    public String getId() {
        return id;
    }


    public StickyNote(){

    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public void setId(String id) {
        this.id = id;
    }
}
