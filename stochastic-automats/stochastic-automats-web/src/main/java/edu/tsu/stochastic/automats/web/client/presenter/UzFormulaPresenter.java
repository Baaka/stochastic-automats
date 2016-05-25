package edu.tsu.stochastic.automats.web.client.presenter;

import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.IsWidget;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.info.Info;
import edu.tsu.stochastic.automats.web.client.AppController;
import edu.tsu.stochastic.automats.web.client.event.AddUzFormulaEvent;
import edu.tsu.stochastic.automats.web.shared.UzFormulaResultModel;

public class UzFormulaPresenter implements Presenter {

    private final Display display;

    public UzFormulaPresenter(Display display) {
        this.display = display;
    }

    public interface Display extends IsWidget {
        SelectEvent.HasSelectHandlers getFilterButton();

        SelectEvent.HasSelectHandlers getAddButton();

        SelectEvent.HasSelectHandlers getEditButton();

        SelectEvent.HasSelectHandlers getDeleteButton();

        SelectEvent.HasSelectHandlers getExportButton();

        SelectEvent.HasSelectHandlers getImportButton();

        void addModel(UzFormulaResultModel model);

        void updateModel(UzFormulaResultModel model);

        //UzFormulaParamModel getModel();

        //void appendResult(String result);
    }

    private void todo() {
        Info.display("//TODO", "//TODO");
    }

    private void bind() {
        if (display.getFilterButton() != null) {
            display.getFilterButton().addSelectHandler(new SelectEvent.SelectHandler() {
                @Override
                public void onSelect(SelectEvent event) {
                    todo();
                }
            });
        }

        if (display.getAddButton() != null) {
            display.getAddButton().addSelectHandler(new SelectEvent.SelectHandler() {
                @Override
                public void onSelect(SelectEvent event) {
                    AppController.eventBus.fireEvent(new AddUzFormulaEvent());
                }
            });
        }

        if (display.getEditButton() != null) {
            display.getEditButton().addSelectHandler(new SelectEvent.SelectHandler() {
                @Override
                public void onSelect(SelectEvent event) {
                    todo();
                }
            });
        }

        if (display.getDeleteButton() != null) {
            display.getDeleteButton().addSelectHandler(new SelectEvent.SelectHandler() {
                @Override
                public void onSelect(SelectEvent event) {
                    todo();
                }
            });
        }

        if (display.getExportButton() != null) {
            display.getExportButton().addSelectHandler(new SelectEvent.SelectHandler() {
                @Override
                public void onSelect(SelectEvent event) {
                    todo();
                }
            });
        }

        if (display.getImportButton() != null) {
            display.getImportButton().addSelectHandler(new SelectEvent.SelectHandler() {
                @Override
                public void onSelect(SelectEvent event) {
                    todo();
                }
            });
        }
    }

    /*private void calculateUzFormula(final UzFormulaParamModel model) {
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
    }*/

    public Display getDisplay() {
        return display;
    }

    @Override
    public void go(HasWidgets container) {
        if (container != null) {
            container.add(display.asWidget());
        }
        bind();
    }
}
