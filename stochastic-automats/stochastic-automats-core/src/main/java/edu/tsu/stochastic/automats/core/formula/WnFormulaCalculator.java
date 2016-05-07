package edu.tsu.stochastic.automats.core.formula;

import edu.tsu.stochastic.automats.core.model.WnFormulaModel;

public class WnFormulaCalculator implements Formula {
    private WnFormulaModel model;
    private final double p1;
    private final double q1;
    private final double p2;
    private final double q2;

    public WnFormulaCalculator(WnFormulaModel model) {
        this.model = model;

        p1 = getBigPForWn(model.getR1(), model.getE1(), model.getA1());
        q1 = getBigQForWn(model.getR1(), model.getZ1(), model.getA1());

        p2 = getBigPForWn(model.getR2(), model.getE2(), model.getA2());
        q2 = getBigQForWn(model.getR2(), model.getZ2(), model.getA2());
    }

    public double getResult(int n, int x) {
        return calculateWn(p1, p2, q1, q2, x, n);
    }

    public double getP1() {
        return p1;
    }

    public double getQ1() {
        return q1;
    }

    public double getP2() {
        return p2;
    }

    public double getQ2() {
        return q2;
    }

    private double calculateWn(double p1, double p2, double q1, double q2, int x, int n) {
        double multiplier = (p1 * p2) / (q1 * q2);

        double point1 = (Math.pow(q1 - p1, 2) / p1) * (Math.pow(q2 / p2, x) - 1) * (Math.pow(p1 / q1, n) * (Math.pow(p2 / q2, x - 1)));
        double point2 = (Math.pow(q2 - p2, 2) / p2) * (Math.pow(q1 / p1, x) - 1) * (Math.pow(p2 / q2, n) * (Math.pow(p1 / q1, x - 1)));

        double separator = 1d - (Math.pow(p1 / q1, x) * Math.pow(p2 / q2, x));

        return (multiplier * (point1 + point2)) / separator;
    }

    private double getBigQForWn(double r, double z, double a) {
        double q = (1d - r + a) / 2d;
        return q + (z * r);
    }

    private double getBigPForWn(double r, double e, double a) {
        double p = (1d - r - a) / 2d;
        return p + (e * r);
    }
}
