package org.ddg.model;

import lombok.Data;

import java.util.List;

@Data
public class OldWorker {
    private long id;
    private String fname;
    private String lname;
    private int age;
    private double wage;
    private boolean active;
    private List<String> activities;
    private String country;
    private String city;
    private String street;
    private String plz;

    public OldWorker(long id, String fname, String lname, int age, double wage, boolean active, List<String> activities, String country, String city, String street, String plz) {
        this.id = id;
        this.fname = fname;
        this.lname = lname;
        this.age = age;
        this.wage = wage;
        this.active = active;
        this.activities = activities;
        this.country = country;
        this.city = city;
        this.street = street;
        this.plz = plz;
    }

    public OldWorker() {
    }
}
