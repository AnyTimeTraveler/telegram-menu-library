package org.simonscode.telegrammenulibrary;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

public class MenuButton {
    private final String buttonText;
    private final String callbackId;
    private final CallbackAction action;

    public MenuButton(String buttonText, CallbackAction action) {
        this.buttonText = buttonText;
        this.action = action;
        this.callbackId = null;
    }

    public MenuButton(String buttonText, String callbackId) {
        this.buttonText = buttonText;
        this.callbackId = callbackId;
        this.action = null;
    }

    public InlineKeyboardButton generateInlineKeyboardButton() {
        InlineKeyboardButton inlineKeyboardButton = new InlineKeyboardButton(buttonText);
        if (action != null) {
            inlineKeyboardButton.setCallbackData(UpdateHook.register(action));
        } else if (callbackId != null) {
            inlineKeyboardButton.setCallbackData(callbackId);
        }
        return inlineKeyboardButton;
    }
}
