package ru.svyatoslavkotov.telegramassistent.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.svyatoslavkotov.telegramassistent.model.Lesson;
import ru.svyatoslavkotov.telegramassistent.repository.LessonRepository;
import ru.svyatoslavkotov.telegramassistent.utils.Emoji;

import java.time.LocalDate;
import java.util.List;

@Service
public class LessonService {

    @Autowired
    private DocumentReadService documentReadService;
    @Autowired
    private LessonRepository lessonRepository;

    @Transactional
    public boolean saveLessonsFromDocument(){
        List<Lesson> lessons = documentReadService.read();
        try{
            for (Lesson lesson : lessons) {
                Lesson lessonFromDb  = lessonRepository.getLessonByDateAndTitle(lesson.getDate(),lesson.getTitle()).orElse(null);
                if (lessonFromDb != null) {
                    if (lesson.getExample()!= null ){
                        lessonRepository.updateLessonExample(lesson.getExample(), LocalDate.now(), lessonFromDb.getId());
                    }
                    if (lesson.getExampleRecommended() != null) {
                        lessonRepository.updateLessonExampleRecommended(lesson.getExampleRecommended(), LocalDate.now(), lessonFromDb.getId());
                    }
                }else {
                    lessonRepository.save(lesson);
                }
            }
            return true;
        } catch (Exception e ){
            return false;
        }

    }
}
