import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * @author siarhei
 */
public class Ex06 {
    /*
     * The characterStream method in Section 2.3, “The filter, map, and flatMap Methods,” on page 25, was a bit clumsy,
     * first filling an array list and then turning it into a stream. Write a stream-based one-liner instead.
     * One approach is to make a stream of integers from 0 to s.length() - 1 and map that with the s::charAt method reference.
     */

    private static Stream<Character> characterStream(String input) {
        Stream<Character> stream;
        if (input == null) {
            stream = Stream.empty();
        } else {
            stream = IntStream.rangeClosed(0, input.length() - 1).mapToObj(input::charAt);
        }
        return stream;
    }

    public static void main(String[] args) {
        String collect = characterStream("Hello, Dude!").map(c -> "" + c).collect(Collectors.joining(", ", "[", "]"));
        System.out.println(collect);
    }
}
