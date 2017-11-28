package com.example.kolot.hackathon;

/**
 * Created by kolot on 28.11.2017.
 */

public class MeetAll {
    private int id;
    private String title;
    private String content;
    private String createdAt;

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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatetAt) {
        this.updatedAt = updatedAt;
    }

    private String updatedAt;
}
