package edu.tsu.stochastic.automats.web.client.presenter;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.IsWidget;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.info.Info;
import edu.tsu.stochastic.automats.web.client.AppController;
import edu.tsu.stochastic.automats.web.client.service.FormulaService;
import edu.tsu.stochastic.automats.web.shared.WnFormulaParamModel;
import edu.tsu.stochastic.automats.web.shared.WnFormulaResultModel;

import java.util.Map;

public class WnFormulaPresenter implements Presenter {

    private final Display display;

    public WnFormulaPresenter(Display display) {
        this.display = display;
    }

    public interface Display extends IsWidget {
        SelectEvent.HasSelectHandlers getCalculateButton();

        boolean isValid();

        WnFormulaParamModel getModel();

        void setResult(String result);

        HasValue<Integer> getMaxN();
    }

    private void bind() {
        if (display.getCalculateButton() != null) {
            display.getCalculateButton().addSelectHandler(new SelectEvent.SelectHandler() {
                @Override
                public void onSelect(SelectEvent event) {
                    if (display.isValid()) {
                        WnFormulaParamModel model = display.getModel();
                        int maxN = display.getMaxN().getValue();
                        calculateWnFormula(model, maxN);
                    } else {
                        Info.display("Warning", "Fill fields correctly");
                    }
                }
            });
        }
    }

    private void calculateWnFormula(final WnFormulaParamModel model, final int maxN) {
        FormulaService.Util.getInstance().calculateWnFormula(model, maxN, new AsyncCallback<WnFormulaResultModel>() {
            @Override
            public void onFailure(Throwable caught) {
                AppController.errorHandler.onError(caught);
            }

            @Override
            public void onSuccess(WnFormulaResultModel result) {
                StringBuilder sb = new StringBuilder();
                sb.append("r1 = ").append(model.getR1())
                        .append("; r2 = ").append(model.getR2())
                        .append("; a1 = ").append(model.getA1())
                        .append("; a2 = ").append(model.getA2())
                        .append("; e1 = ").append(model.getE1())
                        .append("; e2 = ").append(model.getE2())
                        .append("; z1 = ").append(model.getZ1())
                        .append("; z2 = ").append(model.getZ2())
                        .append("; n = ").append(maxN).append(";\n")
                        .append("P1 = ").append(result.getP1())
                        .append("; Q1 = ").append(result.getQ1())
                        .append("; P2 = ").append(result.getP2())
                        .append("; Q2 = ").append(result.getQ2())
                        .append("\n\n");
                for (Map.Entry<Integer, Map<Integer, Double>> entry : result.getResultStore().entrySet()) {
                    sb.append("=========================================")
                            .append(" n = ").append(entry.getKey()).append("\n");
                    for (Map.Entry<Integer, Double> e : entry.getValue().entrySet()) {
                        sb.append("x = " + e.getKey()).append("; result = " + e.getValue()).append("\n");
                    }
                }
                sb.append("\n\n");
                display.setResult(sb.toString());
            }
        });
    }

    @Override
    public void go(HasWidgets container) {
        if (container != null) {
            container.add(display.asWidget());
        }
        bind();
    }
}
