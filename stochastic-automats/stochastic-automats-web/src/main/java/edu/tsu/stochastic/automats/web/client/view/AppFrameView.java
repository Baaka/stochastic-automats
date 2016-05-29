package edu.tsu.stochastic.automats.web.client.view;

import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.core.client.dom.XDOM;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.core.client.util.ToggleGroup;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.button.ToggleButton;
import com.sencha.gxt.widget.core.client.button.ToolButton;
import com.sencha.gxt.widget.core.client.container.*;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.tips.ToolTipConfig;
import com.sencha.gxt.widget.core.client.toolbar.FillToolItem;
import edu.tsu.stochastic.automats.web.client.icons.Icons;
import edu.tsu.stochastic.automats.web.client.presenter.AppFramePresenter;
import edu.tsu.stochastic.automats.web.shared.Formula;

import java.util.List;

public class AppFrameView implements AppFramePresenter.Display {

    private void init() {
        logoutButton = new TextButton("Logout");

        ToggleGroup formulaGroup = new ToggleGroup();

        aboutButton = new ToggleButton("About");
        aboutButton.setIcon(Icons.INSTANCE.info());
        aboutButton.setAllowDepress(false);
        formulaGroup.add(aboutButton);

        adminButton = new ToggleButton("Users");
        adminButton.setIcon(Icons.INSTANCE.users());
        adminButton.setAllowDepress(false);
        formulaGroup.add(adminButton);
        adminButton.setEnabled(userPermissions.contains("edu.tsu.stoch.automats.admin"));

        uzFormulaButton = new ToggleButton("U(z) Formula");
        //uzFormulaButton.setHTML("U&ordm;(z) Formula");
        uzFormulaButton.setIcon(Icons.INSTANCE.function());
        uzFormulaButton.setAllowDepress(false);
        formulaGroup.add(uzFormulaButton);
        //dashboardButton.setValue(true);
        uzFormulaButton.setEnabled(userPermissions.contains(Formula.UZ_FUNCTION.getPermissionCode()));

        wnFormulaButton = new ToggleButton("W(n) Formula");
        wnFormulaButton.setIcon(Icons.INSTANCE.function());
        wnFormulaButton.setAllowDepress(false);
        formulaGroup.add(wnFormulaButton);
        wnFormulaButton.setEnabled(userPermissions.contains(Formula.WN_FUNCTION.getPermissionCode()));

        hdjFormulaButton = new ToggleButton("H(d-j) Formula");
        hdjFormulaButton.setIcon(Icons.INSTANCE.function());
        hdjFormulaButton.setAllowDepress(false);
        formulaGroup.add(hdjFormulaButton);
        hdjFormulaButton.setEnabled(userPermissions.contains(Formula.HDJ_FUNCTION.getPermissionCode()));

        ContentPanel panel = new ContentPanel();
        panel.setHeadingHtml("<center>Stochastic Automats</center>");
        panel.setPixelSize(XDOM.getViewportWidth(), XDOM.getViewportHeight());
        panel.setButtonAlign(BoxLayoutContainer.BoxLayoutPack.END);
        panel.getButtonBar().add(logoutButton);

        VerticalLayoutContainer westContainer = new VerticalLayoutContainer();

        BorderLayoutContainer.BorderLayoutData west = new BorderLayoutContainer.BorderLayoutData(.10);
        west.setMargins(new Margins(10));

        westContainer.add(aboutButton, new VerticalLayoutContainer.VerticalLayoutData(1, -1, new Margins(5)));
        westContainer.add(uzFormulaButton, new VerticalLayoutContainer.VerticalLayoutData(1, -1, new Margins(5)));
        westContainer.add(wnFormulaButton, new VerticalLayoutContainer.VerticalLayoutData(1, -1, new Margins(5)));
        westContainer.add(hdjFormulaButton, new VerticalLayoutContainer.VerticalLayoutData(1, -1, new Margins(5)));
        westContainer.add(adminButton, new VerticalLayoutContainer.VerticalLayoutData(1, -1, new Margins(5)));

        Image image = new Image(Icons.INSTANCE.tsu());
        westContainer.add(new FillToolItem());
        westContainer.add(image, new VerticalLayoutContainer.VerticalLayoutData(-1, -1, new Margins(180, 10, 10, 10)));

        BorderLayoutContainer borderLayoutContainer = new BorderLayoutContainer();
        panel.setWidget(borderLayoutContainer);

        borderLayoutContainer.setWestWidget(westContainer, west);

        centerPanel = new ContentPanel();
        centerPanel.setHeaderVisible(false);

        MarginData center = new MarginData(new Margins(5));

        borderLayoutContainer.setCenterWidget(centerPanel, center);

        BoxLayoutContainer.BoxLayoutData vBoxData = new BoxLayoutContainer.BoxLayoutData(new Margins(5));
        vBoxData.setFlex(1);

        ToolButton info = new ToolButton(ToolButton.QUESTION);
        ToolTipConfig config = new ToolTipConfig("Info", "//TODO Info");
        config.setMaxWidth(225);
        info.setToolTipConfig(config);
        panel.addTool(info);

        container = new SimpleContainer();
        container.add(panel);
    }

    @Override
    public SelectEvent.HasSelectHandlers getWnFormulaButton() {
        return wnFormulaButton;
    }

    @Override
    public SelectEvent.HasSelectHandlers getUzFormulaButton() {
        return uzFormulaButton;
    }

    @Override
    public SelectEvent.HasSelectHandlers getHdjFormulaButton() {
        return hdjFormulaButton;
    }

    @Override
    public SelectEvent.HasSelectHandlers getAdminButton() {
        return adminButton;
    }

    @Override
    public SelectEvent.HasSelectHandlers getAboutButton() {
        return aboutButton;
    }

    @Override
    public SelectEvent.HasSelectHandlers getLogoutButton() {
        return logoutButton;
    }

    @Override
    public void setWidget(Widget widget) {
        centerPanel.clear();
        centerPanel.add(widget);
        centerPanel.forceLayout();
    }

    @Override
    public void selectButton(Formula formula) {
        switch (formula) {
            case WN_FUNCTION:
                wnFormulaButton.setValue(true);
                break;
            case UZ_FUNCTION:
                uzFormulaButton.setValue(true);
                break;
            case HDJ_FUNCTION:
                hdjFormulaButton.setValue(true);
                break;
        }
    }

    @Override
    public void initPermissions(List<String> permissions) {
        this.userPermissions = permissions;
    }

    @Override
    public Widget asWidget() {
        init();
        return container;
    }

    private ContentPanel centerPanel;
    private SimpleContainer container;
    private ToggleButton wnFormulaButton;
    private ToggleButton uzFormulaButton;
    private ToggleButton hdjFormulaButton;
    private ToggleButton adminButton;
    private ToggleButton aboutButton;
    private TextButton logoutButton;
    private List<String> userPermissions;
}
