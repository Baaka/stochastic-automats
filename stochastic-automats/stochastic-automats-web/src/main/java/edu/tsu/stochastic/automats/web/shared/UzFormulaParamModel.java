package edu.tsu.stochastic.automats.web.shared;

import com.google.gwt.core.shared.GwtIncompatible;

import java.io.Serializable;

public class UzFormulaParamModel implements Serializable {
    private double r;
    private double a;
    private double e;
    private double m;
    private double z;
    private double l;

    public UzFormulaParamModel() {
    }

    public UzFormulaParamModel(double r, double a, double e, double m, double z, double l) {
        this.r = r;
        this.a = a;
        this.e = e;
        this.m = m;
        this.z = z;
        this.l = l;
    }

    public double getR() {
        return r;
    }

    public void setR(double r) {
        this.r = r;
    }

    public double getA() {
        return a;
    }

    public void setA(double a) {
        this.a = a;
    }

    public double getE() {
        return e;
    }

    public void setE(double e) {
        this.e = e;
    }

    public double getM() {
        return m;
    }

    public void setM(double m) {
        this.m = m;
    }

    public double getZ() {
        return z;
    }

    public void setZ(double z) {
        this.z = z;
    }

    public double getL() {
        return l;
    }

    public void setL(double l) {
        this.l = l;
    }

    @GwtIncompatible
    public edu.tsu.stochastic.automats.core.model.UzFormulaParamModel toUzFormulaCodeParamModel() {
        edu.tsu.stochastic.automats.core.model.UzFormulaParamModel model = new edu.tsu.stochastic.automats.core.model.UzFormulaParamModel();

        model.setA(a);
        model.setL(l);
        model.setR(r);
        model.setZ(z);
        model.setM(m);
        model.setE(e);

        return model;
    }

}
