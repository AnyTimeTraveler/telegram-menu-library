package org.simonscode.telegrammenulibrary;

import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.bots.AbsSender;

import java.nio.ByteBuffer;
import java.time.Duration;
import java.util.*;
import java.util.stream.Collectors;

public class UpdateHook {
    private static long index = new Random().nextLong();
    private static long maxLifetime = 0;
    private static final Object ACTIONS_LOCK = new Object();
    private static final HashMap<String, Callback> actions = new HashMap<>();
    private static final HashMap<Callback, String> reverseActions = new HashMap<>();
    private static final HashMap<String, Long> actionLifetime = new HashMap<>();
    private static Timer cleanupTimer = new Timer("cleanupTimer", true);

    /**
     * This method looks if the first 12 characters of the CallbackData match a registered Callback and if they do, executes it.
     * Call this in the onUpdateReceived method inside your bot.
     * <p>
     * Can handle up to Long.MAX_VALUE amount of callbacks, but will use quite a bit of memory, since the
     *
     * @return true if a callback was executed, false if not
     */
    public static boolean onUpdateReceived(AbsSender bot, Update update) {
        if (update.hasCallbackQuery()) {
            CallbackQuery callbackQuery = update.getCallbackQuery();
            String callbackId = callbackQuery.getData().substring(0, 12);

            synchronized (ACTIONS_LOCK) {
                Callback action = actions.get(callbackId);
                if (action != null) {
                    action.execute(bot, callbackQuery);
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Register a callback with your own custom id.
     * The UpdateHook will only check the first 12 characters of your callback data.
     * If that isn't enough for you, then I recommend letting it generate an id for you with the other register method.
     *
     * @param id     the callback data you want to check against
     * @param action action to be called when an update with the callback id arrives
     */
    public static void register(String id, Callback action) {
        synchronized (ACTIONS_LOCK) {
            actions.put(id, action);
            actionLifetime.put(id, System.currentTimeMillis());
            reverseActions.put(action, id);
        }
    }

    /**
     * Register a callback.
     * The UpdateHook will generate a 12 character id for your action.
     * You can append your own characters to the callback id without preventing its proper lookup.
     * So if you want to store extra data in your callback, just append it to the callback id when sending the inline callback.
     *
     * @param action action to be called when an update with the correct callback id arrives
     * @return callbackId the callback id that your action was registered with, in case you want to do something with it
     */
    public static String register(Callback action) {
        synchronized (ACTIONS_LOCK) {
            if (reverseActions.containsKey(action)) {
                return reverseActions.get(action);
            }
            String key = generateCallbackId();
            actions.put(key, action);
            actionLifetime.put(key, System.currentTimeMillis());
            reverseActions.put(action, key);
            return key;
        }
    }

    /**
     * Remove a callback from the update hook by its id.
     * <p>
     * This will remove a callback and free up its memory.
     */
    public static void unregister(String id) {
        synchronized (ACTIONS_LOCK) {
            Callback removedAction = actions.remove(id);
            actionLifetime.remove(id);
            reverseActions.remove(removedAction);
        }
    }

    /**
     * Remove a callback from the update hook.
     * <p>
     * This will remove a callback and free up its memory.
     */
    public static void unregister(Callback action) {
        synchronized (ACTIONS_LOCK) {
            String key = reverseActions.remove(action);
            if (key != null) {
                actions.remove(key);
                actionLifetime.remove(key);
            }
        }
    }

    /**
     * Clears all memory.
     * <p>
     * This will cause all menus to stop working, by clearing them all from memory.
     * So call this if you're short on memory.
     *
     * @return amount of callbacks removed
     */
    public static long clearCallbacks() {
        synchronized (ACTIONS_LOCK) {
            long amount = actions.size();
            actions.clear();
            reverseActions.clear();
            actionLifetime.clear();
            return amount;
        }
    }

    /**
     * Removes all menus that are older than a certain amount of milliseconds.
     *
     * @param millis maximum age of a callback
     * @return amount of callbacks removed
     */
    public static long removeCallbacksOlderThan(long millis) {
        long lifetime = System.currentTimeMillis() - millis;
        synchronized (ACTIONS_LOCK) {
            return actionLifetime.entrySet()
                    .stream()
                    .filter(e -> e.getValue() < lifetime)
                    .map(e -> reverseActions.remove(actions.remove(e.getKey())))
                    .collect(Collectors.toList())
                    .stream()
                    .map(actionLifetime::remove)
                    .count();
        }
    }

    /**
     * Generates the next unique identifier for a callback.
     * This does not register it anywhere, so you can use this to register custom functions with the register method.
     *
     * @return unique callbackId
     */
    public static synchronized String generateCallbackId() {
        return Base64.getEncoder().encodeToString(ByteBuffer.allocate(8).putLong(index++).array());
    }

    /**
     * If you want to save memory, this will start a task that will automatically remove menus of a certain age from the list.
     * This is recommended.
     *
     * @param checkInterval the interval at which to check for callbacks, which are over the maxLifetime
     * @param maxLifetime the maximum lifetime of any callback
     */
    public static void setCleanupSchedule(Duration checkInterval, Duration maxLifetime) {
        UpdateHook.maxLifetime = maxLifetime.toMillis();
        cleanupTimer.cancel();
        cleanupTimer = new Timer("cleanupTimer", true);
        long cleanupInterval = checkInterval.toMillis();
        cleanupTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                removeCallbacksOlderThan(UpdateHook.maxLifetime);
            }
        }, cleanupInterval, cleanupInterval);
    }
}
