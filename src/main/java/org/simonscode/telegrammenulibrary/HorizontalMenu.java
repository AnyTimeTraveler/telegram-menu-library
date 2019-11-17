package org.simonscode.telegrammenulibrary;

public class HorizontalMenu extends SimpleMenu {

    /**
     * Add a button to the menu in a horizontal row.
     *
     * @param buttonText text shown on the button
     * @param action     action to execute when the user clicks the button
     * @return itself for chaining
     */
    @Override
    public HorizontalMenu addButton(String buttonText, CallbackAction action) {
        super.addButton(buttonText, action);
        return this;
    }
}
