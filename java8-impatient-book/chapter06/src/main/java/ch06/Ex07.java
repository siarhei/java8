package ch06;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

import static java.util.Comparator.comparing;

public class Ex07 {
    //In a ConcurrentHashMap<String, Long>, find the key with maximum value (breaking ties arbitrarily).
    // Hint: reduceEntries.

    public static void main(String[] args) throws Exception {
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(
                        Objects.requireNonNull(
                                Ex07.class.getClassLoader().getResourceAsStream("WarAndPeace.txt"))))) {
            Stream<String> words = br.lines().flatMap(line -> Stream.of(line.split("[\\P{L}]+")));

            //data init
            final ConcurrentHashMap<String, Long> map = new ConcurrentHashMap<>();
            words.parallel()
                    .map(String::trim)
                    .filter(word -> !word.isEmpty())
                    .forEach(word -> map.computeIfAbsent(word, (key) -> (long) key.length()));

            //search
            Map.Entry<String, Long> keyWithMaxValue = map.reduceEntries(64,
                    (entry1, entry2) ->
                            Objects.compare(
                                    entry1,
                                    entry2,
                                    comparing(Map.Entry::getValue)) > 0 ? entry1 : entry2);

            System.out.println(keyWithMaxValue);
        }
    }
}
