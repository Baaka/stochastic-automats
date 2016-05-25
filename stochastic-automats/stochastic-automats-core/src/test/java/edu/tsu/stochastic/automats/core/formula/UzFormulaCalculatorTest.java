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
        double e = 0.3d;
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


}
