package org.simonscode.telegrammenulibrary.menus;

import org.simonscode.telegrammenulibrary.buttons.MenuButton;

import java.util.List;

/**
 * A VerticalMenu is a Menu that aligns the buttons vertically.
 */
public class VerticalMenu extends SimpleMenu {

    /**
     * Creates a new Menu where all buttons are vertically stacked.
     */
    public VerticalMenu() {
        super();
    }

    /**
     * Creates a new Menu where all buttons are vertically stacked.
     *
     * @param text text to show in the message of the menu
     */
    public VerticalMenu(String text) {
        super(text);
    }

    /**
     * Creates a new Menu where all buttons are vertically stacked.
     *
     * @param text text to show in the message of the menu
     * @param markup buttons below the menu message
     */
    public VerticalMenu(String text, List<MenuButton> markup) {
        super(text);
        for (MenuButton menuButton : markup) {
            nextRow();
            addButton(menuButton);
        }
    }

    /**
     * Add a button to the menu in the current horizontal row.
     *
     * @param button button to be added
     * @return itself for chaining
     */
    public VerticalMenu addButton(MenuButton button) {
        super.addButton(button);
        super.nextRow();
        return this;
    }
}
