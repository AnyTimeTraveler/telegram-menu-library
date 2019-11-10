package org.simonscode.telegrammenulibrary;

import org.simonscode.telegrammenulibrary.callbacks.CallbackAction;

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
