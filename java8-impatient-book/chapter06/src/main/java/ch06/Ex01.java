package ch06;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Stream;

public class Ex01 {

    // Write a program that keeps track of the longest string that is observed by a number of threads.
    // Use an AtomicReference and an appropriate accumulator.

    public static void main(String[] args) throws Exception {
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(
                        Objects.requireNonNull(
                                Ex01.class.getClassLoader().getResourceAsStream("WarAndPeace.txt"))));
             //splitting lines into words using non letters separator
             Stream<String> words = br.lines().flatMap(line -> Stream.of(line.split("[\\P{L}]+")))) {

            final AtomicReference<String> longestWord = new AtomicReference<>("");
            //getAndAccumulate equals to accumulateAndGet in this task
            words.parallel()
                    .map(String::trim)
                    .filter(word -> !word.isEmpty())
                    //.peek(w -> System.out.printf("[%s]: %s\n", Thread.currentThread().getName(), w))
                    .forEach(word -> longestWord.getAndAccumulate(word, (current, update) -> current.length() > update.length() ? current : update));

            System.out.printf("(size=%d) %s", longestWord.get().length(), longestWord.get());
        }
    }
}
