package com.example.wgu_c196.model;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.Date;
@Entity(tableName = "assessments")
public class mAssessment {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String title;
    private Date date;
    private mAssessType assesstype;
    private int crseId;

    public mAssessment(String title, Date date, mAssessType assesstype) {
        this.title = title;
        this.date = date;
        this.assesstype = assesstype;
    }
    @Ignore
    public mAssessment(String title, Date date, mAssessType assesstype, int crseId) {
        this.title = title;
        this.date = date;
        this.assesstype = assesstype;
        this.crseId = crseId;
    }
    @Ignore
    public mAssessment() {
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setAssesstype(mAssessType assesstype) {
        this.assesstype = assesstype;
    }

    public void setCrseId(int crseId) {
        this.crseId = crseId;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public Date getDate() {
        return date;
    }

    public mAssessType getAssesstype() {
        return assesstype;
    }

    public int getCrseId() {
        return crseId;
    }


}
