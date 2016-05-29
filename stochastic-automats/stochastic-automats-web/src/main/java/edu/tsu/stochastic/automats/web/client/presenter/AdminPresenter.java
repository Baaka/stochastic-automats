package edu.tsu.stochastic.automats.web.client.presenter;

import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.IsWidget;
import com.sencha.gxt.widget.core.client.box.AlertMessageBox;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.info.Info;
import edu.tsu.stochastic.automats.web.client.AppController;
import edu.tsu.stochastic.automats.web.client.event.EditUserEvent;
import edu.tsu.stochastic.automats.web.shared.UserModel;

public class AdminPresenter implements Presenter {

    private final Display display;

    public interface Display extends IsWidget {

        SelectEvent.HasSelectHandlers getAddUserButton();

        SelectEvent.HasSelectHandlers getEditUserButton();

        SelectEvent.HasSelectHandlers getDeleteUserButton();

        SelectEvent.HasSelectHandlers getRefreshButton();

        SelectEvent.HasSelectHandlers getPermissionsButton();

        void addModel(UserModel userModel);

        void editModel(UserModel userModel);

        void refresh();

        UserModel getSelectedModel();

    }

    public AdminPresenter(Display display) {
        this.display = display;
    }

    private void todo() {
        Info.display("//TODO", "//TODO");
    }

    private void bind() {

        if (display.getAddUserButton() != null) {
            display.getAddUserButton().addSelectHandler(new SelectEvent.SelectHandler() {
                @Override
                public void onSelect(SelectEvent event) {
                    UserModel userModel = new UserModel();
                    editUser(userModel);
                }
            });
        }

        if (display.getEditUserButton() != null) {
            display.getEditUserButton().addSelectHandler(new SelectEvent.SelectHandler() {
                @Override
                public void onSelect(SelectEvent event) {
                    UserModel userModel = display.getSelectedModel();
                    if (userModel != null) {
                        editUser(userModel);
                    } else {
                        new AlertMessageBox("Warning", "Please select the user you want to edit.").show();
                    }
                }
            });
        }

        if (display.getDeleteUserButton() != null) {
            display.getDeleteUserButton().addSelectHandler(new SelectEvent.SelectHandler() {
                @Override
                public void onSelect(SelectEvent event) {
                     todo();
                }
            });
        }

        if (display.getPermissionsButton() != null) {
            display.getPermissionsButton().addSelectHandler(new SelectEvent.SelectHandler() {
                @Override
                public void onSelect(SelectEvent event) {
                    todo();
                }
            });
        }

        if (display.getRefreshButton() != null) {
            display.getRefreshButton().addSelectHandler(new SelectEvent.SelectHandler() {
                @Override
                public void onSelect(SelectEvent event) {
                    display.refresh();
                }
            });
        }

    }

    private void editUser(UserModel userModel) {
        AppController.eventBus.fireEvent(new EditUserEvent(userModel, display));
    }

    public Display getDisplay() {
        return display;
    }

    @Override
    public void go(HasWidgets container) {
        if (container != null) {
            container.add(display.asWidget());
        }
        bind();
    }
}
