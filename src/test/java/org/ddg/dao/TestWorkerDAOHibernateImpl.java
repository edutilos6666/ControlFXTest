package org.ddg.dao;


import javafx.collections.FXCollections;
import junit.framework.TestCase;
import org.ddg.model.Worker;

import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class TestWorkerDAOHibernateImpl extends TestCase {
    private WorkerDAO dao;

    @Override
    public void setUp() throws Exception {
        super.setUp();
        dao = new WorkerDAOHibernateImpl();
//        ((WorkerDAOHibernateImpl)dao).initSessionFactory();
        addMockData();
    }

    @Override
    public void tearDown() throws Exception {
        super.tearDown();
        ((WorkerDAOHibernateImpl)dao).closeSessionFactory();
    }

    private void addMockData() {
        dao.create(new Worker("foo", "bar", 10, 100.0,
                true, FXCollections.observableArrayList("reading", "writing"), "Germany",
                "Bochum","Laerholzstrasse", "1234"));
        dao.create(new Worker("leo", "messi", 20, 200.0,
                false, FXCollections.observableArrayList("speaking", "listening"), "Spain",
                "Barcelona", "Catalonia", "23456"));
        dao.create(new Worker("cris", "tiano", 30, 300.0,
                true, FXCollections.observableArrayList("reading", "listening"), "Italy",
                "Turin", "Juventus", "34567"));
    }



    public void testCreate() {
        List<Worker> all = dao.findAll();
        assertThat(all.size(), is(3));
        Worker one = all.get(0);
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

        List<Worker> some = dao.findByFname("foo");
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
    }

    public void testUpdate() {
        dao.update(1L, new Worker(1L, "new-foo", "new-bar", 10, 100.0,
                true, FXCollections.observableArrayList("reading", "writing"), "Germany",
                "Bochum","Laerholzstrasse", "1234"));
        List<Worker> all = dao.findAll();
        assertThat(all.size(), is(3));
        Worker one = dao.findById(1L);
        assertThat(one.getFname(), is("new-foo"));
        assertThat(one.getLname(), is("new-bar"));
    }


    public void testDelete() {
        dao.delete(1L);
        List<Worker> all = dao.findAll();
        assertThat(all.size(), is(2));
    }
}
