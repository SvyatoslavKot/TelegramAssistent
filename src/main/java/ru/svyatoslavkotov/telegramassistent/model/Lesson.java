package ru.svyatoslavkotov.telegramassistent.model;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@Table(name = "Lesson")
@Entity
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Lesson {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Exclude
    @Nullable
    @Column(name = "id", columnDefinition = "bigint")
    Long id;
    @Column(columnDefinition = "varchar(225)", name = "title")
    String title;
    @Column(columnDefinition = "int", name = "day_of_weak")
    int dayOfWeak;
    @Column(columnDefinition = "int", name = "number")
    int number;
    @Column(columnDefinition = "varchar(225)", name = "example")
    String example;
    @Column(columnDefinition = "varchar(225)", name = "example_recommended")
    String exampleRecommended;
    @Column(columnDefinition = "timestamp", name = "date")
    LocalDate date;
    @Column(columnDefinition = "timestamp", name = "last_update")
    LocalDate lastUpdate;


    @Override
    public String toString() {
        return "Lesson{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", dayOfWeak=" + dayOfWeak +
                ", number=" + number +
                ", example='" + example + '\'' +
                ", ExampleRecommended='" + exampleRecommended + '\'' +
                ", date=" + date +
                ", lastUpdate=" + lastUpdate +
                '}';
    }
}