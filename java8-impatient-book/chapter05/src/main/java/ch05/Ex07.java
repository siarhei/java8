package ch05;

import java.time.*;
import java.time.temporal.ChronoUnit;

public class Ex07 {

    /*
    Implement a TimeInterval class that represents an interval of time, suitable for calendar events
    (such as a meeting on a given date from 10:00 to 11:00). Provide a method to check whether two intervals overlap.
     */
    public static void main(String[] args) {
        TimeInterval even1 = new TimeInterval(LocalDateTime.of(2019, 1, 13, 12, 0), Duration.of(2, ChronoUnit.HOURS));
        TimeInterval even2 = new TimeInterval(LocalDateTime.of(2019, 1, 13, 15, 0), Duration.of(30, ChronoUnit.MINUTES));

        System.out.println(even1.isOverlap(even2));
    }

    private static class TimeInterval {
        private final LocalDateTime startTime;
        private final Duration duration;

        public TimeInterval(LocalDateTime startTime, Duration duration) {
            this.startTime = startTime;
            this.duration = duration;
        }

        /**
         * True if both intervals are overlapped
         *
         * @param other
         * @return
         */
        boolean isOverlap(TimeInterval other) {
            Instant from = startTime.toInstant(ZoneOffset.UTC);
            Instant to = from.plus(duration);

            Instant othFrom = other.getStartTime().toInstant(ZoneOffset.UTC)
                    .plus(other.getDuration());
            return othFrom.isAfter(from) && othFrom.isBefore(to);
        }

        public LocalDateTime getStartTime() {
            return startTime;
        }

        public Duration getDuration() {
            return duration;
        }
    }
}
