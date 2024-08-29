package multithreadedandparallelprogramming.multithreading;

public class ThreadTesterForJoin {

    public static void main(String[] args) {

        System.out.println("Main is starting");

        Thread thread = new Thread(() -> {
            System.out.println(Thread.currentThread());
        }, "Our Thread");

        thread.start();

        //i want my thread to first complete, and only then the main thread should run.
        try {
            thread.join();  //it will wait for the run method of the thread to complete, before resuming. therefore, the main method is blocked till the user thread completes.
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Main is exiting");
    }
}
