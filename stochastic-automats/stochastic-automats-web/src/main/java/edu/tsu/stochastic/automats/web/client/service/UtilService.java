package edu.tsu.stochastic.automats.web.client.service;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.sencha.gxt.data.shared.loader.PagingLoadConfig;
import com.sencha.gxt.data.shared.loader.PagingLoadResult;
import edu.tsu.stochastic.automats.web.shared.UserModel;

import java.util.List;

@RemoteServiceRelativePath("utilService")
public interface UtilService extends RemoteService {

    class Util {
        private static UtilServiceAsync instance;

        public static UtilServiceAsync getInstance() {
            if (instance == null) {
                instance = GWT.create(UtilService.class);
            }
            return instance;
        }
    }

    void logout();

    List<String> getUserPermissions();

    PagingLoadResult<UserModel> loadUsers(PagingLoadConfig loadConfig);

    UserModel saveUser(UserModel userModel);
}
