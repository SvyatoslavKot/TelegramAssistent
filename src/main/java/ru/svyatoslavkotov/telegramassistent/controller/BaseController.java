package ru.svyatoslavkotov.telegramassistent.controller;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import ru.svyatoslavkotov.telegramassistent.utils.AppReplyMarkup;
import ru.svyatoslavkotov.telegramassistent.utils.Emoji;

@Component
public class BaseController {

    private final AppReplyMarkup replyMarkup;

    public BaseController(AppReplyMarkup replyMarkup) {
        this.replyMarkup = replyMarkup;
    }

    public SendMessage startMsg (Update update) {
        System.out.println(update);

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(update.getMessage().getChatId());
        sendMessage.setText("Hello " +  Emoji.SMILE.getSmile());
        //sendMessage.setReplyMarkup(replyMarkup.testKeyboard());
        sendMessage.setReplyMarkup(replyMarkup.location());

        return sendMessage;
    }

}
