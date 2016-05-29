package edu.tsu.stochastic.automats.web.server.service;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.sencha.gxt.data.shared.loader.PagingLoadConfig;
import com.sencha.gxt.data.shared.loader.PagingLoadResult;
import com.sencha.gxt.data.shared.loader.PagingLoadResultBean;
import edu.tsu.stochastic.automats.core.database.auth.api.AuthorizationLocal;
import edu.tsu.stochastic.automats.core.database.auth.entity.User;
import edu.tsu.stochastic.automats.core.database.auth.util.SecurityUtil;
import edu.tsu.stochastic.automats.web.client.service.UtilService;
import edu.tsu.stochastic.automats.web.shared.UserModel;

import javax.ejb.EJB;
import java.util.ArrayList;
import java.util.List;

public class UtilServiceImpl extends RemoteServiceServlet implements UtilService {

    @EJB
    private AuthorizationLocal authorizationLocal;

    @Override
    public void logout() {
        SecurityUtil.flushAuthCache(getThreadLocalRequest().getRemoteUser());
        getThreadLocalRequest().getSession().invalidate();
    }

    @Override
    public List<String> getUserPermissions() {
        String login = getThreadLocalRequest().getRemoteUser();
        return authorizationLocal.loadUserPermission(login);
    }

    @Override
    public PagingLoadResult<UserModel> loadUsers(PagingLoadConfig loadConfig) {
        List<User> users = authorizationLocal.loadUsers(loadConfig.getLimit(), loadConfig.getOffset());
        List<UserModel> models = new ArrayList<>();
        for (User user : users) {
            models.add(new UserModel().setEntity(user));
        }
        int count = authorizationLocal.getUserCount();

        return new PagingLoadResultBean<>(models, count, loadConfig.getOffset());
    }

    @Override
    public UserModel saveUser(UserModel userModel) {
        User user = authorizationLocal.saveUser(userModel.toEntity());
        return new UserModel().setEntity(user);
    }
}
