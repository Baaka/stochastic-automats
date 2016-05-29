package edu.tsu.stochastic.automats.web.client.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.chart.client.chart.Chart;
import com.sencha.gxt.chart.client.chart.axis.CategoryAxis;
import com.sencha.gxt.chart.client.chart.axis.NumericAxis;
import com.sencha.gxt.chart.client.chart.series.BarSeries;
import com.sencha.gxt.chart.client.chart.series.Series;
import com.sencha.gxt.chart.client.chart.series.SeriesLabelConfig;
import com.sencha.gxt.chart.client.draw.RGB;
import com.sencha.gxt.chart.client.draw.sprite.TextSprite;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.widget.core.client.Portlet;
import com.sencha.gxt.widget.core.client.button.ToolButton;
import com.sencha.gxt.widget.core.client.container.PortalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import edu.tsu.stochastic.automats.web.client.presenter.AboutPresenter;
import edu.tsu.stochastic.automats.web.shared.Data;
import edu.tsu.stochastic.automats.web.shared.DataPropertyAccess;
import edu.tsu.stochastic.automats.web.shared.Portlets;

import java.util.ArrayList;
import java.util.List;

public class AboutView implements AboutPresenter.Display {
    private final List<ToolButton> uzToolButtons = new ArrayList<>();

    public AboutView() {
        uzCounterRefreshButton = new ToolButton(ToolButton.REFRESH);
        uzToolButtons.add(uzCounterRefreshButton);

        DataPropertyAccess dataAccess = GWT.create(DataPropertyAccess.class);
        uzCounterStore = new ListStore<>(dataAccess.nameKey());

        // chart
        uzCounterChart = new Chart<>();
        uzCounterChart.setStore(uzCounterStore);
        uzCounterChart.setShadowChart(true);

        NumericAxis<Data> axis = new NumericAxis<>();
        axis.setPosition(Chart.Position.BOTTOM);
        axis.addField(dataAccess.data());
        axis.setSteps(5);
        TextSprite title = new TextSprite("Number Of Calculations");
        title.setFontSize(18);
        axis.setTitleConfig(title);
        axis.setDisplayGrid(true);
        axis.setWidth(50);
        axis.setMinimum(0);
        uzCounterChart.addAxis(axis);

        CategoryAxis<Data, String> categoryAxis = new CategoryAxis<>();
        categoryAxis.setPosition(Chart.Position.LEFT);
        categoryAxis.setField(dataAccess.name());
        title = new TextSprite("Users");
        title.setFontSize(18);
        categoryAxis.setTitleConfig(title);
        uzCounterChart.addAxis(categoryAxis);

        final BarSeries<Data> column = new BarSeries<>();
        column.setYAxisPosition(Chart.Position.BOTTOM);
        column.addYField(dataAccess.data());
        column.addColor(RGB.GREEN);
        column.setColumn(false);
        SeriesLabelConfig<Data> labelConfig = new SeriesLabelConfig<>();
        labelConfig.setLabelPosition(Series.LabelPosition.END);
        column.setLabelConfig(labelConfig);
        uzCounterChart.addSeries(column);
        uzCounterChart.setAnimated(true);

        portal = new PortalLayoutContainer(2);
        portal.setAutoScroll(true);
        portal.setColumnWidth(0, .50);
        portal.setColumnWidth(1, .50);
        portal.setAutoScroll(true);

        vc = new VerticalLayoutContainer();
        vc.add(portal, new VerticalLayoutContainer.VerticalLayoutData(1, 1));

        Portlet p = new Portlet();
        p.setHeight(400);
        p.setWidth(200);
        p.setCollapsible(true);
        p.setAnimCollapse(true);
        p.setHeadingText(Portlets.UZ_FORMULA_CREATORS.getValue());
        p.add(uzCounterChart);
        p.addTool(uzCounterRefreshButton);

        portal.add(p, Portlets.UZ_FORMULA_CREATORS.getColumn());
    }

    @Override
    public Widget asWidget() {
        return vc;
    }

    @Override
    public void initUzPortlet(List<Data> data) {
        uzCounterStore.clear();
        uzCounterStore.addAll(data);
        uzCounterChart.redrawChart();
    }

    @Override
    public SelectEvent.HasSelectHandlers getUzPortletRefreshButton() {
        return uzCounterRefreshButton;
    }

    private ToolButton uzCounterRefreshButton;
    private ListStore<Data> uzCounterStore;
    private Chart<Data> uzCounterChart;
    private PortalLayoutContainer portal;
    private VerticalLayoutContainer vc;
}
