package org.ddg.dao;

import org.ddg.model.Worker;

import java.util.List;

/**
 * Created by edutilos on 26.10.18.
 */
public interface WorkerDAO {
    void create(Worker one);
    void update(long id, Worker newOne);
    void delete(long id);
    Worker findById(long id);
    List<Worker> findByFname(String fname);
    List<Worker> findByLname(String lname);
    List<Worker> findByAgeMinMax(boolean inclusive, int minAge, int maxAge);
    List<Worker> findByWageMinMax(boolean inclusive, double minWage, double maxWage);
    List<Worker> findByActive(boolean active);
    List<Worker> findByActivity(String... activity);
    List<Worker> findByCountry(String country);
    List<Worker> findByCity(String city);
    List<Worker> findByStreet(String street);
    List<Worker> findByPlz(String plz);
    List<Worker> findAll();
}
