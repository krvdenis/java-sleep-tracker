package ru.yandex.practicum.sleeptracker;

import ru.yandex.practicum.sleeptracker.functions.*;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class SleepTrackerApp {
    final static String DESCRIPTION_RESULT_OF_FUNCTION = "Результат выполнения функции - ";
    final static String DATE_TIME_FORMAT = "dd.MM.yy HH:mm";
    final static String REQUIRE_FILE_PATH_INPUT = "Укажите путь к файлу в качестве аргумента командной строки.";
    final static String FAILED_TO_READ_FILE = "Ошибка при чтении файла: ";
    private static final List<Function<List<SleepingSession>, ?>> analyticsFunctions = List.of(
            new TotalSleepSessionsCounter(),
            new MinSleepDurationCalculator(),
            new MaxSleepDurationCalculator(),
            new AverageSleepDurationCalculator(),
            new BadSleepSessionsCounter(),
            new SleeplessNightsDetector(),
            new ChronotypeClassifier()
    );
    private static List<SleepingSession> sleepingSessions;

    public static void main(String[] args) {
        if (args.length == 0) {
            throw new RuntimeException(REQUIRE_FILE_PATH_INPUT);
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_TIME_FORMAT);

        try (InputStream inputStream = new FileInputStream(args[0]);
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            List<String> lines = reader.lines().collect(Collectors.toList());
            sleepingSessions = lines.stream()
                    .map(line -> line.split(";"))
                    .map(sleppingInfo -> new SleepingSession(
                            LocalDateTime.parse(sleppingInfo[0], formatter),
                            LocalDateTime.parse(sleppingInfo[1], formatter),
                            sleppingInfo[2]
                    ))
                    .collect(Collectors.toList());

            runAnalytics(sleepingSessions);
        } catch (IOException e) {
            throw new RuntimeException(FAILED_TO_READ_FILE + args[0], e);
        }
    }

    public static void runAnalytics(List<SleepingSession> sleepingSessions) {
        analyticsFunctions.stream()
                .map(function -> function.apply(sleepingSessions))
                .forEach(result -> {
                    SleepAnalysisResult sleepAnalysisResult = (SleepAnalysisResult) result;
                    System.out.println(DESCRIPTION_RESULT_OF_FUNCTION + sleepAnalysisResult.getDescription()
                            + sleepAnalysisResult.getResult());
                });
    }
}