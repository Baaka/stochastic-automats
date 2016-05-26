package edu.tsu.stochastic.automats.web.client.event;

import com.google.gwt.event.shared.GwtEvent;
import edu.tsu.stochastic.automats.web.shared.ExportFormat;
import edu.tsu.stochastic.automats.web.shared.Formula;

import java.util.List;

public class ResultExportEvent extends GwtEvent<ResultExportEventHandler> {
    public static final Type<ResultExportEventHandler> TYPE = new Type<>();
    private List<Long> resultIds;
    private Formula formula;
    private ExportFormat exportFormat;

    public ResultExportEvent(List<Long> resultIds, Formula formula, ExportFormat exportFormat) {
        this.resultIds = resultIds;
        this.formula = formula;
        this.exportFormat = exportFormat;
    }

    @Override
    public Type<ResultExportEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(ResultExportEventHandler handler) {
        handler.onExport(resultIds, formula, exportFormat);
    }
}
