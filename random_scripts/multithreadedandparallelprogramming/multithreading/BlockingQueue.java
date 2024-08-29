package multithreadedandparallelprogramming.multithreading;

import java.util.LinkedList;
import java.util.Queue;

//BlockingQueue
//A Queue that additionally supports operations that wait for the queue to become non-empty when retrieving an element, and wait for space to become available in the queue when storing an element.
//Every object has a wait set, which stores references to all the threads that are currently waiting for that object.

public class BlockingQueue {
    private Queue<Integer> q;

    private int capacity;

    public BlockingQueue(int capacity) {
        q = new LinkedList<>();
        this.capacity = capacity;
    }

    //the while loop around the capacity condition is required because assume the case where two pusher threads were notified, but there's only one slot, then both will try to add one after the other.
    //if remover1 removes an element, then pusher1 and pusher2 are notified. pusher1 obtains the lock first and adds an element, then lock is obtained by pusher2 who also tries to add the element. but now, there is no space left again to add the item.
    //therefore, replacing if with while ensures that the threads that wake up always check for the capacity before adding or removing an element. if unable to add/remove, the thread again starts waiting.

    public boolean push(int item) {
        synchronized (q) {
            while(q.size() == capacity) {
                try {
                    q.wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            q.add(item);
            q.notifyAll();  //notifies all the threads that are waiting on this object's monitor. notify() means notify any one particular thread that is waiting on this object's monitor (chosen randomly).
            return true;
        }
    }

    public int remove() {
        synchronized (q) {
            //whenever the size is 0, the remover thread has to wait for the pusher threads to push some items into the queue which the remover thread can remove. Hence, it waits and relinquishes the lock, giving a chance to the other threads to acquire the lock and access the critical section.
            // if we don't do this, then the remove() method won't be able to remove anything. it will just have to return a default value, but the client will think that they actually removed something from the queue which is false.
            //this thread needs to be NOTIFIED by others that the queue now has some items, so that it can wake up and remove those items. in this case, this responsibility of notifying the other threads will have to be taken up by the thread that adds the items.
            //after the threads wake up with notify(), they still have to fight again for the lock before they can resume execution.
            while(q.isEmpty()) {
                try {
                    q.wait();   //waits to be notified by other threads.
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            int val = q.poll();
            q.notifyAll();
            return val;
        }
    }
}
