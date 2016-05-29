package edu.tsu.stochastic.automats.core.database.auth.api;

import edu.tsu.stochastic.automats.core.database.auth.entity.User;
import edu.tsu.stochastic.automats.core.database.auth.util.PasswordChangeStatus;

import java.util.List;
import java.util.Map;

public interface AuthorizationLocal {
    String findByLoginPassword(String username, String password);

    User findUserByLogin(String login);

    User findUserById(long id);

    Map<Long,String> getUsersMap();

    boolean checkUserExists(String login, String password);

    List<String> loadUserPermission(String login);

    PasswordChangeStatus changePassword(String login, String oldPassword, String newPassword);

    User saveUser(User user);

    List<User> loadUsers(int limit, int offset);

    int getUserCount();
}
