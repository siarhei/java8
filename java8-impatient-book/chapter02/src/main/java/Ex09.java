import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

/**
 * @author siarhei
 */
public class Ex09 {
    /*
     * Join all elements in a Stream<ArrayList<T>> to one ArrayList<T>. Show how to do this with the three forms of reduce.
     * */

    public static void main(String[] args) {
        List<String> letters = new ArrayList<>(Arrays.asList("A", "B", "C"));
        List<String> digits = new ArrayList<>(Arrays.asList("1", "2", "3"));
        Stream<List<String>> streamOfLists = Stream.<List<String>>builder().add(letters).add(digits).build();

        print(join(streamOfLists));
    }

    private static <T> List<T> join(Stream<List<T>> streamOfLists) {
        //1 - identity + accumulator
        //return streamOfLists.reduce(new ArrayList<>(), (a, b) -> {a.addAll(b); return a;});
        //2 - accumulator
        //return streamOfLists.reduce((result, element) -> {result.addAll(element); return result;}).orElse(Collections.emptyList());
        //3 - identity; accumulator; combiner
        return streamOfLists.reduce(
                new ArrayList<>(),
                (result, element) -> {
                    result.addAll(element);
                    return result;
                },
                (list1, list2) -> {
                    ArrayList<T> comb = new ArrayList<>(list1);
                    comb.addAll(list2);
                    return comb;
                });
    }

    private static void print(List<?> list) {
        list.forEach(System.out::print);
    }
}
