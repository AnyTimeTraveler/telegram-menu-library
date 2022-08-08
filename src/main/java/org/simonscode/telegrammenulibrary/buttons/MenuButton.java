package org.simonscode.telegrammenulibrary.buttons;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

/**
 * A menu button that can be converted into a {@link InlineKeyboardButton}.
 */
public interface MenuButton {
    /**
     * Generates the {@link InlineKeyboardButton} object that is to be used in the Telegram API.
     * @return generated {@link InlineKeyboardButton} that can be sent to the Telegram API
     */
    InlineKeyboardButton generateInlineKeyboardButton();
}
