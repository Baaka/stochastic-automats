package edu.tsu.stochastic.automats.web.server.service;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import edu.tsu.stochastic.automats.core.formula.UzFormulaCalculator;
import edu.tsu.stochastic.automats.core.formula.WnFormulaCalculator;
import edu.tsu.stochastic.automats.core.model.WnFormulaModel;
import edu.tsu.stochastic.automats.web.client.service.FormulaService;
import edu.tsu.stochastic.automats.web.shared.UzFormulaParamModel;
import edu.tsu.stochastic.automats.web.shared.UzFormulaResultModel;
import edu.tsu.stochastic.automats.web.shared.WnFormulaParamModel;
import edu.tsu.stochastic.automats.web.shared.WnFormulaResultModel;

public class FormulaServiceImpl extends RemoteServiceServlet implements FormulaService {

    @Override
    public UzFormulaResultModel calculateUzFormula(UzFormulaParamModel uzFormulaParamModel) {
        UzFormulaCalculator calculator = new UzFormulaCalculator(uzFormulaParamModel.toUzFormulaModel());

        UzFormulaResultModel resultModel = new UzFormulaResultModel();
        resultModel.setResult(calculator.getResult());
        resultModel.setR(calculator.getR());
        resultModel.setQ(calculator.getQ());
        resultModel.setP(calculator.getP());

        return resultModel;
    }

    @Override
    public WnFormulaResultModel calculateWnFormula(WnFormulaParamModel wnFormulaParamModel, int maxN) {
        WnFormulaResultModel resultModel = new WnFormulaResultModel();

        WnFormulaModel model = wnFormulaParamModel.toWnFormulaModel();
        WnFormulaCalculator calculator = new WnFormulaCalculator(model);

        resultModel.setP1(calculator.getP1());
        resultModel.setP2(calculator.getP2());
        resultModel.setQ1(calculator.getQ1());
        resultModel.setQ2(calculator.getQ2());

        for (int n = 1; n <= maxN; n++) {
            for (int x = 1; x <= n; x++) {
                resultModel.addResult(n, x, calculator.getResult(n, x));
            }
        }

        return resultModel;
    }
}
