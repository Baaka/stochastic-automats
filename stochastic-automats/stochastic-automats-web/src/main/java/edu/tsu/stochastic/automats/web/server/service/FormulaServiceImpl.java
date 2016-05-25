package edu.tsu.stochastic.automats.web.server.service;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.sencha.gxt.data.shared.loader.PagingLoadConfig;
import com.sencha.gxt.data.shared.loader.PagingLoadResult;
import com.sencha.gxt.data.shared.loader.PagingLoadResultBean;
import edu.tsu.stochastic.automats.core.database.entity.UzFormula;
import edu.tsu.stochastic.automats.core.formula.WnFormulaCalculator;
import edu.tsu.stochastic.automats.core.helper.FormulaHelper;
import edu.tsu.stochastic.automats.core.helper.FormulaHelperDbImpl;
import edu.tsu.stochastic.automats.core.model.WnFormulaModel;
import edu.tsu.stochastic.automats.web.client.service.FormulaService;
import edu.tsu.stochastic.automats.web.shared.UzFormulaParamModel;
import edu.tsu.stochastic.automats.web.shared.UzFormulaResultModel;
import edu.tsu.stochastic.automats.web.shared.WnFormulaParamModel;
import edu.tsu.stochastic.automats.web.shared.WnFormulaResultModel;

import java.util.ArrayList;
import java.util.List;

public class FormulaServiceImpl extends RemoteServiceServlet implements FormulaService {

    @Override
    public UzFormulaResultModel calculateUzFormula(UzFormulaParamModel uzFormulaParamModel) {
        FormulaHelper helper = new FormulaHelperDbImpl();
        UzFormula uzFormula = helper.calculateUzFormula(uzFormulaParamModel.toUzFormulaCodeParamModel());

        return new UzFormulaResultModel().setEntity(uzFormula);
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
        List<UzFormulaResultModel> rr = new ArrayList<>();
        UzFormulaResultModel m = new UzFormulaResultModel();
        m.setId(1);
        m.setParamAlpha(12);
        m.setP(123);

        UzFormulaResultModel m1 = new UzFormulaResultModel();
        m.setId(2);
        m.setParamAlpha(2312);
        m.setP(12323);

        rr.add(m);
        rr.add(m1);

        return new PagingLoadResultBean<>(rr, rr.size(), loadConfig.getOffset());
    }
}
