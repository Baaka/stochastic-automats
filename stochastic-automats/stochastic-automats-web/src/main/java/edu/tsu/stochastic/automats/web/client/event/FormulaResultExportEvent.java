package edu.tsu.stochastic.automats.web.client.event;

import com.google.gwt.event.shared.GwtEvent;
import edu.tsu.stochastic.automats.web.shared.Formula;

public class FormulaResultExportEvent extends GwtEvent<FormulaResultExportEventHandler> {
    public static final Type<FormulaResultExportEventHandler> TYPE = new Type<>();
    private Formula formula;
    private String result;

    public FormulaResultExportEvent(Formula formula, String result) {
        this.formula = formula;
        this.result = result;
    }

    @Override
    public Type<FormulaResultExportEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(FormulaResultExportEventHandler addPeriodEventHandler) {
        addPeriodEventHandler.onExport(formula, result);
    }
}
