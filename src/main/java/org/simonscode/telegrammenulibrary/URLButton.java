package org.simonscode.telegrammenulibrary;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

public class URLButton implements MenuButton {
    private final String text;
    private final String url;

    public URLButton(String text, String url) {
        this.text = text;
        this.url = url;
    }

    @Override
    public InlineKeyboardButton generateInlineKeyboardButton() {
        return new InlineKeyboardButton(text).setUrl(url);
    }
}
