package ru.yandex.practicum.sleeptracker.functions;

import ru.yandex.practicum.sleeptracker.SleepAnalysisResult;
import ru.yandex.practicum.sleeptracker.SleepingSession;

import java.time.Duration;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class MaxSleepDurationCalculator implements Function<List<SleepingSession>, SleepAnalysisResult> {

    @Override
    public SleepAnalysisResult apply(List<SleepingSession> sleepingSessions) {
        Optional<Duration> optionalDuration = sleepingSessions.stream()
                .map(SleepingSession::getSleepduration)
                .max(Comparator.comparing(Duration::toMinutes));

        if (optionalDuration.isEmpty()) {
            return new SleepAnalysisResult("Сессии сна для расчёта отсутствуют: ", 0);
        }

        int maxSleepDurationInMinutes = (int) optionalDuration.get().toMinutes();

        return new SleepAnalysisResult(
                "Максимальная продолжительность сессии в минутах: ",
                maxSleepDurationInMinutes
        );
    }
}