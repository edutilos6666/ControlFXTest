package org.ddg.dao;


import javafx.collections.FXCollections;
import junit.framework.TestCase;
import org.ddg.model.WorkerWithDate;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class TestWorkerWithDateDAOHibernateImpl extends TestCase {
    private WorkerWithDateDAO dao;

    @Override
    public void setUp() throws Exception {
        super.setUp();
        dao = new WorkerWithDateDAOHibernateImpl();
//        ((WorkerDAOHibernateImpl)dao).initSessionFactory();
        addMockData();
    }

    @Override
    public void tearDown() throws Exception {
        super.tearDown();
        ((WorkerWithDateDAOHibernateImpl)dao).closeSessionFactory();
    }

    private void addMockData() {
        dao.create(new WorkerWithDate("foo", "bar", 10, 100.0,
                true, FXCollections.observableArrayList("reading", "writing"), "Germany",
                "Bochum","Laerholzstrasse", "1234",
                new GregorianCalendar(2010, Calendar.FEBRUARY, 10).getTime(),
                new GregorianCalendar(2013, Calendar.DECEMBER, 7).getTime(),
                java.sql.Date.valueOf(LocalDate.of(2010, 2, 10)),
                java.sql.Date.valueOf(LocalDate.of(2013, 12, 7)),
                java.sql.Time.valueOf(LocalTime.of(10, 10, 10))
                ));
        dao.create(new WorkerWithDate("leo", "messi", 20, 200.0,
                false, Arrays.asList("speaking", "listening"), "Spain",
                "Barcelona", "Catalonia", "23456",
                new GregorianCalendar(2009, Calendar.FEBRUARY, 10).getTime(),
                new GregorianCalendar(2014, Calendar.DECEMBER, 7).getTime(),
                java.sql.Date.valueOf(LocalDate.of(2009, 2, 10)),
                java.sql.Date.valueOf(LocalDate.of(2014, 12, 7)),
                java.sql.Time.valueOf(LocalTime.of(15, 15, 15))
                ));
        dao.create(new WorkerWithDate("cris", "tiano", 30, 300.0,
                true, FXCollections.observableArrayList("reading", "listening"), "Italy",
                "Turin", "Juventus", "34567",
                new GregorianCalendar(2008, Calendar.FEBRUARY, 10).getTime(),
                new GregorianCalendar(2015, Calendar.DECEMBER, 7).getTime(),
                java.sql.Date.valueOf(LocalDate.of(2008, 2, 10)),
                java.sql.Date.valueOf(LocalDate.of(2015, 12, 7)),
                java.sql.Time.valueOf(LocalTime.of(20, 20, 20))
                ));
    }

    


    public void testCreate() {
        List<WorkerWithDate> all = dao.findAll();
        assertThat(all.size(), is(3));
        WorkerWithDate one = all.get(0);
        assertThat(one.getFname(), is("foo"));
        assertThat(one.getLname(), is("bar"));
        assertThat(one.getAge(), is(10));
        assertThat(one.getWage(), is(100.0));
        assertThat(one.isActive(), is(true));
        assertThat(one.getActivities().size(), is(2));
        assertThat(one.getCountry(), is("Germany"));
        assertThat(one.getCity(), is("Bochum"));
        assertThat(one.getStreet(), is("Laerholzstrasse"));
        assertThat(one.getPlz(), is("1234"));

        List<WorkerWithDate> some = dao.findByFname("foo");
        assertThat(some.size(), is(1));
        some = dao.findByLname("bar");
        assertThat(some.size(), is(1));
        some = dao.findByAgeMinMax(true, 10, 30);
        assertThat(some.size(), is(3));
        some = dao.findByAgeMinMax(false, 10, 30);
        assertThat(some.size(), is(1));
        some = dao.findByWageMinMax(true, 100.0, 300.0);
        assertThat(some.size(), is(3));
        some = dao.findByWageMinMax(false, 100.0, 300.0);
        assertThat(some.size(), is(1));
        some = dao.findByActive(true);
        assertThat(some.size(), is(2));
        some = dao.findByActivity("reading");
        assertThat(some.size(), is(2));
        some = dao.findByCountry("Germany");
        assertThat(some.size(), is(1));
        some = dao.findByCity("Barcelona");
        assertThat(some.size(), is(1));
        some = dao.findByStreet("Catalonia");
        assertThat(some.size(), is(1));
        some = dao.findByPlz("1234");
        assertThat(some.size(), is(1));
        some = dao.findByDateFromTo(new GregorianCalendar(2010, Calendar.FEBRUARY, 10).getTime(),
                new GregorianCalendar(2013, Calendar.DECEMBER, 7).getTime()
                );
        assertThat(some.size(), is(1));
        some = dao.findBySqlDateFromTo(java.sql.Date.valueOf(LocalDate.of(2009, 2, 10)),
                java.sql.Date.valueOf(LocalDate.of(2014, 12, 7)));
        assertThat(some.size(), is(2));
        some = dao.findByHeuteBetween(java.sql.Time.valueOf(LocalTime.of(15, 15, 15)),
                java.sql.Time.valueOf(LocalTime.of(20, 20, 20)));
        assertThat(some.size(), is(2));
    }

    public void testUpdate() {
        dao.update(1L, new WorkerWithDate(1L, "new-foo", "new-bar", 10, 100.0,
                true, FXCollections.observableArrayList("reading", "writing"), "Germany",
                "Bochum","Laerholzstrasse", "1234",
                new GregorianCalendar(2010, Calendar.FEBRUARY, 10).getTime(),
                new GregorianCalendar(2013, Calendar.DECEMBER, 7).getTime(),
                java.sql.Date.valueOf(LocalDate.of(2010, 2, 10)),
                java.sql.Date.valueOf(LocalDate.of(2013, 12, 7)),
                java.sql.Time.valueOf(LocalTime.of(10, 10, 10))
        ));
        List<WorkerWithDate> all = dao.findAll();
        assertThat(all.size(), is(3));
        WorkerWithDate one = dao.findById(1L);
        assertThat(one.getFname(), is("new-foo"));
        assertThat(one.getLname(), is("new-bar"));
    }


    public void testDelete() {
        dao.delete(1L);
        List<WorkerWithDate> all = dao.findAll();
        assertThat(all.size(), is(2));
    }
}
