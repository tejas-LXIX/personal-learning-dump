package multithreadedandparallelprogramming.multithreading;

public class ThreadSafeStackTester {

    public static void main(String[] args) {
        ThreadSafeStack safeStack = new ThreadSafeStack(5);
        new Thread(() -> {
            int counter = 0;
            while (++counter < 10) {
                System.out.println("Pushed: " + safeStack.push(100));
            }
        }, "Pusher").start();

        new Thread(() -> {
            int counter = 0;
            while(++counter < 10) {
                System.out.println("Popped: " + safeStack.pop());
            }
        }, "Popper").start();

        System.out.println("Main is exiting");
    }
}




//unsynchronized (not thread safe. remove synchronized block to see this) implementation of the ThreadSafeStack gives the below output. This is because both the push() and the pop() methods access and modify shared resources (array, stackTop).
//stackTop could have become -1 in the following case.
// The pusher thread executes and it calls push(), which first increments the stackTop from -1 to 0.Then, this thread sleeps and the popper thread becomes active.
// The popper thread now sees that the stack is not empty (since stackTop == 0). So, it executes the code, pops the value and decrements stackTop (stackTop becomes -1 after decrementing). Then the popper thread exits.
//Now, the pusher thread becomes active and resumes it's execution from the point just after it incremented stackTop. so, it tries to access array[stackTop]. but, stackTop was decremented to -1 by the popping thread. So, the pusher thread gets an exception since -1 is not a valid index.

//Main is exiting
//Popped: 0
//Popped: -2147483648
//Popped: -2147483648
//Popped: -2147483648
//Popped: -2147483648
//Popped: -2147483648
//Popped: -2147483648
//Popped: -2147483648
//Popped: -2147483648
//Exception in thread "Pusher" java.lang.ArrayIndexOutOfBoundsException: -1
//	at multithreadedandparallelprogramming.multithreading.ThreadSafeStack.push(ThreadSafeStack.java:21)
//	at multithreadedandparallelprogramming.multithreading.ThreadSafeStackTester.lambda$main$0(ThreadSafeStackTester.java:10)
//	at java.lang.Thread.run(Thread.java:748)
//
//Process finished with exit code 0