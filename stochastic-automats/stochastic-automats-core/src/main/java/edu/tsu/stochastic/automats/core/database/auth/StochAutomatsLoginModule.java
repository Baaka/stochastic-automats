package edu.tsu.stochastic.automats.core.database.auth;

import edu.tsu.stochastic.automats.core.database.auth.api.AuthorizationLocal;
import edu.tsu.stochastic.automats.core.database.auth.impl.AuthorizationSession;
import edu.tsu.stochastic.automats.core.database.auth.util.SecurityUtil;
import org.jboss.security.SimpleGroup;
import org.jboss.security.SimplePrincipal;
import org.jboss.security.auth.spi.UsernamePasswordLoginModule;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.security.auth.login.LoginException;
import java.security.acl.Group;
import java.util.List;

public class StochAutomatsLoginModule extends UsernamePasswordLoginModule {
    @Override
    protected Group[] getRoleSets() throws LoginException {
        // The declarative permissions
        Group roles = new SimpleGroup("Roles");
        // The caller identity
        Group callerPrincipal = new SimpleGroup("CallerPrincipal");
        Group[] groups = {roles, callerPrincipal};
        List<String> permissions = getAuthorizationLocal().loadUserPermission(super.getUsername());
        for (String permission : permissions) {
            SimplePrincipal role = new SimplePrincipal(permission);
            roles.addMember(role);
        }

        return groups;
    }

    @Override
    protected boolean validatePassword(String inputPassword, String expectedPassword) {
        inputPassword = SecurityUtil.encodePassword(inputPassword);
        return super.validatePassword(inputPassword, expectedPassword);
    }

    @Override
    protected String getUsersPassword() throws LoginException {
        return getAuthorizationLocal().findByLoginPassword(super.getUsername(), SecurityUtil.encodePassword(super.getUsernameAndPassword()[1]));
    }


    private AuthorizationLocal getAuthorizationLocal() throws LoginException {
        try {
            InitialContext ic = new InitialContext();
            Object object = ic.lookup(SecurityUtil.generateGlobalJndiName(ic, AuthorizationSession.class, AuthorizationLocal.class));
            AuthorizationLocal authorizationLocal = (AuthorizationLocal) object;
            return authorizationLocal;
        } catch (NamingException e) {
            log.error(e.getMessage(), e);
            throw new LoginException(e.toString(true));
        }
    }
}
