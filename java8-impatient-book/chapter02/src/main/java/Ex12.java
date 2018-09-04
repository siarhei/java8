import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * @author siarhei
 */
public class Ex12 {

    private static final Predicate<String> SHORT_WORD = w -> w.length() < 12;

    /*
    Count all short words in a parallel Stream<String>, as described in Section 2.13, “Parallel Streams,” on page 40,
    by updating an array of AtomicInteger. Use the atomic getAndIncrement method to safely increment each counter.
     */

    /*
    13.
    Repeat the preceding exercise, but filter out the short strings and use the collect method with
    Collectors.groupingBy and Collectors.counting.
     */

    public static void main(String[] args) throws Exception {
        String contents = new String(Files.readAllBytes(
                Paths.get(Ex01.PATH_TO_FILE)), StandardCharsets.UTF_8); // Read file into string
        List<String> words = Arrays.asList(contents.split("[\\P{L}]+"));

        //threadSafeVersion(words);
        //threadUnSafeVersion(words);
        groupingBy(words);
    }

    private static void groupingBy(List<String> words) {
        Map<Integer, Long> collect = words.parallelStream()
                .filter(SHORT_WORD)
                .collect(Collectors.groupingBy(String::length, Collectors.counting()));

        for (Map.Entry<Integer, Long> entry : collect.entrySet()) {
            System.out.printf("length=%d, words:%d\n", entry.getKey(), entry.getValue());
        }
    }

    private static void threadUnSafeVersion(List<String> words) {
        int[] lengthCounter = new int[12];

        words.parallelStream()
                .filter(SHORT_WORD)
                .map(String::length)
                .forEach(ln -> lengthCounter[ln]++);

        for (int i = 0; i < lengthCounter.length; i++) {
            System.out.printf("length=%d, words:%d\n", i, lengthCounter[i]);
        }
    }

    private static void threadSafeVersion(List<String> words) {
        AtomicInteger[] lengthCounter = new AtomicInteger[12];
        init(lengthCounter);

        words.parallelStream()
                .filter(SHORT_WORD)
                .map(String::length)
                .forEach(ln -> lengthCounter[ln].incrementAndGet());

        print(lengthCounter);
    }

    private static void print(AtomicInteger[] lengthCounter) {
        for (int i = 0; i < lengthCounter.length; i++) {
            AtomicInteger count = lengthCounter[i];
            System.out.printf("length=%d, words:%d\n", i, count.get());
        }
    }

    private static void init(AtomicInteger[] lengthCounter) {
        for (int i = 0; i < lengthCounter.length; i++) {
            lengthCounter[i] = new AtomicInteger(0);
        }
    }
}
