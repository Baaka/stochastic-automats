package edu.tsu.stochastic.automats.web.client.error;

import com.sencha.gxt.widget.core.client.info.Info;

public class ErrorHandlerImpl implements ErrorHandler {

    @Override
    public void onError(Throwable throwable) {
        Info.display("Error", throwable.getMessage());
    }
}
