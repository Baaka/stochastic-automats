package edu.tsu.stochastic.automats.web.client.presenter;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.IsWidget;
import com.sencha.gxt.widget.core.client.box.AlertMessageBox;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import edu.tsu.stochastic.automats.web.client.AppController;
import edu.tsu.stochastic.automats.web.client.service.UtilService;
import edu.tsu.stochastic.automats.web.shared.UserModel;

public class EditUserPresenter implements Presenter {

    public interface Display extends IsWidget {
        SelectEvent.HasSelectHandlers getSaveButton();

        SelectEvent.HasSelectHandlers getCancelButton();

        boolean isValid();

        HasValue<String> getLogin();

        HasValue<String> getPassword();

        HasValue<String> getEmail();

        HasValue<String> getPhone();

        HasValue<Boolean> getBlock();

        void close();

        void setMask(boolean isMask);
    }

    private final Display display;
    private final UserModel userModel;
    private final AdminPresenter.Display parentDisplay;

    public EditUserPresenter(Display display, UserModel userModel, AdminPresenter.Display parentDisplay) {
        this.display = display;
        this.userModel = userModel;
        this.parentDisplay = parentDisplay;
    }

    private void bind() {
        init();

        if (display.getCancelButton() != null) {
            display.getCancelButton().addSelectHandler(new SelectEvent.SelectHandler() {
                @Override
                public void onSelect(SelectEvent event) {
                    display.close();
                }
            });
        }

        if (display.getSaveButton() != null) {
            display.getSaveButton().addSelectHandler(new SelectEvent.SelectHandler() {
                @Override
                public void onSelect(SelectEvent event) {
                    if (display.isValid()) {
                        save();
                    } else {
                        new AlertMessageBox("Warning", "Please fill fields correctly.").show();
                    }
                }
            });
        }
    }

    private void save() {
        display.setMask(true);
        userModel.setLogin(display.getLogin().getValue());
        userModel.setPassword(display.getPassword().getValue());
        userModel.setEmail(display.getEmail().getValue());
        userModel.setPhone(display.getPhone().getValue());
        userModel.setBlocked(display.getBlock().getValue());

        UtilService.Util.getInstance().saveUser(userModel, new AsyncCallback<UserModel>() {
            @Override
            public void onFailure(Throwable caught) {
                display.setMask(false);
                AppController.errorHandler.onError(caught);
            }

            @Override
            public void onSuccess(UserModel result) {
                if (userModel.getId() > 0) {
                    parentDisplay.editModel(result);
                } else {
                    parentDisplay.addModel(result);
                }
                display.setMask(false);
                display.close();
            }
        });
    }

    private void init() {
        display.getLogin().setValue(userModel.getLogin());
        display.getPassword().setValue(userModel.getPassword());
        display.getEmail().setValue(userModel.getEmail());
        display.getPhone().setValue(userModel.getPhone());
        display.getBlock().setValue(userModel.isBlocked());
    }

    @Override
    public void go(HasWidgets container) {
        if (container != null) {
            container.add(display.asWidget());
            return;
        }
        display.asWidget();
        bind();
    }
}
