package edu.tsu.stochastic.automats.web.shared;

import com.google.gwt.editor.client.Editor;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;

public interface DataPropertyAccess extends PropertyAccess<Data> {
    ValueProvider<Data, String> name();

    ValueProvider<Data, Double> data();

    @Editor.Path("name")
    ModelKeyProvider<Data> nameKey();
}