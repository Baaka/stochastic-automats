package edu.tsu.stochastic.automats.web.client.icons;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ImageResource;

public interface Icons extends ClientBundle {
    Icons INSTANCE = GWT.create(Icons.class);

    @Source("formula.png")
    ImageResource formula();

    @Source("function.png")
    ImageResource function();
}
