package org.simonscode.telegrammenulibrary;

public class VerticalMenu extends SimpleMenu {

    /**
     * Add a button to the menu in a vertical column.
     *
     * @param buttonText text shown on the button
     * @param action     action to execute when the user clicks the button
     * @return itself for chaining
     */
    public VerticalMenu addButton(String buttonText, Callback action) {
        super.addButton(buttonText, action);
        super.nextLine();
        return this;
    }

    /**
     * Add a button to the menu in the current horizontal row.
     *
     * @param button button to be added
     * @return itself for chaining
     */
    public VerticalMenu addButton(MenuButton button) {
        super.addButton(button);
        super.nextLine();
        return this;
    }
}
