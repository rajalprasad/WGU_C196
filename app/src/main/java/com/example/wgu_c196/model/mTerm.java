package com.example.wgu_c196.model;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "terms")
public class mTerm {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String title;
    private Date strtDate;
    private Date endDate;

    public mTerm(String title, Date strtDate, Date endDate) {
        this.title = title;
        this.strtDate = strtDate;
        this.endDate = endDate;
    }
    @Ignore
    public mTerm(int id, String title, Date strtDate, Date endDate) {
        this.id = id;
        this.title = title;
        this.strtDate = strtDate;
        this.endDate = endDate;
    }
    @Ignore
    public mTerm() {
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

    public Date getStrtDate() {
        return strtDate;
    }

    public void setStrtDate(Date strtDate) {
        this.strtDate = strtDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}
