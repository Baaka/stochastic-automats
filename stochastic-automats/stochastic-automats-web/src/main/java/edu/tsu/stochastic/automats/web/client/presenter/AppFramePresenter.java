package edu.tsu.stochastic.automats.web.client.presenter;

import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.widget.core.client.container.SimpleContainer;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.info.Info;
import edu.tsu.stochastic.automats.web.client.AppController;
import edu.tsu.stochastic.automats.web.client.event.LogoutEvent;
import edu.tsu.stochastic.automats.web.shared.Formula;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AppFramePresenter implements Presenter {

    private final Display display;
    private final Map<Formula, SimpleContainer> containerMap;
    private final List<String> userPermissions;
    private AboutPresenter aboutPresenter;
    private AdminPresenter adminPresenter;
    private SimpleContainer adminContainer;

    public AppFramePresenter(Display display, List<String> userPermissions) {
        this.display = display;
        this.userPermissions = userPermissions;
        containerMap = new HashMap<>();
    }

    public interface Display extends IsWidget {
        SelectEvent.HasSelectHandlers getWnFormulaButton();

        SelectEvent.HasSelectHandlers getUzFormulaButton();

        SelectEvent.HasSelectHandlers getHdjFormulaButton();

        SelectEvent.HasSelectHandlers getAdminButton();

        SelectEvent.HasSelectHandlers getAboutButton();

        SelectEvent.HasSelectHandlers getLogoutButton();

        void setWidget(Widget widget);

        void selectButton(Formula formula);

        void initPermissions(List<String> permissions);
    }

    private void selectFormula(Formula formula) {
        if (containerMap.get(formula) == null) {
            SimpleContainer sc = new SimpleContainer();
            AppController.appFormulasStore.get(formula).go(sc);
            containerMap.put(formula, sc);
        }
        setWidget(formula);
    }

    private boolean checkPermission(String code) {
        return userPermissions.contains(code);
    }

    private void bind() {

        if (aboutPresenter == null) {
            aboutPresenter = new AboutPresenter(AppController.clientFactory.getAboutDisplay());
            display.setWidget(aboutPresenter.getDisplay().asWidget());
        }

        /*if (checkPermission(Formula.UZ_FUNCTION.getPermissionCode())) {
            display.selectButton(Formula.UZ_FUNCTION);
            selectFormula(Formula.UZ_FUNCTION);
        }*/

        if (display.getWnFormulaButton() != null) {
            display.getWnFormulaButton().addSelectHandler(new SelectEvent.SelectHandler() {
                @Override
                public void onSelect(SelectEvent event) {
                    selectFormula(Formula.WN_FUNCTION);

                }
            });
        }

        if (display.getUzFormulaButton() != null) {
            display.getUzFormulaButton().addSelectHandler(new SelectEvent.SelectHandler() {
                @Override
                public void onSelect(SelectEvent event) {
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

        if (display.getAdminButton() != null) {
            display.getAdminButton().addSelectHandler(new SelectEvent.SelectHandler() {
                @Override
                public void onSelect(SelectEvent event) {
                    if (adminPresenter == null || adminContainer == null) {
                        AdminPresenter adminPresenter = new AdminPresenter(AppController.clientFactory.getAdminDisplay());
                        adminContainer = new SimpleContainer();
                        adminPresenter.go(adminContainer);
                    }
                    display.setWidget(adminContainer);

                    //AdminPresenter adminPresenter = new AdminPresenter(AppController.clientFactory.getAdminDisplay());
                    //display.setWidget(adminPresenter.getDisplay().asWidget());
                }
            });
        }

        if (display.getAboutButton() != null) {
            display.getAboutButton().addSelectHandler(new SelectEvent.SelectHandler() {
                @Override
                public void onSelect(SelectEvent event) {
                    display.setWidget(aboutPresenter.getDisplay().asWidget());
                }
            });
        }

        if (display.getLogoutButton() != null) {
            display.getLogoutButton().addSelectHandler(new SelectEvent.SelectHandler() {
                @Override
                public void onSelect(SelectEvent event) {
                    AppController.eventBus.fireEvent(new LogoutEvent());
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
            display.initPermissions(userPermissions);
            container.add(display.asWidget());
        }
        bind();
    }
}
