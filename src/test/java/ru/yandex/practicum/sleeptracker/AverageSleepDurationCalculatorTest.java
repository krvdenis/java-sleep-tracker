package ru.yandex.practicum.sleeptracker;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.sleeptracker.functions.AverageSleepDurationCalculator;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AverageSleepDurationCalculatorTest {
    private final AverageSleepDurationCalculator averageSleepDurationCalculator = new AverageSleepDurationCalculator();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yy HH:mm");
    List<SleepingSession> sleepingSessions;

    @BeforeEach
    void init() {
        sleepingSessions = new ArrayList<>();
    }

    @Test
    void shouldReturnZeroWhenListOfSleepSessionsIsEmpty() {
        SleepAnalysisResult averageSleepDurationInMinutes = averageSleepDurationCalculator.apply(sleepingSessions);

        assertEquals("Средняя продолжительность сессии в минутах: ", averageSleepDurationInMinutes.getDescription());
        assertEquals(0, averageSleepDurationInMinutes.getResult());
    }

    @Test
    void shouldReturnAverageDurationOfSleepSessions() {
        sleepingSessions.addAll(List.of(new SleepingSession(
                        LocalDateTime.parse("01.10.25 23:15", formatter),
                        LocalDateTime.parse("02.10.25 10:15", formatter), "NORMAL"),
                new SleepingSession(
                        LocalDateTime.parse("02.10.25 22:15", formatter),
                        LocalDateTime.parse("03.10.25 10:15", formatter), "NORMAL"
                )));
        SleepAnalysisResult averageSleepDurationInMinutes = averageSleepDurationCalculator.apply(sleepingSessions);

        assertEquals("Средняя продолжительность сессии в минутах: ", averageSleepDurationInMinutes.getDescription());
        assertEquals(690, averageSleepDurationInMinutes.getResult());
    }
}