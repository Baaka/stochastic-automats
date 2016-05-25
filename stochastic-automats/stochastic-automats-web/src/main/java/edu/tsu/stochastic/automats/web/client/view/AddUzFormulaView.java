package edu.tsu.stochastic.automats.web.client.view;

import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.widget.core.client.Window;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.BoxLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.form.FieldLabel;
import com.sencha.gxt.widget.core.client.form.NumberField;
import com.sencha.gxt.widget.core.client.form.NumberPropertyEditor;
import edu.tsu.stochastic.automats.web.client.icons.Icons;
import edu.tsu.stochastic.automats.web.client.presenter.AddUzFormulaPresenter;
import edu.tsu.stochastic.automats.web.shared.UzFormulaParamModel;

public class AddUzFormulaView implements AddUzFormulaPresenter.Display {

    private void init() {
        saveButton = new TextButton("Save", Icons.INSTANCE.calculate());
        cancelButton = new TextButton("Cancel", Icons.INSTANCE.cancel());

        NumberPropertyEditor editor = new NumberPropertyEditor.DoublePropertyEditor();

        rParamField = new NumberField<Double>(editor);
        rParamField.setAllowBlank(false);

        aParamField = new NumberField<Double>(editor);
        aParamField.setAllowBlank(false);

        eParamField = new NumberField<Double>(editor);
        eParamField.setAllowBlank(false);

        mParamField = new NumberField<Double>(editor);
        mParamField.setAllowBlank(false);

        zParamField = new NumberField<Double>(editor);
        zParamField.setAllowBlank(false);

        lParamField = new NumberField<Double>(editor);
        lParamField.setAllowBlank(false);

        VerticalLayoutContainer.VerticalLayoutData vld = new VerticalLayoutContainer.VerticalLayoutData(1, -1, new Margins(5, 10, 5, 10));

        VerticalLayoutContainer vc = new VerticalLayoutContainer();
        vc.add(getLabel(rParamField, "r"), vld);
        vc.add(getLabel(aParamField, "a"), vld);
        vc.add(getLabel(eParamField, "e"), vld);
        vc.add(getLabel(mParamField, "m"), vld);
        vc.add(getLabel(zParamField, "z"), vld);
        vc.add(getLabel(lParamField, "l"), vld);

        window = new Window();
        window.setHeadingHtml("<center>Add U(z) Formula Params</center>");
        window.setWidth("450px");
        window.setHeight("320px");
        window.setWidget(vc);
        window.setButtonAlign(BoxLayoutContainer.BoxLayoutPack.CENTER);
        window.addButton(saveButton);
        window.addButton(cancelButton);
        window.setModal(true);
    }

    private FieldLabel getLabel(Widget widget, String title) {
        return new FieldLabel(widget, title);
    }

    @Override
    public SelectEvent.HasSelectHandlers getSaveButton() {
        return saveButton;
    }

    @Override
    public SelectEvent.HasSelectHandlers getCancelButton() {
        return cancelButton;
    }

    @Override
    public UzFormulaParamModel getParamModel() {
        UzFormulaParamModel model = new UzFormulaParamModel();
        model.setR(rParamField.getValue());
        model.setA(aParamField.getValue());
        model.setE(eParamField.getValue());
        model.setM(mParamField.getValue());
        model.setZ(zParamField.getValue());
        model.setL(lParamField.getValue());

        return model;
    }

    @Override
    public boolean isValid() {
        rParamField.finishEditing();
        aParamField.finishEditing();
        eParamField.finishEditing();
        mParamField.finishEditing();
        zParamField.finishEditing();
        lParamField.finishEditing();

        boolean isValid = rParamField.isValid();
        isValid &= aParamField.isValid();
        isValid &= eParamField.isValid();
        isValid &= mParamField.isValid();
        isValid &= zParamField.isValid();
        isValid &= lParamField.isValid();

        return isValid;
    }

    @Override
    public void close() {
        window.clear();
        window.hide();
    }

    @Override
    public void mask(boolean isMask) {
        if (isMask) {
            window.mask("Please wait...");
            return;
        }
        window.unmask();
    }

    @Override
    public Widget asWidget() {
        init();
        window.show();
        return window;
    }

    private Window window;

    private TextButton saveButton;
    private TextButton cancelButton;

    private NumberField<Double> rParamField;
    private NumberField<Double> aParamField;
    private NumberField<Double> eParamField;
    private NumberField<Double> mParamField;
    private NumberField<Double> zParamField;
    private NumberField<Double> lParamField;
}
