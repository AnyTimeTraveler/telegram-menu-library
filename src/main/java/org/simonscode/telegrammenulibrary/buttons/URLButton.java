package org.simonscode.telegrammenulibrary.buttons;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

/**
 * A menu button that opens a URL when pressed.
 */
public class URLButton implements MenuButton {
    private final String text;
    private final String url;

    /**
     * Creates a new URLButton.
     * @param text the text of the button
     * @param url the URL that is opened when the button is pressed
     */
    public URLButton(String text, String url) {
        this.text = text;
        this.url = url;
    }

    @Override
    public InlineKeyboardButton generateInlineKeyboardButton() {
        return InlineKeyboardButton.builder()
                .text(text)
                .url(url)
                .build();
    }

    @Override
    public String toString() {
        return "URLButton{" +
                "text='" + text + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
