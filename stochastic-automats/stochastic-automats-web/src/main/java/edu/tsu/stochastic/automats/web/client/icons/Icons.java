package edu.tsu.stochastic.automats.web.client.icons;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ImageResource;

public interface Icons extends ClientBundle {
    Icons INSTANCE = GWT.create(Icons.class);

    @Source("formula.png")
    ImageResource formula();

    @Source("function.png")
    ImageResource function();

    @Source("edit.png")
    ImageResource edit();

    @Source("add.png")
    ImageResource add();

    @Source("delete.png")
    ImageResource delete();

    @Source("refresh.png")
    ImageResource refresh();

    @Source("search.png")
    ImageResource search();

    @Source("export.png")
    ImageResource export();

    @Source("import.png")
    ImageResource importSmall();

    @Source("calculate.png")
    ImageResource calculate();

    @Source("cancel.png")
    ImageResource cancel();

    @Source("excel.png")
    ImageResource excel();

    @Source("pdf.png")
    ImageResource pdf();

    @Source("text.png")
    ImageResource text();

    @Source("html.png")
    ImageResource html();

    @Source("tsu.png")
    ImageResource tsu();
}
