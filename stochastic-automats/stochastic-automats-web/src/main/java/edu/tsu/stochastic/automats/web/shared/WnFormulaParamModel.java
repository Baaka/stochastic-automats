package edu.tsu.stochastic.automats.web.shared;

import com.google.gwt.core.shared.GwtIncompatible;
import edu.tsu.stochastic.automats.core.model.WnFormulaModel;

import java.io.Serializable;

public class WnFormulaParamModel implements Serializable {
    private double r1;
    private double a1;
    private double e1;
    private double z1;

    private double r2;
    private double a2;
    private double e2;
    private double z2;

    public WnFormulaParamModel() {
    }

    public WnFormulaParamModel(double r1, double a1, double e1, double z1, double r2, double a2, double e2, double z2) {
        this.r1 = r1;
        this.a1 = a1;
        this.e1 = e1;
        this.z1 = z1;
        this.r2 = r2;
        this.a2 = a2;
        this.e2 = e2;
        this.z2 = z2;
    }

    public double getR1() {
        return r1;
    }

    public void setR1(double r1) {
        this.r1 = r1;
    }

    public double getA1() {
        return a1;
    }

    public void setA1(double a1) {
        this.a1 = a1;
    }

    public double getE1() {
        return e1;
    }

    public void setE1(double e1) {
        this.e1 = e1;
    }

    public double getZ1() {
        return z1;
    }

    public void setZ1(double z1) {
        this.z1 = z1;
    }

    public double getR2() {
        return r2;
    }

    public void setR2(double r2) {
        this.r2 = r2;
    }

    public double getA2() {
        return a2;
    }

    public void setA2(double a2) {
        this.a2 = a2;
    }

    public double getE2() {
        return e2;
    }

    public void setE2(double e2) {
        this.e2 = e2;
    }

    public double getZ2() {
        return z2;
    }

    public void setZ2(double z2) {
        this.z2 = z2;
    }

    @GwtIncompatible
    public WnFormulaModel toWnFormulaModel() {
        WnFormulaModel model = new WnFormulaModel();

        model.setR1(r1);
        model.setR2(r2);
        model.setA1(a1);
        model.setA2(a2);
        model.setE1(e1);
        model.setE2(e2);
        model.setZ1(z1);
        model.setZ2(z2);

        return model;
    }

    @Override
    public String toString() {
        return "WnFormulaModel{" +
                ", r1=" + r1 +
                ", a1=" + a1 +
                ", e1=" + e1 +
                ", z1=" + z1 +
                ", r2=" + r2 +
                ", a2=" + a2 +
                ", e2=" + e2 +
                ", z2=" + z2 +
                '}';
    }
}
