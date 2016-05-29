package edu.tsu.stochastic.automats.core.database.auth.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity(name = "STOCH_USERS")
@Table(name = "STOCH_USERS")
public class User implements Serializable {

    @Id
    @Column(name = "ID")
    private long id;

    @Column(name = "LOGIN", length = 15, unique = true, nullable = false)
    private String login;

    @Column(name = "PASSWORD", length = 40)
    private String password;

    @Column(name = "CHANGEPASSWORD")
    private boolean changePassword;

    @Column(name = "PHONE", length = 40)
    private String phone;

    @Column(name = "EMAIL", length = 40)
    private String email;

    @Column(name = "BLOCKED")
    private boolean blocked;

    @Column(name = "LASTLOGINDATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastLoginDate;

    @Column(name = "LASTPASSWORDCHANGEDATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastPasswirdChangeDate;

    public User() {
    }

    public User(long id, boolean blocked, Date lastLoginDate, Date lastPasswirdChangeDate) {
        this.id = id;
        this.blocked = blocked;
        this.lastLoginDate = lastLoginDate;
        this.lastPasswirdChangeDate = lastPasswirdChangeDate;
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

    public boolean getChangePassword() {
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

    public boolean getBlocked() {
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

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (int) (id ^ (id >>> 32));
        result = prime * result + ((login == null) ? 0 : login.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        User other = (User) obj;
        if (id != other.id)
            return false;
        if (login == null) {
            if (other.login != null)
                return false;
        } else if (!login.equals(other.login))
            return false;
        return true;
    }
}
