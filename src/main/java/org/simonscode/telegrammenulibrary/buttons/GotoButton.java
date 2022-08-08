package org.simonscode.telegrammenulibrary.buttons;

import org.simonscode.telegrammenulibrary.menus.SimpleMenu;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
/**
 * A menu button that executes a callback when pressed.
 */
public class GotoButton implements MenuButton {
    private final String buttonText;
    private final String callbackId;

    /**
     * Creates a new GotoButton, which lets you jump to a different menu.
     * @param buttonText the text of the button
     * @param menu the menu that replaces the current message when the button is pressed
     */
    public GotoButton(String buttonText, SimpleMenu menu) {
        this.buttonText = buttonText;
        this.callbackId = menu.getGotoCallback().getCallbackId();
    }

    /**
     * @return the callback id of this menu
     */
    public String getCallbackId() {
        return callbackId;
    }

    @Override
    public InlineKeyboardButton generateInlineKeyboardButton() {
        return InlineKeyboardButton.builder()
                .text(buttonText)
                .callbackData(callbackId)
                .build();
    }

    @Override
    public String toString() {
        return "GotoButton{" +
                "buttonText='" + buttonText + '\'' +
                ", callbackId='" + callbackId + '\'' +
                '}';
    }
}
