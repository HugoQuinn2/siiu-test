package com.hq.siiutest.models;

public class Row {
    private String name;
    private String time;

    public Row(String name, String time) {
        this.name = name;
        this.time = time;
    }

    public Row() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
