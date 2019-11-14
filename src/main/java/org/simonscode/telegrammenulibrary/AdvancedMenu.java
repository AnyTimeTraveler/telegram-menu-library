package org.simonscode.telegrammenulibrary;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Message;

public class AdvancedMenu extends SimpleMenu {
    private enum ParseMode {
        TEXT(null),
        HTML(org.telegram.telegrambots.meta.api.methods.ParseMode.HTML),
        MARKDOWN(org.telegram.telegrambots.meta.api.methods.ParseMode.MARKDOWN);

        private final String value;

        ParseMode(String value) {
            this.value = value;
        }
    }

    private ParseMode parseMode = ParseMode.TEXT;

    public AdvancedMenu setParseMode(ParseMode parseMode) {
        this.parseMode = parseMode;
        return this;
    }

    public AdvancedMenu enableHtml() {
        this.parseMode = ParseMode.HTML;
        return this;
    }

    public AdvancedMenu enableMarkdown() {
        this.parseMode = ParseMode.MARKDOWN;
        return this;
    }

    @Override
    public EditMessageText generateEditMessage(Message message) {
        EditMessageText editMessageText = super.generateEditMessage(message);
        editMessageText.setParseMode(parseMode.value);

        return editMessageText;
    }

    @Override
    public SendMessage generateSendMessage() {
        SendMessage sendMessage = super.generateSendMessage();
        sendMessage.setParseMode(parseMode.value);
        return sendMessage;
    }
}
