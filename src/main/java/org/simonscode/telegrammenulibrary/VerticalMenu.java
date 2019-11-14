package org.simonscode.telegrammenulibrary;

public class VerticalMenu extends SimpleMenu {
    @Override
    public SimpleMenu addButton(String buttonText, CallbackAction action) {
        super.addButton(buttonText, action);
        return super.nextLine();
    }
}
