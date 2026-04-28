package ru.yandex.practicum.sleeptracker.functions;

import ru.yandex.practicum.sleeptracker.SleepAnalysisResult;
import ru.yandex.practicum.sleeptracker.SleepingSession;

import java.util.List;
import java.util.function.Function;

public class TotalSleepSessionsCounter implements Function<List<SleepingSession>, SleepAnalysisResult> {
    private final static String DESCRIPTION_OF_RESULT = "Общее количество сессий сна: ";
    private final static String DESCRIPTION_OF_EMPTY_RESULT = "Сессии сна для анализа отсутствуют: ";
    private final static int EMPTY_RESULT = 0;

    @Override
    public SleepAnalysisResult apply(List<SleepingSession> sleepingSessions) {
        if (sleepingSessions.isEmpty()) {
            return new SleepAnalysisResult(DESCRIPTION_OF_EMPTY_RESULT, EMPTY_RESULT);
        }

        int totalSleepSessions = sleepingSessions.size();

        return new SleepAnalysisResult(DESCRIPTION_OF_RESULT, totalSleepSessions);
    }
}