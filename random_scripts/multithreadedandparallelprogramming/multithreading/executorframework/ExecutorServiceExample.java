package multithreadedandparallelprogramming.multithreading.executorframework;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ExecutorServiceExample {
    public static void main(String[] args) {
        int coreCount = Runtime.getRuntime().availableProcessors();
        ExecutorService service = Executors.newFixedThreadPool(coreCount);

        for(int i=0;i<10;i++) {
            service.execute(() -> {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println("Thread Name: " + Thread.currentThread().getName());
            });
        }
        System.out.println("Thread Name: " + Thread.currentThread().getName());

        service.shutdown();     //the service needs to be shutdown once all the submitted tasks (including queued ones) have been completed.
        System.out.println("The shutdown for the executor service has started (including the thread pool).");

//        service.isShutdown();   //returns true if the shutdown has begun

//        service.isTerminated();     //returns true if all tasks are completed, including queued ones.

//        service.awaitTermination(10, TimeUnit.SECONDS);   //will wait for 10 seconds. if the shutdown is not completed by that time too, then it will forcefully shut down everything.

//        List<Runnable> pendingTasks = service.shutdownNow();  //Attempts to stop all actively executing tasks, halts the processing of waiting tasks, and returns a list of the tasks that were awaiting execution.
    }
}
