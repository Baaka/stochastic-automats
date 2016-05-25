package edu.tsu.stochastic.automats.web.client.presenter;

import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.widget.core.client.container.SimpleContainer;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.info.Info;
import edu.tsu.stochastic.automats.web.client.AppController;
import edu.tsu.stochastic.automats.web.shared.Formula;

import java.util.HashMap;
import java.util.Map;

public class AppFramePresenter implements Presenter {

    private final Display display;
    private final Map<Formula, SimpleContainer> containerMap;

    public AppFramePresenter(Display display) {
        this.display = display;
        containerMap = new HashMap<>();
    }

    public interface Display extends IsWidget {
        SelectEvent.HasSelectHandlers getWnFormulaButton();

        SelectEvent.HasSelectHandlers getUzFormulaButton();

        SelectEvent.HasSelectHandlers getHdjFormulaButton();

        void setWidget(Widget widget);

        void selectButton(Formula formula);
    }

    private void selectFormula(Formula formula) {
        if (containerMap.get(formula) == null) {
            SimpleContainer sc = new SimpleContainer();
            AppController.appFormulasStore.get(formula).go(sc);
            containerMap.put(formula, sc);
        }
        setWidget(formula);
    }

    private void bind() {

        display.selectButton(Formula.UZ_FUNCTION);
        selectFormula(Formula.UZ_FUNCTION);

        if (display.getWnFormulaButton() != null) {
            display.getWnFormulaButton().addSelectHandler(new SelectEvent.SelectHandler() {
                @Override
                public void onSelect(SelectEvent event) {

                    /*if (containerMap.get(Formula.WN_FUNCTION) == null) {
                        SimpleContainer sc = new SimpleContainer();
                        new WnFormulaPresenter(AppController.clientFactory.getWnFormulaDisplay()).go(sc);
                        //display.setWidget(sc);
                        containerMap.put(Formula.WN_FUNCTION, sc);
                    }
                    setWidget(Formula.WN_FUNCTION);*/

                    selectFormula(Formula.WN_FUNCTION);

                }
            });
        }

        if (display.getUzFormulaButton() != null) {
            display.getUzFormulaButton().addSelectHandler(new SelectEvent.SelectHandler() {
                @Override
                public void onSelect(SelectEvent event) {

                  /*  if (containerMap.get(Formula.UZ_FUNCTION) == null) {
                        SimpleContainer sc = new SimpleContainer();
                        new UzFormulaPresenter(AppController.clientFactory.getUzFormulaDisplay()).go(sc);
                        //display.setWidget(sc);
                        containerMap.put(Formula.UZ_FUNCTION, sc);
                    }

                    setWidget(Formula.UZ_FUNCTION);*/

                    selectFormula(Formula.UZ_FUNCTION);
                }
            });
        }

        if (display.getHdjFormulaButton() != null) {
            display.getHdjFormulaButton().addSelectHandler(new SelectEvent.SelectHandler() {
                @Override
                public void onSelect(SelectEvent event) {
                    Info.display("//TODO", "Hdj");
                    selectFormula(Formula.HDJ_FUNCTION);
                }
            });
        }
    }

    private void setWidget(Formula formula) {
        display.setWidget(containerMap.get(formula));
    }

    @Override
    public void go(HasWidgets container) {
        if (container != null) {
            container.add(display.asWidget());
        }
        bind();
    }
}
