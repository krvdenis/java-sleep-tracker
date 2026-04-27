package ru.yandex.practicum.sleeptracker;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SleepTrackerAppTest {

    @Test
    void shouldThrowRuntimeExceptionWhenMainWithoutArguments() {
        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> SleepTrackerApp.main(new String[0])
        );

        assertEquals("Укажите путь к файлу в качестве аргумента командной строки.", exception.getMessage());
    }

    @Test
    void shouldNotThrowExceptionWhenMainWithValidArgument() {
        String validFilePath = "src/main/resources/sleep_log.txt";

        assertDoesNotThrow(() -> SleepTrackerApp.main(new String[]{validFilePath}));
    }

    @Test
    void runAnalyticsShouldReturnExpectedResult() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yy HH:mm");
        List<SleepingSession> sessions = getSleepingSessions(formatter);
        String exceptedResult = getExceptedResult();

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(byteArrayOutputStream));

        SleepTrackerApp.runAnalytics(sessions);

        System.setOut(originalOut);
        String actualResult = byteArrayOutputStream.toString();

        assertEquals(exceptedResult, actualResult);
    }

    private static List<SleepingSession> getSleepingSessions(DateTimeFormatter formatter) {
        SleepingSession sleepingSession1 = new SleepingSession(
                LocalDateTime.parse("01.10.25 23:15", formatter),
                LocalDateTime.parse("02.10.25 10:15", formatter), "NORMAL"
        );
        SleepingSession sleepingSession2 = new SleepingSession(
                LocalDateTime.parse("03.10.25 06:15", formatter),
                LocalDateTime.parse("03.10.25 15:15", formatter), "BAD"
        );
        SleepingSession sleepingSession3 = new SleepingSession(
                LocalDateTime.parse("03.10.25 21:25", formatter),
                LocalDateTime.parse("04.10.25 05:15", formatter), "NORMAL"
        );
        return List.of(sleepingSession1, sleepingSession2, sleepingSession3);
    }

    private static String getExceptedResult() {
        String resultOfFirstFunction = "Результат выполнения функции - Общее количество сессий сна: 3\r\n";
        String resultOfSecondFunction =
                "Результат выполнения функции - Минимальная продолжительность сессии в минутах: 470\r\n";
        String resultOfThirdFunction =
                "Результат выполнения функции - Максимальная продолжительность сессии в минутах: 660\r\n";
        String resultOfFourthFunction =
                "Результат выполнения функции - Средняя продолжительность сессии в минутах: 556\r\n";
        String resultOfFifthFunction = "Результат выполнения функции - Количество сессий с плохим качеством сна: 1\r\n";
        String resultOfSixthFunction = "Результат выполнения функции - Общее количество бессонных ночей: 1\r\n";
        String resultOfSeventhFunction = "Результат выполнения функции - Ваш хронотип: Голубь\r\n";

        return resultOfFirstFunction + resultOfSecondFunction + resultOfThirdFunction
                + resultOfFourthFunction + resultOfFifthFunction + resultOfSixthFunction + resultOfSeventhFunction;
    }
}