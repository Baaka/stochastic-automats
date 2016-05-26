package edu.tsu.stochastic.automats.web.client.event;

import com.google.gwt.event.shared.GwtEvent;
import edu.tsu.stochastic.automats.web.shared.Formula;

public class ImportFormulaEvent extends GwtEvent<ImportFormulaEventHandler> {
    public static final Type<ImportFormulaEventHandler> TYPE = new Type<>();
    private Formula formula;

    public ImportFormulaEvent(Formula formula) {
        this.formula = formula;
    }

    @Override
    public Type<ImportFormulaEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(ImportFormulaEventHandler handler) {
        handler.onImport(formula);
    }
}
