package edu.tsu.stochastic.automats.web.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.FormElement;
import com.google.gwt.dom.client.InputElement;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.HasWidgets;
import edu.tsu.stochastic.automats.web.client.error.ErrorHandler;
import edu.tsu.stochastic.automats.web.client.event.*;
import edu.tsu.stochastic.automats.web.client.presenter.*;
import edu.tsu.stochastic.automats.web.shared.ExportFormat;
import edu.tsu.stochastic.automats.web.shared.Formula;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AppController implements Presenter {
    public static HandlerManager eventBus;
    public static ClientFactory clientFactory;
    public static ErrorHandler errorHandler;
    public static Map<Formula, Presenter> appFormulasStore = new HashMap<>();

    public AppController(ClientFactory clientFactory) {
        AppController.clientFactory = clientFactory;
        AppController.eventBus = clientFactory.getEventBus();
        AppController.errorHandler = clientFactory.getErrorHandler();

        initAppFormulaPresenters();

        bind();
    }

    private void initAppFormulaPresenters() {
        for (Formula function : Formula.values()) {
            switch (function) {
                case WN_FUNCTION:
                    appFormulasStore.put(function, new WnFormulaPresenter(AppController.clientFactory.getWnFormulaDisplay()));
                    break;
                case UZ_FUNCTION:
                    appFormulasStore.put(function, new UzFormulaPresenter(AppController.clientFactory.getUzFormulaDisplay()));
                    break;
                case HDJ_FUNCTION:
                    break;
            }
        }
    }

    private void bind() {
        AppController.eventBus.addHandler(FormulaResultExportEvent.TYPE, new FormulaResultExportEventHandler() {
            @Override
            public void onExport(Formula formula, String result) {
                String url = GWT.getHostPageBaseURL() + "formulaResultExportServlet";
                final FormPanel form = new FormPanel();
                form.setAction(url);
                form.setMethod(FormPanel.METHOD_POST);
                FormElement formElement = FormElement.as(form.getElement());

                InputElement menuInput = Document.get().createHiddenInputElement();
                menuInput.setName("formulaResult");
                menuInput.setValue(result);
                formElement.appendChild(menuInput);

                Document.get().getBody().appendChild(formElement);
                formElement.submit();
            }
        });

        AppController.eventBus.addHandler(ResultExportEvent.TYPE, new ResultExportEventHandler() {
            @Override
            public void onExport(List<Long> ids, Formula formula, ExportFormat exportFormat) {
                String url = GWT.getHostPageBaseURL() + "resultExportServlet";
                final FormPanel form = new FormPanel();
                form.setAction(url);
                form.setMethod(FormPanel.METHOD_POST);
                FormElement formElement = FormElement.as(form.getElement());

                InputElement formulaElement = Document.get().createHiddenInputElement();
                formulaElement.setName("formula");
                formulaElement.setValue(formula.ordinal() + "");
                formElement.appendChild(formulaElement);

                InputElement exportElement = Document.get().createHiddenInputElement();
                exportElement.setName("exportFormat");
                exportElement.setValue(exportFormat.ordinal() + "");
                formElement.appendChild(exportElement);

                StringBuilder sb = new StringBuilder();
                for (Long id : ids) {
                    sb.append(id)
                            .append(",");
                }

                InputElement idsElement = Document.get().createHiddenInputElement();
                idsElement.setName("ids");
                idsElement.setValue(sb.toString());
                formElement.appendChild(idsElement);

                Document.get().getBody().appendChild(formElement);
                formElement.submit();
            }
        });

        AppController.eventBus.addHandler(AddUzFormulaEvent.TYPE, new AddUzFormulaEventHandler() {
            @Override
            public void onAction() {
                Presenter presenter = new AddUzFormulaPresenter(clientFactory.getAddUzFormulaDisplay(), ((UzFormulaPresenter) appFormulasStore.get(Formula.UZ_FUNCTION)).getDisplay());
                presenter.go(null);
            }
        });

        AppController.eventBus.addHandler(ImportFormulaEvent.TYPE, new ImportFormulaEventHandler() {
            @Override
            public void onImport(Formula formula) {
                Presenter presenter = null;
                switch (formula) {
                    case WN_FUNCTION:
                        break;
                    case UZ_FUNCTION:
                        presenter = new FileImportPresenter(clientFactory.getImportFormulaDisplay(), formula, (UzFormulaPresenter) appFormulasStore.get(formula));
                        break;
                    case HDJ_FUNCTION:
                        break;
                }
                presenter.go(null);
            }
        });
    }

    @Override
    public void go(HasWidgets container) {
        Presenter presenter = new AppFramePresenter(clientFactory.getAppFrameDisplay());
        presenter.go(container);
    }
}
