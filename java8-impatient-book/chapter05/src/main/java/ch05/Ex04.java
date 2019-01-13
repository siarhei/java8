package ch05;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Ex04 {
    /*
    Write an equivalent of the Unix cal program that displays a calendar for a month. For example, java Cal 3 2013 should display

123 4 5 6 7 8 9 10 11 12 13 14 15 16 17 18 19 20 21 22 23 24 25 26 27 28 29 30 31
indicating that March 1 is a Friday. (Show the weekend at the end of the week.)
     */

    public static void main(String[] args) throws Exception {
        assert args != null : "Arguments are not defined";
        assert args.length == 2 : "Expected 2 arguments: year and month number (1 based)";

        StringBuilder sb = new StringBuilder();
        LocalDate date = LocalDate.of(Integer.parseInt(args[0]), Integer.parseInt(args[1]), 1);

        //Creating the header of calendar
        String header = Stream.of(DayOfWeek.values()).map(dow -> dow.name().substring(0, 1)).collect(Collectors.joining("\t"));
        sb.append(header).append("\n");

        //adjusting the first date of month
        for (int i = 1; i < date.getDayOfWeek().getValue(); i++) {
            sb.append("\t");
        }

        int lengthOfMonth = date.lengthOfMonth();
        while (date.getDayOfMonth() < lengthOfMonth) {
            sb.append(date.getDayOfMonth());
            if (date.getDayOfWeek() == DayOfWeek.SUNDAY) {
                sb.append("\n");
            } else {
                sb.append("\t");
            }
            date = date.plusDays(1);
        }

        System.out.println(sb);
    }
}
