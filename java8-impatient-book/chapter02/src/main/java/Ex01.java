import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.*;
import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 * @author siarhei
 */
public class Ex01 {

    private static final int AVAILABLE_PROCESSORS = Runtime.getRuntime().availableProcessors();
    private static final int LONG_WORD_LENGTH = 10;
    public static final String PATH_TO_FILE = "/Users/siarhei/work/Java8/java8-impatient-book/chapter02/src/main/resources/WarAndPeace.txt";
    private static Predicate<String> LONG_WORD = p -> p.length() >= LONG_WORD_LENGTH;


    public static void main(String[] args) throws Exception {
        String contents = new String(Files.readAllBytes(
                Paths.get(PATH_TO_FILE)), StandardCharsets.UTF_8); // Read file into string
        List<String> words = Arrays.asList(contents.split("[\\P{L}]+"));
        System.out.println("Size: " + words.size());

        //44ms 24ms 28ms 28ms
        long before = System.currentTimeMillis();
        /*
        System.out.println("Serial stream: " + longWordsCountWithSerialStream(words));
        System.out.printf("Serial stream took: %d ms\n\n", System.currentTimeMillis() - before);

        //485ms 399ms 538ms 485ms
        before = System.currentTimeMillis();
        System.out.println("Parallel stream: " + longWordsCountWithParallelStream(words));
        System.out.printf("Parallel stream took: %d ms\n\n", System.currentTimeMillis() - before);

        //440ms 429ms 419ms 433ms
        before = System.currentTimeMillis();
        System.out.println("Thread pool: " + longWordsCountWithMultiThreading(words));
        System.out.printf("Thread pool took: %d ms\n\n", System.currentTimeMillis() - before);
        */

        //
        before = System.currentTimeMillis();
        System.out.println("Fork join: " + longWordsCountWithForkJoin(words));
        System.out.printf("Fork join took: %d ms\n\n", System.currentTimeMillis() - before);
    }

    private static long longWordsCountWithSerialStream(List<String> words) {
        Stream<String> stream = words.stream()
                .filter(LONG_WORD);
        return stream.count();
    }

    private static long longWordsCountWithParallelStream(List<String> words) {
        return words.parallelStream().filter(LONG_WORD)
                .count();
    }

    @SuppressWarnings("unchecked")
    private static long longWordsCountWithMultiThreading(List<String> words) throws Exception {
        ExecutorService threadPool = Executors.newFixedThreadPool(AVAILABLE_PROCESSORS);
        int size = words.size();
        Future<Integer>[] futures = new Future[AVAILABLE_PROCESSORS];
        int step = size / AVAILABLE_PROCESSORS;
        for (int i = 0; i < AVAILABLE_PROCESSORS; i++) {
            int end = size - (i + 1) * step < step ? size : (i + 1) * step;
            futures[i] = process(threadPool, words, i * step, end);
        }

        int result = 0;
        for (Future<Integer> future : futures) {
            result += future.get();
        }
        threadPool.shutdown();
        System.out.println(result);
        return result;
    }

    private static Future<Integer> process(ExecutorService es, List<String> words, int start, int end) {
        return es.submit(() -> {
            //System.out.printf("[%s]: start (%d); end (%d) \n", Thread.currentThread().getName(), start, end);
            int longWordsCounter = 0;
            for (int i = start; i < end; i++) {
                if (LONG_WORD.test(words.get(i))) {
                    longWordsCounter++;
                }
            }
            return longWordsCounter;
        });
    }

    static class LongWordTask extends RecursiveTask<Long> {
        final static int THRESHOLD = 512;

        final List<String> words;
        final int start;
        final int end;

        LongWordTask(List<String> words, int start, int end) {
            this.words = words;
            this.start = start;
            this.end = end;
        }

        @Override
        protected Long compute() {
            if (end - start > THRESHOLD) {
                //Java 7 style

                /*int mid = (start + end) >> 1;
                LongWordTask t1 = new LongWordTask(words, start, mid);
                t1.fork();
                LongWordTask t2 = new LongWordTask(words, mid, end);
                return t2.compute() + t1.join();*/

                //Java 8 style
                return invokeAll(createTasks()).stream().mapToLong(LongWordTask::join).sum();
            } else {
                int longWordsCounter = 0;
                for (int i = start; i < end; i++) {
                    if (LONG_WORD.test(words.get(i))) {
                        longWordsCounter++;
                    }
                }
                //System.out.printf("[%s]: start (%d); end (%d). Found: (%d) \n", Thread.currentThread().getName(), start, end, longWordsCounter);
                return (long) longWordsCounter;
            }
        }

        private Collection<LongWordTask> createTasks() {
            int mid = (start + end) >> 1;
            LongWordTask t1 = new LongWordTask(words, start, mid);
            LongWordTask t2 = new LongWordTask(words, mid, end);
            return Arrays.asList(t1, t2);
        }
    }

    private static long longWordsCountWithForkJoin(List<String> words) {
        ForkJoinPool forkJoinPool = ForkJoinPool.commonPool();
        return forkJoinPool.invoke(new LongWordTask(words, 0, words.size()));
    }
}
