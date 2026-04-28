package ru.yandex.practicum.sleeptracker;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.sleeptracker.functions.MinSleepDurationCalculator;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MinSleepDurationCalculatorTest {
    private final static String DATE_TIME_FORMAT = "dd.MM.yy HH:mm";
    private final static String DESCRIPTION_OF_RESULT = "Минимальная продолжительность сессии в минутах: ";
    private final static String DESCRIPTION_OF_EMPTY_RESULT = "Сессии сна для анализа отсутствуют: ";
    private final MinSleepDurationCalculator minSleepDurationCalculator = new MinSleepDurationCalculator();
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_TIME_FORMAT);
    private List<SleepingSession> sleepingSessions;

    @BeforeEach
    void init() {
        sleepingSessions = new ArrayList<>();
    }

    @Test
    void shouldReturnZeroWhenListOfSleepSessionsIsEmpty() {
        SleepAnalysisResult minSleepDurationInMinutes = minSleepDurationCalculator.apply(sleepingSessions);

        assertEquals(DESCRIPTION_OF_EMPTY_RESULT, minSleepDurationInMinutes.getDescription());
        assertEquals(0, minSleepDurationInMinutes.getResult());
    }

    @Test
    void shouldReturnMinDurationInMinutesBetweenTwoSessions() {
        sleepingSessions.addAll(List.of(new SleepingSession(
                        LocalDateTime.parse("01.10.25 23:15", formatter),
                        LocalDateTime.parse("02.10.25 10:15", formatter), "NORMAL"
                ),
                new SleepingSession(
                        LocalDateTime.parse("02.10.25 22:15", formatter),
                        LocalDateTime.parse("03.10.25 10:15", formatter), "NORMAL"
                )));
        SleepAnalysisResult minSleepDurationInMinutes = minSleepDurationCalculator.apply(sleepingSessions);

        assertEquals(
                DESCRIPTION_OF_RESULT,
                minSleepDurationInMinutes.getDescription()
        );
        assertEquals(660, minSleepDurationInMinutes.getResult());
    }
}