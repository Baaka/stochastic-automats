package edu.tsu.stochastic.automats.web.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.FormElement;
import com.google.gwt.dom.client.InputElement;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.HasWidgets;
import edu.tsu.stochastic.automats.web.client.error.ErrorHandler;
import edu.tsu.stochastic.automats.web.client.event.AddUzFormulaEvent;
import edu.tsu.stochastic.automats.web.client.event.AddUzFormulaEventHandler;
import edu.tsu.stochastic.automats.web.client.event.FormulaResultExportEvent;
import edu.tsu.stochastic.automats.web.client.event.FormulaResultExportEventHandler;
import edu.tsu.stochastic.automats.web.client.presenter.*;
import edu.tsu.stochastic.automats.web.shared.Formula;

import java.util.HashMap;
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

        AppController.eventBus.addHandler(AddUzFormulaEvent.TYPE, new AddUzFormulaEventHandler() {
            @Override
            public void onAction() {
                Presenter presenter = new AddUzFormulaPresenter(clientFactory.getAddUzFormulaDisplay(), ((UzFormulaPresenter) appFormulasStore.get(Formula.UZ_FUNCTION)).getDisplay());
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
