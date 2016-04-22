package edu.tsu.stochastic.automats.core.helper;

public class FormulaHelper {
    public static double getSmallP(double r, double a) {
        return (1d - r - a) / 2d;
    }

    public static double getSmallQ(double r, double a) {
        return (1d - r + a) / 2d;
    }
}
