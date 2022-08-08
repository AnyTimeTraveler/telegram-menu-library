package org.simonscode.telegrammenulibrary.callbacks;

import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

/**
 * Interface for custom callback functions
 */
@FunctionalInterface
public interface CallbackFunction {
    /**
     * This method is called when the user clicks the corresponding button in Telegram.
     *
     * @param bot bot that received the callback. Used to execute any actions from the callback
     * @param callbackQuery callbackQuery that caused this callback action to fire
     * @param parameters parameters passed to the callback
     * @throws TelegramApiException if an error occurs
     */
    void execute(AbsSender bot, CallbackQuery callbackQuery, String parameters) throws TelegramApiException;
}
