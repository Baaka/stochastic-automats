package edu.tsu.stochastic.automats.core.formula;

import edu.tsu.stochastic.automats.core.model.WnFormulaModel;
import org.junit.Before;
import org.junit.Test;

public class WnFormulaCalculatorTest {

    private WnFormulaModel model;

    @Before
    public void init() {
        double r1 = 0.5d;
        double a1 = 0.2d;
        double e1 = 0.3d;
        double z1 = 0.5d;

        double r2 = 0.7d;
        double a2 = 0.1d;
        double e2 = 0.4d;
        double z2 = 0.3d;

        model = new WnFormulaModel(r1, a1, e1, z1, r2, a2, e2, z2);
    }

    @Test
    public void test() {
        WnFormulaCalculator formula = new WnFormulaCalculator(model);

        System.out.println(model);

        int maxN = 20;
        // =====================================================
        for (int n = 1; n <= maxN; n++) {
            System.out.println(" n = " + n + " ======================= ");
            for (int x = 1; x <= n; x++) {
                System.out.println("x = " + x + "; n = " + n);
                System.out.println("RESULT: " + formula.getResult(n, x));
            }
            System.out.println(" ===================================== ");
        }
    }

}
