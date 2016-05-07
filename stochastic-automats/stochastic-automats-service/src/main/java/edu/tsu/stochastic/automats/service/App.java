package edu.tsu.stochastic.automats.service;

import edu.tsu.stochastic.automats.service.rest.FormulaService;

import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

public class App extends Application {
    private Set<Object> singletons = new HashSet();
    private Set<Class<?>> empty = new HashSet();

    public App() {
        // ADD YOUR RESTFUL RESOURCES HERE
        this.singletons.add(new FormulaService());
    }

    public Set<Class<?>> getClasses() {
        return this.empty;
    }

    public Set<Object> getSingletons() {
        return this.singletons;
    }
}
