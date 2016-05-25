package edu.tsu.stochastic.automats.core.database.impl;

import edu.tsu.stochastic.automats.core.database.api.FormulaLocal;
import edu.tsu.stochastic.automats.core.database.entity.UzFormula;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;

@Stateless
@Local(FormulaLocal.class)
public class FormulaSession implements FormulaLocal {

    @Inject
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
        return em.createQuery("SELECT uz FROM FORMULA_UZ uz ORDER BY uz.id DESC", UzFormula.class).setFirstResult(offset).setMaxResults(limit).getResultList();
    }
}
