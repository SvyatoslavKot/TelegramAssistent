package ru.svyatoslavkotov.telegramassistent.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.svyatoslavkotov.telegramassistent.config.TGBot;
import ru.svyatoslavkotov.telegramassistent.service.DocumentSaveService;
import ru.svyatoslavkotov.telegramassistent.utils.MessageUtils;

@Component
@Slf4j
public class UpdateController {
    private TGBot telegramBot;

    private final MessageUtils messageUtils;
    // private final UpdateProducer updateProducer;
    private final ControllerManager controllerManager;
    private final DocumentController documentController;
    private final DocumentSaveService documentSaveService;
    // private final CallbackController callbackController;
    // private TelegrammDocument telegrammDocument = new TelegrammDocument();

    public UpdateController(MessageUtils messageUtils, ControllerManager controllerManager, DocumentController documentController, DocumentSaveService documentSaveService) {
        this.messageUtils = messageUtils;
        this.controllerManager = controllerManager;
        this.documentController = documentController;
        this.documentSaveService = documentSaveService;
    }



    public void registerBot(TGBot telegramBot) {
        this.telegramBot = telegramBot;
    }

    public void processUpdate(Update update) {


        if (update == null) {
            log.error("Received update is null");
            return;
        }
        if (update.hasMessage() && update.getMessage().hasText() || update.getMessage().hasDocument()) {

            log.info("Update Message: {}", update.getMessage().getText());
            distributeMessagesByType(update);
        } else if (update.getMessage().getLocation() != null){
            System.out.println(update.getMessage().getLocation());
            System.out.println(update.getMessage());
            SendMessage sendMessage = new SendMessage();
            sendMessage.setChatId(update.getMessage().getChatId());
            sendMessage.setText("Location.");
            setAnswerMessage(sendMessage);
        }else if (update.hasCallbackQuery()){
            log.info("Update Callback: {}", update.getCallbackQuery().getData());
            //telegramBot.sendAnswerMessageCallback(callbackController.callbackAnswer(update));
        }else {
            log.error("Unsupported message type is received: " + update);
        }
    }

    private void distributeMessagesByType(Update update) {
        var message = update.getMessage();
        if (message.hasText() && !message.hasDocument() && !message.hasPhoto()) {
            processTextMessage(update);
        } else if (message.hasDocument()) {
            processDocMessage(update);
        } else if (message.hasPhoto()) {
            processPhotoMessage(update);
        } else {
            setUnsupportedMessageTypeView(update);
        }
    }

    private void setUnsupportedMessageTypeView(Update update) {
        var sendMessage = messageUtils.generateSendMessageWithText(
                update, "Неподдерживаемый тип сообщения!");
        setAnswerMessage(sendMessage);
    }

    private void setFileIsReceivedView(Update update) {
        var sendMessage = messageUtils.generateSendMessageWithText(
                update,"Файл получен! Обрабатывается...");
        setAnswerMessage(sendMessage);
    }


    private void processPhotoMessage(Update update) {
        // TODO: 008 08.11.22  add methods for photo message processing
        //updateProducer.produce(PHOTO_MESSAGE_UPDATE, update);
        setFileIsReceivedView(update);
    }

    private void processDocMessage( Update update) {
        setAnswerMessage(documentController.processDocumentMessage(update));
    }

    private void processTextMessage(Update update) {
        SendMessage responseMsg = controllerManager.requestHandler(update);
        setAnswerMessage(responseMsg);
    }

    public void setAnswerMessage(SendMessage sendMessage) {
        telegramBot.sendAnswerMessage(sendMessage);
    }

}
