package ru.yandex.practicum.sleeptracker;

public class SleepAnalysisResult {
    private final Object result;
    private final String description;

    public SleepAnalysisResult(String description, Object result) {
        this.description = description;
        this.result = result;
    }

    public String getDescription() {
        return description;
    }

    public Object getResult() {
        return result;
    }
}