package edu.tsu.stochastic.automats.web.client.service;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.sencha.gxt.data.shared.loader.PagingLoadConfig;
import com.sencha.gxt.data.shared.loader.PagingLoadResult;
import edu.tsu.stochastic.automats.web.shared.UserModel;

import java.util.List;
import java.util.Map;

public interface UtilServiceAsync {
    void logout(AsyncCallback<Void> async);

    void getUserPermissions(AsyncCallback<List<String>> async);

    void loadUsers(PagingLoadConfig loadConfig, AsyncCallback<PagingLoadResult<UserModel>> async);

    void saveUser(UserModel userModel, AsyncCallback<UserModel> async);
}
