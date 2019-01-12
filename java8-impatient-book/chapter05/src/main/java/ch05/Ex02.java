package ch05;

import java.time.LocalDate;
import java.time.Period;
import java.util.stream.Stream;

public class Ex02 {

    /*
    What happens when you add one year to LocalDate.of(2000, 2, 29)? Four years?
    Four times one year?
     */

    public static void main(String[] args) {
        LocalDate date = LocalDate.of(2000, 2, 29);

        System.out.println(date.plus(Period.ofYears(1))); //2001-02-28
        System.out.println(date.plusYears(1)); //2001-02-28

        Stream.iterate(1, i -> i + 1)
                .limit(4)
                .map(Period::ofYears)
                .map(date::plus)
                .forEach(System.out::println);
    }
}
