package org.simonscode.telegrammenulibrary;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * A simple menu that should make it as easy as possible use it.
 */
public class SimpleMenu implements Menu {

    private String text;
    private List<List<MenuButton>> markup = new ArrayList<>();

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
    public SimpleMenu addButton(String buttonText, Callback action) {
        if (markup.isEmpty()) {
            markup.add(new LinkedList<>());
        }
        markup.get(markup.size() - 1).add(new CallbackButton(buttonText, action));
        return this;
    }

    /**
     * Add a button to the menu in the current horizontal row.
     *
     * @param button button to be added
     * @return itself for chaining
     */
    public SimpleMenu addButton(MenuButton button) {
        if (markup.isEmpty()) {
            markup.add(new LinkedList<>());
        }
        markup.get(markup.size() - 1).add(button);
        return this;
    }

    /**
     * Adds a new row to the button list.
     * Following calls to {@link SimpleMenu#addButton(String, Callback)} will put the button in the row below this.
     *
     * @return itself for chaining
     */
    public SimpleMenu nextLine() {
        markup.add(new LinkedList<>());
        return this;
    }

    /**
     * Generates an {@link EditMessageText} object that can be executed by the bot to edit a message and its buttons.
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
     * Removes all buttons.
     * Useful if you want to swap out buttons or load in another markup.
     *
     * @return itself, for chaining
     */
    public SimpleMenu clearMarkup() {
        markup.clear();
        return this;
    }

    /**
     * Returns the currently configured markup.
     * Useful if you want to swap out another markup.
     *
     * @return the current markup
     */
    public List<List<MenuButton>> getMarkup() {
        return markup;
    }

    /**
     * Replaces the current markup.
     * Useful if you want to swap out another markup.
     *
     * @param markup markup to replace the current one
     * @return itself, for chaining
     */
    public SimpleMenu setMarkup(List<List<MenuButton>> markup) {
        this.markup = markup;
        return this;
    }

    /**
     * Generates a {@link SendMessage} from this menu to send this menu into a telegram chat.
     *
     * @param chatId destination of the generated {@link SendMessage}
     * @return {@link SendMessage} that you can execute with your bot to send this menu into a telegram chat
     */
    public SendMessage generateSendMessage(Long chatId) {
        return generateSendMessage(String.valueOf(chatId));
    }

    /**
     * Generates a {@link SendMessage} from this menu to send this menu into a telegram chat.
     *
     * @param chatId destination of the generated {@link SendMessage}
     * @return {@link SendMessage} that you can execute with your bot to send this menu into a telegram chat
     */
    public SendMessage generateSendMessage(String chatId) {
        return generateSendMessage().setChatId(chatId);
    }

    /**
     * Generates a {@link SendMessage} from this menu to send this menu into a telegram chat.
     * The returned {@link SendMessage} object still requires a chatId before it can be sent off.
     *
     * @return {@link SendMessage} that you can execute with your bot to send this menu into a telegram chat
     */
    public SendMessage generateSendMessage() {
        return new SendMessage()
                .setText(text)
                .setReplyMarkup(generateMarkup(markup));
    }
}
