package org.simonscode.telegrammenulibrary.menus;

import org.simonscode.telegrammenulibrary.buttons.MenuButton;

import java.util.List;

/**
 * A HorizontalMenu is a Menu that aligns the buttons horizontally.
 */
public class HorizontalMenu extends SimpleMenu {

    /**
     * Creates a new Menu where all buttons are in a horizontal row.
     */
    public HorizontalMenu() {
        super();
    }

    /**
     * Creates a new Menu where all buttons are in a horizontal row.
     *
     * @param text text to show in the message of the menu
     */
    public HorizontalMenu(String text) {
        super(text);
    }

    /**
     * Creates a new Menu where all buttons are in a horizontal row.
     *
     * @param text text to show in the message of the menu
     * @param markup buttons below the menu message
     */
    public HorizontalMenu(String text, List<MenuButton> markup) {
        super(text, List.of(markup));
    }

    /**
     * Add a button to the menu in the current horizontal row.
     *
     * @param button button to be added
     * @return itself for chaining
     */
    public HorizontalMenu addButton(MenuButton button) {
        super.addButton(button);
        return this;
    }
}
