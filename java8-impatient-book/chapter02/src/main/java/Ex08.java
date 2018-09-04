import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * @author siarhei
 */
public class Ex08 {

    /*
    * Write a method public static <T> Stream<T> zip(Stream<T> first, Stream<T> second) that
    * alternates elements from the streams first and second, stopping when one of them runs out of elements.
    * */

    public static void main(String[] args) {
        Stream<String> letters = Stream.of("A", "B", "C");
        Stream<String> digits = Stream.of("1", "2", "3", "4");

        zip(letters, digits).forEach(System.out::print);
    }

    private static <T> Stream<T> zip(Stream<T> first, Stream<T> second) {
        List<T> list1 = first.collect(Collectors.toList());
        List<T> list2 = second.collect(Collectors.toList());

        if (list1.isEmpty() || list2.isEmpty()) {
            return Stream.empty();
        }
        int min = Math.min(list1.size(), list2.size());
        return IntStream.iterate(0, i -> i + 1)
                .limit(min)
                .mapToObj(i -> Stream.of(list1.get(i), list2.get(i)))
                .reduce(Stream.empty(), Stream::concat);
    }
}
