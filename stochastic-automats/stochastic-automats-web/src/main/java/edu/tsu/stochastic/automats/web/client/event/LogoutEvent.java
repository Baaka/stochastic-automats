package edu.tsu.stochastic.automats.web.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class LogoutEvent extends GwtEvent<LogoutEventHandler> {

    public static Type<LogoutEventHandler> TYPE = new Type<>();

    @Override
    public Type<LogoutEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(LogoutEventHandler handler) {
        handler.onAction();
    }
}
