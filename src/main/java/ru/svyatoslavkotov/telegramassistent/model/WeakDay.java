package ru.svyatoslavkotov.telegramassistent.model;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public enum WeakDay {
    MONDAY("Понедельник"),
    TUESDAY("Вторник"),
    WEDNESDAY("Среда"),
    THURSDAY("Четверг"),
    FRIDAY("Пятница"),
    SATURDAY("Суббота"),
    SUNDAY("Воскресенье");

    private String value;

    WeakDay(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}