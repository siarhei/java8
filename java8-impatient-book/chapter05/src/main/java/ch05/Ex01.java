package ch05;

import java.time.LocalDate;
import java.time.Month;
import java.time.Period;
import java.time.temporal.ChronoField;

/**
 * @author siarhei
 */
public class Ex01 {
    /*
    * Compute Programmer’s Day without using plusDays.
    */

    public static void main(String[] args) {
        LocalDate date = LocalDate.of(2019, Month.JANUARY, 1);
        LocalDate studentDay = date.plus(Period.ofDays(255));

        System.out.printf("%d, %s", studentDay.get(ChronoField.DAY_OF_YEAR), date);
    }
}
