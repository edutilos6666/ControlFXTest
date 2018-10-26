package org.ddg.dao;

import org.ddg.model.OldWorker;

import java.util.List;

/**
 * Created by edutilos on 26.10.18.
 */
public interface OldWorkerDAO {
    void create(OldWorker one);
    void update(long id, OldWorker newOne);
    void delete(long id);
    OldWorker findById(long id);
    List<OldWorker> findByFname(String fname);
    List<OldWorker> findByLname(String lname);
    List<OldWorker> findByAgeMinMax(boolean inclusive, int minAge, int maxAge);
    List<OldWorker> findByWageMinMax(boolean inclusive, double minWage, double maxWage);
    List<OldWorker> findByActive(boolean active);
    List<OldWorker> findByActivity(String... activity);
    List<OldWorker> findByCountry(String country);
    List<OldWorker> findByCity(String city);
    List<OldWorker> findByStreet(String street);
    List<OldWorker> findByPlz(String plz);
    List<OldWorker> findAll();
}
