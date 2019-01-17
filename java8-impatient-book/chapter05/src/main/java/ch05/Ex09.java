package ch05;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Ex09 {
    //Again using stream operations, find all time zones whose offsets aren’t full hours.

    public static void main(String[] args) {

        Instant now = Instant.now();

        Map<ZoneOffset, List<ZoneId>> collect = ZoneId.getAvailableZoneIds().stream()
                .map(ZoneId::of)
                .collect(Collectors.groupingBy(
                        zoneId -> zoneId.getRules().getOffset(now)
                ));

        collect.entrySet().stream()
                .filter(e -> e.getKey().getTotalSeconds() % 3600 != 0)
                .forEach(e -> System.out.printf("[%s] - (%s)\n", e.getValue(), e.getKey()));
    }
}
