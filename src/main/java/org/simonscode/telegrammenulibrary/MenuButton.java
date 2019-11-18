package org.simonscode.telegrammenulibrary;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

public interface MenuButton {
    InlineKeyboardButton generateInlineKeyboardButton();
}
