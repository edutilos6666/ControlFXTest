package org.ddg.dao;

import org.ddg.model.WorkerWithDate;

import java.sql.Time;
import java.util.Date;
import java.util.List;

/**
 * Created by edutilos on 26.10.18.
 */
public interface WorkerWithDateDAO {
    void create(WorkerWithDate one);
    void update(long id, WorkerWithDate newOne);
    void delete(long id);
    WorkerWithDate findById(long id);
    List<WorkerWithDate> findByFname(String fname);
    List<WorkerWithDate> findByLname(String lname);
    List<WorkerWithDate> findByAgeMinMax(boolean inclusive, int minAge, int maxAge);
    List<WorkerWithDate> findByWageMinMax(boolean inclusive, double minWage, double maxWage);
    List<WorkerWithDate> findByActive(boolean active);
    List<WorkerWithDate> findByActivity(String... activity);
    List<WorkerWithDate> findByCountry(String country);
    List<WorkerWithDate> findByCity(String city);
    List<WorkerWithDate> findByStreet(String street);
    List<WorkerWithDate> findByPlz(String plz);
    List<WorkerWithDate> findByDateFromTo(Date from, Date to);
    List<WorkerWithDate> findBySqlDateFromTo(java.sql.Date from, java.sql.Date to);
    List<WorkerWithDate> findByHeuteBetween(Time from, Time to);
    List<WorkerWithDate> findAll();
    void clear();
}
