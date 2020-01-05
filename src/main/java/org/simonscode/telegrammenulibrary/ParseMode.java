package org.simonscode.telegrammenulibrary;

public enum ParseMode {
    PLAINTEXT(null),
    HTML("html"),
    MARKDOWN("Markdown");

    private final String value;

    ParseMode(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
