package edu.tsu.stochastic.automats.web.client.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.event.logical.shared.HasSelectionHandlers;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.core.client.IdentityValueProvider;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.data.client.loader.RpcProxy;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.loader.LoadResultListStoreBinding;
import com.sencha.gxt.data.shared.loader.PagingLoadConfig;
import com.sencha.gxt.data.shared.loader.PagingLoadResult;
import com.sencha.gxt.data.shared.loader.PagingLoader;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.BoxLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.event.RefreshEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.form.NumberField;
import com.sencha.gxt.widget.core.client.form.NumberPropertyEditor;
import com.sencha.gxt.widget.core.client.grid.CheckBoxSelectionModel;
import com.sencha.gxt.widget.core.client.grid.ColumnConfig;
import com.sencha.gxt.widget.core.client.grid.ColumnModel;
import com.sencha.gxt.widget.core.client.grid.Grid;
import com.sencha.gxt.widget.core.client.menu.Item;
import com.sencha.gxt.widget.core.client.menu.MenuItem;
import com.sencha.gxt.widget.core.client.tips.ToolTipConfig;
import com.sencha.gxt.widget.core.client.toolbar.PagingToolBar;
import com.sencha.gxt.widget.core.client.toolbar.SeparatorToolItem;
import com.sencha.gxt.widget.core.client.toolbar.ToolBar;
import edu.tsu.stochastic.automats.web.client.AppController;
import edu.tsu.stochastic.automats.web.client.icons.Icons;
import edu.tsu.stochastic.automats.web.client.presenter.UzFormulaPresenter;
import edu.tsu.stochastic.automats.web.client.service.FormulaService;
import edu.tsu.stochastic.automats.web.client.widget.ExportButton;
import edu.tsu.stochastic.automats.web.shared.UzFormulaResultModel;
import edu.tsu.stochastic.automats.web.shared.UzFormulaResultModelProperties;

import java.util.ArrayList;
import java.util.List;

public class UzFormulaView implements UzFormulaPresenter.Display {

    private final UzFormulaResultModelProperties properties = GWT.create(UzFormulaResultModelProperties.class);
    private PagingToolBar pagingToolBar = new PagingToolBar(10);

    private void initButtonToolbar() {
        addButton = new TextButton("Add", Icons.INSTANCE.add());
        deleteButton = new TextButton("Delete", Icons.INSTANCE.delete());
        refreshButton = new TextButton("Refresh", Icons.INSTANCE.refresh());
        importButton = new TextButton("Import", Icons.INSTANCE.importSmall());

        exportItem = new MenuItem();
        ExportButton exportButton = new ExportButton("exp", Icons.INSTANCE.export(), new SelectionHandler<Item>() {
            @Override
            public void onSelection(SelectionEvent<Item> event) {
                exportItem.fireEvent(event);
            }
        });

        buttonToolbar = new ToolBar();
        buttonToolbar.add(addButton);
        buttonToolbar.add(deleteButton);
        buttonToolbar.add(new SeparatorToolItem());
        buttonToolbar.add(refreshButton);
        buttonToolbar.add(importButton);
        buttonToolbar.add(exportButton);
    }

    private void initGrid() {
        RpcProxy<PagingLoadConfig, PagingLoadResult<UzFormulaResultModel>> proxy = new RpcProxy<PagingLoadConfig, PagingLoadResult<UzFormulaResultModel>>() {
            @Override
            public void load(PagingLoadConfig loadConfig, final AsyncCallback<PagingLoadResult<UzFormulaResultModel>> callback) {
                FormulaService.Util.getInstance().loadCalculatedUzFormulas(loadConfig, new AsyncCallback<PagingLoadResult<UzFormulaResultModel>>() {
                    @Override
                    public void onFailure(Throwable caught) {
                        AppController.errorHandler.onError(caught);
                        callback.onFailure(caught);
                    }

                    @Override
                    public void onSuccess(PagingLoadResult<UzFormulaResultModel> result) {
                        callback.onSuccess(result);
                    }
                });
            }
        };

        ColumnConfig<UzFormulaResultModel, Double> rParamCol = new ColumnConfig<>(properties.paramR(), 15, "r");
        ColumnConfig<UzFormulaResultModel, Double> alphaParamCol = new ColumnConfig<>(properties.paramAlpha(), 20, "alpha");
        ColumnConfig<UzFormulaResultModel, Double> epsilonParamCol = new ColumnConfig<>(properties.paramEpsilon(), 20, "epsilon");
        ColumnConfig<UzFormulaResultModel, Double> etaParamCol = new ColumnConfig<>(properties.paramEta(), 20, "eta");
        ColumnConfig<UzFormulaResultModel, Double> zParamCol = new ColumnConfig<>(properties.paramZ(), 15, "z");
        ColumnConfig<UzFormulaResultModel, Double> lParamCol = new ColumnConfig<>(properties.paramL(), 15, "l");
        ColumnConfig<UzFormulaResultModel, Double> rCalcCol = new ColumnConfig<>(properties.r(), 20, "Calc: R");
        ColumnConfig<UzFormulaResultModel, Double> pCalcCol = new ColumnConfig<>(properties.p(), 20, "Calc: P");
        ColumnConfig<UzFormulaResultModel, Double> qCalcCol = new ColumnConfig<>(properties.q(), 20, "Calc: Q");
        ColumnConfig<UzFormulaResultModel, Double> resultCol = new ColumnConfig<>(properties.result(), 30, "Result");
        ColumnConfig<UzFormulaResultModel, Double> privResultCol = new ColumnConfig<>(properties.privateCaseResult(), 30, "Pr.Case Result");

        ListStore<UzFormulaResultModel> store = new ListStore<>(new ModelKeyProvider<UzFormulaResultModel>() {
            @Override
            public String getKey(UzFormulaResultModel item) {
                return "" + item.getId();
            }
        });
        final PagingLoader<PagingLoadConfig, PagingLoadResult<UzFormulaResultModel>> loader = new PagingLoader<>(proxy);
        loader.addLoadHandler(new LoadResultListStoreBinding<PagingLoadConfig, UzFormulaResultModel, PagingLoadResult<UzFormulaResultModel>>(store));

        IdentityValueProvider<UzFormulaResultModel> identity = new IdentityValueProvider<>();
        final CheckBoxSelectionModel<UzFormulaResultModel> selectionModel = new CheckBoxSelectionModel<UzFormulaResultModel>(identity) {
            @Override
            protected void onRefresh(RefreshEvent event) {
                // this code selects all rows when paging if the header checkbox is selected
                if (isSelectAllChecked()) {
                    selectAll();
                }
                super.onRefresh(event);
            }
        };

        List<ColumnConfig<UzFormulaResultModel, ?>> columnConfigs = new ArrayList<>();
        columnConfigs.add(selectionModel.getColumn());
        columnConfigs.add(rParamCol);
        columnConfigs.add(alphaParamCol);
        columnConfigs.add(epsilonParamCol);
        columnConfigs.add(etaParamCol);
        columnConfigs.add(zParamCol);
        columnConfigs.add(lParamCol);
        columnConfigs.add(rCalcCol);
        columnConfigs.add(pCalcCol);
        columnConfigs.add(qCalcCol);
        columnConfigs.add(resultCol);
        columnConfigs.add(privResultCol);

        ColumnModel<UzFormulaResultModel> cm = new ColumnModel<>(columnConfigs);

        grid = new Grid<UzFormulaResultModel>(store, cm) {
            @Override
            protected void onAfterFirstAttach() {
                super.onAfterFirstAttach();
                Scheduler.get().scheduleDeferred(new Scheduler.ScheduledCommand() {
                    @Override
                    public void execute() {
                        loader.load();
                    }
                });
            }
        };


        grid.setSelectionModel(selectionModel);
        grid.getView().setAutoFill(true);
        grid.setLoadMask(true);
        grid.setLoader(loader);
        grid.setColumnReordering(true);

        pagingToolBar.getElement().getStyle().setProperty("borderBottom", "none");
        pagingToolBar.setPack(BoxLayoutContainer.BoxLayoutPack.CENTER);
        pagingToolBar.bind(loader);
    }


    private void init() {
        initGrid();

        ToolTipConfig epsilonConfig = new ToolTipConfig();
        epsilonConfig.setBodyHtml("e = &#949;");

        ToolTipConfig etaConfig = new ToolTipConfig();
        etaConfig.setBodyHtml("m = &#951;");

        ToolTipConfig alphaConfig = new ToolTipConfig();
        alphaConfig.setBodyHtml("a = &#945;");

        rNumberField = new NumberField<Double>(new NumberPropertyEditor.DoublePropertyEditor());
        rNumberField.setEmptyText("r");
        rNumberField.setToolTip(rNumberField.getEmptyText());

        // alpha &#945;
        aNumberField = new NumberField<Double>(new NumberPropertyEditor.DoublePropertyEditor());
        aNumberField.setEmptyText("a");
        aNumberField.setToolTipConfig(alphaConfig);

        // epsilon &#949;
        eNumberField = new NumberField<Double>(new NumberPropertyEditor.DoublePropertyEditor());
        eNumberField.setEmptyText("e");
        eNumberField.setToolTipConfig(epsilonConfig);

        //eta &#951;
        mNumberField = new NumberField<Double>(new NumberPropertyEditor.DoublePropertyEditor());
        mNumberField.setEmptyText("m");
        mNumberField.setToolTipConfig(etaConfig);

        zNumberField = new NumberField<Double>(new NumberPropertyEditor.DoublePropertyEditor());
        zNumberField.setEmptyText("z");
        zNumberField.setEmptyText(zNumberField.getEmptyText());

        lNumberField = new NumberField<Double>(new NumberPropertyEditor.DoublePropertyEditor());
        lNumberField.setEmptyText("l");
        lNumberField.setToolTip(lNumberField.getEmptyText());

        filterButton = new TextButton();
        filterButton.setIcon(Icons.INSTANCE.search());

        BoxLayoutContainer.BoxLayoutData flexData = new BoxLayoutContainer.BoxLayoutData();
        flexData.setFlex(1d);

        filterToolbar = new ToolBar();
        filterToolbar.setHorizontalSpacing(5);
        filterToolbar.add(rNumberField, flexData);
        filterToolbar.add(aNumberField, flexData);
        filterToolbar.add(eNumberField, flexData);
        filterToolbar.add(mNumberField, flexData);
        filterToolbar.add(zNumberField, flexData);
        filterToolbar.add(lNumberField, flexData);
        filterToolbar.add(filterButton);
    }


    @Override
    public SelectEvent.HasSelectHandlers getFilterButton() {
        return filterButton;
    }

    @Override
    public SelectEvent.HasSelectHandlers getAddButton() {
        return addButton;
    }

    @Override
    public SelectEvent.HasSelectHandlers getDeleteButton() {
        return deleteButton;
    }

    @Override
    public SelectEvent.HasSelectHandlers getRefreshButton() {
        return refreshButton;
    }

    @Override
    public SelectEvent.HasSelectHandlers getImportButton() {
        return importButton;
    }

    @Override
    public HasSelectionHandlers<Item> getExportButton() {
        return exportItem;
    }

    @Override
    public void addModel(UzFormulaResultModel model) {
        grid.getStore().add(model);
    }

    @Override
    public List<UzFormulaResultModel> getResultModels() {
        return grid.getSelectionModel().getSelectedItems();
    }

    @Override
    public void refresh() {
        grid.getLoader().load();
    }

    @Override
    public Widget asWidget() {
        init();
        initGrid();
        initButtonToolbar();

        VerticalLayoutContainer vc = new VerticalLayoutContainer();
        vc.add(buttonToolbar, new VerticalLayoutContainer.VerticalLayoutData(1, -1, new Margins(5)));
        vc.add(filterToolbar, new VerticalLayoutContainer.VerticalLayoutData(1, -1, new Margins(5)));
        vc.add(grid, new VerticalLayoutContainer.VerticalLayoutData(1, 1, new Margins(5)));
        vc.add(pagingToolBar, new VerticalLayoutContainer.VerticalLayoutData(1, -1, new Margins(5)));

        return vc;
    }

    private NumberField<Double> rNumberField;
    private NumberField<Double> aNumberField;
    private NumberField<Double> eNumberField;
    private NumberField<Double> mNumberField;
    private NumberField<Double> zNumberField;
    private NumberField<Double> lNumberField;
    private TextButton filterButton;
    private ToolBar filterToolbar;
    private Grid<UzFormulaResultModel> grid;
    private ToolBar buttonToolbar;
    private TextButton addButton;
    private TextButton deleteButton;
    private TextButton refreshButton;
    private TextButton importButton;
    private MenuItem exportItem;
}
