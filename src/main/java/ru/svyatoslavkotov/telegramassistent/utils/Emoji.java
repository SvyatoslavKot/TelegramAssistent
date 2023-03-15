package ru.svyatoslavkotov.telegramassistent.utils;

import com.vdurmont.emoji.EmojiParser;

public enum Emoji {
    SMILE (" :blush:"),
    SAD (":slightly_frowning_face:"),
    CHART_WITH_UPWARDS_TREND (" :chart_with_upwards_trend:");

    String shortCods;

    Emoji(String shortCods) {
        this.shortCods = shortCods;
    }

    public String getSmile(){
        return EmojiParser.parseToUnicode(shortCods);
    }
}
