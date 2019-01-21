package ch06;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Ex08 {
    //How large does an array have to be for Arrays.parallelSort to be faster than Arrays.sort on your computer?

    public static void main(String[] args) throws Exception {
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(
                        Objects.requireNonNull(
                                Ex07.class.getClassLoader().getResourceAsStream("WarAndPeace.txt"))));
             Stream<String> words = br.lines().flatMap(line -> Stream.of(line.split("[\\P{L}]+")))) {

            String[] unsorted = words.collect(Collectors.toList()).toArray(new String[]{});
            System.out.println(unsorted.length);

            Instant start = Instant.now();
            Arrays.parallelSort(unsorted);
            System.out.printf("Sorting done in %s\n", Duration.between(start, Instant.now()));
        }
    }
}
//length = 596628 words

//Arrays.sort
//0.305S
//0.333S
//0.303S

//Arrays.parallelSort
//0.229S
//0.23S
//0.297S
