package multithreadedandparallelprogramming.multithreading.executorframework;

import java.util.Date;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class CallableAndFuture {

    public static void main(String[] args) {
        ExecutorService service = Executors.newFixedThreadPool(10);
        Future<Integer> future = service.submit(new Task());    //Future is a placeholder for the value that will arrive sometime in the future. it submits the task and proceeds. service.submit() is not blocking.

        System.out.println(new Date(System.currentTimeMillis()));
        //complete some unrelated operations, while you wait for the submitted task to return.
        try {
            Integer result = future.get();   //blocking. the main thread gets blocked till the future is ready to return a value (this will only happen once the associated task finishes execution and returns).
            System.out.println(result);
            System.out.println(new Date(System.currentTimeMillis()));
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
        service.shutdown();
    }

    //can be replaced by a lambda too. writing a proper class just to make it clear.
    static class Task implements Callable<Integer> {
        @Override
        public Integer call() throws Exception {
            System.out.println("Thread Name: " + Thread.currentThread().getName());
            Thread.sleep(3000);
            return new Random().nextInt();
        }
    }
}
