package edu.tsu.stochastic.automats.web.client.presenter;

import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.IsWidget;
import com.sencha.gxt.widget.core.client.box.AlertMessageBox;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SubmitCompleteEvent;
import com.sencha.gxt.widget.core.client.form.FormPanel;
import edu.tsu.stochastic.automats.web.shared.Formula;

public class FileImportPresenter implements Presenter {
    public interface Display extends IsWidget {
        HasValue<Formula> getSelectedFormula();

        SelectEvent.HasSelectHandlers getSubmitButton();

        SelectEvent.HasSelectHandlers getCancelButton();

        void close();

        void mask(boolean isMask);

        void submit();

        boolean isValid();

        FormPanel getForm();
    }

    private final Display display;
    private final Formula formulaToImport;
    private Presenter parentPresenter;

    public FileImportPresenter(Display display, Formula formulaToImport, Presenter parentPresenter) {
        this.display = display;
        this.formulaToImport = formulaToImport;
        this.parentPresenter = parentPresenter;
    }

    private void bind() {
        display.getSelectedFormula().setValue(formulaToImport);

        if (display.getSubmitButton() != null) {
            display.getSubmitButton().addSelectHandler(new SelectEvent.SelectHandler() {
                @Override
                public void onSelect(SelectEvent event) {
                    if (display.isValid()) {
                        display.mask(true);
                        display.submit();
                    } else {
                        new AlertMessageBox("Warning", "Please fill all fields!").show();
                    }
                }
            });
        }

        if (display.getForm() != null) {
            display.getForm().addSubmitCompleteHandler(new SubmitCompleteEvent.SubmitCompleteHandler() {
                @Override
                public void onSubmitComplete(SubmitCompleteEvent event) {
                    display.mask(false);
                    display.close();
                    switch (formulaToImport) {
                        case WN_FUNCTION:
                            break;
                        case UZ_FUNCTION:
                            UzFormulaPresenter.Display parentDisplay = ((UzFormulaPresenter) parentPresenter).getDisplay();
                            parentDisplay.refresh();
                            break;
                        case HDJ_FUNCTION:
                            break;
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

    public Display getDisplay() {
        return display;
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
