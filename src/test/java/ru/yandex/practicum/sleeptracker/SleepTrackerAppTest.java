package ru.yandex.practicum.sleeptracker;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SleepTrackerAppTest {
    final static String REQUIRE_FILE_PATH_INPUT = "Укажите путь к файлу в качестве аргумента командной строки.";
    final static String VALID_FILE_PATH = "src/main/resources/sleep_log.txt";

    @Test
    void shouldThrowRuntimeExceptionWhenMainWithoutArguments() {
        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> SleepTrackerApp.main(new String[0])
        );

        assertEquals(REQUIRE_FILE_PATH_INPUT, exception.getMessage());
    }

    @Test
    void shouldNotThrowExceptionWhenMainWithValidArgument() {
        assertDoesNotThrow(() -> SleepTrackerApp.main(new String[]{VALID_FILE_PATH}));
    }
}