package edu.tsu.stochastic.automats.web.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootLayoutPanel;

public class App implements EntryPoint {
    @Override
    public void onModuleLoad() {
        ClientFactory clientFactory = new ClientFactoryImpl();
        AppController appViewer = new AppController(clientFactory);
        appViewer.go(RootLayoutPanel.get());
    }
}
