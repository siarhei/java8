package ch03;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * @author siarhei
 */
public class Ex02 {
    /*

    When you use a ReentrantLock, you are required to lock and unlock with the idiom
    myLock.lock(); try {
    some action
    } finally { myLock.unlock();
    }
    Provide a method withLock so that one can call
    withLock(myLock, () -> { some action })


     */

    private static final Lock _lock = new ReentrantLock();

    public static void main(String[] args) {
        ExecutorService es = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        for (int i = 0; i < 8; i++) {
            Runnable action = getAction(i);
            es.submit(() -> withLock(_lock, action, Throwable::printStackTrace));
        }
        es.shutdown();
    }

    private static Runnable getAction(int i) {
        return () -> System.out.printf("[%s] doing sth on %d iteration\n", Thread.currentThread().getName(), i);
    }

    private static void withLock(Lock lock, Runnable action, Consumer<Throwable> handler) {
        lock.lock();
        try {
            Thread.sleep((long) (Math.random() * 2000));
            action.run();
        } catch (Exception e) {
            handler.accept(e);
        } finally {
            lock.unlock();
        }
    }
}
