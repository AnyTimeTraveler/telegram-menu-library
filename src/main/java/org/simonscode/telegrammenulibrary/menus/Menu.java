package org.simonscode.telegrammenulibrary.menus;

import org.simonscode.telegrammenulibrary.callbacks.CallbackFunction;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

/**
 * This is the base class for menus.
 */
public abstract class Menu implements CallbackFunction {

    /**
     * Creates a new {@link SendMessage} that contains the text and buttons of the menu.
     *
     * @return {@link SendMessage} to send the menu to the user
     */
    public abstract SendMessage generateSendMessage();

    /**
     * Creates a new {@link EditMessageText} that edits the original message to the text and buttons of this menu.
     *
     * @param message the original message to edit
     * @return {@link EditMessageText} to edit the message to the text and buttons of this menu
     */
    public abstract EditMessageText generateEditMessage(Message message);

    /**
     * Allows the menu to act as a callback function.
     * Therefore, the menu can be used in a {@link org.simonscode.telegrammenulibrary.buttons.CallbackButton} to switch to this menu.
     *
     * @param bot           bot that received the callback. Used to send the callback confirmation and the menu
     * @param callbackQuery callbackQuery that caused this callback action to fire
     * @param parameters    parameters passed to the callback. Unused
     * @throws TelegramApiException if an error occurs while interacting with telegram
     */
    @Override
    public void execute(AbsSender bot, CallbackQuery callbackQuery, String parameters) throws TelegramApiException {
        bot.execute(new AnswerCallbackQuery(callbackQuery.getId()));
        bot.execute(generateEditMessage(callbackQuery.getMessage()));
    }
}
