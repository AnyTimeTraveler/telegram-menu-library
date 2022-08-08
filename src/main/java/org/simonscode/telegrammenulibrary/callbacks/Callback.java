package org.simonscode.telegrammenulibrary.callbacks;

import org.simonscode.telegrammenulibrary.UpdateHook;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.Objects;

/**
 * A callback that is used to assign a callback id to a {@link CallbackFunction}
 */
public class Callback {
    private final CallbackFunction callbackFunction;
    private final String callbackId;

    /**
     * Creates a new Callback
     * @param callbackFunction the function to be called when the callback is triggered
     */
    public Callback(CallbackFunction callbackFunction) {
        this.callbackFunction = callbackFunction;
        callbackId = UpdateHook.register(this);
    }

    /**
     * @return the callback id of this callback
     */
    public String getCallbackId() {
        return callbackId;
    }

    /**
     * Execute a {@link CallbackFunction} and then answer the callback query
     * @param bot to execute telegram api methods
     * @param callbackQuery the callback query that triggered this callback
     * @throws TelegramApiException if an error occurs on the telegram api
     */
    public void execute(AbsSender bot, CallbackQuery callbackQuery) throws TelegramApiException {
        String parameters = callbackQuery.getData().substring(12);
        callbackFunction.execute(bot, callbackQuery, parameters);

        bot.execute(AnswerCallbackQuery.builder()
                .callbackQueryId(callbackQuery.getId())
                .build());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Callback callback = (Callback) o;
        return callbackFunction.equals(callback.callbackFunction);
    }

    @Override
    public int hashCode() {
        return Objects.hash(callbackFunction);
    }
}
