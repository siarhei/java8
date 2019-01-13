package ch05;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Ex06 {
    //List all Friday the 13th in the twentieth century.

    public static void main(String[] args) {
        final LocalDate startDate = LocalDate.of(1900, 1, 13);
        final LocalDate endDate = startDate.plus(1, ChronoUnit.CENTURIES);
        for (LocalDate date = startDate; date.isBefore(endDate); date = date.plusMonths(1)) {
            if (date.getDayOfWeek() == DayOfWeek.FRIDAY) {
                System.out.println(date);
            }
        }
    }
}
