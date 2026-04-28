package ru.yandex.practicum.sleeptracker.functions;

import ru.yandex.practicum.sleeptracker.SleepAnalysisResult;
import ru.yandex.practicum.sleeptracker.SleepingSession;

import java.util.List;
import java.util.function.Function;

public class BadSleepSessionsCounter implements Function<List<SleepingSession>, SleepAnalysisResult> {
    private final static String DESCRIPTION_OF_RESULT = "Количество сессий с плохим качеством сна: ";
    private final static String DESCRIPTION_OF_EMPTY_RESULT = "Сессии сна для анализа отсутствуют: ";
    private final static String NEEDED_SLEEP_QUALITY = "BAD";
    private final static int EMPTY_RESULT = 0;

    @Override
    public SleepAnalysisResult apply(List<SleepingSession> sleepingSessions) {

        if (sleepingSessions.isEmpty()) {
            return new SleepAnalysisResult(DESCRIPTION_OF_EMPTY_RESULT, EMPTY_RESULT);
        }

        int amountSessionsWithBadSleepQuality = (int) sleepingSessions.stream()
                .map(SleepingSession::getSleepQuality)
                .filter(sleepQuality -> sleepQuality.equals(NEEDED_SLEEP_QUALITY))
                .count();

        return new SleepAnalysisResult(DESCRIPTION_OF_RESULT, amountSessionsWithBadSleepQuality);
    }
}