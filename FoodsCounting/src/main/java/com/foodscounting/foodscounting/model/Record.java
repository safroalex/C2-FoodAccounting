package com.foodscounting.foodscounting.model;

public class Record {
    private int id;
    private String content;

    public Record(int id, String content) {
        this.id = id;
        this.content = content;
    }

    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
