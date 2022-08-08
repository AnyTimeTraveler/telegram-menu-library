package org.simonscode.telegrammenulibrary.menus;

import org.simonscode.telegrammenulibrary.callbacks.Callback;
import org.simonscode.telegrammenulibrary.buttons.GotoButton;
import org.simonscode.telegrammenulibrary.UpdateHook;
import org.simonscode.telegrammenulibrary.buttons.CallbackButton;
import org.simonscode.telegrammenulibrary.buttons.MenuButton;
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
public class SimpleMenu extends Menu {

    private String text;
    private List<List<MenuButton>> markup = new ArrayList<>();
    private final Callback gotoCallback = new Callback(this);

    /**
     * Creates a new SimpleMenu.
     */
    public SimpleMenu() {
        UpdateHook.register(gotoCallback);
    }

    /**
     * Creates a new SimpleMenu.
     * @param text text to show in the message of the menu
     */
    public SimpleMenu(String text) {
        this();
        this.text = text;
    }

    /**
     * Creates a new SimpleMenu.
     * @param text text to show in the message of the menu
     * @param markup buttons below the menu message
     */
    public SimpleMenu(String text, List<List<MenuButton>> markup) {
        this(text);
        this.markup = markup;
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
     * Following calls to {@link SimpleMenu#addButton(MenuButton)} will put the button in the row below this.
     *
     * @return itself for chaining
     */
    public SimpleMenu nextRow() {
        if (markup.isEmpty() || !markup.get(markup.size() - 1).isEmpty()) {
            markup.add(new LinkedList<>());
        }
        return this;
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
        return new InlineKeyboardMarkup(returnRows);
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
     * @return the callback that belongs to this menu
     */
    public Callback getGotoCallback() {
        return gotoCallback;
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
     * The returned {@link SendMessage} object still requires a chatId before it can be sent off.
     *
     * @return {@link SendMessage} that you can execute with your bot to send this menu into a telegram chat
     */
    public SendMessage generateSendMessage() {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setText(text);
        sendMessage.setReplyMarkup(generateMarkup(markup));
        return sendMessage;
    }

    /**
     * Generates an {@link EditMessageText} object that can be executed by the bot to edit a message and its buttons.
     *
     * @param message message to edit
     * @return editmessage that you can execute with your bot to change the message to this menu
     */
    public EditMessageText generateEditMessage(Message message) {
        return EditMessageText.builder()
                .chatId(message.getChatId())
                .messageId(message.getMessageId())
                .text(text)
                .replyMarkup(generateMarkup(markup))
                .build();
    }

    /**
     * @param alsoRemoveLinkedMenus if true, all menus linked to this menu will be removed as well
     */
    public void unregister(boolean alsoRemoveLinkedMenus) {
        UpdateHook.unregister(gotoCallback);
        for (List<MenuButton> menuButtons : markup) {
            for (MenuButton menuButton : menuButtons) {
                if (menuButton instanceof GotoButton button) {
                    if (alsoRemoveLinkedMenus) {
                        UpdateHook.unregister(button.getCallbackId());
                    }
                }
                if (menuButton instanceof CallbackButton button) {
                    UpdateHook.unregister(button.getCallbackId());
                }
            }
        }
    }

    @Override
    public String toString() {
        return "SimpleMenu{" +
                "text='" + text + '\'' +
                ", markup=" + markup +
                '}';
    }
}
