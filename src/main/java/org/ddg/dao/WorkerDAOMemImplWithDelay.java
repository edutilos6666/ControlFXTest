package org.ddg.dao;

import org.ddg.model.Worker;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * Created by edutilos on 26.10.18.
 */
public class WorkerDAOMemImplWithDelay implements WorkerDAO {
    Map<Long, Worker> workerMap;
    private long delay;

    public WorkerDAOMemImplWithDelay(long delay) {
        this.delay = delay;
        this.workerMap = new ConcurrentHashMap<>();
    }

    @Override
    public void clear() {
        try {
            Thread.sleep(delay);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        workerMap.clear();
    }

    @Override
    public void create(Worker one) {
        try {
            Thread.sleep(delay);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        workerMap.putIfAbsent(one.getId(), one);
    }

    @Override
    public void update(long id, Worker newOne) {
        try {
            Thread.sleep(delay);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        workerMap.put(id, newOne);
    }

    @Override
    public void delete(long id) {
        try {
            Thread.sleep(delay);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        workerMap.remove(id);
    }

    @Override
    public Worker findById(long id) {
        try {
            Thread.sleep(delay);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return workerMap.get(id);
    }

    @Override
    public List<Worker> findByFname(String fname) {
        try {
            Thread.sleep(delay);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return workerMap.values().stream().filter(w-> {
            return w.getFname().equalsIgnoreCase(fname);
        }).collect(Collectors.toList());
    }

    @Override
    public List<Worker> findByLname(String lname) {
        try {
            Thread.sleep(delay);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return workerMap.values().stream().filter(w-> {
           return w.getLname().equalsIgnoreCase(lname);
        }).collect(Collectors.toList());
    }

    @Override
    public List<Worker> findByAgeMinMax(boolean inclusive, int minAge, int maxAge) {
        try {
            Thread.sleep(delay);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if(inclusive)
            return workerMap.values().stream().filter(w-> {
                return w.getAge() >= minAge && w.getAge() <= maxAge;
            }).collect(Collectors.toList());
        return workerMap.values().stream().filter(w-> {
            return w.getAge() > minAge && w.getAge() < maxAge;
        }).collect(Collectors.toList());
    }

    @Override
    public List<Worker> findByWageMinMax(boolean inclusive, double minWage, double maxWage) {
        try {
            Thread.sleep(delay);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if(inclusive)
            return workerMap.values().stream().filter(w-> {
               return w.getWage() >= minWage && w.getWage() <= maxWage;
            }).collect(Collectors.toList());

        return workerMap.values().stream().filter(w-> {
            return w.getWage() > minWage && w.getWage() < maxWage;
        }).collect(Collectors.toList());
    }

    @Override
    public List<Worker> findByActive(boolean active) {
        try {
            Thread.sleep(delay);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return workerMap.values().parallelStream().filter(w-> {
          return w.isActive() == active;
        }).collect(Collectors.toList());
    }

    @Override
    public List<Worker> findByActivity(String... activity) {
        try {
            Thread.sleep(delay);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return workerMap.values().parallelStream().filter(w-> {
          for(String a: activity) {
              if(w.getActivities().indexOf(a) < 0) return false;
          }
          return true;
        }).collect(Collectors.toList());
    }

    @Override
    public List<Worker> findByCountry(String country) {
        try {
            Thread.sleep(delay);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return workerMap.values().parallelStream().filter(w-> {
          return w.getCountry().equalsIgnoreCase(country);
        }).collect(Collectors.toList());
    }

    @Override
    public List<Worker> findByCity(String city) {
        try {
            Thread.sleep(delay);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return workerMap.values().parallelStream().filter(w-> {
            return w.getCity().equalsIgnoreCase(city);
        }).collect(Collectors.toList());
    }

    @Override
    public List<Worker> findByStreet(String street) {
        try {
            Thread.sleep(delay);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return workerMap.values().parallelStream().filter(w-> {
            return w.getStreet().equalsIgnoreCase(street);
        }).collect(Collectors.toList());
    }

    @Override
    public List<Worker> findByPlz(String plz) {
        try {
            Thread.sleep(delay);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return workerMap.values().parallelStream().filter(w-> {
            return w.getPlz().equalsIgnoreCase(plz);
        }).collect(Collectors.toList());
    }

    @Override
    public List<Worker> findAll() {
        try {
            Thread.sleep(delay);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return new ArrayList<>(workerMap.values());
    }
}
