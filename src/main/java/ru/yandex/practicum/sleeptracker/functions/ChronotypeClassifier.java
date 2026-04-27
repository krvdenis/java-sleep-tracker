package ru.yandex.practicum.sleeptracker.functions;

import ru.yandex.practicum.sleeptracker.Chronotypes;
import ru.yandex.practicum.sleeptracker.SleepAnalysisResult;
import ru.yandex.practicum.sleeptracker.SleepingSession;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.function.Function;

public class ChronotypeClassifier implements Function<List<SleepingSession>, SleepAnalysisResult> {

    @Override
    public SleepAnalysisResult apply(List<SleepingSession> sleepingSessions) {

        if (sleepingSessions.isEmpty()) {
            return new SleepAnalysisResult(
                    "Сессия cна для анализа отсутствуют: " ,
                    "невозможно определить хронотип"
            );
        }

        LocalTime lateEveningThreshold = LocalTime.of(23, 59, 59, 59);
        LocalTime morningThreshold = LocalTime.of(6, 0);
        int amountOfNightsWithSleep = (int) sleepingSessions.stream()
                .filter(sleepingSession -> {
                    LocalTime sleepTime = sleepingSession.getSleepDateTime().toLocalTime();
                    LocalDate sleepDate = sleepingSession.getSleepDateTime().toLocalDate();
                    LocalDate wakeUppDate = sleepingSession.getWakeUpDateTime().toLocalDate();

                    return (sleepTime.isBefore(lateEveningThreshold) && !sleepDate.equals(wakeUppDate)) ||
                            sleepTime.isBefore(morningThreshold);
                })
                .count();

        int amountOfNightOwlTypeSessions = (int) sleepingSessions.stream()
                .filter(sleepingSession -> {
                    LocalTime sleepTime = sleepingSession.getSleepDateTime().toLocalTime();
                    LocalTime wakeUpTime = sleepingSession.getWakeUpDateTime().toLocalTime();
                    return wakeUpTime.isAfter(LocalTime.of(9, 0)) &&
                            (sleepTime.isAfter(LocalTime.of(23, 0)) ||
                                    sleepTime.isBefore(LocalTime.of(6, 0)));
                })
                .count();

        int amountOfEarlyBirdTypeSessions = (int) sleepingSessions.stream()
                .filter(sleepingSession -> {
                    LocalTime sleepTime = sleepingSession.getSleepDateTime().toLocalTime();
                    LocalTime wakeUpTime = sleepingSession.getWakeUpDateTime().toLocalTime();
                    return wakeUpTime.isBefore(LocalTime.of(7, 0))
                            && sleepTime.isBefore(LocalTime.of(22, 0))
                            && !sleepTime.isBefore(LocalTime.of(7, 0)); // учесть ситуацию, когда лёг до 6 утра
                })
                .count();

        String chronotypeOfUser = getChronotypeOfUser(amountOfNightsWithSleep, amountOfEarlyBirdTypeSessions, amountOfNightOwlTypeSessions);

        return new SleepAnalysisResult("Ваш хронотип: ", chronotypeOfUser);
    }

    private static String getChronotypeOfUser(int amountOfNightsWithSleep, int amountOfEarlyBirdTypeSessions, int amountOfNightOwlTypeSessions) {
        int amountOfPigeonTypeSessions = amountOfNightsWithSleep - amountOfEarlyBirdTypeSessions
                - amountOfNightOwlTypeSessions;
        String chronoTypeOfUser = Chronotypes.NIGHT_OWL.getRusName();

        if (amountOfNightOwlTypeSessions == amountOfEarlyBirdTypeSessions) {
            chronoTypeOfUser = Chronotypes.PIGEON.getRusName();
        } else if (amountOfEarlyBirdTypeSessions > amountOfNightOwlTypeSessions) {
            if (amountOfEarlyBirdTypeSessions > amountOfPigeonTypeSessions) {
                chronoTypeOfUser = Chronotypes.EARLY_BIRD.getRusName();
            } else {
                chronoTypeOfUser = Chronotypes.PIGEON.getRusName();
            }
        }
        return chronoTypeOfUser;
    }
}