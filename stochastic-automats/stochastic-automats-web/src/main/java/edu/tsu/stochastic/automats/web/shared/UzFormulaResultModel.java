package edu.tsu.stochastic.automats.web.shared;

import com.google.gwt.core.shared.GwtIncompatible;
import edu.tsu.stochastic.automats.core.database.formula.entity.UzFormula;

import java.io.Serializable;

public class UzFormulaResultModel implements Serializable {
    private long id;
    private double paramR;
    private double paramAlpha;
    private double paramEpsilon;
    private double paramEta;
    private double paramZ;
    private double paramL;
    private double r;
    private double p;
    private double q;
    private double result;
    private double privateCaseResult;
    private long creatorId;

    public UzFormulaResultModel() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getParamR() {
        return paramR;
    }

    public void setParamR(double paramR) {
        this.paramR = paramR;
    }

    public double getParamAlpha() {
        return paramAlpha;
    }

    public void setParamAlpha(double paramAlpha) {
        this.paramAlpha = paramAlpha;
    }

    public double getParamEpsilon() {
        return paramEpsilon;
    }

    public void setParamEpsilon(double paramEpsilon) {
        this.paramEpsilon = paramEpsilon;
    }

    public double getParamEta() {
        return paramEta;
    }

    public void setParamEta(double paramEta) {
        this.paramEta = paramEta;
    }

    public double getParamZ() {
        return paramZ;
    }

    public void setParamZ(double paramZ) {
        this.paramZ = paramZ;
    }

    public double getParamL() {
        return paramL;
    }

    public void setParamL(double paramL) {
        this.paramL = paramL;
    }

    public double getR() {
        return r;
    }

    public void setR(double r) {
        this.r = r;
    }

    public double getP() {
        return p;
    }

    public void setP(double p) {
        this.p = p;
    }

    public double getQ() {
        return q;
    }

    public void setQ(double q) {
        this.q = q;
    }

    public double getResult() {
        return result;
    }

    public void setResult(double result) {
        this.result = result;
    }

    public double getPrivateCaseResult() {
        return privateCaseResult;
    }

    public void setPrivateCaseResult(double privateCaseResult) {
        this.privateCaseResult = privateCaseResult;
    }

    public long getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(long creatorId) {
        this.creatorId = creatorId;
    }

    @GwtIncompatible
    public UzFormulaResultModel setEntity(UzFormula formula) {
        if (formula != null) {
            this.id = formula.getId();
            this.paramR = formula.getParamR();
            this.paramAlpha = formula.getParamAlpha();
            this.paramEpsilon = formula.getParamEpsilon();
            this.paramEta = formula.getParamEta();
            this.paramZ = formula.getParamZ();
            this.paramL = formula.getParamL();
            this.r = formula.getR();
            this.p = formula.getP();
            this.q = formula.getQ();
            this.result = formula.getResult();
            this.privateCaseResult = formula.getPrivateCaseResult();
            this.creatorId = formula.getCreatorId();
        }
        return this;
    }

    @GwtIncompatible
    public UzFormula toEntity() {
        UzFormula formula = new UzFormula();
        formula.setId(this.id);
        formula.setParamR(this.paramR);
        formula.setParamAlpha(this.paramAlpha);
        formula.setParamEpsilon(this.paramEpsilon);
        formula.setParamEta(this.paramEta);
        formula.setParamZ(this.paramZ);
        formula.setParamL(this.paramL);
        formula.setR(this.r);
        formula.setP(this.p);
        formula.setQ(this.q);
        formula.setResult(this.result);
        formula.setPrivateCaseResult(this.privateCaseResult);
        formula.setCreatorId(this.creatorId);
        return formula;
    }

    @Override
    public String toString() {
        return "UzFormulaResultModel{" +
                "result=" + result +
                ", q=" + q +
                ", p=" + p +
                ", r=" + r +
                ", paramL=" + paramL +
                ", paramZ=" + paramZ +
                ", paramEta=" + paramEta +
                ", paramEpsilon=" + paramEpsilon +
                ", paramAlpha=" + paramAlpha +
                ", paramR=" + paramR +
                ", id=" + id +
                '}';
    }
}
