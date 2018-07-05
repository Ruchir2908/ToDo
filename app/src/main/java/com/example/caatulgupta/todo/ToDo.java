package com.example.caatulgupta.todo;

import android.os.Parcelable;

import java.io.Serializable;

public class ToDo implements Serializable {

    private long id;
    private String title;
    private String description;
    private String date;
    private String time;
    private String dtCreated;
    private int star;

    public int isStar() {
        return star;
    }

    public void setStar(int star) {
        this.star = star;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDtCreated() {
        return dtCreated;
    }

    public void setDtCreated(String dtCreated) {
        this.dtCreated = dtCreated;
    }


    public ToDo(String title, String description, String date, String time,String dtCreated) {
        this.title = title;
        this.description = description;
        this.date = date;
        this.time = time;
        this.dtCreated = dtCreated;
        this.star = 0;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
