package org.ddg.dao;

import org.ddg.model.OldWorker;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * Created by edutilos on 26.10.18.
 */
public class OldWorkerDAOMemImpl implements OldWorkerDAO {
    Map<Long, OldWorker> workerMap;

    public OldWorkerDAOMemImpl() {
        this.workerMap = new ConcurrentHashMap<>();
    }

    public void clear() {
        workerMap.clear();
    }

    @Override
    public void create(OldWorker one) {
        workerMap.putIfAbsent(one.getId(), one);
    }

    @Override
    public void update(long id, OldWorker newOne) {
        workerMap.put(id, newOne);
    }

    @Override
    public void delete(long id) {
        workerMap.remove(id);
    }

    @Override
    public OldWorker findById(long id) {
        return workerMap.get(id);
    }

    @Override
    public List<OldWorker> findByFname(String fname) {
        return workerMap.values().stream().filter(w-> {
            return w.getFname().equalsIgnoreCase(fname);
        }).collect(Collectors.toList());
    }

    @Override
    public List<OldWorker> findByLname(String lname) {
        return workerMap.values().stream().filter(w-> {
           return w.getLname().equalsIgnoreCase(lname);
        }).collect(Collectors.toList());
    }

    @Override
    public List<OldWorker> findByAgeMinMax(boolean inclusive, int minAge, int maxAge) {
        if(inclusive)
            return workerMap.values().stream().filter(w-> {
                return w.getAge() >= minAge && w.getAge() <= maxAge;
            }).collect(Collectors.toList());
        return workerMap.values().stream().filter(w-> {
            return w.getAge() > minAge && w.getAge() < maxAge;
        }).collect(Collectors.toList());
    }

    @Override
    public List<OldWorker> findByWageMinMax(boolean inclusive, double minWage, double maxWage) {
        if(inclusive)
            return workerMap.values().stream().filter(w-> {
               return w.getWage() >= minWage && w.getWage() <= maxWage;
            }).collect(Collectors.toList());

        return workerMap.values().stream().filter(w-> {
            return w.getWage() > minWage && w.getWage() < maxWage;
        }).collect(Collectors.toList());
    }

    @Override
    public List<OldWorker> findByActive(boolean active) {
        return workerMap.values().parallelStream().filter(w-> {
          return w.isActive() == active;
        }).collect(Collectors.toList());
    }

    @Override
    public List<OldWorker> findByActivity(String... activity) {
        return workerMap.values().parallelStream().filter(w-> {
          for(String a: activity) {
              if(w.getActivities().indexOf(a) < 0) return false;
          }
          return true;
        }).collect(Collectors.toList());
    }

    @Override
    public List<OldWorker> findByCountry(String country) {
        return workerMap.values().parallelStream().filter(w-> {
          return w.getCountry().equalsIgnoreCase(country);
        }).collect(Collectors.toList());
    }

    @Override
    public List<OldWorker> findByCity(String city) {
        return workerMap.values().parallelStream().filter(w-> {
            return w.getCity().equalsIgnoreCase(city);
        }).collect(Collectors.toList());
    }

    @Override
    public List<OldWorker> findByStreet(String street) {
        return workerMap.values().parallelStream().filter(w-> {
            return w.getStreet().equalsIgnoreCase(street);
        }).collect(Collectors.toList());
    }

    @Override
    public List<OldWorker> findByPlz(String plz) {
        return workerMap.values().parallelStream().filter(w-> {
            return w.getPlz().equalsIgnoreCase(plz);
        }).collect(Collectors.toList());
    }

    @Override
    public List<OldWorker> findAll() {
        return new ArrayList<>(workerMap.values());
    }
}
