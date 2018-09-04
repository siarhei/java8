import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * @author siarhei
 */
public class Ex04 {
    public static void main(String[] args) {
        int[] arr = {1, 2, 3, 4, 5};
        Stream<int[]> streamOfArrays = Stream.of(arr);
        IntStream intStream = IntStream.of(arr);

        intStream.filter(i -> i%2==0).forEach(System.out::println);
    }
}
