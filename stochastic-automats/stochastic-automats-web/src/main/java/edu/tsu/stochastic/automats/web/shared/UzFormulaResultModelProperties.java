package edu.tsu.stochastic.automats.web.shared;

import com.google.gwt.editor.client.Editor;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;

public interface UzFormulaResultModelProperties extends PropertyAccess<UzFormulaResultModel> {
    @Editor.Path("id")
    ModelKeyProvider<UzFormulaResultModel> key();

    ValueProvider<UzFormulaResultModel, Double> paramR();

    ValueProvider<UzFormulaResultModel, Double> paramAlpha();

    ValueProvider<UzFormulaResultModel, Double> paramEpsilon();

    ValueProvider<UzFormulaResultModel, Double> paramEta();

    ValueProvider<UzFormulaResultModel, Double> paramZ();

    ValueProvider<UzFormulaResultModel, Double> paramL();

    ValueProvider<UzFormulaResultModel, Double> r();

    ValueProvider<UzFormulaResultModel, Double> p();

    ValueProvider<UzFormulaResultModel, Double> q();

    ValueProvider<UzFormulaResultModel, Double> result();
}
