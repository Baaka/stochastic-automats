package edu.tsu.stochastic.automats.web.shared;

import java.io.Serializable;
import java.util.Map;
import java.util.TreeMap;

public class WnFormulaResultModel implements Serializable {
    private double p1;
    private double q1;
    private double p2;
    private double q2;

    // n => x => result
    private Map<Integer, Map<Integer, Double>> resultStore;


    public WnFormulaResultModel() {
    }

    public double getP1() {
        return p1;
    }

    public void setP1(double p1) {
        this.p1 = p1;
    }

    public double getQ1() {
        return q1;
    }

    public void setQ1(double q1) {
        this.q1 = q1;
    }

    public double getP2() {
        return p2;
    }

    public void setP2(double p2) {
        this.p2 = p2;
    }

    public double getQ2() {
        return q2;
    }

    public void setQ2(double q2) {
        this.q2 = q2;
    }

    public Map<Integer, Map<Integer, Double>> getResultStore() {
        return resultStore;
    }

    public void addResult(int n, int x, double result) {
        if (resultStore == null) {
            resultStore = new TreeMap<>();
        }

        if (resultStore.get(n) != null) {
            resultStore.get(n).put(x, result);
        } else {
            Map<Integer, Double> results = new TreeMap<>();
            results.put(x, result);
            resultStore.put(n, results);
        }
    }

}
