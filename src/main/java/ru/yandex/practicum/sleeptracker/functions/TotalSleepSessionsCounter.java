package ru.yandex.practicum.sleeptracker.functions;

import ru.yandex.practicum.sleeptracker.SleepAnalysisResult;
import ru.yandex.practicum.sleeptracker.SleepingSession;

import java.util.List;
import java.util.function.Function;

public class TotalSleepSessionsCounter implements Function<List<SleepingSession>, SleepAnalysisResult> {

    @Override
    public SleepAnalysisResult apply(List<SleepingSession> sleepingSessions) {
        if (sleepingSessions.isEmpty()) {
            return new SleepAnalysisResult("Сессии сна для расчёта отсутствуют: ", 0);
        }

        int totalSleepSessions = sleepingSessions.size();

        return new SleepAnalysisResult("Общее количество сессий сна: ", totalSleepSessions);
    }
}