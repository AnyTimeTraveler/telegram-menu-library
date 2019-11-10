package org.simonscode.telegrammenulibrary.callbacks;

import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.bots.AbsSender;

@FunctionalInterface
public interface CallbackAction {
    void execute(AbsSender bot, CallbackQuery callbackQuery);
}
