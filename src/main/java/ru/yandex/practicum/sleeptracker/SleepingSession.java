package ru.yandex.practicum.sleeptracker;

import java.time.Duration;
import java.time.LocalDateTime;

public class SleepingSession {

    private final LocalDateTime sleepDateTime;
    private final LocalDateTime wakeUpDateTime;
    private final String sleepQuality;
    private final Duration sleepduration;

    public SleepingSession(LocalDateTime sleepDateTime, LocalDateTime wakeUpDateTime, String sleepQuality) {
        this.sleepDateTime = sleepDateTime;
        this.wakeUpDateTime = wakeUpDateTime;
        this.sleepQuality = sleepQuality;
        this.sleepduration = Duration.between(sleepDateTime, wakeUpDateTime);
    }

    public Duration getSleepduration() {
        return sleepduration;
    }

    public String getSleepQuality() {
        return sleepQuality;
    }

    public LocalDateTime getSleepDateTime() {
        return sleepDateTime;
    }

    public LocalDateTime getWakeUpDateTime() {
        return wakeUpDateTime;
    }
}