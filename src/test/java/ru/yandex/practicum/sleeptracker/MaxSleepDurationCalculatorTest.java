package ru.yandex.practicum.sleeptracker;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.sleeptracker.functions.MaxSleepDurationCalculator;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MaxSleepDurationCalculatorTest {

    private final MaxSleepDurationCalculator maxSleepDurationCalculator = new MaxSleepDurationCalculator();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yy HH:mm");
    List<SleepingSession> sleepingSessions;

    @BeforeEach
    void init() {
        sleepingSessions = new ArrayList<>();
    }

    @Test
    void shouldReturnZeroWhenListOfSleepSessionsIsEmpty() {
        SleepAnalysisResult maxSleepDurationInMinutes = maxSleepDurationCalculator.apply(sleepingSessions);

        assertEquals("Сессии сна для расчёта отсутствуют: ", maxSleepDurationInMinutes.getDescription());
        assertEquals(0, maxSleepDurationInMinutes.getResult());
    }

    @Test
    void shouldReturnMaxDurationInMinutesBetweenTwoSessions() {
        sleepingSessions.addAll(List.of(new SleepingSession(
                        LocalDateTime.parse("01.10.25 23:15", formatter),
                        LocalDateTime.parse("02.10.25 10:15", formatter), "NORMAL"
                ),
                new SleepingSession(
                        LocalDateTime.parse("02.10.25 22:15", formatter),
                        LocalDateTime.parse("03.10.25 10:15", formatter), "NORMAL"
                )));
        SleepAnalysisResult maxSleepDurationInMinutes = maxSleepDurationCalculator.apply(sleepingSessions);

        assertEquals(
                "Максимальная продолжительность сессии в минутах: ",
                maxSleepDurationInMinutes.getDescription()
        );
        assertEquals(720, maxSleepDurationInMinutes.getResult());
    }
}