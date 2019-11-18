package org.simonscode.telegrammenulibrary;

import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.bots.AbsSender;

@FunctionalInterface
public interface Callback {
    /**
     * This method is called when the user clicks the corresponding button in Telegram.
     *
     * @param bot bot that received the callback. Used to execute any actions from the callback
     * @param callbackQuery callbackQuery that caused this callbacaction to fire
     */
    void execute(AbsSender bot, CallbackQuery callbackQuery);
}
