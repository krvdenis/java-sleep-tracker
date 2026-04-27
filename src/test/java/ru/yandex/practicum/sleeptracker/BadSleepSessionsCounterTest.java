package ru.yandex.practicum.sleeptracker;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.sleeptracker.functions.BadSleepSessionsCounter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BadSleepSessionsCounterTest {
    private final BadSleepSessionsCounter badSleepSessionsCounter = new BadSleepSessionsCounter();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yy HH:mm");
    List<SleepingSession> sleepingSessions;

    @BeforeEach
    void init() {
        sleepingSessions = new ArrayList<>();
    }

    @Test
    void shouldReturnZeroWhenListOfSleepSessionsIsEmpty() {
        SleepAnalysisResult amountSessionsWithBadSleepQuality = badSleepSessionsCounter.apply(sleepingSessions);

        assertEquals(
                "Сессии сна для расчёта отсутствуют: ",
                amountSessionsWithBadSleepQuality.getDescription()
        );
        assertEquals(0, amountSessionsWithBadSleepQuality.getResult());
    }

    @Test
    void shouldReturnZeroSessionsWithBadSleepQuality() {
        sleepingSessions.addAll(List.of(new SleepingSession(
                        LocalDateTime.parse("01.10.25 23:15", formatter),
                        LocalDateTime.parse("02.10.25 10:15", formatter), "NORMAL"
                ),
                new SleepingSession(
                        LocalDateTime.parse("02.10.25 22:15", formatter),
                        LocalDateTime.parse("03.10.25 10:15", formatter), "NORMAL"
                )));
        SleepAnalysisResult amountSessionsWithBadSleepQuality = badSleepSessionsCounter.apply(sleepingSessions);

        assertEquals(
                "Количество сессий с плохим качеством сна: ",
                amountSessionsWithBadSleepQuality.getDescription()
        );
        assertEquals(0, amountSessionsWithBadSleepQuality.getResult());
    }

    @Test
    void shouldReturnOneSessionWithBadSleepQuality() {
        sleepingSessions.addAll(List.of(new SleepingSession(
                        LocalDateTime.parse("01.10.25 23:15", formatter),
                        LocalDateTime.parse("02.10.25 10:15", formatter), "NORMAL"
                ),
                new SleepingSession(
                        LocalDateTime.parse("02.10.25 22:15", formatter),
                        LocalDateTime.parse("03.10.25 10:15", formatter), "BAD"
                )));
        SleepAnalysisResult amountSessionsWithBadSleepQuality = badSleepSessionsCounter.apply(sleepingSessions);

        assertEquals(
                "Количество сессий с плохим качеством сна: ",
                amountSessionsWithBadSleepQuality.getDescription()
        );
        assertEquals(1, amountSessionsWithBadSleepQuality.getResult());
    }

    @Test
    void shouldReturnTwoSessionsWithBadSleepQuality() {
        sleepingSessions.addAll(List.of(new SleepingSession(
                        LocalDateTime.parse("01.10.25 23:15", formatter),
                        LocalDateTime.parse("02.10.25 10:15", formatter), "BAD"
                ),
                new SleepingSession(
                        LocalDateTime.parse("02.10.25 22:15", formatter),
                        LocalDateTime.parse("03.10.25 10:15", formatter), "BAD"
                )));
        SleepAnalysisResult amountSessionsWithBadSleepQuality = badSleepSessionsCounter.apply(sleepingSessions);

        assertEquals(
                "Количество сессий с плохим качеством сна: ",
                amountSessionsWithBadSleepQuality.getDescription()
        );
        assertEquals(2, amountSessionsWithBadSleepQuality.getResult());
    }
}