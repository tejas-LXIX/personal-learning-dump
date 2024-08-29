import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class StreamsTester {
    public static void main(String[] args) {
        List<String> list = Arrays.asList("abc1", "abc2", "abc3", "abc4", "xyz2");
        Optional<String> stream = list.stream().filter(element -> {
            System.out.println("filter() was called");
            return element.contains("2");
        }).map(element -> {
            System.out.println("map() was called");
            return element.toUpperCase();
        }).findFirst();

        System.out.println("First stream pipeline executed.\n");

        List<String> stringList = list.stream().filter(element -> {
            System.out.println("filter() was called");
            return element.contains("2");
        }).map(element -> {
            System.out.println("map() was called");
            return element.toUpperCase();
        }).collect(Collectors.toList());
    }
}
