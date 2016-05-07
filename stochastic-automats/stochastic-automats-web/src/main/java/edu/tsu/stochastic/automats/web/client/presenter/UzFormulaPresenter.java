package edu.tsu.stochastic.automats.web.client.presenter;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.IsWidget;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.info.Info;
import edu.tsu.stochastic.automats.web.client.AppController;
import edu.tsu.stochastic.automats.web.client.service.FormulaService;
import edu.tsu.stochastic.automats.web.shared.UzFormulaParamModel;
import edu.tsu.stochastic.automats.web.shared.UzFormulaResultModel;

public class UzFormulaPresenter implements Presenter {

    private final Display display;

    public UzFormulaPresenter(Display display) {
        this.display = display;
    }

    public interface Display extends IsWidget {
        SelectEvent.HasSelectHandlers getCalculateButton();

        boolean isValid();

        UzFormulaParamModel getModel();

        void appendResult(String result);
    }

    private void bind() {
        if (display.getCalculateButton() != null) {
            display.getCalculateButton().addSelectHandler(new SelectEvent.SelectHandler() {
                @Override
                public void onSelect(SelectEvent event) {
                    if (display.isValid()) {
                        UzFormulaParamModel model = display.getModel();
                        calculateUzFormula(model);
                    } else {
                        Info.display("Warning", "Fill fields correctly");
                    }
                }
            });
        }
    }

    private void calculateUzFormula(final UzFormulaParamModel model) {
        FormulaService.Util.getInstance().calculateUzFormula(model, new AsyncCallback<UzFormulaResultModel>() {
            @Override
            public void onFailure(Throwable caught) {
                AppController.errorHandler.onError(caught);
            }

            @Override
            public void onSuccess(UzFormulaResultModel result) {
                StringBuilder sb = new StringBuilder();
                sb.append("a = ").append(model.getA()).append("; ")
                        .append("r = ").append(model.getR()).append("; ")
                        .append("m = ").append(model.getM()).append("; ")
                        .append("z = ").append(model.getZ()).append("; ")
                        .append("l = ").append(model.getL()).append("; \n")
                        .append("R = ").append(result.getR()).append("; ")
                        .append("P = ").append(result.getP()).append("; ")
                        .append("Q = ").append(result.getQ()).append("; ")
                        .append("RESULT U(z) = ").append(result.getResult()).append("; \n\n");
                display.appendResult(sb.toString());
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
