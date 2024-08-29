//Non-generic
//changes made on list1 actually reflect in the map. This shows that java objects are always returned by reference (i.e the references to objects are copied by value).
//this is not the case with c++, where changes on the vector returned by the map don't reflect in the actual map.
import java.util.*;
public class mapTester {
    public static void main(String[] args) {
        Map<Character, List<Integer>> map=new HashMap<>();
        List<Integer> list = new ArrayList<>();
        for(int i=0;i<5;i++)
            list.add(i+1);
        map.put('a',list);
        List<Integer> list1 = map.get('a');
        for(int i=0;i<5;i++)
            list1.set(i,list1.get(i)+10);
        for(int i=0;i<5;i++)
            System.out.print(list1.get(i)+" ");
        System.out.println();
        for(int i=0;i<5;i++)
            System.out.print(list.get(i)+" ");
    }
}