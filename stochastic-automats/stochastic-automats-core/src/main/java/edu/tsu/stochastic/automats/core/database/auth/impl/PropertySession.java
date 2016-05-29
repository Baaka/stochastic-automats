package edu.tsu.stochastic.automats.core.database.auth.impl;

import edu.tsu.stochastic.automats.core.database.auth.api.PropertyLocal;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Stateless
@Local(PropertyLocal.class)
public class PropertySession implements PropertyLocal {
    @PersistenceContext
    private EntityManager em;

    public String getSystemProperty(String key) {
        TypedQuery<String> query = em.createQuery("select c.value from STOCH_PROPERTIES c where c.propKey=:key", String.class);
        query.setParameter("key", key);
        List<String> values = query.getResultList();
        String value = null;
        for (String string : values) {
            value = string;
        }
        return value;
    }
}
