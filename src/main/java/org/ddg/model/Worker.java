package org.ddg.model;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="worker")
@EqualsAndHashCode
public class Worker {
    private LongProperty id = new SimpleLongProperty();
    private StringProperty fname = new SimpleStringProperty();
    private StringProperty lname = new SimpleStringProperty();
    private IntegerProperty age = new SimpleIntegerProperty();
    private DoubleProperty wage = new SimpleDoubleProperty();
    private BooleanProperty active = new SimpleBooleanProperty();
    private ListProperty<String> activities = new SimpleListProperty<>();
    private StringProperty country = new SimpleStringProperty();
    private StringProperty city = new SimpleStringProperty();
    private StringProperty street = new SimpleStringProperty();
    private StringProperty plz = new SimpleStringProperty();

    public Worker(long id, String fname, String lname, int age, double wage, boolean active, List<String> activities, String country, String city, String street, String plz) {
        this.id.set(id);
        this.fname.set(fname);
        this.lname.set(lname);
        this.age.set(age);
        this.wage.set(wage);
        this.active.set(active);
        this.activities.set(FXCollections.observableArrayList(activities));
        this.country.set(country);
        this.city.set(city);
        this.street.set(street);
        this.plz.set(plz);
    }

    public Worker(String fname, String lname, int age, double wage, boolean active, List<String> activities, String country, String city, String street, String plz) {
        this.fname.set(fname);
        this.lname.set(lname);
        this.age.set(age);
        this.wage.set(wage);
        this.active.set(active);
        this.activities.set(FXCollections.observableArrayList(activities));
        this.country.set(country);
        this.city.set(city);
        this.street.set(street);
        this.plz.set(plz);
    }


    public Worker() {
    }


    public LongProperty idProperty() {
        return id;
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    public long getId() {
        return id.get();
    }
    public void setId(long id) {
        this.id.set(id);
    }

    public StringProperty fnameProperty() {
        return fname;
    }
    @Column(name="fname")
    public String getFname() {
        return fname.get();
    }
    public void setFname(String fname) {
        this.fname.set(fname);
    }
    public StringProperty lnameProperty() {
        return lname;
    }
    @Column(name="lname")
    public String getLname() {
        return lname.get();
    }
    public void setLname(String lname) {
        this.lname.set(lname);
    }

    public IntegerProperty ageProperty() {
        return age;
    }
    @Column(name="age")
    public int getAge() {
        return age.get();
    }
    public void setAge(int age) {
        this.age.set(age);
    }
    public DoubleProperty wageProperty() {
        return wage;
    }
    @Column(name="wage")
    public double getWage() {
        return wage.get();
    }
    public void setWage(double wage) {
        this.wage.set(wage);
    }
    public BooleanProperty activeProperty() {
        return active;
    }
    @Column(name="active")
    public boolean isActive() {
        return active.get();
    }
    public void setActive(boolean active) {
        this.active.set(active);
    }

    public ListProperty<String> activitiesProperty() {
        return activities;
    }
    @ElementCollection
    public List<String> getActivities() {
        return activities.get();
    }

    public void setActivities(List<String> activities) {
        this.activities.set(FXCollections.observableArrayList(activities));
    }

    public StringProperty countryProperty() {
        return country;
    }
    @Column(name="country")
    public String getCountry() {
        return country.get();
    }
    public void setCountry(String country) {
        this.country.set(country);
    }

    public StringProperty cityProperty() {
        return city;
    }
    @Column(name="city")
    public String getCity() {
        return city.get();
    }
    public void setCity(String city) {
        this.city.set(city);
    }

    public StringProperty streetProperty() {
        return street;
    }
    @Column(name="street")
    public String getStreet() {
        return street.get();
    }
    public void setStreet(String street) {
        this.street.set(street);
    }

    public StringProperty plzProperty() {
        return plz;
    }
    @Column(name="plz")
    public String getPlz() {
        return plz.get();
    }
    public void setPlz(String plz) {
        this.plz.set(plz);
    }

    @Override
    public String toString() {
        return "Worker{" +
                "id=" + id.get() +
                ", fname=" + fname.get() +
                ", lname=" + lname.get() +
                ", age=" + age.get() +
                ", wage=" + wage.get() +
                ", active=" + active.get() +
                ", activities=" + activities.get() +
                ", country=" + country.get() +
                ", city=" + city.get() +
                ", street=" + street.get() +
                ", plz=" + plz.get() +
                '}';
    }
}
