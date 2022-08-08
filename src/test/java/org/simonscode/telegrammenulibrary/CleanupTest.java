package org.simonscode.telegrammenulibrary;

import org.simonscode.telegrammenulibrary.buttons.GotoButton;
import org.simonscode.telegrammenulibrary.callbacks.Callback;
import org.simonscode.telegrammenulibrary.menus.SimpleMenu;
import org.simonscode.telegrammenulibrary.menus.VerticalMenu;

import java.lang.reflect.Field;
import java.util.HashMap;


public class CleanupTest {
    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException {
        Field actionsField = UpdateHook.class.getDeclaredField("actions");
        actionsField.setAccessible(true);
        HashMap<String, Callback> actions = (HashMap<String, Callback>) actionsField.get(null);
        System.out.println(actions.size());

        VerticalMenu recipeList = new VerticalMenu("Recipe List");
        SimpleMenu recipe1 = new VerticalMenu("Recipe 1\nStep 1. Be awesome!\nStep 2. ...");
        SimpleMenu recipe2 = new VerticalMenu("Recipe 2\nStep 1. Be cool!\nStep 2. ...");
        SimpleMenu recipe3 = new VerticalMenu("Recipe 3\nStep 1. Be fun!\nStep 2. ...");

        recipeList.addButton(new GotoButton("Goto Recipe 1",  recipe1));
        recipeList.addButton(new GotoButton("Goto Recipe 2",  recipe2));
        recipeList.addButton(new GotoButton("Goto Recipe 3",  recipe3));

        recipe1.addButton(new GotoButton("Back",  recipeList));
        recipe2.addButton(new GotoButton("Back",  recipeList));
        recipe3.addButton(new GotoButton("Back",  recipeList));

        System.out.println(actions.size());
        recipeList.unregister(false);
        System.out.println(actions.size());
        recipe2.unregister(false);
        System.out.println(actions.size());
        recipe3.unregister(false);
        System.out.println(actions.size());
        recipe1.unregister(false);
        System.out.println(actions.size());
    }
}
