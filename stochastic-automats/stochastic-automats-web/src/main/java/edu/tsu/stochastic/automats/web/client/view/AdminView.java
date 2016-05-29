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
import com.sencha.gxt.widget.core.client.grid.CheckBoxSelectionModel;
import com.sencha.gxt.widget.core.client.grid.ColumnConfig;
import com.sencha.gxt.widget.core.client.grid.ColumnModel;
import com.sencha.gxt.widget.core.client.grid.Grid;
import com.sencha.gxt.widget.core.client.toolbar.PagingToolBar;
import com.sencha.gxt.widget.core.client.toolbar.SeparatorToolItem;
import com.sencha.gxt.widget.core.client.toolbar.ToolBar;
import edu.tsu.stochastic.automats.web.client.AppController;
import edu.tsu.stochastic.automats.web.client.icons.Icons;
import edu.tsu.stochastic.automats.web.client.presenter.AdminPresenter;
import edu.tsu.stochastic.automats.web.client.service.UtilService;
import edu.tsu.stochastic.automats.web.shared.UserModel;
import edu.tsu.stochastic.automats.web.shared.UserModelProperties;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AdminView implements AdminPresenter.Display {

    private final UserModelProperties properties = GWT.create(UserModelProperties.class);
    private PagingToolBar pagingToolBar = new PagingToolBar(10);

    private void initGrid() {
        RpcProxy<PagingLoadConfig, PagingLoadResult<UserModel>> proxy = new RpcProxy<PagingLoadConfig, PagingLoadResult<UserModel>>() {
            @Override
            public void load(PagingLoadConfig loadConfig, final AsyncCallback<PagingLoadResult<UserModel>> callback) {
                UtilService.Util.getInstance().loadUsers(loadConfig, new AsyncCallback<PagingLoadResult<UserModel>>() {
                    @Override
                    public void onFailure(Throwable caught) {
                        AppController.errorHandler.onError(caught);
                        callback.onFailure(caught);
                    }

                    @Override
                    public void onSuccess(PagingLoadResult<UserModel> result) {
                        callback.onSuccess(result);
                    }
                });
            }
        };

        ColumnConfig<UserModel, String> loginColumn = new ColumnConfig<UserModel, String>(properties.login(), 30, "Login");
        ColumnConfig<UserModel, String> phoneColumn = new ColumnConfig<UserModel, String>(properties.phone(), 30, "Phone");
        ColumnConfig<UserModel, String> emailColumn = new ColumnConfig<UserModel, String>(properties.email(), 30, "Email");
        ColumnConfig<UserModel, Boolean> blockedColumn = new ColumnConfig<UserModel, Boolean>(properties.blocked(), 10, "Blocked");
        ColumnConfig<UserModel, Date> lastLoginDateColumn = new ColumnConfig<UserModel, Date>(properties.lastLoginDate(), 30, "Last Login");

        ListStore<UserModel> store = new ListStore<>(new ModelKeyProvider<UserModel>() {
            @Override
            public String getKey(UserModel item) {
                return "" + item.getId();
            }
        });
        final PagingLoader<PagingLoadConfig, PagingLoadResult<UserModel>> loader = new PagingLoader<>(proxy);
        loader.addLoadHandler(new LoadResultListStoreBinding<PagingLoadConfig, UserModel, PagingLoadResult<UserModel>>(store));

        IdentityValueProvider<UserModel> identity = new IdentityValueProvider<>();
        final CheckBoxSelectionModel<UserModel> selectionModel = new CheckBoxSelectionModel<UserModel>(identity) {
            @Override
            protected void onRefresh(RefreshEvent event) {
                // this code selects all rows when paging if the header checkbox is selected
                if (isSelectAllChecked()) {
                    selectAll();
                }
                super.onRefresh(event);
            }
        };

        List<ColumnConfig<UserModel, ?>> columnConfigs = new ArrayList<>();
        columnConfigs.add(selectionModel.getColumn());
        columnConfigs.add(loginColumn);
        columnConfigs.add(phoneColumn);
        columnConfigs.add(emailColumn);
        columnConfigs.add(blockedColumn);
        columnConfigs.add(lastLoginDateColumn);

        ColumnModel<UserModel> cm = new ColumnModel<>(columnConfigs);

        grid = new Grid<UserModel>(store, cm) {
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

    private void initToolbar() {
        addUserButton = new TextButton("Add", Icons.INSTANCE.add());
        editUserButton = new TextButton("Edit", Icons.INSTANCE.edit());
        deleteUserButton = new TextButton("Delete", Icons.INSTANCE.delete());
        refreshButton = new TextButton("Refresh", Icons.INSTANCE.refresh());
        permissionsButton = new TextButton("Permissions", Icons.INSTANCE.export());

        buttonToolbar = new ToolBar();
        buttonToolbar.add(addUserButton);
        buttonToolbar.add(editUserButton);
        buttonToolbar.add(deleteUserButton);
        buttonToolbar.add(new SeparatorToolItem());
        buttonToolbar.add(refreshButton);
        buttonToolbar.add(new SeparatorToolItem());
        buttonToolbar.add(permissionsButton);
    }

    @Override
    public Widget asWidget() {
        initGrid();
        initToolbar();

        VerticalLayoutContainer vc = new VerticalLayoutContainer();
        vc.add(buttonToolbar, new VerticalLayoutContainer.VerticalLayoutData(1, -1, new Margins(5)));
        vc.add(grid, new VerticalLayoutContainer.VerticalLayoutData(1, 1, new Margins(5)));
        vc.add(pagingToolBar, new VerticalLayoutContainer.VerticalLayoutData(1, -1, new Margins(5)));

        return vc;
    }

    @Override
    public SelectEvent.HasSelectHandlers getAddUserButton() {
        return addUserButton;
    }

    @Override
    public SelectEvent.HasSelectHandlers getEditUserButton() {
        return editUserButton;
    }

    @Override
    public SelectEvent.HasSelectHandlers getDeleteUserButton() {
        return deleteUserButton;
    }

    @Override
    public SelectEvent.HasSelectHandlers getRefreshButton() {
        return refreshButton;
    }

    @Override
    public SelectEvent.HasSelectHandlers getPermissionsButton() {
        return permissionsButton;
    }

    @Override
    public void addModel(UserModel userModel) {
        grid.getStore().add(userModel);
    }

    @Override
    public void editModel(UserModel userModel) {
        grid.getStore().update(userModel);
    }

    @Override
    public void refresh() {
        grid.getLoader().load();
    }

    @Override
    public UserModel getSelectedModel() {
        return grid.getSelectionModel().getSelectedItem();
    }

    private Grid<UserModel> grid;
    private TextButton addUserButton;
    private TextButton editUserButton;
    private TextButton deleteUserButton;
    private TextButton refreshButton;
    private TextButton permissionsButton;
    private ToolBar buttonToolbar;
}
