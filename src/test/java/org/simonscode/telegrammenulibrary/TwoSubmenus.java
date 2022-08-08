package org.simonscode.telegrammenulibrary;

import org.simonscode.telegrammenulibrary.buttons.CallbackButton;
import org.simonscode.telegrammenulibrary.buttons.GotoButton;
import org.simonscode.telegrammenulibrary.buttons.URLButton;
import org.simonscode.telegrammenulibrary.callbacks.NullCallback;
import org.simonscode.telegrammenulibrary.menus.SimpleMenu;
import org.simonscode.telegrammenulibrary.menus.VerticalMenu;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.Optional;

public class TwoSubmenus {

    private static Optional<Message> message = Optional.empty();

    public static void main(String[] args) throws TelegramApiException {
        // Create the menus first, so they can be referenced later
        // A simple menu lets you layout the buttons in any order
        SimpleMenu mainMenu = new SimpleMenu();
        // A vertical menu stacks the buttons vertically
        SimpleMenu subMenuOne = new VerticalMenu();
        SimpleMenu subMenuTwo = new VerticalMenu();
        // there is also HorizontalMenu for laying the buttons out in a horizontal row

        // Now fill the main menu with text and some buttons that do things
        mainMenu
                .setText("Main Menu")
                .addButton(new GotoButton("SubMenu 1", subMenuOne))
                .addButton(new GotoButton("SubMenu 2", subMenuTwo))
                .nextRow()
                .addButton(new CallbackButton("Use the same function with parameters:", new NullCallback()))
                .nextRow()
                .addButton(new CallbackButton("sendMessage with 'hello world!'", TwoSubmenus::sendMessage, "hello world!"))
                .addButton(new CallbackButton("sendMessage with 'cheese'", TwoSubmenus::sendMessage, "cheese"))
                .nextRow()
                .addButton(new CallbackButton("Small", TwoSubmenus::setMessageText, "Small"))
                .addButton(new CallbackButton("buttons", TwoSubmenus::setMessageText, "buttons"))
                .addButton(new CallbackButton("in", TwoSubmenus::setMessageText, "in"))
                .addButton(new CallbackButton("one", TwoSubmenus::setMessageText, "one"))
                .addButton(new CallbackButton("row.", TwoSubmenus::setMessageText, "row."));

        // Populate submenu 1 with text and buttons
        subMenuOne
                .setText("Sub Menu 1\n" +
                        "As you can see, the buttons here are laid out horizontally.")
                .addButton(new CallbackButton("Send something to the console", (bot, callbackQuery, parameters) -> {
                    System.out.println("Hi from Telegram!");
                    bot.execute(new AnswerCallbackQuery(callbackQuery.getId()));
                }))
                .addButton(new CallbackButton("Send a message in this chat", (bot, callbackQuery, parameters) -> {
                    bot.execute(new SendMessage(callbackQuery.getMessage().getChatId().toString(), "Hello from Telegram!"));
                    bot.execute(new AnswerCallbackQuery(callbackQuery.getId()));
                }))
                .addButton(new URLButton("Go to simonscode.org", "https://simonscode.org/"))
                .addButton(new GotoButton("Go Back", mainMenu));

        // Populate submenu 2 with text and buttons
        subMenuTwo.setText("Sub Menu 2\n" +
                "The buttons here are all in a vertical column.");
        subMenuTwo.addButton(new GotoButton("Go to SubMenu 1", subMenuOne));
        subMenuTwo.addButton(new GotoButton("Also go to SubMenu 1", subMenuOne));
        subMenuTwo.addButton(new GotoButton("Go Back", mainMenu));

        TestBot.startBotWithMenu(mainMenu);
    }

    private static void sendMessage(AbsSender bot, CallbackQuery callbackQuery, String parameters) throws TelegramApiException {
        bot.execute(new SendMessage(
                callbackQuery.getMessage().getChatId().toString(),
                "The parameters are: '" + parameters + "'"
        ));
    }

    private static void setMessageText(AbsSender bot, CallbackQuery callbackQuery, String text) throws TelegramApiException {
        System.out.println(text);
        if (message.isPresent()) {
            Message msg = message.get();
            EditMessageText edit = new EditMessageText(text);
            edit.setChatId(msg.getChatId());
            edit.setMessageId(msg.getMessageId());
            bot.execute(edit);
        } else {
            message = Optional.of(bot.execute(new SendMessage(callbackQuery.getMessage().getChatId().toString(), text)));
        }
        bot.execute(new AnswerCallbackQuery(callbackQuery.getId()));
    }
}
