package ru.svyatoslavkotov.telegramassistent.controller;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class ControllerManager {


    private final BaseController baseController;

    public ControllerManager(BaseController baseController) {
        this.baseController = baseController;
    }

    public SendMessage requestHandler(Update update) {
        var msg = update.getMessage().getText();

        if (msg.contains("/start")) {
            return baseController.startMsg(update);
        } else {
            System.out.println("loc -> " + update.getMessage().getLocation());
            SendMessage sendMessage = new SendMessage();
            sendMessage.setChatId(update.getMessage().getChatId());
            sendMessage.setText("Команда не распознана.");
            return sendMessage;
        }
    }
}
