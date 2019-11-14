package org.simonscode.telegrammenulibrary;

import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class GotoCallback implements CallbackAction{
    private SimpleMenu targetMenu;

    public GotoCallback() {
    }

    public GotoCallback(SimpleMenu targetMenu) {
        this.targetMenu = targetMenu;
    }

    public void setTargetMenu(SimpleMenu targetMenu) {
        this.targetMenu = targetMenu;
    }

    @Override
    public void execute(AbsSender bot, CallbackQuery callbackQuery) {
        try {
            EditMessageText editMessage = targetMenu.generateEditMessage(callbackQuery.getMessage());
            /*if (editMessage.getText().equals(callbackQuery.getMessage().getText())){
                // Telegram API errors, when you send an edit without changing the text
                return;
            }*/
            bot.execute(editMessage);
        } catch (TelegramApiException e) {
            // TODO: Handle exceptions better
            // Let it error to stdout for now, it should not affect the workings of the bot.
            e.printStackTrace();
        }
    }
}
