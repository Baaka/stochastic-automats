package edu.tsu.stochastic.automats.web.client.service;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import edu.tsu.stochastic.automats.web.shared.UzFormulaParamModel;
import edu.tsu.stochastic.automats.web.shared.UzFormulaResultModel;
import edu.tsu.stochastic.automats.web.shared.WnFormulaParamModel;
import edu.tsu.stochastic.automats.web.shared.WnFormulaResultModel;

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

    WnFormulaResultModel calculateWnFormula(WnFormulaParamModel wnFormulaParamModel, int maxN);
}
