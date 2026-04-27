package ru.yandex.practicum.sleeptracker.functions;

import ru.yandex.practicum.sleeptracker.SleepAnalysisResult;
import ru.yandex.practicum.sleeptracker.SleepingSession;

import java.time.Duration;
import java.util.List;
import java.util.OptionalDouble;
import java.util.function.Function;

public class AverageSleepDurationCalculator implements Function<List<SleepingSession>, SleepAnalysisResult> {

    @Override
    public SleepAnalysisResult apply(List<SleepingSession> sleepingSessions) {

        OptionalDouble optionalDoubleSleepDurarion = sleepingSessions.stream()
                .map(SleepingSession::getSleepduration)
                .mapToLong(Duration::toMinutes)
                .average();
        int averageSleepDurationInMinutes = (int) optionalDoubleSleepDurarion.orElse(0);

        return new SleepAnalysisResult(
                "Средняя продолжительность сессии в минутах: ",
                averageSleepDurationInMinutes
        );
    }
}