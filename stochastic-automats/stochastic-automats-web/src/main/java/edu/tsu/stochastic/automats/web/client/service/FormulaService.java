package edu.tsu.stochastic.automats.web.client.service;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.sencha.gxt.data.shared.loader.PagingLoadConfig;
import com.sencha.gxt.data.shared.loader.PagingLoadResult;
import edu.tsu.stochastic.automats.web.shared.UzFormulaParamModel;
import edu.tsu.stochastic.automats.web.shared.UzFormulaResultModel;
import edu.tsu.stochastic.automats.web.shared.WnFormulaParamModel;
import edu.tsu.stochastic.automats.web.shared.WnFormulaResultModel;

import java.util.List;
import java.util.Map;

@RemoteServiceRelativePath("formulaService")
public interface FormulaService extends RemoteService {

    class Util {
        private static FormulaServiceAsync instance;

        private Util() {
            instance = GWT.create(FormulaService.class);
        }

        public static FormulaServiceAsync getInstance() {
            if (instance == null) {
                new Util();
            }
            return instance;
        }
    }

    UzFormulaResultModel calculateUzFormula(UzFormulaParamModel uzFormulaParamModel);

    void deleteCalculatedUzFormula(List<UzFormulaResultModel> formulas);

    WnFormulaResultModel calculateWnFormula(WnFormulaParamModel wnFormulaParamModel, int maxN);

    PagingLoadResult<UzFormulaResultModel> loadCalculatedUzFormulas(PagingLoadConfig loadConfig);

    Map<String,Integer> getUzFormulaCount();
}
