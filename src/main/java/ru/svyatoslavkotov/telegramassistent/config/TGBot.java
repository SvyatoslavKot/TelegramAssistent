package ru.svyatoslavkotov.telegramassistent.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.svyatoslavkotov.telegramassistent.controller.UpdateController;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class TGBot extends TelegramLongPollingBot {

    private final UpdateController updateController;

    final BotConfig config;


    public TGBot(UpdateController updateController, BotConfig config) {
        this.updateController = updateController;
        this.config = config;
        List<BotCommand> listOfCommands = new ArrayList<>();
        listOfCommands.add(new BotCommand("/start", "start bot"));
        listOfCommands.add(new BotCommand("/mydata", "get your data stored"));
        listOfCommands.add(new BotCommand("/currency", "get currency rate"));
        listOfCommands.add(new BotCommand("/login", "login for app"));
        listOfCommands.add(new BotCommand("/help", "info how to use this bot"));
        listOfCommands.add(new BotCommand("/charcode", "get char code"));
        try {
            this.execute(new SetMyCommands(listOfCommands, new BotCommandScopeDefault(), null));
        } catch (TelegramApiException e) {
            log.error("ERROR setting bot's command list: " + e.getMessage(), e.getCause());
        }
    }

    @PostConstruct
    public void init() {
        updateController.registerBot(this);
    }

    @Override
    public String getBotUsername() {
        return config.getBotName();
    }

    @Override
    public String getBotToken() {
        return config.getToken();
    }

    @Override
    public void onUpdateReceived(Update update) {
        updateController.processUpdate(update);
    }

    public void sendAnswerMessage(SendMessage message) {
        if (message != null) {
            try {
                execute(message);
            } catch (TelegramApiException e) {
                log.error(e.getMessage(), e.getCause());
            }
        }
    }

    public void sendAnswerMessageCallback(EditMessageText editMessageText) {
        if (editMessageText != null) {
            try {
                execute(editMessageText);
            } catch (TelegramApiException e) {
                log.error(e.getMessage(), e.getCause());
            }
        }
    }
}
