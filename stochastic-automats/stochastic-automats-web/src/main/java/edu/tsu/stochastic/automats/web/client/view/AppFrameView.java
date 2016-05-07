package edu.tsu.stochastic.automats.web.client.view;

import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.core.client.dom.XDOM;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.core.client.util.ToggleGroup;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.button.ToggleButton;
import com.sencha.gxt.widget.core.client.button.ToolButton;
import com.sencha.gxt.widget.core.client.container.*;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.tips.ToolTipConfig;
import edu.tsu.stochastic.automats.web.client.icons.Icons;
import edu.tsu.stochastic.automats.web.client.presenter.AppFramePresenter;
import edu.tsu.stochastic.automats.web.shared.Formula;

public class AppFrameView implements AppFramePresenter.Display {

    private void init() {
        ToggleGroup formulaGroup = new ToggleGroup();

        uzFormulaButton = new ToggleButton("U(z) Formula");
        //uzFormulaButton.setHTML("U&ordm;(z) Formula");
        uzFormulaButton.setIcon(Icons.INSTANCE.function());
        uzFormulaButton.setAllowDepress(false);
        formulaGroup.add(uzFormulaButton);
        //dashboardButton.setValue(true);

        wnFormulaButton = new ToggleButton("W(n) Formula");
        wnFormulaButton.setIcon(Icons.INSTANCE.function());
        wnFormulaButton.setAllowDepress(false);
        formulaGroup.add(wnFormulaButton);

        hdjFormulaButton = new ToggleButton("H(d-j) Formula");
        hdjFormulaButton.setIcon(Icons.INSTANCE.function());
        hdjFormulaButton.setAllowDepress(false);
        formulaGroup.add(hdjFormulaButton);

        ContentPanel panel = new ContentPanel();
        panel.setHeadingHtml("<center>Stochastic Automats</center>");
        panel.setPixelSize(XDOM.getViewportWidth(), XDOM.getViewportHeight());

        VerticalLayoutContainer westContainer = new VerticalLayoutContainer();

        BorderLayoutContainer.BorderLayoutData west = new BorderLayoutContainer.BorderLayoutData(250);
        west.setMargins(new Margins(10));

        westContainer.add(uzFormulaButton, new VerticalLayoutContainer.VerticalLayoutData(1, -1, new Margins(5)));
        westContainer.add(wnFormulaButton, new VerticalLayoutContainer.VerticalLayoutData(1, -1, new Margins(5)));
        westContainer.add(hdjFormulaButton, new VerticalLayoutContainer.VerticalLayoutData(1, -1, new Margins(5)));

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
    public Widget asWidget() {
        init();
        return container;
    }

    private ContentPanel centerPanel;
    private SimpleContainer container;
    private ToggleButton wnFormulaButton;
    private ToggleButton uzFormulaButton;
    private ToggleButton hdjFormulaButton;
}
