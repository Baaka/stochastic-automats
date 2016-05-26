package edu.tsu.stochastic.automats.web.client.event;

import com.google.gwt.event.shared.EventHandler;
import edu.tsu.stochastic.automats.web.shared.ExportFormat;
import edu.tsu.stochastic.automats.web.shared.Formula;

import java.util.List;

public interface ResultExportEventHandler extends EventHandler {
    void onExport(List<Long> ids, Formula formula, ExportFormat exportFormat);
}
