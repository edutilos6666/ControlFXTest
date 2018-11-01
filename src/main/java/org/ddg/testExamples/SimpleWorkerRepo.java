package org.ddg.testExamples;

import java.util.Arrays;
import java.util.List;

/**
 * Created by edutilos on 01.11.18.
 */
public class SimpleWorkerRepo {
    public List<SimpleWorker> fetchWorkers() {
        try {
            Thread.sleep(3000);
            return Arrays.asList(
                    new SimpleWorker("foo", 10),
                    new SimpleWorker("bar", 20),
                    new SimpleWorker("bim", 30),
                    new SimpleWorker("pako", 40)
            );
        } catch (InterruptedException e) {
            e.printStackTrace();
            return null;
        }

    }
}
