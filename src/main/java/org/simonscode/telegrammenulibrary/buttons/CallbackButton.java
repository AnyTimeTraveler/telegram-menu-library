package org.simonscode.telegrammenulibrary.buttons;

import org.simonscode.telegrammenulibrary.callbacks.Callback;
import org.simonscode.telegrammenulibrary.callbacks.CallbackFunction;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

/**
 * A menu button that executes a callback when pressed.
 */
public class CallbackButton implements MenuButton {
    private final String buttonText;
    private final String callbackId;
    private final String parameters;

    /**
     * Creates a new CallbackButton.
     * You can reuse the same function for multiple buttons, but you need to provide different parameters for each button.
     * @param buttonText The text of the button
     * @param function The callback function to be called when the button is pressed
     * @param parameters The parameters to be passed to the callback function
     */
    public CallbackButton(String buttonText, CallbackFunction function, String parameters) {
        this(buttonText, new Callback(function).getCallbackId(), parameters);
    }

    /**
     * Creates a new CallbackButton.
     * @param buttonText The text of the button
     * @param function The callback function to be called when the button is pressed
     */
    public CallbackButton(String buttonText, CallbackFunction function) {
        this(buttonText, new Callback(function).getCallbackId(), "");
    }

    /**
     * Creates a new CallbackButton.
     * @param buttonText The text of the button
     * @param callback The callback object to be called when the button is pressed
     */
    public CallbackButton(String buttonText, Callback callback) {
        this(buttonText, callback.getCallbackId(), "");
    }

    /**
     * Creates a new CallbackButton.
     * @param buttonText The text of the button
     * @param callback The callback object to be called when the button is pressed
     * @param parameters The parameters to be passed to the callback function
     */
    public CallbackButton(String buttonText, Callback callback, String parameters) {
        this(buttonText, callback.getCallbackId(), parameters);
    }

    /**
     * Creates a new CallbackButton.
     * @param buttonText The text of the button
     * @param callbackId The callback id of the callback that is to be called when the button is pressed
     */
    public CallbackButton(String buttonText, String callbackId) {
        this(buttonText, callbackId, "");
    }

    /**
     * Creates a new CallbackButton.
     * @param buttonText The text of the button
     * @param callbackId The callback id of the callback that is to be called when the button is pressed
     * @param parameters The parameters to be passed to the callback function
     */
    public CallbackButton(String buttonText, String callbackId, String parameters) {
        this.buttonText = buttonText;
        this.callbackId = callbackId;
        this.parameters = parameters;
    }

    /**
     * Gets the callback id.
     * @return the callback id
     */
    public String getCallbackId() {
        return callbackId;
    }

    /**
     * Generate the telegram compatible button for use in a telegram message.
     * @return telegram compatible button
     */
    @Override
    public InlineKeyboardButton generateInlineKeyboardButton() {
        return InlineKeyboardButton.builder()
                .text(buttonText)
                .callbackData(callbackId + parameters)
                .build();
    }

    /**
     * Generates a string representation of this callback button.
     * @return string representation of the callback button
     */
    @Override
    public String toString() {
        return "CallbackButton{" +
                "buttonText='" + buttonText + '\'' +
                ", callbackId='" + callbackId + '\'' +
                ", parameters='" + parameters + '\'' +
                '}';
    }
}
