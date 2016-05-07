package edu.tsu.stochastic.automats.web.client.view;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.BoxLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.form.*;
import com.sencha.gxt.widget.core.client.toolbar.ToolBar;
import edu.tsu.stochastic.automats.web.client.AppController;
import edu.tsu.stochastic.automats.web.client.event.FormulaResultExportEvent;
import edu.tsu.stochastic.automats.web.client.presenter.WnFormulaPresenter;
import edu.tsu.stochastic.automats.web.shared.Formula;
import edu.tsu.stochastic.automats.web.shared.WnFormulaParamModel;

public class WnFormulaView implements WnFormulaPresenter.Display {

    private void init() {
        r1Field = new NumberField<Double>(new NumberPropertyEditor.DoublePropertyEditor());
        r1Field.setEmptyText("r1");
        r1Field.setToolTip(r1Field.getEmptyText());

        r2Field = new NumberField<Double>(new NumberPropertyEditor.DoublePropertyEditor());
        r2Field.setEmptyText("r2");
        r2Field.setToolTip(r2Field.getEmptyText());

        a1Field = new NumberField<Double>(new NumberPropertyEditor.DoublePropertyEditor());
        a1Field.setEmptyText("a1");
        a1Field.setToolTip(a1Field.getEmptyText());

        a2Field = new NumberField<Double>(new NumberPropertyEditor.DoublePropertyEditor());
        a2Field.setEmptyText("a2");
        a2Field.setToolTip(a2Field.getEmptyText());

        e1Field = new NumberField<Double>(new NumberPropertyEditor.DoublePropertyEditor());
        e1Field.setEmptyText("e1");
        e1Field.setToolTip(e1Field.getEmptyText());

        e2Field = new NumberField<Double>(new NumberPropertyEditor.DoublePropertyEditor());
        e2Field.setEmptyText("e2");
        e2Field.setToolTip(e2Field.getEmptyText());

        z1Field = new NumberField<Double>(new NumberPropertyEditor.DoublePropertyEditor());
        z1Field.setEmptyText("z1");
        z1Field.setToolTip(z1Field.getEmptyText());

        z2Field = new NumberField<Double>(new NumberPropertyEditor.DoublePropertyEditor());
        z2Field.setEmptyText("z2");
        z2Field.setToolTip(z2Field.getEmptyText());

        maxNField = new NumberField<Integer>(new NumberPropertyEditor.IntegerPropertyEditor());
        maxNField.setEmptyText("n");
        maxNField.setToolTip(maxNField.getEmptyText());

        calculateButton = new TextButton("calculate");

        resultArea = new TextArea();
        resultArea.setReadOnly(true);
        FieldLabel resultLabel = new FieldLabel(resultArea, "RESULT");
        resultLabel.setLabelAlign(FormPanel.LabelAlign.TOP);

        BoxLayoutContainer.BoxLayoutData flexData = new BoxLayoutContainer.BoxLayoutData();
        flexData.setFlex(1d);

        ToolBar toolBar = new ToolBar();
        toolBar.setHorizontalSpacing(5);
        toolBar.add(r1Field, flexData);
        toolBar.add(r2Field, flexData);
        toolBar.add(a1Field, flexData);
        toolBar.add(a2Field, flexData);
        toolBar.add(e1Field, flexData);
        toolBar.add(e2Field, flexData);
        toolBar.add(z1Field, flexData);
        toolBar.add(z2Field, flexData);
        toolBar.add(maxNField, flexData);
        toolBar.add(calculateButton);

        Anchor exportFormulaResultAnchor = new Anchor("Export formula results");
        exportFormulaResultAnchor.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                resultArea.finishEditing();
                AppController.eventBus.fireEvent(new FormulaResultExportEvent(Formula.UZ_FUNCTION, resultArea.getValue()));
            }
        });

        vc = new VerticalLayoutContainer();
        vc.add(toolBar, new VerticalLayoutContainer.VerticalLayoutData(1, -1, new Margins(10)));
        vc.add(exportFormulaResultAnchor, new VerticalLayoutContainer.VerticalLayoutData(-1, 1, new Margins(0, 0, 0, 10)));
        vc.add(resultLabel, new VerticalLayoutContainer.VerticalLayoutData(1, 1, new Margins(2, 10, 10, 10)));
    }

    @Override
    public SelectEvent.HasSelectHandlers getCalculateButton() {
        return calculateButton;
    }

    //TODO
    @Override
    public boolean isValid() {
        return true;
    }

    @Override
    public WnFormulaParamModel getModel() {
        r1Field.finishEditing();
        a1Field.finishEditing();
        e1Field.finishEditing();
        z1Field.finishEditing();
        r2Field.finishEditing();
        a2Field.finishEditing();
        e2Field.finishEditing();
        z2Field.finishEditing();

        WnFormulaParamModel model = new WnFormulaParamModel();
        model.setR1(r1Field.getValue());
        model.setR2(r2Field.getValue());
        model.setA1(a1Field.getValue());
        model.setA2(a2Field.getValue());
        model.setE1(e1Field.getValue());
        model.setE2(e2Field.getValue());
        model.setZ1(z1Field.getValue());
        model.setZ2(z2Field.getValue());

        return model;
    }

    @Override
    public void setResult(String result) {
        resultArea.clear();
        resultArea.setValue(result);
    }

    @Override
    public HasValue<Integer> getMaxN() {
        maxNField.finishEditing();
        return maxNField;
    }

    @Override
    public Widget asWidget() {
        init();
        return vc;
    }

    private VerticalLayoutContainer vc;
    private TextButton calculateButton;
    private TextArea resultArea;
    private NumberField<Double> r1Field;
    private NumberField<Double> a1Field;
    private NumberField<Double> e1Field;
    private NumberField<Double> z1Field;
    private NumberField<Double> r2Field;
    private NumberField<Double> a2Field;
    private NumberField<Double> e2Field;
    private NumberField<Double> z2Field;
    private NumberField<Integer> maxNField;
}
