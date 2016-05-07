package edu.tsu.stochastic.automats.web.shared;

import java.io.Serializable;

public class UzFormulaResultModel implements Serializable {
    private double r;
    private double q;
    private double p;
    private double result;

    public UzFormulaResultModel() {
    }

    public double getR() {
        return r;
    }

    public void setR(double r) {
        this.r = r;
    }

    public double getQ() {
        return q;
    }

    public void setQ(double q) {
        this.q = q;
    }

    public double getP() {
        return p;
    }

    public void setP(double p) {
        this.p = p;
    }

    public double getResult() {
        return result;
    }

    public void setResult(double result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "UzFormulaResultModel{" +
                "result=" + result +
                ", p=" + p +
                ", q=" + q +
                ", r=" + r +
                '}';
    }
}
