package ch06;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.LongAdder;

public class Ex02 {
    //Does a LongAdder help with yielding a sequence of increasing IDs? Why or why not?
    private static final int CORES = Runtime.getRuntime().availableProcessors();

    public static void main(String[] args) throws Exception {
        LongAdder longAdder = new LongAdder();

        //When you have a very large number of threads accessing the same atomic values, performance suffers
        //because the optimistic updates require too many retries. Java 8 provides classes LongAdder and
        //LongAccumulator to solve this problem. A LongAdder is composed of multiple variables whose collective
        //sum is the current value. Multiple threads can update different summands, and new summands are
        //automatically provided when the number of threads increases. This is efficient in the common situation
        //where the value of the sum is not needed until after all work has been done.

        //LongAdder is not the best choice for "sequence increasing IDs" because 'sum' operation yields summing all summands
        //and usually used when the main work is done. AtomicLong is better option since it holds only one value and
        //doesn't need any intermediate operations to get the result
        AtomicLong atomicLong = new AtomicLong(0L);
        ExecutorService es = Executors.newFixedThreadPool(CORES);
        int iterations = CORES << 2;
        for (int i = 0; i < iterations; i++) {
            es.execute(() -> {
                System.out.printf("[%s] incrementing to %d\n", Thread.currentThread().getName(), atomicLong.incrementAndGet());
            });
        }
        es.shutdown();
        es.awaitTermination(10, TimeUnit.SECONDS);
        System.out.println(atomicLong.get());
    }
}
