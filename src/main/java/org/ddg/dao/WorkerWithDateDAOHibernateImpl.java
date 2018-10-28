package org.ddg.dao;

import org.ddg.model.WorkerWithDate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.sql.Time;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by edutilos on 27.10.18.
 */
public class WorkerWithDateDAOHibernateImpl implements  WorkerWithDateDAO{
    private SessionFactory factory;
    public void initSessionFactory() {
        factory = new Configuration().configure().buildSessionFactory();
    }
    public void closeSessionFactory() {
        if(factory != null && factory.isOpen())
            factory.close();
    }

    public WorkerWithDateDAOHibernateImpl() {
        initSessionFactory();
    }

    @Override
    public void create(WorkerWithDate one) {
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
    public void update(long id, WorkerWithDate newOne) {
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
    public WorkerWithDate findById(long id) {
        WorkerWithDate one = null;
        Session session = factory.openSession();
        one = session.find(WorkerWithDate.class, id);
        session.close();
        return one;
    }

    @Override
    public List<WorkerWithDate> findByFname(String fname) {
        List<WorkerWithDate> some = null;
        Session session = factory.openSession();
        some = session.createQuery("from WorkerWithDate where fname = :fname", WorkerWithDate.class)
                .setParameter("fname", fname)
                .getResultList();
        session.close();
        return some;
    }

    @Override
    public List<WorkerWithDate> findByLname(String lname) {
        List<WorkerWithDate> some = null;
        Session session = factory.openSession();
        some = session.createQuery("from WorkerWithDate where lname = :lname", WorkerWithDate.class)
                .setParameter("lname", lname)
                .getResultList();
        session.close();
        return some;
    }

    @Override
    public List<WorkerWithDate> findByAgeMinMax(boolean inclusive, int minAge, int maxAge) {
        List<WorkerWithDate> some = null;
        String query = null;
        if(inclusive) {
            query = "from WorkerWithDate where age >= :minAge and age <= :maxAge";
        } else {
            query = "from WorkerWithDate where age > :minAge and age < :maxAge";
        }
        Session session = factory.openSession();
        some = session.createQuery(query, WorkerWithDate.class)
                .setParameter("minAge", minAge)
                .setParameter("maxAge", maxAge)
                .getResultList();
        session.close();
        return some;
    }

    @Override
    public List<WorkerWithDate> findByWageMinMax(boolean inclusive, double minWage, double maxWage) {
        List<WorkerWithDate> some = null;
        String query = null;
        if(inclusive) {
            query = "from WorkerWithDate where wage >= :minWage and wage <= :maxWage";
        } else {
            query = "from WorkerWithDate where wage > :minWage and wage < :maxWage";
        }
        Session session = factory.openSession();
        some = session.createQuery(query, WorkerWithDate.class)
                .setParameter("minWage", minWage)
                .setParameter("maxWage", maxWage)
                .getResultList();
        session.close();
        return some;
    }

    @Override
    public List<WorkerWithDate> findByActive(boolean active) {
        List<WorkerWithDate> some = null;
        Session session = factory.openSession();
        some = session.createQuery("from WorkerWithDate where active = :active", WorkerWithDate.class)
                .setParameter("active", active)
                .getResultList();
        session.close();
        return some;
    }

    @Override
    public List<WorkerWithDate> findByActivity(String... activity) {
        List<WorkerWithDate> some = null;
        Session session = factory.openSession();
        List<WorkerWithDate> all = session.createQuery("from WorkerWithDate", WorkerWithDate.class)
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
    public List<WorkerWithDate> findByCountry(String country) {
        List<WorkerWithDate> some = null;
        Session session = factory.openSession();
        some = session.createQuery("from WorkerWithDate where country = :country", WorkerWithDate.class)
                .setParameter("country", country)
                .getResultList();
        session.close();
        return some;
    }

    @Override
    public List<WorkerWithDate> findByCity(String city) {
        List<WorkerWithDate> some = null;
        Session session = factory.openSession();
        some = session.createQuery("from WorkerWithDate where city = :city", WorkerWithDate.class)
                .setParameter("city", city)
                .getResultList();
        session.close();
        return some;
    }

    @Override
    public List<WorkerWithDate> findByStreet(String street) {
        List<WorkerWithDate> some = null;
        Session session = factory.openSession();
        some = session.createQuery("from WorkerWithDate where street = :street", WorkerWithDate.class)
                .setParameter("street", street)
                .getResultList();
        session.close();
        return some;
    }

    @Override
    public List<WorkerWithDate> findByPlz(String plz) {
        List<WorkerWithDate> some = null;
        Session session = factory.openSession();
        some = session.createQuery("from WorkerWithDate where plz = :plz", WorkerWithDate.class)
                .setParameter("plz", plz)
                .getResultList();
        session.close();
        return some;
    }

    @Override
    public List<WorkerWithDate> findByDateFromTo(Date from, Date to) {
        List<WorkerWithDate> some = null;
        Session session = factory.openSession();
        some = session.createQuery("from WorkerWithDate where dateFrom >= :dateFrom and dateTo <= :dateTo", WorkerWithDate.class)
                .setParameter("dateFrom", from)
                .setParameter("dateTo", to)
                .getResultList();
        session.close();
        return some;
    }

    @Override
    public List<WorkerWithDate> findBySqlDateFromTo(java.sql.Date from, java.sql.Date to) {
        List<WorkerWithDate> some = null;
        Session session = factory.openSession();
        some = session.createQuery("from WorkerWithDate where sqlDateFrom >= :sqlDateFrom and sqlDateTo <= :sqlDateTo", WorkerWithDate.class)
                .setParameter("sqlDateFrom", from)
                .setParameter("sqlDateTo", to)
                .getResultList();
        session.close();
        return some;
    }

    @Override
    public List<WorkerWithDate> findByHeuteBetween(Time from, Time to) {
        List<WorkerWithDate> some = null;
        Session session = factory.openSession();
        some = session.createQuery("from WorkerWithDate where heute >= :heuteFrom and heute <= :heuteTo", WorkerWithDate.class)
                .setParameter("heuteFrom", from)
                .setParameter("heuteTo", to)
                .getResultList();
        session.close();
        return some;
    }

    @Override
    public List<WorkerWithDate> findAll() {
        List<WorkerWithDate> some = null;
        Session session = factory.openSession();
        some = session.createQuery("from WorkerWithDate", WorkerWithDate.class)
                .getResultList();
        session.close();
        return some;
    }

    @Override
    public void clear() {
        Session session = factory.openSession();
        try {
            session.getTransaction().begin();
            session.createQuery("delete From WorkerWithDate", WorkerWithDate.class).executeUpdate();
            session.getTransaction().commit();
        } catch(Exception ex) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
    }
}
