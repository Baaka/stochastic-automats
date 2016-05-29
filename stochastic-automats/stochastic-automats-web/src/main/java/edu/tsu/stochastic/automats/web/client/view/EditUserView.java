package edu.tsu.stochastic.automats.web.client.view;

import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.widget.core.client.Window;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.BoxLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.form.CheckBox;
import com.sencha.gxt.widget.core.client.form.FieldLabel;
import com.sencha.gxt.widget.core.client.form.PasswordField;
import com.sencha.gxt.widget.core.client.form.TextField;
import edu.tsu.stochastic.automats.web.client.presenter.EditUserPresenter;

public class EditUserView implements EditUserPresenter.Display {

    private void init() {
        saveButton = new TextButton("Save");
        cancelButton = new TextButton("Cancel");

        loginField = new TextField();
        phoneField = new TextField();
        emailField = new TextField();
        passwordField = new PasswordField();
        passwordField1 = new PasswordField();
        blockCheckBox = new CheckBox();

        FieldLabel loginFieldLabel = new FieldLabel(loginField, "Login");
        FieldLabel phoneFieldLabel = new FieldLabel(phoneField, "Phone");
        FieldLabel emailFieldLabel = new FieldLabel(emailField, "Email");
        FieldLabel passwordFieldLabel = new FieldLabel(passwordField, "Password");
        FieldLabel password1FieldLabel = new FieldLabel(passwordField1, "Password");
        FieldLabel blockFieldLabel = new FieldLabel(blockCheckBox, "Block");

        VerticalLayoutContainer.VerticalLayoutData vd = new VerticalLayoutContainer.VerticalLayoutData(1, -1, new Margins(5));

        VerticalLayoutContainer vc = new VerticalLayoutContainer();
        vc.add(loginFieldLabel, vd);
        vc.add(passwordFieldLabel, vd);
        vc.add(password1FieldLabel, vd);
        vc.add(phoneFieldLabel, vd);
        vc.add(emailFieldLabel, vd);
        vc.add(blockFieldLabel, vd);

        window = new Window();
        window.setHeadingHtml("<center>User Manager</center>");
        window.setWidth("350px");
        window.setHeight("350px");
        window.add(vc);
        window.setButtonAlign(BoxLayoutContainer.BoxLayoutPack.CENTER);
        window.getButtonBar().add(saveButton);
        window.getButtonBar().add(cancelButton);
        window.setModal(true);
    }

    @Override
    public SelectEvent.HasSelectHandlers getSaveButton() {
        return saveButton;
    }

    @Override
    public SelectEvent.HasSelectHandlers getCancelButton() {
        return cancelButton;
    }

    @Override
    public boolean isValid() {
        return true;
    }

    @Override
    public HasValue<String> getLogin() {
        return loginField;
    }

    @Override
    public HasValue<String> getPassword() {
        return passwordField;
    }

    @Override
    public HasValue<String> getEmail() {
        return emailField;
    }

    @Override
    public HasValue<String> getPhone() {
        return phoneField;
    }

    @Override
    public HasValue<Boolean> getBlock() {
        return blockCheckBox;
    }

    @Override
    public void close() {
        window.clear();
        window.hide();
    }

    @Override
    public void setMask(boolean isMask) {
        if (isMask) {
            window.mask("Please wait...");
            return;
        }
        window.unmask();
    }

    @Override
    public Widget asWidget() {
        init();
        window.show();
        return window;
    }

    private TextField loginField;
    private TextField phoneField;
    private TextField emailField;
    private PasswordField passwordField;
    private PasswordField passwordField1;
    private CheckBox blockCheckBox;
    private TextButton saveButton;
    private TextButton cancelButton;
    private Window window;
}
