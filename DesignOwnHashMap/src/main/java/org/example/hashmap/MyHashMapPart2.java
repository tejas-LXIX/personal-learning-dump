package org.example.hashmap;

public class MyHashMapPart2<K,V> {

    private static final int DEFAULT_CAPACITY = 16;

    private static final float DEFAULT_LOAD_FACTOR = 0.75f;

    private int capacity;

    private float loadFactor;

    private int size;

    private Node<K,V> [] table;

    public MyHashMapPart2() {
        this(DEFAULT_CAPACITY, DEFAULT_LOAD_FACTOR);
    }

    public MyHashMapPart2(int capacity, float loadFactor) {
        this.capacity = capacity;
        this.loadFactor = loadFactor;
        this.table = (Node<K,V>[]) new Node[capacity];
    }

    private int hash(K key) {
        return key.hashCode() % capacity;
    }

    public void put(K key, V value) {
        int index = hash(key);
        Node<K,V> node = table[index];
        while(node!=null) {
            if(node.key.equals(key)) {
                node.value = value;
                return;
            }
            node=node.next;
        }
        Node<K,V> newNode = new Node<>(key, value);
        newNode.next = table[index];
        table[index] = newNode;
        size++;
        if(size > capacity*loadFactor) {
            resize();
        }
    }

    public V get(K key) {
        int index = hash(key);
        Node<K,V> node = table[index];
        while(node!=null) {
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
        while(node!=null) {
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
        Node<K,V> newTable[] = new Node[capacity];
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

        capacity = newCapacity;
        table = newTable;
    }

    private static class Node<K,V> {
        private K key;

        private V value;

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
