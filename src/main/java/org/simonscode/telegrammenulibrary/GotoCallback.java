package org.simonscode.telegrammenulibrary;

import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.Objects;

public class GotoCallback implements CallbackAction {
    private SimpleMenu targetMenu;

    public GotoCallback() {
    }

    /**
     * Jump to targetMenu when called.
     * This will edit the current message to show this menu.
     *
     * @param targetMenu menu to jump to when executing this callback.
     */
    public GotoCallback(SimpleMenu targetMenu) {
        this.targetMenu = targetMenu;
    }

    /**
     * Set the menu to jump to.
     *
     * @param targetMenu menu to jump to
     */
    public void setTargetMenu(SimpleMenu targetMenu) {
        this.targetMenu = targetMenu;
    }

    /**
     * This method is called when the user clicks the corresponding button in Telegram.
     *
     * @param bot bot that received the callback. Used to execute any actions from the callback
     * @param callbackQuery callbackQuery that caused this callbacaction to fire
     */
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GotoCallback that = (GotoCallback) o;
        return Objects.equals(targetMenu, that.targetMenu);
    }

    @Override
    public int hashCode() {
        return Objects.hash(targetMenu);
    }
}
