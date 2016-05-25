package edu.tsu.stochastic.automats.core.helper;

import edu.tsu.stochastic.automats.core.database.api.FormulaLocal;
import edu.tsu.stochastic.automats.core.database.entity.UzFormula;
import edu.tsu.stochastic.automats.core.formula.UzFormulaCalculator;
import edu.tsu.stochastic.automats.core.model.UzFormulaParamModel;

import javax.ejb.EJB;
import java.util.List;

public class FormulaHelperDbImpl implements FormulaHelper {
    @EJB
    private FormulaLocal formulaLocal;

    public UzFormula calculateUzFormula(UzFormulaParamModel uzFormulaParamModel) {
        UzFormulaCalculator calculator = new UzFormulaCalculator(uzFormulaParamModel);

        return formulaLocal.saveUzFormula(calculator.getCalculatedUzFormula());
    }

    public void deleteUzFormula(long formulaId) {
        formulaLocal.deleteUzFormula(formulaId);
    }

    public List<UzFormula> loadCalculatedUzFormulas(int limit, int offset) {
        return formulaLocal.loadCalculatedUzFormulas(limit, offset);
    }
}
