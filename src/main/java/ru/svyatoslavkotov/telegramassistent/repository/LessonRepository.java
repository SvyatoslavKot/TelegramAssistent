package ru.svyatoslavkotov.telegramassistent.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import ru.svyatoslavkotov.telegramassistent.model.Lesson;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.Optional;

public interface LessonRepository extends JpaRepository<Lesson, Long> {

    Optional<Lesson> getLessonByDateAndTitle(LocalDate date, String title);

    @Transactional
    @Modifying
    @Query("update Lesson l set l.example = ?1,  l.lastUpdate = ?2 where l.id = ?3")
    int updateLessonExample(String example, LocalDate updateDate, Long id );

    @Transactional
    @Modifying
    @Query("update Lesson l set l.exampleRecommended = ?1, l.lastUpdate = ?2 where l.id = ?3")
    int updateLessonExampleRecommended(String ExampleRecommended, LocalDate updateDate, Long id );




}