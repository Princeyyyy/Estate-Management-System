package com.example.estate_management_system;

public class UserModel {

    private String id;
    private String houseno;
    private String fname;
    private String lname;
    private String rent;
    private String due_date;


    public UserModel() {
    }

    public UserModel(String id, String houseno, String fname, String lname, String rent, String due_date) {
        this.id = id;
        this.houseno = houseno;
        this.fname = fname;
        this.lname = lname;
        this.rent = rent;
        this.due_date = due_date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHouseno() {
        return houseno;
    }

    public void setHouseno(String houseno) {
        this.houseno = houseno;
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
