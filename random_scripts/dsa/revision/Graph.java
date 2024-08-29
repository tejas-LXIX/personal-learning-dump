package dsa.revision;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

class GenericGraph<T> {
    private Map<T, List<T>> graph = new HashMap<>();

    public void addVertex(T vertex) {
        graph.put(vertex, new LinkedList<>());
    }

    public void addEdge(T source, T destination, boolean biDirectional) {
        if(!graph.containsKey(source)) {
            addVertex(source);
        }
        if(!graph.containsKey(destination)) {
            addVertex(destination);
        }
        graph.get(source).add(destination);
        if(biDirectional) {
            graph.get(destination).add(source);
        }
    }

    public int getVertexCount() {
        return graph.keySet().size();
    }

    public int getEdgesCount(boolean bidirectional) {
        int count = 0;
        for(Map.Entry<T,List<T>> entry : graph.entrySet()) {
            count+=entry.getValue().size();
        }
        if(bidirectional) {
            return count/2;
        }
        return count;
    }

    public boolean hasVertex(T vertex) {
        return graph.containsKey(vertex);
    }

    public boolean hasEdge(T source, T destination) {
        if(!graph.containsKey(source)) {
            return false;
        }
        return graph.get(source).contains(destination);
    }

    public List<T> getNeighbours(T source) {
        if (!graph.containsKey(source)) {
            return Collections.emptyList();
        }
        return new LinkedList<>(graph.get(source));
    }
}

public class Graph {

    public static void main(String[] args) {
        GenericGraph<Integer> g = new GenericGraph<>();

        // edges are added.
        // Since the graph is bidirectional,
        // so boolean bidirectional is passed as true.
        g.addEdge(0, 1, true);
        g.addEdge(0, 4, true);
        g.addEdge(1, 2, true);
        g.addEdge(1, 3, true);
        g.addEdge(1, 4, true);
        g.addEdge(2, 3, true);
        g.addEdge(3, 4, true);

        // Gives the no of vertices in the graph.
        System.out.println(g.getVertexCount());

        // Gives the no of edges in the graph.
        System.out.println(g.getEdgesCount(true));

        // Tells whether the edge is present or not.
        System.out.println(g.hasEdge(3, 4));

        // Tells whether vertex is present or not
        System.out.println(g.hasVertex(5));
        List<Integer> neighbours = g.getNeighbours(1);
        for(int neighbour:neighbours) {
            System.out.println(neighbour);
        }

        System.out.println("Student Graph starts from here");

        GenericGraph<Student> studGraph = new GenericGraph<>();
        Student s1 = new Student("tejas", 23);
        Student s2 = new Student("kashish", 24);
        Student s3 = new Student("arbab", 45);

        studGraph.addEdge(s1,s2,true);
        studGraph.addEdge(s1,s3,false);
        studGraph.addEdge(s2,s3,true);

        System.out.println(studGraph.getVertexCount());

        // Gives the no of edges in the graph.
        System.out.println(studGraph.getEdgesCount(false));

        // Tells whether the edge is present or not.
        System.out.println(studGraph.hasEdge(s1, s3));
        System.out.println(studGraph.hasEdge(s3, s1));

        // Tells whether vertex is present or not
        System.out.println(studGraph.hasVertex(s2));
        System.out.println(studGraph.hasVertex(new Student("abcd",367)));
        List<Student> studentNeighbours = studGraph.getNeighbours(s1);
        for(Student neighbour:studentNeighbours) {
            System.out.println(neighbour.getName() + " " + neighbour.getAge());
        }

    }

}

class Student {
    private String name;
    private int age;

    Student(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
