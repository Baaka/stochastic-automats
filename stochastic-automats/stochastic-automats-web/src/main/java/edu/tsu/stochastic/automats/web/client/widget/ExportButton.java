package edu.tsu.stochastic.automats.web.client.widget;

import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.resources.client.ImageResource;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.menu.Item;
import com.sencha.gxt.widget.core.client.menu.Menu;
import com.sencha.gxt.widget.core.client.menu.MenuItem;
import edu.tsu.stochastic.automats.web.client.icons.Icons;
import edu.tsu.stochastic.automats.web.shared.ExportFormat;

public class ExportButton extends TextButton {

    public ExportButton(String text, ImageResource icon, SelectionHandler<Item> selectionHandler) {
        super(text, icon);
        setMenu(createMenu(selectionHandler));
    }

    private Menu createMenu(SelectionHandler<Item> selectionHandler) {
        Menu menu = new Menu();
        for (ExportFormat format : ExportFormat.values()) {
            if (format != ExportFormat.HTML && format != ExportFormat.PDF) {
                MenuItem menuItem = new MenuItem(format.getExtension());
                menuItem.setIcon(getMenuIcon(format));
                menuItem.setData("exportFormat", format);
                menuItem.addSelectionHandler(selectionHandler);
                menu.add(menuItem);
            }
        }
        return menu;
    }

    private ImageResource getMenuIcon(ExportFormat format) {
        ImageResource icon = null;
        switch (format) {
            case TXT:
                icon = Icons.INSTANCE.text();
                break;
            case HTML:
                icon = Icons.INSTANCE.html();
                break;
            case PDF:
                icon = Icons.INSTANCE.pdf();
                break;
            case XLS:
                icon = Icons.INSTANCE.excel();
                break;
        }
        return icon;
    }
}
