package edu.tsu.stochastic.automats.core.database.auth.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@SuppressWarnings("serial")
@Entity(name = "STOCH_USER_PASSWORDS")
@Table(name = "STOCH_USER_PASSWORDS")
@IdClass(UserPasswordId.class)
@NamedQueries({@NamedQuery(name = "loadUserPasswordsByUserId", query = "select up from STOCH_USER_PASSWORDS as up where up.user.id=:userId order by up.storedate DESC")})
public class UserPassword implements Serializable {
    @Id
    @ManyToOne
    @JoinColumn(name = "USERID")
    private User user;

    @Column(name = "PASSWORD", length = 40)
    private String password;

    @Id
    @Column(name = "STOREDATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date storedate;

    public UserPassword() {
    }

    public UserPassword(String password) {
        this.password = password;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getStoredate() {
        return storedate;
    }

    public void setStoredate(Date storedate) {
        this.storedate = storedate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        UserPassword that = (UserPassword) o;

        if (password != null ? !password.equals(that.password) : that.password != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        return password != null ? password.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "UserPassword{" +
                "password='" + password + '\'' +
                ", storedate=" + storedate +
                '}';
    }
}


