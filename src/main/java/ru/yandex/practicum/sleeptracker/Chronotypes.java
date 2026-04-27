package ru.yandex.practicum.sleeptracker;

public enum Chronotypes {
    NIGHT_OWL("Сова"),
    EARLY_BIRD("Жаворонок"),
    PIGEON("Голубь");

    private final String rusName;

    Chronotypes(String rusName) {
        this.rusName = rusName;
    }

    public String getRusName() {
        return rusName;
    }
}