package org.simonscode.telegrammenulibrary;

import org.simonscode.telegrammenulibrary.buttons.CallbackButton;
import org.simonscode.telegrammenulibrary.buttons.GotoButton;
import org.simonscode.telegrammenulibrary.buttons.URLButton;
import org.simonscode.telegrammenulibrary.callbacks.Callback;
import org.simonscode.telegrammenulibrary.callbacks.NullCallback;
import org.simonscode.telegrammenulibrary.menus.SimpleMenu;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class SimpleMenuExample {
    private static Update update;
    private static SimpleMenu myOtherMenu;
    private static AbsSender bot;

    public static void main(String[] args) throws TelegramApiException {
        SimpleMenu myMenu = new SimpleMenu("Menu Title");
        myMenu.addButton(new CallbackButton("Callback Button", new Callback((bot, callbackQuery, parameters) -> {
            System.out.println("This code will execute when the button is clicked");
        })));
        myMenu.addButton(new URLButton("URL Button", "https://www.simonscode.org/"));
        myMenu.addButton(new GotoButton("Back", myOtherMenu));

        myMenu.addButton(new CallbackButton("Top",new NullCallback()));
        myMenu.nextRow();
        myMenu.addButton(new CallbackButton("Bottom",new NullCallback()));

        SendMessage sendMessage = myMenu.generateSendMessage();
        sendMessage.setChatId(update.getMessage().getChatId());
        bot.execute(sendMessage);
        myMenu.unregister(true);
    }
}
