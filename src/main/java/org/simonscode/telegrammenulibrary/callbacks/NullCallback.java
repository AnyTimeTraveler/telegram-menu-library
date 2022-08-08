package org.simonscode.telegrammenulibrary.callbacks;

/**
 * A callback that does nothing
 */
public class NullCallback extends Callback {

    private static final CallbackFunction NULL_FUNCTION = (bot, callbackQuery, parameters) -> {
        // do nothing
    };

    /**
     * Creates a new NullCallback
     */
    public NullCallback() {
        super(NULL_FUNCTION);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        return o instanceof NullCallback;
    }
}
