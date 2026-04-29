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
    private static final String DATE_TIME_FORMAT = "dd.MM.yy HH:mm";
    private static final String DESCRIPTION_OF_RESULT = "Средняя продолжительность сессий в минутах: ";
    private final AverageSleepDurationCalculator averageSleepDurationCalculator = new AverageSleepDurationCalculator();
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_TIME_FORMAT);
    private List<SleepingSession> sleepingSessions;

    @BeforeEach
    void init() {
        sleepingSessions = new ArrayList<>();
    }

    @Test
    void shouldReturnZeroWhenListOfSleepSessionsIsEmpty() {
        SleepAnalysisResult averageSleepDurationInMinutes = averageSleepDurationCalculator.apply(sleepingSessions);

        assertEquals(DESCRIPTION_OF_RESULT, averageSleepDurationInMinutes.getDescription());
        assertEquals(0, averageSleepDurationInMinutes.getResult());
    }

    @Test
    void shouldReturnAverageDurationOfSleepSessions() {
        sleepingSessions.addAll(List.of(new SleepingSession(
                        LocalDateTime.parse("01.10.25 23:15", formatter),
                        LocalDateTime.parse("02.10.25 10:15", formatter), "NORMAL"),
                new SleepingSession(
                        LocalDateTime.parse("02.10.25 22:15", formatter),
                        LocalDateTime.parse("03.10.25 10:15", formatter), "NORMAL")
        ));
        SleepAnalysisResult averageSleepDurationInMinutes = averageSleepDurationCalculator.apply(sleepingSessions);

        assertEquals(DESCRIPTION_OF_RESULT, averageSleepDurationInMinutes.getDescription());
        assertEquals(690, averageSleepDurationInMinutes.getResult());
    }
}