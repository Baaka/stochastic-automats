package edu.tsu.stochastic.automats.web.client.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.FileUpload;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.cell.core.client.form.ComboBoxCell;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.data.shared.LabelProvider;
import com.sencha.gxt.widget.core.client.Window;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.BoxLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.form.ComboBox;
import com.sencha.gxt.widget.core.client.form.FieldLabel;
import com.sencha.gxt.widget.core.client.form.FormPanel;
import com.sencha.gxt.widget.core.client.form.SimpleComboBox;
import edu.tsu.stochastic.automats.web.client.presenter.FileImportPresenter;
import edu.tsu.stochastic.automats.web.shared.Formula;

import java.util.Arrays;

public class FileImportView implements FileImportPresenter.Display {

    private void init() {
        submitButton = new TextButton("Submit");
        cancelButton = new TextButton("Cancel");

        formulaComboBox = new SimpleComboBox<>(new LabelProvider<Formula>() {
            @Override
            public String getLabel(Formula item) {
                return item.getFunctionName();
            }
        });
        formulaComboBox.setAllowBlank(false);
        formulaComboBox.setTriggerAction(ComboBoxCell.TriggerAction.ALL);
        formulaComboBox.getStore().addAll(Arrays.asList(Formula.values()));
        formulaComboBox.disable();
        FieldLabel formulaComboLabel = new FieldLabel(formulaComboBox, "Formula");

        fileUpload = new FileUpload();
        fileUpload.setName("upload");
        fileUpload.setWidth("100%");
        fileUpload.getElement().setPropertyString("accept", ".xlsx,xls");
        FieldLabel fileUploadLabel = new FieldLabel(fileUpload, "File");

        VerticalLayoutContainer vc = new VerticalLayoutContainer();
        vc.add(formulaComboLabel, new VerticalLayoutContainer.VerticalLayoutData(1, -1, new Margins(5)));
        vc.add(fileUploadLabel, new VerticalLayoutContainer.VerticalLayoutData(1, -1,new Margins(5)));

        formPanel = new FormPanel();
        formPanel.setEncoding(FormPanel.Encoding.MULTIPART);
        formPanel.setMethod(FormPanel.Method.POST);
        formPanel.add(vc);

        window = new Window();
        window.setHeadingHtml("<center>File Import</center>");
        window.setWidth("350px");
        window.setHeight("180px");
        window.setButtonAlign(BoxLayoutContainer.BoxLayoutPack.CENTER);
        window.getButtonBar().add(submitButton);
        window.getButtonBar().add(cancelButton);
        window.setModal(true);
        window.add(formPanel);
    }

    @Override
    public HasValue<Formula> getSelectedFormula() {
        formulaComboBox.finishEditing();
        return formulaComboBox;
    }

    @Override
    public SelectEvent.HasSelectHandlers getSubmitButton() {
        return submitButton;
    }

    @Override
    public SelectEvent.HasSelectHandlers getCancelButton() {
        return cancelButton;
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
    public void submit() {
        formPanel.setAction(GWT.getModuleBaseURL() + "fileImportServlet?formula=" + formulaComboBox.getValue().ordinal());
        formPanel.submit();
    }

    @Override
    public boolean isValid() {
        return fileUpload.getFilename() != null && !fileUpload.getFilename().isEmpty()
                && formulaComboBox.isValid();
    }

    @Override
    public FormPanel getForm() {
        return formPanel;
    }

    @Override
    public Widget asWidget() {
        init();
        window.show();
        return window;
    }

    private TextButton submitButton;
    private TextButton cancelButton;
    private ComboBox<Formula> formulaComboBox;
    private Window window;
    private FileUpload fileUpload;
    private FormPanel formPanel;
}
