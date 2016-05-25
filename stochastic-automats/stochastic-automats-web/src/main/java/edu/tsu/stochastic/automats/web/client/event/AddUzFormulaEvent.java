package edu.tsu.stochastic.automats.web.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class AddUzFormulaEvent extends GwtEvent<AddUzFormulaEventHandler> {
    public static final Type<AddUzFormulaEventHandler> TYPE = new Type<>();

    @Override
    public Type<AddUzFormulaEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(AddUzFormulaEventHandler handler) {
        handler.onAction();
    }
}
