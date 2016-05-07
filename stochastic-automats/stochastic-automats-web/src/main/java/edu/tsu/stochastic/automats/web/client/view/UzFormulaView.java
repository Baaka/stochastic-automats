package edu.tsu.stochastic.automats.web.client.view;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.BoxLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.form.*;
import com.sencha.gxt.widget.core.client.tips.ToolTipConfig;
import com.sencha.gxt.widget.core.client.toolbar.ToolBar;
import edu.tsu.stochastic.automats.web.client.AppController;
import edu.tsu.stochastic.automats.web.client.event.FormulaResultExportEvent;
import edu.tsu.stochastic.automats.web.client.presenter.UzFormulaPresenter;
import edu.tsu.stochastic.automats.web.shared.Formula;
import edu.tsu.stochastic.automats.web.shared.UzFormulaParamModel;

public class UzFormulaView implements UzFormulaPresenter.Display {

    private void init() {

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

        calculateButton = new TextButton("calculate");

        resultArea = new TextArea();
        resultArea.setReadOnly(true);
        FieldLabel resultLabel = new FieldLabel(resultArea, "RESULT");
        resultLabel.setLabelAlign(FormPanel.LabelAlign.TOP);

        BoxLayoutContainer.BoxLayoutData flexData = new BoxLayoutContainer.BoxLayoutData();
        flexData.setFlex(1d);

        ToolBar toolBar = new ToolBar();
        toolBar.setHorizontalSpacing(5);
        toolBar.add(rNumberField, flexData);
        toolBar.add(aNumberField, flexData);
        toolBar.add(eNumberField, flexData);
        toolBar.add(mNumberField, flexData);
        toolBar.add(zNumberField, flexData);
        toolBar.add(lNumberField, flexData);
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
    }

    @Override
    public void appendResult(String result) {
        resultArea.finishEditing();
        String res = resultArea.getValue() + "\n" + result;
        resultArea.setValue(res);
    }

    @Override
    public Widget asWidget() {
        init();
        return vc;
    }

    private NumberField<Double> rNumberField;
    private NumberField<Double> aNumberField;
    private NumberField<Double> eNumberField;
    private NumberField<Double> mNumberField;
    private NumberField<Double> zNumberField;
    private NumberField<Double> lNumberField;

    private TextArea resultArea;

    private TextButton calculateButton;
    private VerticalLayoutContainer vc;
}
