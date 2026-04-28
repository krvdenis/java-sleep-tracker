package ru.yandex.practicum.sleeptracker.functions;

import ru.yandex.practicum.sleeptracker.SleepAnalysisResult;
import ru.yandex.practicum.sleeptracker.SleepingSession;

import java.time.Duration;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class MinSleepDurationCalculator implements Function<List<SleepingSession>, SleepAnalysisResult> {
    private final static String DESCRIPTION_OF_RESULT = "Минимальная продолжительность сессии в минутах: ";
    private final static String DESCRIPTION_OF_EMPTY_RESULT = "Сессии сна для анализа отсутствуют: ";
    private final static int EMPTY_RESULT = 0;

    @Override
    public SleepAnalysisResult apply(List<SleepingSession> sleepingSessions) {
        Optional<Duration> optionalDuration = sleepingSessions.stream()
                .map(SleepingSession::getSleepduration)
                .min(Comparator.comparing(Duration::toMinutes));

        if (optionalDuration.isEmpty()) {
            return new SleepAnalysisResult(DESCRIPTION_OF_EMPTY_RESULT, EMPTY_RESULT);
        }

        int minSleepDurationInMinutes = (int) optionalDuration.get().toMinutes();

        return new SleepAnalysisResult(DESCRIPTION_OF_RESULT, minSleepDurationInMinutes);
    }
}