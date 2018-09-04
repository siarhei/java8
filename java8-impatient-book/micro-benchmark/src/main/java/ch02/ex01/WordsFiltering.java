package ch02.ex01;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

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
public class WordsFiltering {

    private static final int AVAILABLE_PROCESSORS = Runtime.getRuntime().availableProcessors();
    private static final int LONG_WORD_LENGTH = 10;
    private static final String PATH_TO_FILE = "/Users/siarhei/work/Java8/java8-impatient-book/chapter02/src/main/resources/WarAndPeace.txt";
    private static Predicate<String> LONG_WORD = p -> p.length() >= LONG_WORD_LENGTH;

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(WordsFiltering.class.getSimpleName())
                .threads(4)
                .forks(1)
                .build();

        new Runner(opt).run();
    }

    @org.openjdk.jmh.annotations.State(Scope.Benchmark)
    public static class State {
        static String content;
        static List<String> words;
        static {
            try {
                content = new String(Files.readAllBytes(
                        Paths.get(PATH_TO_FILE)), StandardCharsets.UTF_8); // Read file into string
                words = Arrays.asList(content.split("[\\P{L}]+"));
            } catch (Exception e) {
                System.err.println("Error occurred: " + e);
            }
        }
    }

    @Benchmark
    @BenchmarkMode(Mode.SampleTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public long longWordsCountWithSerialStream(State state) {
        Stream<String> stream = state.words.stream()
                .filter(LONG_WORD);
        return stream.count();
    }

    @Benchmark
    @BenchmarkMode(Mode.SampleTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public long longWordsCountWithParallelStream(State state) {
        return state.words.parallelStream().filter(LONG_WORD)
                .count();
    }

    /*@Benchmark
    @OutputTimeUnit(TimeUnit.SECONDS)*/
    @SuppressWarnings("unchecked")
    public long longWordsCountWithMultiThreading(State state) throws Exception {
        ExecutorService threadPool = Executors.newFixedThreadPool(AVAILABLE_PROCESSORS);
        int size = state.words.size();
        Future<Integer>[] futures = new Future[AVAILABLE_PROCESSORS];
        int step = size / AVAILABLE_PROCESSORS;
        for (int i = 0; i < AVAILABLE_PROCESSORS; i++) {
            int end = size - (i + 1) * step < step ? size : (i + 1) * step;
            futures[i] = process(threadPool, state.words, i * step, end);
        }

        int result = 0;
        for (Future<Integer> future : futures) {
            result += future.get();
        }
        threadPool.shutdown();
        //System.out.println(result);
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

    /*@Benchmark
    @OutputTimeUnit(TimeUnit.SECONDS)*/
    public long longWordsCountWithForkJoin(State state) {
        ForkJoinPool forkJoinPool = ForkJoinPool.commonPool();
        return forkJoinPool.invoke(new LongWordTask(state.words, 0, state.words.size()));
    }
}
