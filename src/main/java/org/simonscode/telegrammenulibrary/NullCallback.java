package org.simonscode.telegrammenulibrary;

import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.bots.AbsSender;

public class NullCallback implements Callback {
    @Override
    public void execute(AbsSender bot, CallbackQuery callbackQuery) {

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        return o instanceof NullCallback;
    }
}
