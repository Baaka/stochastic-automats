package edu.tsu.stochastic.automats.core.database.impl;

import edu.tsu.stochastic.automats.core.database.api.FormulaLocal;
import edu.tsu.stochastic.automats.core.database.entity.UzFormula;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Stateless
@Local(FormulaLocal.class)
public class FormulaSession implements FormulaLocal {
    @PersistenceContext
    private EntityManager em;

    public UzFormula saveUzFormula(UzFormula uzFormula) {
        em.persist(uzFormula);
        return uzFormula;
    }

    public void deleteUzFormula(long formulaId) {
        UzFormula uzFormula = em.find(UzFormula.class, formulaId);
        em.remove(uzFormula);
    }

    public List<UzFormula> loadCalculatedUzFormulas(int limit, int offset) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<UzFormula> cq = cb.createQuery(UzFormula.class);
        Root<UzFormula> root = cq.from(UzFormula.class);
        cq.select(root);
        cq.orderBy(cb.desc(root.get("id")));

        TypedQuery<UzFormula> query = em.createQuery(cq);
        if (limit > 0) {
            query.setFirstResult(offset);
            query.setMaxResults(limit);
        }

        return query.getResultList();
    }

    public int getCalculatedUzFormulaCount() {
        return loadCalculatedUzFormulas(-1, -1).size();
    }
}
