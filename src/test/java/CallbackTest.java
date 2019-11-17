
import org.simonscode.telegrammenulibrary.*;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.nio.ByteBuffer;
import java.util.Base64;

public class CallbackTest {
    public static void main(String[] args) {
        System.out.println(Base64.getEncoder().encodeToString(ByteBuffer.allocate(8).putLong(0).array()));
        System.out.println(Base64.getEncoder().encodeToString(ByteBuffer.allocate(8).putLong(Long.MIN_VALUE).array()).substring(0,12));
        GotoCallback subMenuOneCallback = new GotoCallback();
        SimpleMenu subMenuTwo = new VerticalMenu();
        SimpleMenu mainMenu = new SimpleMenu()
                .setText("Main Menu")
                .addButton("SubMenu 1", subMenuOneCallback)
                .nextLine()
                .addButton("SubMenu 2", new GotoCallback(subMenuTwo))
                .nextLine()
                .addButton("Send a message", CallbackTest::sendMessage)
                .nextLine()
                .addButton("Small", (a, b) -> System.out.println("Small"))
                .addButton("buttons", (a, b) -> System.out.println("buttons"))
                .addButton("in", (a, b) -> System.out.println("in"))
                .addButton("one", (a, b) -> System.out.println("one"))
                .addButton("row.", (a, b) -> System.out.println("row."));

        SimpleMenu subMenuOne = new HorizontalMenu()
                .setText("Sub Menu 1\n" +
                        "As you can see, the buttons here are laid out horizontally.")
                .addButton("Send something to the console", (a, b) -> System.out.println("Hi from Telegram!"))
                .addButton("Go Back", new GotoCallback(mainMenu));

        // Populate a second submenu, this one has a
        subMenuTwo.setText("Sub Menu 2\n" +
                "The buttons here are all in a vertical column.");
        subMenuTwo.addButton("Go to SubMenu 1", subMenuOneCallback);
        subMenuTwo.addButton("Also go to SubMenu 1", new GotoCallback(subMenuOne));
        subMenuTwo.addButton("Go Back", new GotoCallback(mainMenu));

        // Add callback destinations later, when the menu that you want to go to doesn't exist, yet.
        subMenuOneCallback.setTargetMenu(subMenuOne);
        bot.execute(mainMenu.generateSendMessage().setChatId())
    }

    private static void sendMessage(AbsSender bot, CallbackQuery callbackQuery) {
        try {
            bot.execute(new SendMessage()
                    .setText("Hello from the Main menu!")
                    .setChatId(callbackQuery.getMessage().getChatId()));
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
