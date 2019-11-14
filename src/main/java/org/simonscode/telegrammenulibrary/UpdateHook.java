package org.simonscode.telegrammenulibrary;

import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.bots.AbsSender;

import java.nio.ByteBuffer;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

public class UpdateHook {
    private static long index = 0;
    private static final Object ACTIONS_LOCK = new Object();
    private static final HashMap<String, CallbackAction> actions = new HashMap<>();

    /**
     * This method looks if the first 12 characters of the CallbackData match a registered Callback and if they do, executes it.
     * @return true if a callback was executed, false if not
     */
    public static boolean onUpdateReceived(AbsSender bot, Update update) {
        if (update.hasCallbackQuery()) {
            CallbackQuery callbackQuery = update.getCallbackQuery();
            String callbackId = callbackQuery.getData().substring(0, 12);

            synchronized (ACTIONS_LOCK) {
                CallbackAction action = actions.get(callbackId);
                if (action != null) {
                    action.execute(bot, callbackQuery);
                    return true;
                }
            }
        }
        return false;
    }

    public static String getCallbackData(CallbackAction action) {
        synchronized (ACTIONS_LOCK) {
            for (Map.Entry<String, CallbackAction> entry : actions.entrySet()) {
                if (action == entry.getValue()) {
                    return entry.getKey();
                }
            }
            String key = generateCallbackId();
            actions.put(key, action);
            return key;
        }
    }

    public void register(String id, CallbackAction action) {
        synchronized (ACTIONS_LOCK) {
            actions.put(id, action);
        }
    }

    public void register(CallbackAction action) {
        synchronized (ACTIONS_LOCK) {
            actions.put(generateCallbackId(), action);
        }
    }

    public void unregister(String id) {
        synchronized (ACTIONS_LOCK) {
            actions.remove(id);
        }
    }

    public void unregister(CallbackAction action) {
        synchronized (ACTIONS_LOCK) {
            String key = null;
            for (Map.Entry<String, CallbackAction> entry : actions.entrySet()) {
                if (entry.getValue() == action) {
                    key = entry.getKey();
                    break;
                }
            }
            if (key != null) {
                actions.remove(key);
            }
        }
    }

    public static synchronized String generateCallbackId() {
        return Base64.getEncoder().encodeToString(ByteBuffer.allocate(8).putLong(index++).array());
    }
}
