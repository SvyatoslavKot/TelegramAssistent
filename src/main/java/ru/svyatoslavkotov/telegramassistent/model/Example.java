package ru.svyatoslavkotov.telegramassistent.model;

public class Example {

    private String title;
    private String example;
    private String exampleRecommended;
    private boolean active;

    public Example(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getExample() {
        return example;
    }

    public void setExample(String example) {
        this.example = example;
    }

    public String getExampleRecommended() {
        return exampleRecommended;
    }

    public void setExampleRecommended(String exampleRecommended) {
        this.exampleRecommended = exampleRecommended;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
