package org.simonscode.telegrammenulibrary;

import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class GotoPageCallback implements CallbackAction{
    private final Menu targetMenu;

    public GotoPageCallback(Menu targetMenu) {
        this.targetMenu = targetMenu;
    }

    @Override
    public void execute(AbsSender bot, CallbackQuery callbackQuery) {
        try {
            // TODO: Check if the message Text is actually different. If not, send an edit for the buttons.
            bot.execute(targetMenu.generateEditMessage());
        } catch (TelegramApiException e) {
            // TODO: Handle exceptions better
            e.printStackTrace();
        }
    }
}
