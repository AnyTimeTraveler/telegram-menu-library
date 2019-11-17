package org.simonscode.telegrammenulibrary;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * A more advanced menu that lets you take greater control of the behaviour of the menu.
 */
public class TemplateMenu {
    private Callable<String> textGenerator;
    private List<List<MenuButton>> markup = new ArrayList<>();

    /**
     * Set the textGenerator, which will generate the text shown in the menu, when called.
     *
     * @param textGenerator a function that generates the text for the menu
     * @return itself for chaining
     */
    public TemplateMenu setTextGenerator(Callable<String> textGenerator) {
        this.textGenerator = textGenerator;
        return this;
    }

    /**
     * Add a button to the menu in the current horizontal row.
     *
     * @param buttonText text shown on the button
     * @param action     action to execute when the user clicks the button
     * @return itself for chaining
     */
    public TemplateMenu addButton(String buttonText, CallbackAction action) {
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
    public TemplateMenu nextLine() {
        markup.add(new LinkedList<>());
        return this;
    }

    /**
     * Generates an EditMessageText object that can be executed by the bot to edit a message and its buttons.
     *
     * @param message message to edit
     * @return editmessage that you can execute with your bot to change the message to this menu
     */
    public EditMessageText generateEditMessage(Message message) throws Exception {
        return new EditMessageText()
                .setMessageId(message.getMessageId())
                .setChatId(message.getChatId())
                .setText(textGenerator.call())
                .setReplyMarkup(generateMarkup(markup));
    }

    /**
     * Generate the telegram compatible keyboard markup for this menu.
     *
     * @param markup markup to convert
     * @return telegram compatible keyboard markup
     */
    public InlineKeyboardMarkup generateMarkup(List<List<MenuButton>> markup) {
        List<List<InlineKeyboardButton>> returnRows = new LinkedList<>();
        for (List<MenuButton> row : markup) {
            List<InlineKeyboardButton> returnColumns = new LinkedList<>();
            for (MenuButton button : row) {
                returnColumns.add(button.generateInlineKeyboardButton());
            }
            returnRows.add(returnColumns);
        }
        return new InlineKeyboardMarkup().setKeyboard(returnRows);
    }

    /**
     *
     * @return
     * @throws Exception
     */
    public SendMessage generateSendMessage() throws Exception {
        return new SendMessage()
                .setText(textGenerator.call())
                .setReplyMarkup(generateMarkup(markup));
    }

    /**
     *
     * @return
     */
    public TemplateMenu clearMarkup() {
        markup.clear();
        return this;
    }

    /**
     *
     * @return
     */
    public List<List<MenuButton>> getMarkup() {
        return markup;
    }

    /**
     *
     * @param markup
     * @return
     */
    public TemplateMenu setMarkup(List<List<MenuButton>> markup) {
        this.markup = markup;
        return this;
    }
}
