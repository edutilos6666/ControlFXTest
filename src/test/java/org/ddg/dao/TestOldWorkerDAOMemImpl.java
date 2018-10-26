package org.ddg.dao;


import junit.framework.TestCase;
import org.ddg.model.OldWorker;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class TestOldWorkerDAOMemImpl extends TestCase {
    private OldWorkerDAO dao;

    @Override
    public void setUp() throws Exception {
        super.setUp();
        dao = new OldWorkerDAOMemImpl();
        addMockData();
    }

    @Override
    public void tearDown() throws Exception {
        super.tearDown();
        ((OldWorkerDAOMemImpl)dao).clear();
        dao = null;
    }

    private void addMockData() {
        dao.create(new OldWorker(1L, "foo", "bar", 10, 100.0,
                true, Arrays.asList("reading", "writing"), "Germany",
                "Bochum","Laerholzstrasse", "1234"));
        dao.create(new OldWorker(2L, "leo", "messi", 20, 200.0,
                false, Arrays.asList("speaking", "listening"), "Spain",
                "Barcelona", "Catalonia", "23456"));
        dao.create(new OldWorker(3L, "cris", "tiano", 30, 300.0,
                true, Arrays.asList("reading", "listening"), "Italy",
                "Turin", "Juventus", "34567"));
    }



    public void testCreate() {
        List<OldWorker> all = dao.findAll();
        assertThat(all.size(), is(3));
        OldWorker one = all.get(0);
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

        List<OldWorker> some = dao.findByFname("foo");
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
        dao.update(1L, new OldWorker(1L, "new-foo", "new-bar", 10, 100.0,
                true, Arrays.asList("reading", "writing"), "Germany",
                "Bochum","Laerholzstrasse", "1234"));
        List<OldWorker> all = dao.findAll();
        assertThat(all.size(), is(3));
        OldWorker one = dao.findById(1L);
        assertThat(one.getFname(), is("new-foo"));
        assertThat(one.getLname(), is("new-bar"));
    }


    public void testDelete() {
        dao.delete(1L);
        List<OldWorker> all = dao.findAll();
        assertThat(all.size(), is(2));
    }
}
