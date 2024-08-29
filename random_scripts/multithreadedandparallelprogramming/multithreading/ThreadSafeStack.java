package multithreadedandparallelprogramming.multithreading;

//surround the critical section of the code with synchronized. The lock should be an object, but it can be any Object (String, User Defined Objects, Integer etc.). The lock cannot be a primitive.
//the critical section of the code (surrounded by synchronized) can only be accessed by a thread once it gets acquires the lock. Otherwise, it has to wait till the other thread releases the lock.
//if thread1 acquires the lock, thread2 cannot execute any method that uses the same lock. For example: if thread1 acquires the lock to execute push(), thread2 cannot execute pop() because access to pop() is also controlled by the same lock.
//ideally, all those methods that access the same shared resource, should be controlled by the same lock.

public class ThreadSafeStack {
    private int[] array;
    private int stackTop;
    private final Object lock;

    public ThreadSafeStack(int capacity) {
        array = new int[capacity];
        stackTop = -1;
        lock = new Object();
    }


    //two ways to use synchronized. 1. either use it inside the method for the critical section. 2. otherwise, if the entire method is critical, then write synchronized in the method signature. In the second option, the compiler uses "this" as the lock i.e it uses the current instance of the class (the object itself) as the lock. that is why we don't have to explicitly pass a lock in the second way.
    //therefore, in the second way, the lock for all the synchronized methods in the class is the same (the current object itself).
    public synchronized boolean push(int element) {
        if(isFull()) {
            return false;
        }
        stackTop++;
        try {
            Thread.sleep(1000);
        } catch (Exception e) {
        }
        array[stackTop] = element;
        return true;
    }

//    public boolean push(int element) {
//        synchronized (lock) {
//            if(isFull()) {
//                return false;
//            }
//            stackTop++;
//            try {
//                Thread.sleep(1000);
//            } catch (Exception e) {
//            }
//            array[stackTop] = element;
//            return true;
//        }
//    }

    public synchronized int pop() {
        if(isEmpty()) {
            return Integer.MIN_VALUE;
        }
        int val = array[stackTop];
        array[stackTop] = Integer.MIN_VALUE;
        try {
            Thread.sleep(1000);
        } catch (Exception e) {
        }
        stackTop--;
        return val;
    }

//    public int pop() {
//        synchronized (lock) {
//            if(isEmpty()) {
//                return Integer.MIN_VALUE;
//            }
//            int val = array[stackTop];
//            array[stackTop] = Integer.MIN_VALUE;
//            try {
//                Thread.sleep(1000);
//            } catch (Exception e) {
//            }
//            stackTop--;
//            return val;
//        }
//    }

    public boolean isFull() {
        return stackTop >= array.length-1;
    }

    public boolean isEmpty() {
        return stackTop < 0;
    }
}
