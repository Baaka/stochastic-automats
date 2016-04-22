package edu.tsu.stochastic.automats.core.formula;

import edu.tsu.stochastic.automats.core.model.UzFormulaModel;
import org.junit.Before;
import org.junit.Test;

public class UzFormulaCalculatorTest {

    private UzFormulaModel model;

    @Before
    public void init() {
        double r = 0.5d;
        double a = 0.2d;
        double e = 0.3d;
        double m = 0.5d;

        double z = 1;
        double l = 1;

        model = new UzFormulaModel(r, a, e, m, z, l);
    }

    @Test
    public void test() {
        UzFormulaCalculator formula = new UzFormulaCalculator(model);

        System.out.println(model);
        System.out.println("Q = " + formula.getQ() + "; P = " + formula.getP() + "; R = " + formula.getR());
        System.out.println("Result: " + formula.getResult());
    }

}
