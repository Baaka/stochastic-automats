package edu.tsu.stochastic.automats.web.client.event;

import com.google.gwt.event.shared.GwtEvent;
import edu.tsu.stochastic.automats.web.client.presenter.AdminPresenter;
import edu.tsu.stochastic.automats.web.shared.UserModel;

public class EditUserEvent extends GwtEvent<EditUserEventHandler> {
    public static final Type<EditUserEventHandler> TYPE = new Type<>();
    private final UserModel userModel;
    private final AdminPresenter.Display parent;

    public EditUserEvent(UserModel userModel, AdminPresenter.Display parent) {
        this.userModel = userModel;
        this.parent = parent;
    }

    @Override
    public Type<EditUserEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(EditUserEventHandler handler) {
        handler.onEdit(userModel, parent);
    }
}
