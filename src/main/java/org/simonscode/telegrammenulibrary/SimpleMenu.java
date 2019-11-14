package org.simonscode.telegrammenulibrary;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.*;

public class SimpleMenu {

    private String text;
    private List<List<MenuButton>> markup = new ArrayList<>();

    public SimpleMenu setText(String text) {
        this.text = text;
        return this;
    }

    public SimpleMenu addButton(String buttonText, CallbackAction action) {
        if (markup.isEmpty()) {
            markup.add(new LinkedList<>());
        }
        markup.get(markup.size() - 1).add(new MenuButton(buttonText, action));
        return this;
    }

    public SimpleMenu nextLine() {
        markup.add(new LinkedList<>());
        return this;
    }

    public EditMessageText generateEditMessage(Message message) {
        return new EditMessageText()
                .setMessageId(message.getMessageId())
                .setChatId(message.getChatId())
                .setText(text)
                .setReplyMarkup(generateMarkup(markup));
    }

    private InlineKeyboardMarkup generateMarkup(List<List<MenuButton>> markup) {
        List<List<InlineKeyboardButton>> returnRows = new LinkedList<>();
        for (List<MenuButton> row : markup) {
            List<InlineKeyboardButton> returnColumns = new LinkedList<>();
            for (MenuButton button: row) {
                returnColumns.add(button.generateInlineKeyboardButton());
            }
            returnRows.add(returnColumns);
        }
        return new InlineKeyboardMarkup().setKeyboard(returnRows);
    }

    public SendMessage generateSendMessage() {
        return new SendMessage()
                .setText(text)
                .setReplyMarkup(generateMarkup(markup));
    }

    public SimpleMenu setMarkup(List<List<MenuButton>> markup) {
        this.markup = markup;
        return this;
    }
}
