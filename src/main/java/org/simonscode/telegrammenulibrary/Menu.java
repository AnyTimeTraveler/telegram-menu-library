package org.simonscode.telegrammenulibrary;

import org.simonscode.telegrammenulibrary.callbacks.CallbackAction;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;

import java.util.*;
import java.util.concurrent.Callable;

public class Menu {

    private String text;
    private Callable<String> textGenerator;
    List<List<MenuButton>> markup = new LinkedList<>();
    private String id;

    public Menu setText(String text) {
        this.text = text;
        return this;
    }

    public Menu setTextGenerator(Callable<String> textGenerator) {
        this.textGenerator = textGenerator;
        return this;
    }

    public Menu addButton(String buttonText, CallbackAction action) {
        if (markup.isEmpty()) {
            markup.add(new LinkedList<>());
        }
        markup.get(markup.size() - 1).add(new MenuButton(buttonText, action));
        return this;
    }

    public Menu nextLine() {
        markup.add(new LinkedList<>());
        return this;
    }

    public Menu setCallbackId(String id) {
        this.id = id;
        return this;
    }

    public EditMessageText generateEditMessage() {
        return null;
    }

    public SendMessage generateSendMessage() {
        return null;
    }

    public Menu setMarkup(List<List<MenuButton>> markup) {
        this.markup = markup;
        return this;
    }
}
