package org.simonscode.telegrammenulibrary;

import org.simonscode.telegrammenulibrary.CallbackAction;
import org.simonscode.telegrammenulibrary.UpdateHook;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.bots.AbsSender;

public class MenuButton {
    private final String buttonText;
    private final CallbackAction action;
    private final String callbackId;

    public MenuButton(String buttonText, CallbackAction action) {
        this.buttonText = buttonText;
        this.action = action;
        this.callbackId = UpdateHook.generateCallbackId();
    }

    public MenuButton(String buttonText, String callbackId) {
        this.buttonText = buttonText;
        this.action = null;
        this.callbackId = callbackId;
    }
}
