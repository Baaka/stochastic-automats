package edu.tsu.stochastic.automats.web.server.service;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.sencha.gxt.data.shared.loader.PagingLoadConfig;
import com.sencha.gxt.data.shared.loader.PagingLoadResult;
import com.sencha.gxt.data.shared.loader.PagingLoadResultBean;
import edu.tsu.stochastic.automats.core.database.auth.api.AuthorizationLocal;
import edu.tsu.stochastic.automats.core.database.auth.entity.User;
import edu.tsu.stochastic.automats.core.database.formula.api.FormulaLocal;
import edu.tsu.stochastic.automats.core.database.formula.entity.UzFormula;
import edu.tsu.stochastic.automats.core.formula.UzFormulaCalculator;
import edu.tsu.stochastic.automats.core.formula.WnFormulaCalculator;
import edu.tsu.stochastic.automats.core.model.WnFormulaModel;
import edu.tsu.stochastic.automats.web.client.service.FormulaService;
import edu.tsu.stochastic.automats.web.shared.UzFormulaParamModel;
import edu.tsu.stochastic.automats.web.shared.UzFormulaResultModel;
import edu.tsu.stochastic.automats.web.shared.WnFormulaParamModel;
import edu.tsu.stochastic.automats.web.shared.WnFormulaResultModel;

import javax.ejb.EJB;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FormulaServiceImpl extends RemoteServiceServlet implements FormulaService {
    @EJB
    private FormulaLocal formulaLocal;
    @EJB
    private AuthorizationLocal authorizationLocal;

    @Override
    public UzFormulaResultModel calculateUzFormula(UzFormulaParamModel uzFormulaParamModel) {
        User currUser = authorizationLocal.findUserByLogin(getThreadLocalRequest().getUserPrincipal().getName());

        UzFormulaCalculator calculator = new UzFormulaCalculator(uzFormulaParamModel.toUzFormulaCodeParamModel());
        UzFormula uzFormula = calculator.getCalculatedUzFormula();
        uzFormula.setCreatorId(currUser.getId());
        uzFormula = formulaLocal.saveUzFormula(calculator.getCalculatedUzFormula());
        return new UzFormulaResultModel().setEntity(uzFormula);
    }

    @Override
    public void deleteCalculatedUzFormula(List<UzFormulaResultModel> formulas) {
        for (UzFormulaResultModel model : formulas) {
            formulaLocal.deleteUzFormula(model.getId());
        }
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

    @Override
    public PagingLoadResult<UzFormulaResultModel> loadCalculatedUzFormulas(PagingLoadConfig loadConfig) {
        List<UzFormula> formulas = formulaLocal.loadCalculatedUzFormulas(loadConfig.getLimit(), loadConfig.getOffset());

        List<UzFormulaResultModel> resultModels = new ArrayList<>();
        for (UzFormula formula : formulas) {
            resultModels.add(new UzFormulaResultModel().setEntity(formula));
        }

        int count = formulaLocal.getCalculatedUzFormulaCount();

        return new PagingLoadResultBean<>(resultModels, count, loadConfig.getOffset());
    }

    @Override
    public Map<String, Integer> getUzFormulaCount() {
        return formulaLocal.getUzFormulaCountByUser();
    }
}
