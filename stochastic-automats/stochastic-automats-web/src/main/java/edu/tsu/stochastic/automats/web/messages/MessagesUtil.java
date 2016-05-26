package edu.tsu.stochastic.automats.web.messages;

import com.google.gwt.core.client.GWT;

public class MessagesUtil {
    private static Messages messages = GWT.create(Messages.class);
    private static MessagesUtil instance;

    private MessagesUtil() {
    }

    public static MessagesUtil getInstance() {
        if (instance == null)
            instance = new MessagesUtil();
        return instance;
    }

    public String getString(String key) {
        if (key != null) {
            String value = key.replace(".", "_");
            return messages.getString(value);
        }
        return "undefined";
    }
}
