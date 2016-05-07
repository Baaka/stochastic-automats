package edu.tsu.stochastic.automats.web.client.event;

import com.google.gwt.event.shared.EventHandler;
import edu.tsu.stochastic.automats.web.shared.Formula;

public interface FormulaResultExportEventHandler extends EventHandler {
    void onExport(Formula formula, String result);
}
