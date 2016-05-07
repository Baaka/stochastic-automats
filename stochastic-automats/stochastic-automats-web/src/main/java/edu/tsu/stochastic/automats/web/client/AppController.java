package edu.tsu.stochastic.automats.web.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.FormElement;
import com.google.gwt.dom.client.InputElement;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.HasWidgets;
import edu.tsu.stochastic.automats.web.client.error.ErrorHandler;
import edu.tsu.stochastic.automats.web.client.event.FormulaResultExportEvent;
import edu.tsu.stochastic.automats.web.client.event.FormulaResultExportEventHandler;
import edu.tsu.stochastic.automats.web.client.presenter.AppFramePresenter;
import edu.tsu.stochastic.automats.web.client.presenter.Presenter;
import edu.tsu.stochastic.automats.web.shared.Formula;

public class AppController implements Presenter {
    public static HandlerManager eventBus;
    public static ClientFactory clientFactory;
    public static ErrorHandler errorHandler;

    public AppController(ClientFactory clientFactory) {
        AppController.clientFactory = clientFactory;
        AppController.eventBus = clientFactory.getEventBus();
        AppController.errorHandler = clientFactory.getErrorHandler();

        bind();
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
    }

    @Override
    public void go(HasWidgets container) {
        Presenter presenter = new AppFramePresenter(clientFactory.getAppFrameDisplay());
        presenter.go(container);
    }
}
