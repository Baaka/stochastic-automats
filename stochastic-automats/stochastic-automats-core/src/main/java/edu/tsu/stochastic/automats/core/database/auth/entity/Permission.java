package edu.tsu.stochastic.automats.core.database.auth.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity(name = "STOCH_PERMISSIONS")
@Table(name = "STOCH_PERMISSIONS")
public class Permission implements Serializable {

    @Id
    @Column(name = "ID")
    private long id;

    @Column(name = "IDNAME", length = 80, unique = true, nullable = false)
    private String idName;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getIdName() {
        return idName;
    }

    public void setIdName(String idName) {
        this.idName = idName;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((idName == null) ? 0 : idName.hashCode());
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
        Permission other = (Permission) obj;
        if (idName == null) {
            if (other.idName != null)
                return false;
        } else if (!idName.equals(other.idName))
            return false;
        return true;
    }
}

