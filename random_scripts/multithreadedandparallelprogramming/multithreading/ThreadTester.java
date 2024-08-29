package multithreadedandparallelprogramming.multithreading;

//There is no order. it is upto the mercy of the JVM to decide when to run which thread, and whether to interrupt one thread in between to run the other thread.
//Even if main exits, the program doesn't terminate because we have a user thread (thread1) that still needs to run. But, if the thread was a daemon thread, then there is nothing stopping the program from terminating, thereby ending the daemon thread abruptly.

public class ThreadTester {
    public static void main(String[] args) {
        //this program has two threads. one is the main thread, and the other one is the new thread (thread1) that we spawned off from the main thread. thread1 is the child thread of the main thread.

        System.out.println("main is starting");

        //creating a thread by extending the Thread class. This isn't preferred because if you extend Thread, then you can't extend any other class that you want to.
        Thread thread1 = new Thread1("thread1 name");
//        thread1.setDaemon(true);  //setting the thread1 as a daemon thread. the program might let the thread1 finish execution, OR it might also end it abruptly. depends on the JVm. whereas, if it was a user thread, then the program will wait for the user thread to finish execution.
        thread1.start();    //doesn't start the thread immediately. it is an asynchronous method that returns immediately. JVM will start the thread whenever it wants to by calling the thread's run() method.

        //creating a thread by implementing the runnable interface. Thread2 class implements Runnable. This is preferred, because a class can implement any number of interfaces. So, implementing runnable will not block you in any way.
//        Thread thread2 = new Thread(new Thread2(), "thread2");
        Thread thread2 = new Thread(() -> {
            for(int i=0;i<5;i++) {
                System.out.println(Thread.currentThread() + ", " + i);
            }
        }, "thread2");
        thread2.start();

        System.out.println("main is exiting");
    }
}
