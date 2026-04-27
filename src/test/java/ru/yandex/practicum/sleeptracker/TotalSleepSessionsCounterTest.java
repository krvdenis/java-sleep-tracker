package ru.yandex.practicum.sleeptracker;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.sleeptracker.functions.TotalSleepSessionsCounter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TotalSleepSessionsCounterTest {

    private final TotalSleepSessionsCounter totalSleepSessionsCounter = new TotalSleepSessionsCounter();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yy HH:mm");
    private List<SleepingSession> sleepingSessions;

    @BeforeEach
    void init() {
        sleepingSessions = new ArrayList<>();
    }

    @Test
    void shouldReturnZeroWhenListOfSleepSessionsIsEmpty() {
        SleepAnalysisResult totalSleepSessions = totalSleepSessionsCounter.apply(sleepingSessions);

        assertEquals("Сессии сна для расчёта отсутствуют: ", totalSleepSessions.getDescription());
        assertEquals(0, totalSleepSessions.getResult());
    }

    @Test
    void shouldReturnOneWhenListOfSleepSessionsHaveOneSleepingSession() {
        sleepingSessions.add(new SleepingSession(
                LocalDateTime.parse("01.10.25 23:15", formatter),
                LocalDateTime.parse("02.10.25 10:15", formatter), "NORMAL"
        ));
        SleepAnalysisResult totalSleepSessions = totalSleepSessionsCounter.apply(sleepingSessions);

        assertEquals("Общее количество сессий сна: ", totalSleepSessions.getDescription());
        assertEquals(1, totalSleepSessions.getResult());
    }

    @Test
    void shouldReturnTwoWhenListOfSleepSessionsHaveTwoSleepingSession() {
        sleepingSessions.addAll(List.of(new SleepingSession(
                        LocalDateTime.parse("01.10.25 23:15", formatter),
                        LocalDateTime.parse("02.10.25 10:15", formatter), "NORMAL"
                ),
                new SleepingSession(
                        LocalDateTime.parse("02.10.25 22:15", formatter),
                        LocalDateTime.parse("03.10.25 08:15", formatter), "NORMAL"
                )));
        SleepAnalysisResult totalSleepSessions = totalSleepSessionsCounter.apply(sleepingSessions);

        assertEquals("Общее количество сессий сна: ", totalSleepSessions.getDescription());
        assertEquals(2, totalSleepSessions.getResult());
    }
}