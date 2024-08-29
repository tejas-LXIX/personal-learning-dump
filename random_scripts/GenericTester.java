import java.util.ArrayList;
import java.util.List;

public class GenericTester {
    public static void main(String args[]){
        List<Solution<StudentDetails>> solutions = new ArrayList<>();
        solutions.add(new Solution<>());
        solutions.get(0).setMarks(73);
        solutions.get(0).setPayload(new StudentDetails("Tejas",20,"B-401,Patel's Green Park, Yapral"));
        System.out.println(solutions.get(0));
        System.out.println(solutions.getClass());
    }
}

class Solution<T> {
    private static final long serialVersionUID = 1L;
    private int marks;
    private T payload;

    public int getMarks() {
        return marks;
    }

    public void setMarks(int marks) {
        this.marks = marks;
    }

    public T getPayload() {
        return payload;
    }

    public void setPayload(T payload) {
        this.payload = payload;
    }
    @Override
    public String toString() {
        return payload.toString() + " " + getMarks() + " " + serialVersionUID;
    }
}

class StudentDetails {
    private String name;
    private int age;
    private String address;

    public StudentDetails(String name, int age, String address) {
        this.name = name;
        this.age = age;
        this.address = address;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return getName() + " " + getAge() + " " + getAddress();
    }
}
