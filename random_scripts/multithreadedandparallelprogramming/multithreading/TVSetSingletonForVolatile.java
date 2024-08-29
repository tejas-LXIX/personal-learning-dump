package multithreadedandparallelprogramming.multithreading;

//just a regular singleton. To demonstrate the use of the volatile keyword.
public class TVSetSingletonForVolatile {

    private static volatile TVSetSingletonForVolatile tvSetSingletonForVolatileInstance = null;

    private TVSetSingletonForVolatile() {
        System.out.println("TV Set Instantiated");
    }

    //to use a synchronized block inside a static method, you can use the class itself as the lock.
    public static TVSetSingletonForVolatile getInstance() {
        if(tvSetSingletonForVolatileInstance == null) {
            synchronized (ThreadSafeStack.class) {
                if (tvSetSingletonForVolatileInstance == null) {
                    tvSetSingletonForVolatileInstance = new TVSetSingletonForVolatile();
                }
            }
        }
        return tvSetSingletonForVolatileInstance;
    }

}
