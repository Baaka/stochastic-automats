package edu.tsu.stochastic.automats.web.client;

import com.google.gwt.event.shared.HandlerManager;
import edu.tsu.stochastic.automats.web.client.error.ErrorHandler;
import edu.tsu.stochastic.automats.web.client.presenter.AppFramePresenter;
import edu.tsu.stochastic.automats.web.client.presenter.UzFormulaPresenter;
import edu.tsu.stochastic.automats.web.client.presenter.WnFormulaPresenter;

public interface ClientFactory {
    ErrorHandler getErrorHandler();

    HandlerManager getEventBus();

    AppFramePresenter.Display getAppFrameDisplay();

    UzFormulaPresenter.Display getUzFormulaDisplay();

    WnFormulaPresenter.Display getWnFormulaDisplay();
}
