package edu.tsu.stochastic.automats.core.database.api;

import edu.tsu.stochastic.automats.core.database.entity.UzFormula;

import java.util.List;

public interface FormulaLocal {
    UzFormula saveUzFormula(UzFormula uzFormula);

    void saveUzFormulas(List<UzFormula> formulas);

    void deleteUzFormula(long formulaId);

    List<UzFormula> loadCalculatedUzFormulas(int limit, int offset);

    int getCalculatedUzFormulaCount();
}
