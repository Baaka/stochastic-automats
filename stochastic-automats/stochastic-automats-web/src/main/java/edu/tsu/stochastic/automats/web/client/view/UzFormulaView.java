package edu.tsu.stochastic.automats.web.client.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
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
import com.sencha.gxt.widget.core.client.tips.ToolTipConfig;
import com.sencha.gxt.widget.core.client.toolbar.PagingToolBar;
import com.sencha.gxt.widget.core.client.toolbar.SeparatorToolItem;
import com.sencha.gxt.widget.core.client.toolbar.ToolBar;
import edu.tsu.stochastic.automats.web.client.AppController;
import edu.tsu.stochastic.automats.web.client.icons.Icons;
import edu.tsu.stochastic.automats.web.client.presenter.UzFormulaPresenter;
import edu.tsu.stochastic.automats.web.client.service.FormulaService;
import edu.tsu.stochastic.automats.web.shared.UzFormulaResultModel;
import edu.tsu.stochastic.automats.web.shared.UzFormulaResultModelProperties;

import java.util.ArrayList;
import java.util.List;

public class UzFormulaView implements UzFormulaPresenter.Display {

    private final UzFormulaResultModelProperties properties = GWT.create(UzFormulaResultModelProperties.class);
    private PagingToolBar pagingToolBar = new PagingToolBar(10);

    private void initButtonToolbar() {
        addButton = new TextButton("Add", Icons.INSTANCE.add());
        editButton = new TextButton("Edit", Icons.INSTANCE.edit());
        deleteButton = new TextButton("Delete", Icons.INSTANCE.delete());
        refreshButton = new TextButton("Refresh", Icons.INSTANCE.refresh());
        exportButton = new TextButton("Export", Icons.INSTANCE.export());
        importButton = new TextButton("Import", Icons.INSTANCE.importSmall());

        /*BoxLayoutContainer.BoxLayoutData flexData = new BoxLayoutContainer.BoxLayoutData();
        flexData.setFlex(1d);
*/
        buttonToolbar = new ToolBar();
        buttonToolbar.add(addButton);
        buttonToolbar.add(editButton);
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

        ColumnConfig<UzFormulaResultModel, Double> rParamCol = new ColumnConfig<>(properties.paramR(), 20, "r");
        ColumnConfig<UzFormulaResultModel, Double> alphaParamCol = new ColumnConfig<>(properties.paramR(), 20, "alpha");
        ColumnConfig<UzFormulaResultModel, Double> epsilonParamCol = new ColumnConfig<>(properties.paramR(), 20, "epsilon");
        ColumnConfig<UzFormulaResultModel, Double> etaParamCol = new ColumnConfig<>(properties.paramR(), 20, "eta");
        ColumnConfig<UzFormulaResultModel, Double> zParamCol = new ColumnConfig<>(properties.paramR(), 20, "z");
        ColumnConfig<UzFormulaResultModel, Double> lParamCol = new ColumnConfig<>(properties.paramR(), 20, "l");
        ColumnConfig<UzFormulaResultModel, Double> rCalcCol = new ColumnConfig<>(properties.paramR(), 20, "Calc: R");
        ColumnConfig<UzFormulaResultModel, Double> pCalcCol = new ColumnConfig<>(properties.paramR(), 20, "Calc: P");
        ColumnConfig<UzFormulaResultModel, Double> qCalcCol = new ColumnConfig<>(properties.paramR(), 20, "Calc: Q");
        ColumnConfig<UzFormulaResultModel, Double> resultCol = new ColumnConfig<>(properties.paramR(), 30, "Result");

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

        /*resultArea = new TextArea();
        resultArea.setReadOnly(true);
        FieldLabel resultLabel = new FieldLabel(resultArea, "RESULT");
        resultLabel.setLabelAlign(FormPanel.LabelAlign.TOP);*/

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

       /* Anchor exportFormulaResultAnchor = new Anchor("Export formula results");
        exportFormulaResultAnchor.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                resultArea.finishEditing();
                AppController.eventBus.fireEvent(new FormulaResultExportEvent(Formula.UZ_FUNCTION, resultArea.getValue()));
            }
        });*/
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
    public SelectEvent.HasSelectHandlers getEditButton() {
        return editButton;
    }

    @Override
    public SelectEvent.HasSelectHandlers getDeleteButton() {
        return deleteButton;
    }

    @Override
    public SelectEvent.HasSelectHandlers getExportButton() {
        return exportButton;
    }

    @Override
    public SelectEvent.HasSelectHandlers getImportButton() {
        return importButton;
    }

    @Override
    public void addModel(UzFormulaResultModel model) {
        grid.getStore().add(model);
    }

    @Override
    public void updateModel(UzFormulaResultModel model) {
        //TODO
    }

    /*@Override
    public boolean isValid() {
        return true;
    }*/

    /*@Override
    public UzFormulaParamModel getModel() {
        rNumberField.finishEditing();
        aNumberField.finishEditing();
        eNumberField.finishEditing();
        mNumberField.finishEditing();
        zNumberField.finishEditing();
        lNumberField.finishEditing();

        UzFormulaParamModel model = new UzFormulaParamModel();
        model.setR(rNumberField.getValue());
        model.setA(aNumberField.getValue());
        model.setE(eNumberField.getValue());
        model.setM(mNumberField.getValue());
        model.setZ(zNumberField.getValue());
        model.setL(lNumberField.getValue());

        return model;
    }*/

    /*@Override
    public void appendResult(String result) {
        resultArea.finishEditing();
        String res = resultArea.getValue() + "\n" + result;
        resultArea.setValue(res);
    }*/

    @Override
    public Widget asWidget() {
        init();
        initGrid();
        initButtonToolbar();

        VerticalLayoutContainer vc = new VerticalLayoutContainer();
        vc.add(buttonToolbar, new VerticalLayoutContainer.VerticalLayoutData(1, -1, new Margins(5)));
        vc.add(filterToolbar, new VerticalLayoutContainer.VerticalLayoutData(1, -1, new Margins(5)));
        //vc.add(exportFormulaResultAnchor, new VerticalLayoutContainer.VerticalLayoutData(-1, 1, new Margins(0, 0, 0, 10)));
        //vc.add(resultLabel, new VerticalLayoutContainer.VerticalLayoutData(1, 1, new Margins(2, 10, 10, 10)));
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

    //private TextArea resultArea;

    private TextButton filterButton;

    private ToolBar filterToolbar;

    private Grid<UzFormulaResultModel> grid;

    private ToolBar buttonToolbar;
    private TextButton addButton;
    private TextButton editButton;
    private TextButton deleteButton;
    private TextButton refreshButton;
    private TextButton exportButton;
    private TextButton importButton;
}
