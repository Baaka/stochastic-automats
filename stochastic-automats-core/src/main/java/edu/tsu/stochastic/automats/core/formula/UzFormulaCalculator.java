package edu.tsu.stochastic.automats.core.formula;

import edu.tsu.stochastic.automats.core.helper.FormulaHelper;
import edu.tsu.stochastic.automats.core.model.UzFormulaModel;

public class UzFormulaCalculator implements Formula {

    private UzFormulaModel model;

    private final double r;
    private final double p;
    private final double q;

    public UzFormulaCalculator(UzFormulaModel model) {
        this.model = model;

        r = getRForUz(model.getE(), model.getM(), model.getR());
        p = getPForUz(model.getR(), model.getA(), model.getE());
        q = getQForUz(model.getR(), model.getA(), model.getM());
    }

    public double getResult() {
        return calculateUz(model.getZ(), p, q, r, model.getL());
    }

    public double getR() {
        return r;
    }

    public double getP() {
        return p;
    }

    public double getQ() {
        return q;
    }

    private double calculateUz(double z, double p, double q, double r, double l) {
        double par1 = (p * z) / (1d - r * z);
        double par2 = ((q * z) / (1d - r * z)) * (Math.pow((p * z) / (1d - r * z), l + 1));
        double par3 = (l + 1d) * (Math.pow((q * z) / (1d - r * z), 2)) * (Math.pow((p * z) / (1 - r * z), 2 * l + 1));
        double par4 = (((l + 1d) * (3d * l + 2d)) / 2d) * (Math.pow((q * z) / (1d - r * z), 3)) * (Math.pow((p * z) / (1d - r * z), 3 * l + 1));

        return par1 + par2 + par3 + par4;
    }

    private double getRForUz(double e, double m, double r) {
        return (1d - e - m) * r;
    }

    private double getPForUz(double r, double a, double e) {
        double p = FormulaHelper.getSmallP(r, a);
        return p + (e * r);
    }

    private double getQForUz(double r, double a, double m) {
        double q = FormulaHelper.getSmallQ(r, a);
        return q + (m * r);
    }
}