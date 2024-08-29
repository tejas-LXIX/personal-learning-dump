package multithreadedandparallelprogramming.multithreading;

public class ThreadTesterForCreatingDeadlock {
    public static void main(String[] args) {
        String lock1 = "tejas";
        String lock2 = "tatineni";

        System.out.println("Main started");

        Thread thread1 = new Thread(() -> {
            synchronized (lock1) {
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (lock2) {
                    System.out.println("Lock acquired");
                }
            }
        }, "thread1");

        Thread thread2 = new Thread(() -> {
            synchronized (lock2) {
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (lock1) {
                    System.out.println("Lock acquired");
                }
            }
        }, "thread2");

        thread1.start();

        thread2.start();

        System.out.println("Main exited");
    }

}
