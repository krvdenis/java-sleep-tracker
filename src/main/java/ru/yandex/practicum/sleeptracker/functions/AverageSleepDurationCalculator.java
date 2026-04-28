package ru.yandex.practicum.sleeptracker.functions;

import ru.yandex.practicum.sleeptracker.SleepAnalysisResult;
import ru.yandex.practicum.sleeptracker.SleepingSession;

import java.time.Duration;
import java.util.List;
import java.util.OptionalDouble;
import java.util.function.Function;

public class AverageSleepDurationCalculator implements Function<List<SleepingSession>, SleepAnalysisResult> {
    private static final String DESCRIPTION_OF_RESULT = "Средняя продолжительность сессий в минутах: ";
    private static final int EMPTY_RESULT = 0;

    @Override
    public SleepAnalysisResult apply(List<SleepingSession> sleepingSessions) {

        OptionalDouble optionalDoubleSleepDuration = sleepingSessions.stream()
                .map(SleepingSession::getSleepduration)
                .mapToLong(Duration::toMinutes)
                .average();
        int averageSleepDurationInMinutes = (int) optionalDoubleSleepDuration.orElse(EMPTY_RESULT);

        return new SleepAnalysisResult(DESCRIPTION_OF_RESULT, averageSleepDurationInMinutes);
    }
}