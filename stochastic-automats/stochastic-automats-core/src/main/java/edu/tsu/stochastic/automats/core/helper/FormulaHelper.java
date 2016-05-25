package edu.tsu.stochastic.automats.core.helper;

import edu.tsu.stochastic.automats.core.database.entity.UzFormula;
import edu.tsu.stochastic.automats.core.model.UzFormulaParamModel;

import java.util.List;

public interface FormulaHelper {
    UzFormula calculateUzFormula(UzFormulaParamModel uzFormulaParamModel);

    void deleteUzFormula(long formulaId);

    List<UzFormula> loadCalculatedUzFormulas(int limit, int offset);
}
