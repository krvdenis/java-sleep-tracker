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
    private final MinSleepDurationCalculator minSleepDurationCalculator = new MinSleepDurationCalculator();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yy HH:mm");
    List<SleepingSession> sleepingSessions;

    @BeforeEach
    void init() {
        sleepingSessions = new ArrayList<>();
    }

    @Test
    void shouldReturnZeroWhenListOfSleepSessionsIsEmpty() {
        SleepAnalysisResult minSleepDurationInMinutes = minSleepDurationCalculator.apply(sleepingSessions);

        assertEquals("Сессии сна для расчёта отсутствуют: ", minSleepDurationInMinutes.getDescription());
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
                "Минимальная продолжительность сессии в минутах: ",
                minSleepDurationInMinutes.getDescription()
        );
        assertEquals(660, minSleepDurationInMinutes.getResult());
    }
}