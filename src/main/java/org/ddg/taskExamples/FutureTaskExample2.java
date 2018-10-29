package org.ddg.taskExamples;

import java.util.concurrent.*;

/**
 * Created by edutilos on 29.10.18.
 */
public class FutureTaskExample2 {
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        CustomCallable cc1, cc2, cc3;
        cc1 = new CustomCallable(1000);
        cc2 = new CustomCallable(2000);
        cc3 = new CustomCallable(3000);
        FutureTask<String> ft1 = new FutureTask<String>(cc1);
        FutureTask<String> ft2 = new FutureTask<String>(cc2);
        FutureTask<String> ft3 = new FutureTask<String>(cc3);
        executorService.execute(ft1);
        executorService.execute(ft2);
        executorService.execute(ft3);
        while (true) {
            try {
                if (ft1.isDone() && ft2.isDone() && ft3.isDone()) {
                    System.out.println("Done");
                    executorService.shutdown();
                    break;
                }
                if (!ft1.isDone()) {
                    System.out.println(String.format("ft1 = %s", ft1.get()));
                }
                System.out.println("Waiting for ft2");

                String s2, s3;
                s2 = ft2.get(200L, TimeUnit.MILLISECONDS);
                if(s2 != null) {
                    System.out.printf("ft2 = %s\n", s2);
                }
                System.out.println("Waiting for ft3");
                s3 = ft3.get(300L, TimeUnit.MILLISECONDS);
                if(s3 != null) {
                    System.out.printf("ft3 = %s\n", s3);
                }
            } catch (InterruptedException | ExecutionException ex) {
                ex.printStackTrace();
            } catch(TimeoutException ex) {
                // do nothing
            }
        }
    }


    static class CustomCallable implements Callable<String> {
        private int timeForWait;

        public CustomCallable(int timeForWait) {
            this.timeForWait = timeForWait;
        }

        @Override
        public String call() throws Exception {
            Thread.sleep(timeForWait);
            return Thread.currentThread().getName();
        }
    }
}
