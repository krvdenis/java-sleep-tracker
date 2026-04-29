package ru.yandex.practicum.sleeptracker.functions;

import ru.yandex.practicum.sleeptracker.SleepAnalysisResult;
import ru.yandex.practicum.sleeptracker.SleepingSession;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.function.Function;

public class SleeplessNightsDetector implements Function<List<SleepingSession>, SleepAnalysisResult> {
    private static final String DESCRIPTION_OF_RESULT = "Общее количество бессонных ночей: ";
    private static final String DESCRIPTION_OF_EMPTY_RESULT = "Сессии cна для анализа отсутствуют: ";
    private static final String EMPTY_RESULT = "невозможно определить количество бессонных ночей";

    @Override
    public SleepAnalysisResult apply(List<SleepingSession> sleepingSessions) {
        if (sleepingSessions.isEmpty()) {
            return new SleepAnalysisResult(DESCRIPTION_OF_EMPTY_RESULT, EMPTY_RESULT);
        }

        LocalDate firstNight;
        LocalDate lastNight = sleepingSessions.getLast().getWakeUpDateTime().toLocalDate();
        LocalDateTime firstSession = sleepingSessions.getFirst().getSleepDateTime();

        if (firstSession.toLocalTime().isAfter(LocalTime.of(12, 0))) {
            firstNight = firstSession.toLocalDate().plusDays(1);
        } else {
            firstNight = firstSession.toLocalDate();
        }

        LocalTime lateEveningThreshold = LocalTime.of(23, 59, 59, 59);
        LocalTime morningThreshold = LocalTime.of(6, 0);
        int amountOfNights = (int) ChronoUnit.DAYS.between(firstNight, lastNight.plusDays(1));
        int amountOfNightsWithSleep = (int) sleepingSessions.stream()
                .filter(sleepingSession -> {
                    LocalTime sleepTime = sleepingSession.getSleepDateTime().toLocalTime();
                    LocalDate sleepDate = sleepingSession.getSleepDateTime().toLocalDate();
                    LocalDate wakeUppDate = sleepingSession.getWakeUpDateTime().toLocalDate();

                    return (sleepTime.isBefore(lateEveningThreshold) && !sleepDate.equals(wakeUppDate))
                            || sleepTime.isBefore(morningThreshold);
                })
                .count();

        int amountOfSleeplessNight = amountOfNights - amountOfNightsWithSleep;

        return new SleepAnalysisResult(DESCRIPTION_OF_RESULT, amountOfSleeplessNight);
    }
}