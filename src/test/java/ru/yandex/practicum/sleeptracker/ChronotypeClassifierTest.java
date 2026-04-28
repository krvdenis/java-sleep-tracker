package ru.yandex.practicum.sleeptracker;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.sleeptracker.functions.ChronotypeClassifier;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ChronotypeClassifierTest {
    private static final String DATE_TIME_FORMAT = "dd.MM.yy HH:mm";
    private static final String DESCRIPTION_OF_RESULT = "Ваш хронотип: ";
    private static final String DESCRIPTION_OF_EMPTY_RESULT = "Сессии cна для анализа отсутствуют: ";
    private static final String EMPTY_RESULT = "невозможно определить хронотип";
    private final ChronotypeClassifier chronotypeClassifier = new ChronotypeClassifier();
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_TIME_FORMAT);
    private List<SleepingSession> sleepingSessions;

    @BeforeEach
    void init() {
        sleepingSessions = new ArrayList<>();
    }

    @Test
    void shouldReturnWarningWhenListOfSleepSessionsIsEmpty() {
        SleepAnalysisResult chronotypeOfUser = chronotypeClassifier.apply(sleepingSessions);

        assertEquals(DESCRIPTION_OF_EMPTY_RESULT, chronotypeOfUser.getDescription());
        assertEquals(EMPTY_RESULT, chronotypeOfUser.getResult());
    }

    @Test
    void shouldReturnPigeonTypeWhenNumberOfNightOwlAndEarlyBirdSessionsIsEqual() {
        SleepingSession sleepingSession1 = new SleepingSession(
                LocalDateTime.parse("01.10.25 23:15", formatter),
                LocalDateTime.parse("02.10.25 10:15", formatter), "NORMAL"
        );
        SleepingSession sleepingSession2 = new SleepingSession(
                LocalDateTime.parse("02.10.25 21:15", formatter),
                LocalDateTime.parse("03.10.25 06:15", formatter), "NORMAL"
        );

        sleepingSessions.addAll(List.of(sleepingSession1, sleepingSession2));
        SleepAnalysisResult chronotypeOfUser = chronotypeClassifier.apply(sleepingSessions);

        assertEquals(
                DESCRIPTION_OF_RESULT,
                chronotypeOfUser.getDescription()
        );
        assertEquals("Голубь", chronotypeOfUser.getResult());
    }

    @Test
    void shouldReturnNightOwlTypeIfNumberOfNightOwlSessionsMoreThanEarlyBirdSessions() {
        SleepingSession sleepingSession1 = new SleepingSession(
                LocalDateTime.parse("01.10.25 23:15", formatter),
                LocalDateTime.parse("02.10.25 10:15", formatter), "NORMAL"
        );
        SleepingSession sleepingSession2 = new SleepingSession(
                LocalDateTime.parse("02.10.25 21:15", formatter),
                LocalDateTime.parse("03.10.25 06:15", formatter), "NORMAL"
        );
        SleepingSession sleepingSession3 = new SleepingSession(
                LocalDateTime.parse("03.10.25 23:25", formatter),
                LocalDateTime.parse("04.10.25 11:15", formatter), "NORMAL"
        );

        sleepingSessions.addAll(List.of(sleepingSession1, sleepingSession2, sleepingSession3));
        SleepAnalysisResult chronotypeOfUser = chronotypeClassifier.apply(sleepingSessions);

        assertEquals(
                DESCRIPTION_OF_RESULT,
                chronotypeOfUser.getDescription()
        );
        assertEquals("Сова", chronotypeOfUser.getResult());
    }

    @Test
    void shouldReturnEarlyBirdTypeIfNumberOfNightOwlSessionsLessThanEarlyBirdSessions() {
        SleepingSession sleepingSession1 = new SleepingSession(
                LocalDateTime.parse("01.10.25 23:15", formatter),
                LocalDateTime.parse("02.10.25 10:15", formatter), "NORMAL"
        );
        SleepingSession sleepingSession2 = new SleepingSession(
                LocalDateTime.parse("02.10.25 21:15", formatter),
                LocalDateTime.parse("03.10.25 06:15", formatter), "NORMAL"
        );
        SleepingSession sleepingSession3 = new SleepingSession(
                LocalDateTime.parse("03.10.25 21:25", formatter),
                LocalDateTime.parse("04.10.25 05:15", formatter), "NORMAL"
        );

        sleepingSessions.addAll(List.of(sleepingSession1, sleepingSession2, sleepingSession3));
        SleepAnalysisResult chronotypeOfUser = chronotypeClassifier.apply(sleepingSessions);

        assertEquals(
                DESCRIPTION_OF_RESULT,
                chronotypeOfUser.getDescription()
        );
        assertEquals("Жаворонок", chronotypeOfUser.getResult());
    }

    @Test
    void shouldReturnPigeonTypeIfNumberPigeonSessionsMoreThanOthers() {
        SleepingSession sleepingSession1 = new SleepingSession(
                LocalDateTime.parse("01.10.25 23:15", formatter),
                LocalDateTime.parse("02.10.25 10:15", formatter), "NORMAL"
        );
        SleepingSession sleepingSession2 = new SleepingSession(
                LocalDateTime.parse("02.10.25 21:15", formatter),
                LocalDateTime.parse("03.10.25 07:15", formatter), "BAD"
        );
        SleepingSession sleepingSession3 = new SleepingSession(
                LocalDateTime.parse("03.10.25 21:25", formatter),
                LocalDateTime.parse("04.10.25 05:15", formatter), "NORMAL"
        );

        sleepingSessions.addAll(List.of(sleepingSession1, sleepingSession2, sleepingSession3));
        SleepAnalysisResult chronotypeOfUser = chronotypeClassifier.apply(sleepingSessions);

        assertEquals(
                DESCRIPTION_OF_RESULT,
                chronotypeOfUser.getDescription()
        );
        assertEquals("Голубь", chronotypeOfUser.getResult());
    }
}