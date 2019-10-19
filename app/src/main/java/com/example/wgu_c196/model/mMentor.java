package com.example.wgu_c196.model;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "mentors")
public class mMentor {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;
    private String phonenumber;
    private int crseId;
    private String emailaddress;

    public mMentor(String name, String emailaddress, String phonenumber) {
        this.name = name;
        this.emailaddress = emailaddress;
        this.phonenumber = phonenumber;
    }
    @Ignore
    public mMentor(String name, String emailaddress, String phonenumber, int crseId) {
        this.name = name;
        this.emailaddress = emailaddress;
        this.phonenumber = phonenumber;
        this.crseId = crseId;
    }
    @Ignore
    public mMentor() {
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmailaddress(String emailaddress) {
        this.emailaddress = emailaddress;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public void setCrseId(int crseId) {
        this.crseId = crseId;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmailaddress() {
        return emailaddress;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public int getCrseId() {
        return crseId;
    }


}
