package edu.tsu.stochastic.automats.core.database.auth.util;

import org.jboss.logging.Logger;
import org.jboss.security.util.MBeanServerLocator;

import javax.management.MBeanServer;
import javax.management.ObjectName;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SecurityUtil {

    public static String generateGlobalJndiName(InitialContext initialContext, Class<?> bean, Class<?> local) throws NamingException {
        String appName = (String) initialContext.lookup("java:app/AppName");
        String beanSimpleName = bean.getSimpleName();
        String beanName = local.getName();
        return "java:global" + "/" + appName + "/" + beanSimpleName + "!" + beanName;
    }

    public static String encodePassword(String pass) {
        String encodedPass = null;
        if (pass != null) {
            byte[] password = pass.getBytes();
            MessageDigest md = null;
            try {
                md = MessageDigest.getInstance("SHA-1");
            } catch (NoSuchAlgorithmException e) {
                Logger.getLogger("StochAutomatsLoginModule").error(e.getMessage(), e);
            }
            password = md.digest(password);

            StringBuffer str = new StringBuffer();
            for (int i = 0; i < password.length; i++) {
                str.append(Integer.toHexString((password[i] & 0xf0) >> 4));
                str.append(Integer.toHexString(password[i] & 0x0f));
            }
            encodedPass = str.toString();
        }
        return encodedPass;
    }

    public static void flushAuthCache(String login) {
        Logger log = Logger.getLogger(login);
        try {
            MBeanServer server = MBeanServerLocator.locateJBoss();
            server.invoke(new ObjectName(
                            "jboss.as.expr:subsystem=security,security-domain=StochAutomatsSecurityDomain"),
                    "flushCache",
                    new String[]{login}, new String[]{"java.lang.String"});
            log.info("Flush auth cache:" + login);
        } catch (Exception ex) {
            log.warn("Error flushing authenticate cache", ex);
        }
    }
}

