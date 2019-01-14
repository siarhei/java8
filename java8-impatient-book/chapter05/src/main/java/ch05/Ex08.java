package ch05;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class Ex08 {
    /*
    Obtain the offsets of today’s date in all supported time zones for the current time instant,
    turning ZoneId.getAvailableIds into a stream and using stream operations.
     */

    public static void main(String[] args) {
        Instant now = Instant.now();
        ZoneId.getAvailableZoneIds().stream()
                .map(ZoneId::of)
                .map(zoneId -> ZonedDateTime.ofInstant(now, zoneId))
                .forEach(System.out::println);
    }
}
