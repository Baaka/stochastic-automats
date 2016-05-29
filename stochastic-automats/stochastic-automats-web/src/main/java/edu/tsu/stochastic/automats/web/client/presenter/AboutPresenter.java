package edu.tsu.stochastic.automats.web.client.presenter;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.IsWidget;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import edu.tsu.stochastic.automats.web.client.AppController;
import edu.tsu.stochastic.automats.web.client.service.FormulaService;
import edu.tsu.stochastic.automats.web.shared.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AboutPresenter implements Presenter {
    private final Display display;

    public interface Display extends IsWidget {
        void initUzPortlet(List<Data> data);

        SelectEvent.HasSelectHandlers getUzPortletRefreshButton();
    }

    public AboutPresenter(Display display) {
        this.display = display;
        bind();
    }

    private void bind() {
        initUzPortlet();

        if (display.getUzPortletRefreshButton() != null) {
            display.getUzPortletRefreshButton().addSelectHandler(new SelectEvent.SelectHandler() {
                @Override
                public void onSelect(SelectEvent event) {
                     initUzPortlet();
                }
            });
        }
    }

    private void initUzPortlet() {
        FormulaService.Util.getInstance().getUzFormulaCount(new AsyncCallback<Map<String, Integer>>() {
            @Override
            public void onFailure(Throwable caught) {
                AppController.errorHandler.onError(caught);
            }

            @Override
            public void onSuccess(Map<String, Integer> result) {
                if (result != null) {
                    List<Data> data = new ArrayList<>();
                    for (Map.Entry<String, Integer> entry : result.entrySet()) {
                        data.add(new Data(entry.getKey(), entry.getValue()));
                    }
                    display.initUzPortlet(data);
                }
            }
        });
    }

    public Display getDisplay() {
        return display;
    }

    @Override
    public void go(HasWidgets container) {
        if (container != null) {
            container.add(display.asWidget());
        }
    }
}
