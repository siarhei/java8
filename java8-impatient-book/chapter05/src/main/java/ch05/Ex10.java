package ch05;

import java.time.*;
import java.time.format.DateTimeFormatter;

public class Ex10 {
    /*
    Your flight from Los Angeles to Frankfurt leaves at 3:05 pm local time and takes 10 hours and 50 minutes.
    When does it arrive? Write a program that can handle calculations like this.
     */

    public static void main(String[] args) {
        LocalDate now = LocalDate.now();
        LocalTime departure = LocalTime.of(15, 5); //3:05 PM
        Duration flightDuration = Duration.ofMinutes(650); //10h 50m

        ZonedDateTime zonedDeparture = ZonedDateTime.of(
                now,
                departure,
                ZoneId.of(ZoneId.SHORT_IDS.get("PST"))
        );

        ZonedDateTime zonedArrival = zonedDeparture.plus(flightDuration).withZoneSameInstant(ZoneId.of("GMT+1"));

        System.out.println("Departure from Los Angeles: " + DateTimeFormatter.ISO_DATE_TIME.format(zonedDeparture));
        System.out.println("Arrival at Frankfurt: " + DateTimeFormatter.ISO_DATE_TIME.format(zonedArrival));
    }
}
