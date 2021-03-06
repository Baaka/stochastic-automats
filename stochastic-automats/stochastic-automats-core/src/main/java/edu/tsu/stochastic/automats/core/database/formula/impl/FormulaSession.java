package edu.tsu.stochastic.automats.core.database.formula.impl;

import edu.tsu.stochastic.automats.core.database.auth.api.AuthorizationLocal;
import edu.tsu.stochastic.automats.core.database.formula.api.FormulaLocal;
import edu.tsu.stochastic.automats.core.database.formula.entity.UzFormula;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Stateless
@Local(FormulaLocal.class)
public class FormulaSession implements FormulaLocal {
    @PersistenceContext
    private EntityManager em;
    @EJB
    private AuthorizationLocal authorizationLocal;

    public UzFormula saveUzFormula(UzFormula uzFormula) {
        em.persist(uzFormula);
        return uzFormula;
    }

    public void saveUzFormulas(List<UzFormula> formulas) {
        for (UzFormula formula : formulas) {
            saveUzFormula(formula);
        }
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

    public Map<String, Integer> getUzFormulaCountByUser() {
        Map<String, Integer> result = new HashMap<String, Integer>();
        Map<Long, String> usersMap = authorizationLocal.getUsersMap();
        for (Map.Entry<Long, String> entry : usersMap.entrySet()) {
            result.put(entry.getValue(), 0);
        }
        List<UzFormula> uzCalcs = loadCalculatedUzFormulas(-1, -1);
        for (UzFormula formula : uzCalcs) {
            String login = usersMap.get(formula.getCreatorId());
            int count = (result.get(login) + 1);
            result.put(login, count);
        }
        return result;
    }
}
