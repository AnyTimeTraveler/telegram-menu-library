package org.simonscode.telegrammenulibrary;

import org.simonscode.telegrammenulibrary.menus.Menu;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

public class TestBot extends TelegramLongPollingBot {

    private final Menu menu;

    public TestBot(Menu menu) {
        this.menu = menu;
    }


    public static void startBotWithMenu(Menu menu) throws TelegramApiException {
        TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
        botsApi.registerBot(new TestBot(menu));
    }

    @Override
    public String getBotUsername() {
        return "TelegramMenuLibraryTestBot";
    }

    @Override
    public String getBotToken() {
        return System.getenv("TOKEN");
    }

    @Override
    public void onUpdateReceived(Update update) {
        try {
            UpdateHook.onUpdateReceived(this, update);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
        if (update.hasMessage() && update.getMessage().hasText() && update.getMessage().getText().equals("/start")) {
            System.out.println(update.getMessage().getText());
            SendMessage sendMessage = menu.generateSendMessage();
            sendMessage.setChatId(update.getMessage().getChatId());
            sendMessage.setReplyToMessageId(update.getMessage().getMessageId());
            try {
                execute(sendMessage);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }
}
