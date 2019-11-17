package org.simonscode.telegrammenulibrary;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.*;

/**
 * A simple menu that should make it as easy as possible use it.
 */
public class SimpleMenu {

    private String text;
    private final List<List<MenuButton>> markup = new ArrayList<>();

    /**
     * Creates a new SimpleMenu and sets the text shown in the menu.
     *
     * @param text text to show in the message of the menu
     */
    public SimpleMenu(String text) {
        setText(text);
    }

    /**
     * Creates a new SimpleMenu.
     */
    public SimpleMenu() {
    }

    /**
     * Set the text shown in the menu.
     *
     * @param text text to show in the message of the menu
     * @return itself for chaining
     */
    public SimpleMenu setText(String text) {
        this.text = text;
        return this;
    }

    /**
     * Add a button to the menu in the current horizontal row.
     *
     * @param buttonText text shown on the button
     * @param action     action to execute when the user clicks the button
     * @return itself for chaining
     */
    public SimpleMenu addButton(String buttonText, CallbackAction action) {
        if (markup.isEmpty()) {
            markup.add(new LinkedList<>());
        }
        markup.get(markup.size() - 1).add(new MenuButton(buttonText, action));
        return this;
    }

    /**
     * Adds a new row to the button list.
     * Following calls to addButton will put the button in the row below this.
     *
     * @return itself for chaining
     */
    public SimpleMenu nextLine() {
        markup.add(new LinkedList<>());
        return this;
    }

    /**
     * Generates an EditMessageText object that can be executed by the bot to edit a message and its buttons.
     *
     * @param message message to edit
     * @return editmessage that you can execute with your bot to change the message to this menu
     */
    public EditMessageText generateEditMessage(Message message) {
        return new EditMessageText()
                .setMessageId(message.getMessageId())
                .setChatId(message.getChatId())
                .setText(text)
                .setReplyMarkup(generateMarkup(markup));
    }

    /**
     * Generate the telegram compatible keyboard markup for this menu.
     *
     * @param markup markup to convert
     * @return telegram compatible keyboard markup
     */
    private InlineKeyboardMarkup generateMarkup(List<List<MenuButton>> markup) {
        List<List<InlineKeyboardButton>> returnRows = new LinkedList<>();
        for (List<MenuButton> row : markup) {
            List<InlineKeyboardButton> returnColumns = new LinkedList<>();
            for (MenuButton button : row) {
                returnColumns.add(button.generateInlineKeyboardButton());
            }
            if (!returnColumns.isEmpty()) {
                returnRows.add(returnColumns);
            }
        }
        return new InlineKeyboardMarkup().setKeyboard(returnRows);
    }


    /**
     * Generates a SendMessage from this menu to send this menu into a telegram chat.
     *
     * @param chatId destination of the generated SendMessage
     * @return SendMessage that you can execute with your bot to send this menu into a telegram chat
     */
    public SendMessage generateSendMessage(Long chatId) {
        return generateSendMessage(String.valueOf(chatId));
    }

    /**
     * Generates a SendMessage from this menu to send this menu into a telegram chat.
     *
     * @param chatId destination of the generated SendMessage
     * @return SendMessage that you can execute with your bot to send this menu into a telegram chat
     */
    public SendMessage generateSendMessage(String chatId) {
        return new SendMessage()
                .setText(text)
                .setReplyMarkup(generateMarkup(markup))
                .setChatId(chatId);
    }
}
