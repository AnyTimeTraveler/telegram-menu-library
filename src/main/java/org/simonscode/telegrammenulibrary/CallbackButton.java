package org.simonscode.telegrammenulibrary;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

public class CallbackButton implements MenuButton {
    private final String buttonText;
    private final String callbackId;
    private final Callback action;

    public CallbackButton(String buttonText, Callback action) {
        this.buttonText = buttonText;
        this.action = action;
        this.callbackId = null;
    }

    public CallbackButton(String buttonText, String callbackId) {
        this.buttonText = buttonText;
        this.callbackId = callbackId;
        this.action = null;
    }

    @Override
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
