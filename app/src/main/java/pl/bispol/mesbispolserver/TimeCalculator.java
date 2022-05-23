package pl.bispol.mesbispolserver;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Optional;

public class TimeCalculator {
    public static long timeBetweenToMinutes(LocalDateTime start, LocalDateTime stop) {
        LocalDateTime stopTime = Optional.ofNullable(stop).orElse(LocalDateTime.now());
        return Duration.between(start, stopTime).toMinutes();
    }
}
