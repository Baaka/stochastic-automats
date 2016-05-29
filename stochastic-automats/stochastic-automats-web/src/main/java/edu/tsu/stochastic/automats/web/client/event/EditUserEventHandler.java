package edu.tsu.stochastic.automats.web.client.event;

import com.google.gwt.event.shared.EventHandler;
import edu.tsu.stochastic.automats.web.client.presenter.AdminPresenter;
import edu.tsu.stochastic.automats.web.shared.UserModel;

public interface EditUserEventHandler extends EventHandler {
    void onEdit(UserModel userModel, AdminPresenter.Display parent);
}
