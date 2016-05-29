package edu.tsu.stochastic.automats.core.database.formula.api;

import edu.tsu.stochastic.automats.core.database.formula.entity.UzFormula;

import java.util.List;
import java.util.Map;

public interface FormulaLocal {
    UzFormula saveUzFormula(UzFormula uzFormula);

    void saveUzFormulas(List<UzFormula> formulas);

    void deleteUzFormula(long formulaId);

    List<UzFormula> loadCalculatedUzFormulas(int limit, int offset);

    int getCalculatedUzFormulaCount();

    Map<String,Integer> getUzFormulaCountByUser();
}
