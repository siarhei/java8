package ch05;

import java.time.LocalDate;
import java.time.Month;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAdjuster;
import java.util.function.Predicate;

public class Ex03 {

    private static final Predicate<LocalDate> WORKDAY = d -> d.getDayOfWeek().getValue() < 6;
    private static final Predicate<LocalDate> WEEKEND = WORKDAY.negate();

    /*
    Implement a method next that takes a Predicate<LocalDate> and returns an adjuster yielding the next date fulfilling the predicate. For example,
today.with(next(w -> getDayOfWeek().getValue() < 6))
computes the next workday.
     */

    public static void main(String[] args) {
        LocalDate today = LocalDate.of(2019, Month.JANUARY, 12);
        System.out.println(today.with(next(WORKDAY)));
    }

    private static TemporalAdjuster next(@SuppressWarnings("SameParameterValue") Predicate<LocalDate> predicate) {
        return (Temporal date) -> {
            LocalDate dt;
            //noinspection StatementWithEmptyBody
            for (dt = (LocalDate) date; !predicate.test(dt); dt = dt.plusDays(1)) {}
            return dt;
        };
    }
}
