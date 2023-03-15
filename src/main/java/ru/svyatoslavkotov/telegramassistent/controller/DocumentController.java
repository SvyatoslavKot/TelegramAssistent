package ru.svyatoslavkotov.telegramassistent.controller;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.svyatoslavkotov.telegramassistent.model.AppDocument;
import ru.svyatoslavkotov.telegramassistent.repository.LessonRepository;
import ru.svyatoslavkotov.telegramassistent.service.DocumentSaveService;
import ru.svyatoslavkotov.telegramassistent.service.LessonService;
import ru.svyatoslavkotov.telegramassistent.utils.Emoji;

@Component
public class DocumentController {

    private final DocumentSaveService documentSaveService;
    private final LessonService lessonService;
    private final LessonRepository lessonRepository;

    public DocumentController(DocumentSaveService documentSaveService, LessonService lessonService, LessonRepository lessonRepository) {
        this.documentSaveService = documentSaveService;
        this.lessonService = lessonService;
        this.lessonRepository = lessonRepository;
    }

    public SendMessage processDocumentMessage(Update update){
        System.out.println(update.getMessage().getText());
        AppDocument appDocument = documentSaveService.processDoc(update.getMessage());
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(update.getMessage().getChatId());
        if (appDocument != null){
            if (lessonService.saveLessonsFromDocument()){
                sendMessage.setText("Данные успешно сохранены " +  Emoji.SMILE.getSmile()  );
            }else {
                sendMessage.setText("Сохранение данных не увенчалось упехом " +  Emoji.SAD.getSmile() + " попробуте еще раз, или повторите попытку позже." );
            }
        }else {
            sendMessage.setText("Не удачная попытка прочитать файл " +  Emoji.SAD.getSmile() );
        }
        return sendMessage;
    }
}
