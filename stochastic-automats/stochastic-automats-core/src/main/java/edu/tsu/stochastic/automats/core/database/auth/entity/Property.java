package edu.tsu.stochastic.automats.core.database.auth.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity(name = "STOCH_PROPERTIES")
@Table(name = "STOCH_PROPERTIES")
public class Property implements Serializable {

    @Id
    @Column(name = "PROP_KEY", length = 64, nullable = false, unique = true)
    private String propKey;

    @Column(name = "VALUE", length = 256)
    private String value;

    public String getPropKey() {
        return propKey;
    }

    public void setPropKey(String propKey) {
        this.propKey = propKey;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
