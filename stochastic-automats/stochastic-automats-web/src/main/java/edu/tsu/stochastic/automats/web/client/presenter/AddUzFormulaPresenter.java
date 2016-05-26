package edu.tsu.stochastic.automats.web.client.presenter;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.IsWidget;
import com.sencha.gxt.widget.core.client.box.AlertMessageBox;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import edu.tsu.stochastic.automats.web.client.AppController;
import edu.tsu.stochastic.automats.web.client.service.FormulaService;
import edu.tsu.stochastic.automats.web.shared.UzFormulaParamModel;
import edu.tsu.stochastic.automats.web.shared.UzFormulaResultModel;

public class AddUzFormulaPresenter implements Presenter {
    private final Display display;
    private final UzFormulaPresenter.Display uzFormulaDisplay;

    public interface Display extends IsWidget {
        SelectEvent.HasSelectHandlers getSaveButton();

        SelectEvent.HasSelectHandlers getCancelButton();

        UzFormulaParamModel getParamModel();

        boolean isValid();

        void close();

        void mask(boolean isMask);
    }

    public AddUzFormulaPresenter(Display display, UzFormulaPresenter.Display uzFormulaDisplay) {
        this.display = display;
        this.uzFormulaDisplay = uzFormulaDisplay;
    }

    private void bind() {
        if (display.getSaveButton() != null) {
            display.getSaveButton().addSelectHandler(new SelectEvent.SelectHandler() {
                @Override
                public void onSelect(SelectEvent event) {
                    display.mask(true);
                    if (display.isValid()) {
                        save();
                    } else {
                        display.mask(false);
                        new AlertMessageBox("Warning", "Please fill all required fields!").show();
                    }
                }
            });
        }

        if (display.getCancelButton() != null) {
            display.getCancelButton().addSelectHandler(new SelectEvent.SelectHandler() {
                @Override
                public void onSelect(SelectEvent event) {
                    display.close();
                }
            });
        }
    }

    private void save() {
        UzFormulaParamModel model = display.getParamModel();

        FormulaService.Util.getInstance().calculateUzFormula(model, new AsyncCallback<UzFormulaResultModel>() {
            @Override
            public void onFailure(Throwable caught) {
                display.mask(false);
                AppController.errorHandler.onError(caught);
            }

            @Override
            public void onSuccess(UzFormulaResultModel result) {
                uzFormulaDisplay.addModel(result);
                display.close();
            }
        });
    }

    @Override
    public void go(HasWidgets container) {
        if (container != null) {
            container.add(display.asWidget());
            return;
        }
        display.asWidget();
        bind();
    }
}
