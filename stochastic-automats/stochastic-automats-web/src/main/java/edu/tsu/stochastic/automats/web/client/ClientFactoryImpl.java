package edu.tsu.stochastic.automats.web.client;

import com.google.gwt.event.shared.HandlerManager;
import edu.tsu.stochastic.automats.web.client.error.ErrorHandler;
import edu.tsu.stochastic.automats.web.client.error.ErrorHandlerImpl;
import edu.tsu.stochastic.automats.web.client.presenter.*;
import edu.tsu.stochastic.automats.web.client.view.*;

public class ClientFactoryImpl implements ClientFactory {
    private final HandlerManager eventBus = new HandlerManager(null);
    private final ErrorHandler errorHandler = new ErrorHandlerImpl();

    public ClientFactoryImpl() {
    }

    @Override
    public ErrorHandler getErrorHandler() {
        return errorHandler;
    }

    @Override
    public HandlerManager getEventBus() {
        return eventBus;
    }

    @Override
    public AppFramePresenter.Display getAppFrameDisplay() {
        return new AppFrameView();
    }

    @Override
    public UzFormulaPresenter.Display getUzFormulaDisplay() {
        return new UzFormulaView();
    }

    @Override
    public WnFormulaPresenter.Display getWnFormulaDisplay() {
        return new WnFormulaView();
    }

    @Override
    public AddUzFormulaPresenter.Display getAddUzFormulaDisplay() {
        return new AddUzFormulaView();
    }

    @Override
    public FileImportPresenter.Display getImportFormulaDisplay() {
        return new FileImportView();
    }

    @Override
    public AdminPresenter.Display getAdminDisplay() {
        return new AdminView();
    }

    @Override
    public AboutPresenter.Display getAboutDisplay() {
        return new AboutView();
    }

    @Override
    public EditUserPresenter.Display getEditUserDisplay() {
        return new EditUserView();
    }
}
