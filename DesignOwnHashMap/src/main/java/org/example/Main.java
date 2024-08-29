package org.example;

import org.example.hashmap.MyHashMap;

public class Main {
    public static void main(String[] args) {
        MyHashMap<Integer,String> myHashMap = new MyHashMap<>();
        myHashMap.put(1, "apple");
        myHashMap.put(17, "banana");
        String val = myHashMap.get(1);
        System.out.println(val);
        val = myHashMap.get(17);
        System.out.println(val);
        val = myHashMap.get(4);
        System.out.println(val);
    }
}