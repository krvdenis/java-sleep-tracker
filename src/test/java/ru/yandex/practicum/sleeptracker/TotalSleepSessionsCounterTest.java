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
    private static final String DATE_TIME_FORMAT = "dd.MM.yy HH:mm";
    private static final String DESCRIPTION_OF_RESULT = "Общее количество сессий сна: ";
    private static final String DESCRIPTION_OF_EMPTY_RESULT = "Сессии сна для анализа отсутствуют: ";
    private final TotalSleepSessionsCounter totalSleepSessionsCounter = new TotalSleepSessionsCounter();
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_TIME_FORMAT);
    private List<SleepingSession> sleepingSessions;

    @BeforeEach
    void init() {
        sleepingSessions = new ArrayList<>();
    }

    @Test
    void shouldReturnZeroWhenListOfSleepSessionsIsEmpty() {
        SleepAnalysisResult totalSleepSessions = totalSleepSessionsCounter.apply(sleepingSessions);

        assertEquals(DESCRIPTION_OF_EMPTY_RESULT, totalSleepSessions.getDescription());
        assertEquals(0, totalSleepSessions.getResult());
    }

    @Test
    void shouldReturnOneWhenListOfSleepSessionsHaveOneSleepingSession() {
        sleepingSessions.add(new SleepingSession(
                LocalDateTime.parse("01.10.25 23:15", formatter),
                LocalDateTime.parse("02.10.25 10:15", formatter), "NORMAL"
        ));
        SleepAnalysisResult totalSleepSessions = totalSleepSessionsCounter.apply(sleepingSessions);

        assertEquals(DESCRIPTION_OF_RESULT, totalSleepSessions.getDescription());
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

        assertEquals(DESCRIPTION_OF_RESULT, totalSleepSessions.getDescription());
        assertEquals(2, totalSleepSessions.getResult());
    }
}