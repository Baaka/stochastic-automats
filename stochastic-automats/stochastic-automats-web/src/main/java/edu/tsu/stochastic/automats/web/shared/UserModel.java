package edu.tsu.stochastic.automats.web.shared;

import com.google.gwt.core.shared.GwtIncompatible;
import edu.tsu.stochastic.automats.core.database.auth.entity.User;

import java.io.Serializable;
import java.util.Date;

public class UserModel implements Serializable {
    private long id;
    private String login;
    private String password;
    private boolean changePassword;
    private String phone;
    private String email;
    private boolean blocked;
    private Date lastLoginDate;
    private Date lastPasswirdChangeDate;

    public UserModel() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isChangePassword() {
        return changePassword;
    }

    public void setChangePassword(boolean changePassword) {
        this.changePassword = changePassword;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isBlocked() {
        return blocked;
    }

    public void setBlocked(boolean blocked) {
        this.blocked = blocked;
    }

    public Date getLastLoginDate() {
        return lastLoginDate;
    }

    public void setLastLoginDate(Date lastLoginDate) {
        this.lastLoginDate = lastLoginDate;
    }

    public Date getLastPasswirdChangeDate() {
        return lastPasswirdChangeDate;
    }

    public void setLastPasswirdChangeDate(Date lastPasswirdChangeDate) {
        this.lastPasswirdChangeDate = lastPasswirdChangeDate;
    }

    @GwtIncompatible
    public UserModel setEntity(User user) {
        if (user != null) {
            this.id = user.getId();
            this.login = user.getLogin();
            this.password = user.getPassword();
            this.changePassword = user.getChangePassword();
            this.phone = user.getPhone();
            this.email = user.getEmail();
            this.blocked = user.getBlocked();
            this.lastLoginDate = user.getLastLoginDate();
            this.lastPasswirdChangeDate = user.getLastPasswirdChangeDate();
        }
        return this;
    }

    @GwtIncompatible
    public User toEntity() {
        User user = new User();
        user.setId(this.id);
        user.setLogin(this.login);
        user.setPassword(this.password);
        user.setChangePassword(this.changePassword);
        user.setPassword(this.phone);
        user.setEmail(this.email);
        user.setBlocked(this.blocked);
        user.setLastLoginDate(this.lastLoginDate);
        user.setLastPasswirdChangeDate(this.lastPasswirdChangeDate);

        return user;
    }
}
