package edu.tsu.stochastic.automats.web.shared;

import com.google.gwt.editor.client.Editor;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;

import java.util.Date;

public interface UserModelProperties extends PropertyAccess<UserModel> {
    @Editor.Path("id")
    ModelKeyProvider<UserModel> key();

    ValueProvider<UserModel, String> login();

    ValueProvider<UserModel, String> phone();

    ValueProvider<UserModel, String> email();

    ValueProvider<UserModel, Boolean> blocked();

    ValueProvider<UserModel, Date> lastLoginDate();
}
