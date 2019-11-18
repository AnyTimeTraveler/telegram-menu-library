package org.simonscode.telegrammenulibrary;

import java.util.LinkedList;

public class HorizontalMenu extends SimpleMenu {

    /**
     * Add a button to the menu in a horizontal row.
     *
     * @param buttonText text shown on the button
     * @param action     action to execute when the user clicks the button
     * @return itself for chaining
     */
    @Override
    public HorizontalMenu addButton(String buttonText, Callback action) {
        super.addButton(buttonText, action);
        return this;
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
