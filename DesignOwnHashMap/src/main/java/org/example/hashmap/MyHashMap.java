package org.example.hashmap;

public class MyHashMap<K,V> {
    private static final int DEFAULT_CAPACITY = 16;
    private static final float DEFAULT_LOAD_FACTOR = 0.75f;

    private int capacity;

    //The load factor plays a crucial role when it comes to resizing the Hashmap bucket. The load factor is the ratio of the number of elements present to the total number of slots present in the Bucket.
    private float loadFactor;
    private int size;
    private Node<K,V> [] table;

    public MyHashMap() {
        this(DEFAULT_CAPACITY, DEFAULT_LOAD_FACTOR);
    }

    public MyHashMap(int capacity, float loadFactor) {
        this.capacity = capacity;
        this.loadFactor = loadFactor;
        this.table = new Node[capacity];
    }

    //hash function is responsible for taking a key and returning a unique index for that key in the array. Should return the same index for the same key every time it's called.
    private int hash(K key) {
        return key.hashCode() % capacity;
    }

    //Hash collisions occur when two or more keys are hashed to the same index in the array. This can cause problems if we try to store multiple values at the same index. There are two main techniques for handling hash collisions: chaining and open addressing.
    //we have implemented chaining here. when two same keys try to occupy the same index of bucket/array, then we can create a linked list at each index of that bucket.
    //the new node is inserted at the start of the linkedlist (at table[index]). the original nodes now become linked to this new node.
    public void put(K key, V value) {
        int index = hash(key);
        Node<K,V> node = table[index];
        while (node != null) {
            if (node.key.equals(key)) {
                node.value = value;
                return;
            }
            node = node.next;
        }
        Node<K,V> newNode = new Node<>(key,value);
        newNode.next = table[index];
        table[index] = newNode;
        size++;
        if (size > capacity*loadFactor) {
            resize();
        }
    }

    public V get(K key) {
        int index = hash(key);
        Node<K,V> node = table[index];
        while(node != null) {
            if(node.key.equals(key)) {
                return node.value;
            }
            node = node.next;
        }
        return null;
    }

    public void remove(K key) {
        int index = hash(key);
        Node<K,V> node = table[index];
        Node<K,V> prev = null;
        while(node != null) {
            if(node.key.equals(key)) {
                if(prev == null) {
                    table[index] = node.next;
                } else {
                    prev.next = node.next;
                }
                size--;
                return;
            }
            prev = node;
            node = node.next;
        }
    }

    private void resize() {
        int newCapacity = capacity*2;
        Node<K,V> newTable[] = new Node[newCapacity];
        for(int i=0;i<capacity;i++) {
            Node<K,V> node = table[i];
            while(node!=null) {
                Node<K,V> next = node.next;
                int index = hash(node.key);
                node.next = newTable[index];
                newTable[index] = node;
                node = next;
            }
        }
        table = newTable;
        capacity = newCapacity;
    }

    //6.2. Compelling Reasons to Use a static Inner Class
    //Let’s take a look at a few reasons for using static inner classes in our code:
    //
    //grouping classes that will be used only in one place increases encapsulation
    //we bring the code closer to the only place that will use it. This increases readability, and the code is more maintainable.
    //if a nested class doesn’t require any access to its enclosing class instance members, it’s better to declare it as static. This way, it won’t be coupled to the outer class and is therefore more optimal, as they won’t require any heap or stack memory.

    private static class Node<K,V> {
        final K key;
        V value;
        Node<K,V> next;

        Node(K key, V value) {
            this.key = key;
            this.value = value;
            this.next = null;
        }

        Node(K key, V value, Node<K,V> next) {
            this.key = key;
            this.value = value;
            this.next = next;
        }
    }
}
