package ru.yandex.practicum.sleeptracker;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.sleeptracker.functions.SleeplessNightsDetector;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SleeplessNightsDetectorTest {
    private static final String DATE_TIME_FORMAT = "dd.MM.yy HH:mm";
    private static final String DESCRIPTION_OF_RESULT = "Общее количество бессонных ночей: ";
    private static final String DESCRIPTION_OF_EMPTY_RESULT = "Сессии cна для анализа отсутствуют: ";
    private static final String EMPTY_RESULT = "невозможно определить количество бессонных ночей";
    private final SleeplessNightsDetector sleeplessNightsDetector = new SleeplessNightsDetector();
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_TIME_FORMAT);
    private List<SleepingSession> sleepingSessions;

    @BeforeEach
    void init() {
        sleepingSessions = new ArrayList<>();
    }

    @Test
    void shouldReturnWarningWhenListOfSleepSessionsIsEmpty() {
        SleepAnalysisResult amountOfSleeplessNight = sleeplessNightsDetector.apply(sleepingSessions);

        assertEquals(DESCRIPTION_OF_EMPTY_RESULT, amountOfSleeplessNight.getDescription());
        assertEquals(EMPTY_RESULT, amountOfSleeplessNight.getResult());
    }

    @Test
    void shouldReturnTwentySevenSleeplessNight() {
        SleepingSession sleepingSession1 = new SleepingSession(
                LocalDateTime.parse("01.10.25 23:15", formatter),
                LocalDateTime.parse("02.10.25 10:15", formatter), "NORMAL"
        );
        SleepingSession sleepingSession2 = new SleepingSession(
                LocalDateTime.parse("02.10.25 21:15", formatter),
                LocalDateTime.parse("03.10.25 06:15", formatter), "NORMAL"
        );
        SleepingSession sleepingSession3 = new SleepingSession(
                LocalDateTime.parse("30.10.25 23:25", formatter),
                LocalDateTime.parse("31.10.25 11:15", formatter), "NORMAL"
        );

        sleepingSessions.addAll(List.of(sleepingSession1, sleepingSession2, sleepingSession3));
        SleepAnalysisResult amountOfSleeplessNight = sleeplessNightsDetector.apply(sleepingSessions);

        assertEquals(
                DESCRIPTION_OF_RESULT,
                amountOfSleeplessNight.getDescription()
        );
        assertEquals(27, amountOfSleeplessNight.getResult());
    }

    @Test
    void shouldReturnTwentyNineWhenFirstSleepSessionBeginsBeforeNoon() {
        SleepingSession sleepingSession1 = new SleepingSession(
                LocalDateTime.parse("01.10.25 11:15", formatter),
                LocalDateTime.parse("01.10.25 20:15", formatter), "NORMAL"
        );
        SleepingSession sleepingSession2 = new SleepingSession(
                LocalDateTime.parse("02.10.25 21:15", formatter),
                LocalDateTime.parse("03.10.25 06:15", formatter), "NORMAL"
        );

        SleepingSession sleepingSession3 = new SleepingSession(
                LocalDateTime.parse("30.10.25 23:25", formatter),
                LocalDateTime.parse("31.10.25 11:15", formatter), "NORMAL"
        );

        sleepingSessions.addAll(List.of(sleepingSession1, sleepingSession2, sleepingSession3));
        SleepAnalysisResult amountOfSleeplessNight = sleeplessNightsDetector.apply(sleepingSessions);

        assertEquals(
                DESCRIPTION_OF_RESULT,
                amountOfSleeplessNight.getDescription()
        );
        assertEquals(29, amountOfSleeplessNight.getResult());
    }

    @Test
    void shouldReturnTwentySixWhenSecondSleepSessionBeginsBeforeSixAM() {
        SleepingSession sleepingSession1 = new SleepingSession(
                LocalDateTime.parse("01.10.25 23:15", formatter),
                LocalDateTime.parse("02.10.25 10:15", formatter), "NORMAL"
        );
        SleepingSession sleepingSession2 = new SleepingSession(
                LocalDateTime.parse("03.10.25 00:15", formatter),
                LocalDateTime.parse("03.10.25 05:15", formatter), "NORMAL"
        );
        SleepingSession sleepingSession3 = new SleepingSession(
                LocalDateTime.parse("03.10.25 23:25", formatter),
                LocalDateTime.parse("04.10.25 11:15", formatter), "NORMAL"
        );
        SleepingSession sleepingSession4 = new SleepingSession(
                LocalDateTime.parse("30.10.25 23:25", formatter),
                LocalDateTime.parse("31.10.25 11:15", formatter), "NORMAL"
        );

        sleepingSessions.addAll(List.of(sleepingSession1, sleepingSession2, sleepingSession3, sleepingSession4));
        SleepAnalysisResult amountOfSleeplessNight = sleeplessNightsDetector.apply(sleepingSessions);

        assertEquals(
                DESCRIPTION_OF_RESULT,
                amountOfSleeplessNight.getDescription()
        );
        assertEquals(26, amountOfSleeplessNight.getResult());
    }

    @Test
    void shouldReturnTwentySevenWhenSecondSleepSessionBeginsAtSixAM() {
        SleepingSession sleepingSession1 = new SleepingSession(
                LocalDateTime.parse("01.10.25 23:15", formatter),
                LocalDateTime.parse("02.10.25 10:15", formatter), "NORMAL"
        );
        SleepingSession sleepingSession2 = new SleepingSession(
                LocalDateTime.parse("03.10.25 06:00", formatter),
                LocalDateTime.parse("03.10.25 12:15", formatter), "NORMAL"
        );
        SleepingSession sleepingSession3 = new SleepingSession(
                LocalDateTime.parse("03.10.25 23:25", formatter),
                LocalDateTime.parse("04.10.25 11:15", formatter), "NORMAL"
        );
        SleepingSession sleepingSession4 = new SleepingSession(
                LocalDateTime.parse("30.10.25 23:25", formatter),
                LocalDateTime.parse("31.10.25 11:15", formatter), "NORMAL"
        );

        sleepingSessions.addAll(List.of(sleepingSession1, sleepingSession2, sleepingSession3, sleepingSession4));
        SleepAnalysisResult amountOfSleeplessNight = sleeplessNightsDetector.apply(sleepingSessions);

        assertEquals(
                DESCRIPTION_OF_RESULT,
                amountOfSleeplessNight.getDescription()
        );
        assertEquals(27, amountOfSleeplessNight.getResult());
    }
}