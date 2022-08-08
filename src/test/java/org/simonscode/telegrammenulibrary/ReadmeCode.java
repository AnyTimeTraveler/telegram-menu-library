package org.simonscode.telegrammenulibrary;

import org.simonscode.telegrammenulibrary.buttons.CallbackButton;
import org.simonscode.telegrammenulibrary.buttons.GotoButton;
import org.simonscode.telegrammenulibrary.callbacks.Callback;
import org.simonscode.telegrammenulibrary.menus.SimpleMenu;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class ReadmeCode {
    public static void main(String[] args) throws TelegramApiException {
        SimpleMenu firstMenu = new SimpleMenu("Example Menu Title");
        SimpleMenu secondMenu = new SimpleMenu("Second Menu: Text that will be displayed in the telegram message");

        // Add buttons to the first menu
        firstMenu.addButton(new CallbackButton("Callback Button", new Callback((bot, callbackQuery, parameters) -> {
            System.out.println("This code will execute when the button is clicked");
        })));

        // Put the following buttons in the next row, otherwise buttons will be laid out horizontally
        firstMenu.nextRow();

        // Buttons also allow you to go to other menus.
        // That way, you can create a tree of submenus. Check the RecipeExample for a nesting example.
        firstMenu.addButton(new GotoButton("Go to Second Menu", secondMenu));

        // You can also chain the functions together, since each returns the menu.

        // Laying the buttons out in this shape:
        //
        //  +-------+-------+
        //  |   1   |   2   |
        //  +-------+-------+
        //  |   3   |   4   |
        //  +-------+-------+
        //  |       5       |
        //  +---------------+

        secondMenu
                .addButton(new GotoButton("Back",firstMenu))
                .addButton(new GotoButton("Also Back",firstMenu))
                .nextRow()
                .addButton(new GotoButton("Still Back",firstMenu))
                .addButton(new GotoButton("Again, back...",firstMenu))
                .nextRow()
                .addButton(new CallbackButton("Send a message, replying to this message", new Callback((bot, callbackQuery, parameters) -> {
                    SendMessage reply = new SendMessage(callbackQuery.getMessage().getChatId().toString(), "This is a reply message");
                    reply.setReplyToMessageId(callbackQuery.getMessage().getMessageId());
                    bot.execute(reply);
                })));

        // Run the test bot with the first menu.
        TestBot.startBotWithMenu(firstMenu);
    }
}
