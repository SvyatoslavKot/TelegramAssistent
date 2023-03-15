package ru.svyatoslavkotov.telegramassistent.utils;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

@Component
public class AppReplyMarkup {

    public ReplyKeyboardMarkup testKeyboard (){
        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        List<KeyboardRow> keyboardRows = new ArrayList<>();
        KeyboardRow row = new KeyboardRow();
        row.add("weather");
        row.add("get randomjoke");

        keyboardRows.add(row);
        row = new KeyboardRow();

        row.add("register");
        row.add("check my data");
        row.add("delete my data");

        keyboardRows.add(row);
        keyboardMarkup.setKeyboard(keyboardRows);
        return  keyboardMarkup;
    }

    public ReplyKeyboardMarkup location() {
        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        KeyboardButton button = new KeyboardButton();
        KeyboardRow row = new KeyboardRow();
        List<KeyboardRow> rowList  = new ArrayList<>();

        button.setText("location");
        button.setRequestLocation(true);

        row.add(button);
        rowList.add(row);
        keyboardMarkup.setKeyboard(rowList);



        return keyboardMarkup;
    }
}
