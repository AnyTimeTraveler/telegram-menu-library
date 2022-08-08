package org.simonscode.telegrammenulibrary;

import org.simonscode.telegrammenulibrary.buttons.GotoButton;
import org.simonscode.telegrammenulibrary.menus.SimpleMenu;
import org.simonscode.telegrammenulibrary.menus.VerticalMenu;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class RecipeExample {
    public static void main(String[] args) throws TelegramApiException {
        VerticalMenu recipeList = new VerticalMenu("Recipe List");
        SimpleMenu recipe1 = new VerticalMenu("Recipe 1\n Step 1. Be awesome!\n Step 2. ...");
        SimpleMenu recipe2 = new VerticalMenu("Recipe 2\n Step 1. Be cool!\n Step 2. ...");
        SimpleMenu recipe3 = new VerticalMenu("Recipe 3\n This one is complicated!\nThe Steps are in submenus!");
        SimpleMenu recipe3step1 = new VerticalMenu("Step 1. Be awesome!");
        SimpleMenu recipe3step2 = new VerticalMenu("Step 2. Be cool!");
        SimpleMenu recipe3step3 = new VerticalMenu("Step 3. Done!");

        recipeList.addButton(new GotoButton("Goto Recipe 1", recipe1));
        recipeList.addButton(new GotoButton("Goto Recipe 2", recipe2));
        recipeList.addButton(new GotoButton("Goto Recipe 3", recipe3));

        recipe1.addButton(new GotoButton("Back", recipeList));

        recipe2.addButton(new GotoButton("Back", recipeList));

        recipe3.addButton(new GotoButton("Step 1", recipe3step1));
        recipe3.addButton(new GotoButton("Step 2", recipe3step2));
        recipe3.addButton(new GotoButton("Step 3", recipe3step3));
        recipe3.addButton(new GotoButton("Back", recipeList));

        recipe3step1.addButton(new GotoButton("Back", recipe3));
        recipe3step1.addButton(new GotoButton("Next Step", recipe3step2));

        recipe3step2.addButton(new GotoButton("Back", recipe3));
        recipe3step2.addButton(new GotoButton("Next Step", recipe3step3));

        recipe3step3.addButton(new GotoButton("Back", recipe3));

        // Actually, we don't need recipe 3 anymore.
        recipe3.unregister(false);
        recipe3step1.unregister(false);
        recipe3step2.unregister(false);
        recipe3step3.unregister(false);
        // Now, all callbacks regarding recipe 3 will have been freed.
        // The button "Goto Recipe 3" won't do anything anymore.
        // The other menus will still work.

        // So, let's remove that button:
        recipeList.getMarkup().remove(2);
        // The next time, that menu will be shown, it won't have the button anymore.

        TestBot.startBotWithMenu(recipeList);
    }
}
