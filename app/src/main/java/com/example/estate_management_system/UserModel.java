package com.example.estate_management_system;

public class UserModel {

    private String houseid;
    private String fname;
    private String lname;
    private String rent;
    private String due_date;


    public UserModel() {
    }

    public UserModel(String houseid, String fname, String lname, String rent, String due_date) {
        this.houseid = houseid;
        this.fname = fname;
        this.lname = lname;
        this.rent = rent;
        this.due_date = due_date;
    }

    public String getHouseid() {
        return houseid;
    }

    public void setHouseid(String houseid) {
        this.houseid = houseid;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getRent() {
        return rent;
    }

    public void setRent(String rent) {
        this.rent = rent;
    }

    public String getDue_date() {
        return due_date;
    }

    public void setDue_date(String due_date) {
        this.due_date = due_date;
    }
}
