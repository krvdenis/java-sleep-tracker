package ru.yandex.practicum.sleeptracker;

import org.junit.jupiter.api.Test;

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
}