package ru.yandex.practicum.sleeptracker.functions;

import ru.yandex.practicum.sleeptracker.SleepAnalysisResult;
import ru.yandex.practicum.sleeptracker.SleepingSession;

import java.util.List;
import java.util.function.Function;

public class BadSleepSessionsCounter implements Function<List<SleepingSession>, SleepAnalysisResult> {

    @Override
    public SleepAnalysisResult apply(List<SleepingSession> sleepingSessions) {

        if (sleepingSessions.isEmpty()) {
            return new SleepAnalysisResult("Сессии сна для расчёта отсутствуют: ", 0);
        }

        int amountSessionsWithBadSleepQuality = (int) sleepingSessions.stream()
                .map(SleepingSession::getSleepQuality)
                .filter(sleepQuality -> sleepQuality.equals("BAD"))
                .count();

        return new SleepAnalysisResult(
                "Количество сессий с плохим качеством сна: ",
                amountSessionsWithBadSleepQuality
        );
    }
}