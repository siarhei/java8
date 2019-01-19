package ch06;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.LongAdder;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Ex03 {
    //Generate 1,000 threads, each of which increments a counter 100,000 times.
    //Compare the performance of using AtomicLong versus LongAdder.

    private static final int THREADS_N = 1_000;
    private static final int INC_N = 1_000_000;

    public static void main(String[] args) throws Exception {
        Instant before = Instant.now();

        //final AtomicLong incrementor = new AtomicLong(0);
        final LongAdder incrementor = new LongAdder();
        generateThreads(THREADS_N, () -> {
            for (int i = 0; i < INC_N; i++) {
                //incrementor.incrementAndGet();
                incrementor.increment();
            }
        }).forEach(Ex03::startAndJoin);

        Instant after = Instant.now();
        Duration duration = Duration.between(before, after);

        System.out.printf("Duration is %s; Inc: %d", duration, incrementor.sum());

    }

    private static Stream<Thread> generateThreads(int threadsNumber, Runnable incrementOperation) {
        return IntStream.range(0, threadsNumber)
                .mapToObj(i -> String.format("Thread %d", i))
                .map(name -> new Thread(incrementOperation, name));
    }

    private static void startAndJoin(Thread t) {
        t.start();
        try {
            t.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    //AtomicLong:
    //4.803S
    //4.808S
    //4.823

    //LongAdder
    //8.137S
    //8.084S
    //8.091S
}
