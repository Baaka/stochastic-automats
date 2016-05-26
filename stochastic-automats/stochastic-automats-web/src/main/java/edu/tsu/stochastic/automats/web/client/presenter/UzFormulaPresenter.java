package edu.tsu.stochastic.automats.web.client.presenter;

import com.google.gwt.event.logical.shared.HasSelectionHandlers;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.IsWidget;
import com.sencha.gxt.widget.core.client.box.AlertMessageBox;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.info.Info;
import com.sencha.gxt.widget.core.client.menu.Item;
import edu.tsu.stochastic.automats.web.client.AppController;
import edu.tsu.stochastic.automats.web.client.event.AddUzFormulaEvent;
import edu.tsu.stochastic.automats.web.client.event.ResultExportEvent;
import edu.tsu.stochastic.automats.web.client.service.FormulaService;
import edu.tsu.stochastic.automats.web.shared.ExportFormat;
import edu.tsu.stochastic.automats.web.shared.Formula;
import edu.tsu.stochastic.automats.web.shared.UzFormulaResultModel;

import java.util.ArrayList;
import java.util.List;

public class UzFormulaPresenter implements Presenter {

    private final Display display;

    public UzFormulaPresenter(Display display) {
        this.display = display;
    }

    public interface Display extends IsWidget {
        SelectEvent.HasSelectHandlers getFilterButton();

        SelectEvent.HasSelectHandlers getAddButton();

        SelectEvent.HasSelectHandlers getDeleteButton();

        SelectEvent.HasSelectHandlers getRefreshButton();

        SelectEvent.HasSelectHandlers getImportButton();

        HasSelectionHandlers<Item> getExportButton();

        void addModel(UzFormulaResultModel model);

        List<UzFormulaResultModel> getResultModels();

        void refresh();
    }

    private void todo() {
        Info.display("//TODO", "//TODO");
    }

    private void bind() {
        if (display.getFilterButton() != null) {
            display.getFilterButton().addSelectHandler(new SelectEvent.SelectHandler() {
                @Override
                public void onSelect(SelectEvent event) {
                    todo();
                }
            });
        }

        if (display.getAddButton() != null) {
            display.getAddButton().addSelectHandler(new SelectEvent.SelectHandler() {
                @Override
                public void onSelect(SelectEvent event) {
                    AppController.eventBus.fireEvent(new AddUzFormulaEvent());
                }
            });
        }

        if (display.getDeleteButton() != null) {
            display.getDeleteButton().addSelectHandler(new SelectEvent.SelectHandler() {
                @Override
                public void onSelect(SelectEvent event) {
                    List<UzFormulaResultModel> resultModels = display.getResultModels();
                    if (resultModels != null && !resultModels.isEmpty()) {
                        deleteResults(resultModels);
                    } else {
                        new AlertMessageBox("Warning", "Please choose results you want to delete!").show();
                    }
                }
            });
        }

        if (display.getRefreshButton() != null) {
            display.getRefreshButton().addSelectHandler(new SelectEvent.SelectHandler() {
                @Override
                public void onSelect(SelectEvent event) {
                    display.refresh();
                }
            });
        }

        if (display.getImportButton() != null) {
            display.getImportButton().addSelectHandler(new SelectEvent.SelectHandler() {
                @Override
                public void onSelect(SelectEvent event) {
                    todo();
                }
            });
        }

        if (display.getExportButton() != null) {
            display.getExportButton().addSelectionHandler(new SelectionHandler<Item>() {
                @Override
                public void onSelection(SelectionEvent<Item> event) {
                    ExportFormat exportFormat = event.getSelectedItem().getData("exportFormat");
                    List<Long> ids = new ArrayList<>();
                    List<UzFormulaResultModel> selectedModels = display.getResultModels();
                    if (selectedModels != null) {
                        for (UzFormulaResultModel model : selectedModels) {
                            ids.add(model.getId());
                        }
                    }
                    AppController.eventBus.fireEvent(new ResultExportEvent(ids, Formula.UZ_FUNCTION, exportFormat));
                }
            });
        }
    }

    private void deleteResults(List<UzFormulaResultModel> resultModels) {
        FormulaService.Util.getInstance().deleteCalculatedUzFormula(resultModels, new AsyncCallback<Void>() {
            @Override
            public void onFailure(Throwable caught) {
                AppController.errorHandler.onError(caught);
            }

            @Override
            public void onSuccess(Void result) {
                display.refresh();
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
        bind();
    }
}
