package edu.tsu.stochastic.automats.web.client;

import com.google.gwt.event.shared.HandlerManager;
import edu.tsu.stochastic.automats.web.client.error.ErrorHandler;
import edu.tsu.stochastic.automats.web.client.presenter.*;

public interface ClientFactory {
    ErrorHandler getErrorHandler();

    HandlerManager getEventBus();

    AppFramePresenter.Display getAppFrameDisplay();

    UzFormulaPresenter.Display getUzFormulaDisplay();

    WnFormulaPresenter.Display getWnFormulaDisplay();

    AddUzFormulaPresenter.Display getAddUzFormulaDisplay();

    FileImportPresenter.Display getImportFormulaDisplay();

    AdminPresenter.Display getAdminDisplay();

    AboutPresenter.Display getAboutDisplay();

    EditUserPresenter.Display getEditUserDisplay();
}
