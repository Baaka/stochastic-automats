package edu.tsu.stochastic.automats.core.database.auth.impl;

import edu.tsu.stochastic.automats.core.database.auth.api.AuthorizationLocal;
import edu.tsu.stochastic.automats.core.database.auth.api.PropertyLocal;
import edu.tsu.stochastic.automats.core.database.auth.entity.Property;
import edu.tsu.stochastic.automats.core.database.auth.entity.User;
import edu.tsu.stochastic.automats.core.database.auth.entity.UserPassword;
import edu.tsu.stochastic.automats.core.database.auth.util.PasswordChangeStatus;
import edu.tsu.stochastic.automats.core.database.auth.util.SecurityUtil;
import edu.tsu.stochastic.automats.core.util.AppConstants;
import org.jboss.logging.Logger;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.math.BigDecimal;
import java.util.*;

@Stateless
@Local(AuthorizationLocal.class)
public class AuthorizationSession implements AuthorizationLocal {

    private Logger log = Logger.getLogger(getClass());

    private static final Map<String, Integer> loginErrors = Collections.synchronizedMap(new HashMap<String, Integer>());

    @PersistenceContext
    private EntityManager em;

    @EJB
    private PropertyLocal propertyLocal;

    private int getSysPropertyValue(String key) {
        Query query = em.createQuery("select c.value from " + Property.class.getName() + " c where c.propKey=:key").setParameter("key", key);
        return Integer.parseInt(query.getSingleResult().toString());
    }

    public String findByLoginPassword(String username, String password) {
        StringBuffer buffer = new StringBuffer();
        buffer.append("select u from STOCH_USERS as u where trim(LOWER(u.login))=:username");

        Query findQuery = em.createQuery(buffer.toString() + " and trim(u.password)=:password");
        findQuery.setParameter("username", username.toLowerCase());
        findQuery.setParameter("password", password);

        User user = null;
        try {
            user = (User) findQuery.getSingleResult();
        } catch (PersistenceException ex) {

            log.error(ex.getMessage(), ex);

            if (blockUser(username)) {
                findQuery = em.createQuery(buffer.toString()).setParameter("username", username);
                @SuppressWarnings("unchecked")
                List<User> users = findQuery.getResultList();
                if (users.size() == 1) {
                    user = em.find(User.class, users.get(0).getId());
                    user.setBlocked(true);
                    user.setLastLoginDate(new Date());
                    em.merge(user);
                }
            }

            return null;
        }

        if (user.getBlocked()) {
            log.info("Login failed. User account is blocked. User name: " + username);
            return null;
        } else {
            int allowedAccountInactivityPeriod = getSysPropertyValue(AppConstants.ALLOWED_ACCOUNT_INACTIVITY_PERIOD);

            Calendar lastLogin = Calendar.getInstance();
            Date lastLoginDate = user.getLastLoginDate();
            if (lastLoginDate == null) {
                lastLoginDate = new Date();
            }
            lastLogin.setTime(lastLoginDate);
            lastLogin.add(Calendar.DAY_OF_YEAR, allowedAccountInactivityPeriod);

            Calendar currTime = Calendar.getInstance();

            try {
                if (!lastLogin.after(currTime) && allowedAccountInactivityPeriod != -1) {
                    user = em.find(User.class, user.getId());
                    user.setBlocked(true);
                    log.info("Account inactivity period is overdue. User name: " + username);
                    return null;
                } else {

                    loginErrors.remove(username);

                    user.setLastLoginDate(new Date());

                    int passValPeriod = getSysPropertyValue(AppConstants.PASSWORD_VALIDITY_PERIOD);

                    Calendar lastPwndChangeDate = Calendar.getInstance();
                    if (user.getLastPasswirdChangeDate() == null) {
                        lastPwndChangeDate.setTime(new Date());
                    } else {
                        lastPwndChangeDate.setTime(user.getLastPasswirdChangeDate());
                    }
                    lastPwndChangeDate.add(Calendar.DAY_OF_YEAR, passValPeriod);

                    if (!lastPwndChangeDate.after(currTime) && passValPeriod != -1) {
                        log.info("Password expired. Forcing user to change password. User name: " + username);
                        user.setChangePassword(true);
                    }

                }
            } finally {
                em.merge(user);
            }
        }

        return user.getPassword();
    }

    private boolean blockUser(String username) {
        Integer loginAttemptNumber = loginErrors.get(username);
        if (loginAttemptNumber == null) {
            loginAttemptNumber = 1;
        } else {
            loginAttemptNumber = loginAttemptNumber + 1;
        }
        loginErrors.put(username, loginAttemptNumber);
        return loginAttemptNumber > getSysPropertyValue(AppConstants.ALLOWED_LOGIN_ATTEMPT_NUMBER);
    }

    public User findUserByLogin(String login) {
        return em.createQuery("select u from STOCH_USERS  u where trim(u.login)=:login", User.class).setParameter("login", login.trim()).getSingleResult();
    }

    public User findUserById(long id) {
        return em.createQuery("select u from STOCH_USERS  u where trim(u.id)=:id", User.class).setParameter("id", id).getSingleResult();
    }

    public Map<Long, String> getUsersMap() {
        Map<Long, String> result = new HashMap<Long, String>();
        List<User> users = loadUsers(-1, -1);
        for (User user : users) {
            result.put(user.getId(), user.getLogin());
        }
        return result;
    }

    public boolean checkUserExists(String login, String password) {
        return em.createQuery("SELECT u FROM STOCH_USERS u WHERE u.login=:login AND u.password=:password", User.class)
                .setParameter("login", login)
                .setParameter("password", SecurityUtil.encodePassword(password))
                .getResultList().size() == 1;
    }

    public List<String> loadUserPermission(String login) {
        String loadUserPermissiosSql =
                "SELECT DISTINCT PERM.ID AS BYUSER " +
                        " FROM STOCH_USERS USRS, " +
                        " STOCH_USER_PERMISSIONS USRPERMS," +
                        " STOCH_PERMISSIONS PERM  " +
                        "WHERE USRS.ID = USRPERMS.USERID  " +
                        " AND USRPERMS.PERMISSIONID = PERM.ID  " +
                        " AND ltrim(rtrim(LOWER(USRS.LOGIN))) =?1";
        Query query = em.createNativeQuery(loadUserPermissiosSql);
        query.setParameter(1, login.trim().toLowerCase());
        List<BigDecimal> userPermIds = query.getResultList();

        List<String> result = new ArrayList<String>();

        if (userPermIds.size() > 0) {
            Query idNamesQuery = em.createQuery("SELECT PERM.idName FROM STOCH_PERMISSIONS PERM WHERE PERM.id IN " + userPermIds.toString().replace('[', '(').replace(']', ')'));
            result = idNamesQuery.getResultList();
        }

        return result;
    }

    public PasswordChangeStatus changePassword(String login, String oldPassword, String newPassword) {
        PasswordChangeStatus status = PasswordChangeStatus.ERROR;

        Query query = em.createQuery("SELECT u from STOCH_USERS u WHERE trim(u.login)=:login and trim(u.password)=:oldPassword ");
        query.setParameter("login", login.trim());
        query.setParameter("oldPassword", oldPassword);

        try {
            User user = (User) query.getSingleResult();

            if (passwordAlreadyUsed(newPassword, user.getId())) {
                status = PasswordChangeStatus.ALREADY_USED;
            } else {

                UserPassword up = new UserPassword();
                up.setPassword(newPassword);
                Date changeDate = new Date();
                up.setStoredate(changeDate);
                up.setUser(user);

                em.persist(up);

                String ejbqlUpdate = "update STOCH_USERS u set u.lastPasswirdChangeDate=:lastPasswirdChangeDate, u.lastLoginDate=:lastLoginDate,u.changePassword=:changePassword, u.password=:password  WHERE trim(u.login)=:login";
                em.createQuery(ejbqlUpdate).setParameter("lastPasswirdChangeDate", changeDate).setParameter("lastLoginDate", changeDate).setParameter("changePassword", false).setParameter("password", newPassword).setParameter("login", login).executeUpdate();

                status = PasswordChangeStatus.SUCCESS;
            }

        } catch (PersistenceException ex) {
            Logger.getLogger(getClass()).error(ex.getMessage(), ex);
        }

        return status;
    }

    public User saveUser(User user) {
        if (user.getId() > 0) {
            em.merge(user);
        } else {
            em.persist(user);
        }
        return user;
    }

    public List<User> loadUsers(int limit, int offset) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<User> cq = cb.createQuery(User.class);
        Root<User> root = cq.from(User.class);
        cq.select(root);
        cq.orderBy(cb.desc(root.get("login")));

        TypedQuery<User> query = em.createQuery(cq);
        if (limit > 0) {
            query.setFirstResult(offset);
            query.setMaxResults(limit);
        }

        return query.getResultList();
    }

    public int getUserCount() {
        return loadUsers(-1, -1).size();
    }

    private boolean passwordAlreadyUsed(String newPassword, long userId) {
        boolean usedBefore = false;

        @SuppressWarnings("unchecked")
        List<UserPassword> userPasswords = em.createNamedQuery("loadUserPasswordsByUserId").setParameter("userId", userId).getResultList();

        int oldStoredPasswordsNumber = Integer.parseInt(propertyLocal.getSystemProperty(AppConstants.OLD_STORED_PASSWORDS_NUMBER));

        for (int i = 0; i < oldStoredPasswordsNumber && i < userPasswords.size(); i++) {
            UserPassword up = userPasswords.get(i);
            if (up.getPassword().trim().equalsIgnoreCase(newPassword.trim())) {
                usedBefore = true;
            }
        }

        return usedBefore;
    }

}
