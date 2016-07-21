package edu.tsu.stochastic.automats.core.formula;

import edu.tsu.stochastic.automats.core.model.UzFormulaParamModel;
import org.junit.Before;
import org.junit.Test;

public class UzFormulaCalculatorTest {

    private UzFormulaParamModel model;

    @Before
    public void init() {
        double r = 0.5d;
        double a = 0.2d;
        double e = 0.4d;
        double m = 0.5d;

        double z = 1;
        double l = 1;

        model = new UzFormulaParamModel(r, a, e, m, z, l);
    }

    @Test
    public void test() {
        UzFormulaCalculator formula = new UzFormulaCalculator(model);

        System.out.println(model);
        System.out.println("Q = " + formula.getQ() + "; P = " + formula.getP() + "; R = " + formula.getR());
        System.out.println("Result: " + formula.getResult());
    }

    @Test
    public void testPrivateCaseOne() { // case when l = 1;
        UzFormulaCalculator formula = new UzFormulaCalculator(model);

        System.out.println("l = 1; r = " + model.getR() + "; a = " + model.getA() + "; m = " + model.getM());
        double res = (formula.getP() / formula.getQ());
        System.out.println("Result: " + res);
    }

    @Test
    public void testPrivateCaseTwo() { // case when l = 2;
        UzFormulaCalculator formula = new UzFormulaCalculator(model);

        double res = (Math.sqrt(1d + 4d * formula.getP() / formula.getQ()) - 1d) / 2d;

        System.out.println("l = 2; r = " + model.getR() + "; a = " + model.getA() + "; m = " + model.getM());
        System.out.println("Result = " + res);
    }


    @Test
    public void haha2() {
        double epsilon = 0.4d;
        double eta = 0.5d;

        double r = 0.2d;
        double p = 0.1d;
        double q = 0.7d;

        int l = 2;
        //int z = 1;

        double bigP = getP(p, epsilon, r);
        double bigQ = getQ(q, eta, r);
        double bigR = getR(epsilon, eta, r);

        double first = (bigP / (1 - bigR));
        double second = (bigQ / (1 - bigR)) * (Math.pow(bigP / (1 - bigR), (l + 1)));
        double third = (l + 1) * (Math.pow(bigQ / (1 - bigR), 2)) * (Math.pow(bigP / (1 - bigR), (2 * l + 1)));
        double fourth = ((l + 1) * (3 * l + 2)) / 2 * Math.pow(bigQ / (1 - bigR), 3) * Math.pow(bigP / (1 - bigR), 3 * l + 1);

        System.out.println("R = " + bigR + "; P = " + bigP + "; Q = " + bigQ);
        System.out.println("Our Res: " + (first + second + third + fourth));

        double privCase = getPrivateCaseResult(bigP, bigQ, l);
        System.out.println("Priv: " + privCase);

    }

    private double getPrivateCaseResult(double bigP, double bigQ, int l) {
        if (l == 1) {
            return bigP / bigQ;
        } else if (l == 2) {
            double res = (Math.sqrt(1 + (4 * (bigP / bigQ))) - 1) / 2;
            return res;
        }
        return -1;
    }

    private double getR(double epsilon, double eta, double r) {
        return ((1 - epsilon - eta) * r);
    }

    private double getQ(double q, double eta, double r) {
        return (q + (eta * r));
    }

    private double getP(double p, double epsilon, double r) {
        return (p + (epsilon * r));
    }
}
