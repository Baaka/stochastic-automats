package edu.tsu.stochastic.automats.core.formula;

import edu.tsu.stochastic.automats.core.database.formula.entity.UzFormula;
import edu.tsu.stochastic.automats.core.helper.CommonFormulaHelper;
import edu.tsu.stochastic.automats.core.model.UzFormulaParamModel;

public class UzFormulaCalculator implements Formula {

    private UzFormulaParamModel model;

    private final double r;
    private final double p;
    private final double q;

    public UzFormulaCalculator(UzFormulaParamModel model) {
        this.model = model;

        r = getRForUz(model.getE(), model.getM(), model.getR());
        p = getPForUz(model.getR(), model.getA(), model.getE());
        q = getQForUz(model.getR(), model.getA(), model.getM());
    }

    public double getResult() {
        return calculateUz(model.getZ(), p, q, r, model.getL());
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
        double p = CommonFormulaHelper.getSmallP(r, a);
        return p + (e * r);
    }

    private double getQForUz(double r, double a, double m) {
        double q = CommonFormulaHelper.getSmallQ(r, a);
        return q + (m * r);
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

    public UzFormula getCalculatedUzFormula() {
        UzFormula formula = new UzFormula();
        formula.setParamR(model.getR());
        formula.setParamAlpha(model.getA());
        formula.setParamEpsilon(model.getE());
        formula.setParamEta(model.getM());
        formula.setParamL(model.getL());
        formula.setParamZ(model.getZ());
        formula.setR(r);
        formula.setP(p);
        formula.setQ(q);
        formula.setResult(getResult());

        double privateCaseResult = 0;
        if (model.getZ() == 1) {
            if (model.getL() == 1) {
                privateCaseResult = (p / q);
            } else if (model.getL() == 2) {
                privateCaseResult = (Math.sqrt(1d + 4d * p / q) - 1d) / 2d;
            }
        }
        formula.setPrivateCaseResult(privateCaseResult);

        return formula;
    }
}