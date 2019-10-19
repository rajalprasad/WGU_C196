package com.example.wgu_c196.model;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.Date;
@Entity(tableName = "courses")
public class mCourse {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String title;
    private Date strtDate;
    private Date endDate;
    private mCourseStat crseStat;
    private String note;
    private int trmID;

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setStrtDate(Date strtDate) {
        this.strtDate = strtDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public void setCrseStat(mCourseStat crseStat) {
        this.crseStat = crseStat;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public void setTrmID(int trmID) {
        this.trmID = trmID;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public Date getStrtDate() {
        return strtDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public mCourseStat getCrseStat() {
        return crseStat;
    }

    public String getNote() {
        return note;
    }

    public int getTrmID() {
        return trmID;
    }

    public mCourse(String title, Date strtDate, Date endDate, mCourseStat crseStat) {
        this.title = title;
        this.strtDate = strtDate;
        this.endDate = endDate;
        this.crseStat = crseStat;
    }
    @Ignore
    public mCourse() {
    }

    @Ignore
    public mCourse(String title, Date strtDate, Date endDate, mCourseStat crseStat, int trmID) {
        this.title = title;
        this.strtDate = strtDate;
        this.endDate = endDate;
        this.crseStat = crseStat;
        this.trmID = trmID;
    }
    @Ignore
    public mCourse(int id, String title, Date strtDate, Date endDate, mCourseStat crseStat, String note, int trmID) {
        this.id = id;
        this.title = title;
        this.strtDate = strtDate;
        this.endDate = endDate;
        this.crseStat = crseStat;
        this.note = note;
        this.trmID = trmID;
    }
}
