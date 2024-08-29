package multithreadedandparallelprogramming.multithreading.threadlocal;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadLocalExample {

    private static ExecutorService threadPool = Executors.newFixedThreadPool(10);

    public static void main(String[] args) {
        for(int i=0;i<1000;i++) {
            int id = i;
            threadPool.submit(() -> {
                String birthDate = new ThreadLocalExample().birthDate(id);
                System.out.println(birthDate);
            });
        }
        threadPool.shutdown();
    }

    public String birthDate(int userId) {
        Date birthDate = birthDateFromDB(userId);
        final SimpleDateFormat df = ThreadSafeFormatter.df.get();   //based on which thread has called this method, the thread will get it's own copy.
        return df.format(birthDate);
    }

    private Date birthDateFromDB(int userId) {
        return new Date();
    }
}

class ThreadSafeFormatter {
    //each thread will get it's own copy of SimpleDateFormat. The SDF object will only be created once, after which it will be reused everytime the thread needs it. therefore, we are memory efficient but also fast (no need for synchronization since every thread has it's own copy)
    public static ThreadLocal<SimpleDateFormat> df = ThreadLocal.withInitial(() -> new SimpleDateFormat("yyyy-MM-dd"));
}
