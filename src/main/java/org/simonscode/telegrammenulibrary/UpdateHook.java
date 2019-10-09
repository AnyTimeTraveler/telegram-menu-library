package org.simonscode.telegrammenulibrary;

import org.apache.commons.codec.binary.Base64;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.bots.AbsSender;

import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

public class UpdateHook {
    private static char delimiter = ':';
    private static long index = 0;
    private static final Object ACTIONS_LOCK = new Object();
    private static final Map<String, CallbackAction> actions = new HashMap<>();

    public static void onUpdateReceived(AbsSender bot, Update update) {
        if (update.hasCallbackQuery()) {
            CallbackQuery callbackQuery = update.getCallbackQuery();
            String data = callbackQuery.getData();
            int index = data.indexOf(delimiter);
            if (index == -1) {
                return;
            }
            synchronized (ACTIONS_LOCK) {
                CallbackAction action = actions.get(data.substring(0, index));
                if (action != null) {
                    action.execute(bot, callbackQuery);
                }
            }
        }
    }

    void register(String id, CallbackAction action) {
        synchronized (ACTIONS_LOCK) {
            actions.put(id, action);
        }
    }

    public static synchronized String generateCallbackId() {
        return Base64.encodeBase64String(ByteBuffer.allocate(4).putLong(index++).array());
    }

    public static void setDelimiter(char delimiter) {
        UpdateHook.delimiter = delimiter;
    }
}
