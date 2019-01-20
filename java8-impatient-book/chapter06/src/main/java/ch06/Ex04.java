package ch06;

import java.time.Duration;
import java.time.Instant;
import java.util.Random;
import java.util.concurrent.atomic.LongAccumulator;

public class Ex04 {
    //Use a LongAccumulator to compute the maximum or minimum of the accumulated elements.
    private static final LongAccumulator MAX = new LongAccumulator(Math::max, Long.MIN_VALUE);
    private static final LongAccumulator MIN = new LongAccumulator(Math::min, Long.MAX_VALUE);

    public static void main(String[] args) {
        LongAccumulator accumulator = MIN;
        Instant start = Instant.now();

        Random random = new Random(start.toEpochMilli());
        random.longs().limit(100_000_000).parallel().forEach(accumulator::accumulate);

        System.out.printf("%d, %s", accumulator.longValue(), Duration.between(start, Instant.now()));
    }
}
