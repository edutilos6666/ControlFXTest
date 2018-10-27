package org.ddg.dao;

import org.ddg.model.Worker;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by edutilos on 27.10.18.
 */
public class WorkerDAOHibernateImpl implements  WorkerDAO{
    private SessionFactory factory;
    public void initSessionFactory() {
        factory = new Configuration().configure().buildSessionFactory();
    }
    public void closeSessionFactory() {
        if(factory != null && factory.isOpen())
            factory.close();
    }

    public WorkerDAOHibernateImpl() {
        initSessionFactory();
    }

    @Override
    public void create(Worker one) {
        Session session = factory.openSession();
        try {
            session.getTransaction().begin();
            one.setId(0);
            session.persist(one);
            session.getTransaction().commit();
        } catch(Exception ex) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
    }

    @Override
    public void update(long id, Worker newOne) {
        Session session = factory.openSession();
        try {
            session.getTransaction().begin();
            newOne.setId(id);
            session.saveOrUpdate(newOne);
            session.getTransaction().commit();
        } catch(Exception ex) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
    }

    @Override
    public void delete(long id) {
        Session session = factory.openSession();
        try {
            session.getTransaction().begin();
            session.delete(findById(id));
            session.getTransaction().commit();
        } catch(Exception ex) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
    }

    @Override
    public Worker findById(long id) {
        Worker one = null;
        Session session = factory.openSession();
        one = session.find(Worker.class, id);
        session.close();
        return one;
    }

    @Override
    public List<Worker> findByFname(String fname) {
        List<Worker> some = null;
        Session session = factory.openSession();
        some = session.createQuery("from Worker where fname = :fname", Worker.class)
                .setParameter("fname", fname)
                .getResultList();
        session.close();
        return some;
    }

    @Override
    public List<Worker> findByLname(String lname) {
        List<Worker> some = null;
        Session session = factory.openSession();
        some = session.createQuery("from Worker where lname = :lname", Worker.class)
                .setParameter("lname", lname)
                .getResultList();
        session.close();
        return some;
    }

    @Override
    public List<Worker> findByAgeMinMax(boolean inclusive, int minAge, int maxAge) {
        List<Worker> some = null;
        String query = null;
        if(inclusive) {
            query = "from Worker where age >= :minAge and age <= :maxAge";
        } else {
            query = "from Worker where age > :minAge and age < :maxAge";
        }
        Session session = factory.openSession();
        some = session.createQuery(query, Worker.class)
                .setParameter("minAge", minAge)
                .setParameter("maxAge", maxAge)
                .getResultList();
        session.close();
        return some;
    }

    @Override
    public List<Worker> findByWageMinMax(boolean inclusive, double minWage, double maxWage) {
        List<Worker> some = null;
        String query = null;
        if(inclusive) {
            query = "from Worker where wage >= :minWage and wage <= :maxWage";
        } else {
            query = "from Worker where wage > :minWage and wage < :maxWage";
        }
        Session session = factory.openSession();
        some = session.createQuery(query, Worker.class)
                .setParameter("minWage", minWage)
                .setParameter("maxWage", maxWage)
                .getResultList();
        session.close();
        return some;
    }

    @Override
    public List<Worker> findByActive(boolean active) {
        List<Worker> some = null;
        Session session = factory.openSession();
        some = session.createQuery("from Worker where active = :active", Worker.class)
                .setParameter("active", active)
                .getResultList();
        session.close();
        return some;
    }

    @Override
    public List<Worker> findByActivity(String... activity) {
        List<Worker> some = null;
        Session session = factory.openSession();
        List<Worker> all = session.createQuery("from Worker", Worker.class)
                .getResultList();
        some = all.stream().filter(w -> {
           for(String act: activity) {
               if(w.getActivities().contains(act)) return true;
           }
           return false;
        }).collect(Collectors.toList());
        session.close();
        return some;
    }

    @Override
    public List<Worker> findByCountry(String country) {
        List<Worker> some = null;
        Session session = factory.openSession();
        some = session.createQuery("from Worker where country = :country", Worker.class)
                .setParameter("country", country)
                .getResultList();
        session.close();
        return some;
    }

    @Override
    public List<Worker> findByCity(String city) {
        List<Worker> some = null;
        Session session = factory.openSession();
        some = session.createQuery("from Worker where city = :city", Worker.class)
                .setParameter("city", city)
                .getResultList();
        session.close();
        return some;
    }

    @Override
    public List<Worker> findByStreet(String street) {
        List<Worker> some = null;
        Session session = factory.openSession();
        some = session.createQuery("from Worker where street = :street", Worker.class)
                .setParameter("street", street)
                .getResultList();
        session.close();
        return some;
    }

    @Override
    public List<Worker> findByPlz(String plz) {
        List<Worker> some = null;
        Session session = factory.openSession();
        some = session.createQuery("from Worker where plz = :plz", Worker.class)
                .setParameter("plz", plz)
                .getResultList();
        session.close();
        return some;
    }

    @Override
    public List<Worker> findAll() {
        List<Worker> some = null;
        Session session = factory.openSession();
        some = session.createQuery("from Worker", Worker.class)
                .getResultList();
        session.close();
        return some;
    }

    @Override
    public void clear() {
        Session session = factory.openSession();
        try {
            session.getTransaction().begin();
            session.createQuery("delete From Worker", Worker.class).executeUpdate();
            session.getTransaction().commit();
        } catch(Exception ex) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
    }
}
