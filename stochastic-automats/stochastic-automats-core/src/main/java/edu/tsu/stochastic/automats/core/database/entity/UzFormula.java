package edu.tsu.stochastic.automats.core.database.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity(name = "FORMULA_UZ")
@Table(name = "FORMULA_UZ")
public class UzFormula implements Serializable {
    @Id
    @SequenceGenerator(name = "uz_formula_sequence", sequenceName = "uz_formula_sequence", allocationSize = 1)
    @GeneratedValue(generator = "uz_formula_sequence", strategy = GenerationType.SEQUENCE)
    private long id;
    @Column(name = "PARAM_R")
    private double paramR;
    @Column(name = "PARAM_ALPHA")
    private double paramAlpha;
    @Column(name = "PARAM_EPSILON")
    private double paramEpsilon;
    @Column(name = "PARAM_ETA")
    private double paramEta;
    @Column(name = "PARAM_Z")
    private double paramZ;
    @Column(name = "PARAM_L")
    private double paramL;
    @Column(name = "CALC_R")
    private double r;
    @Column(name = "CALC_P")
    private double p;
    @Column(name = "CALC_Q")
    private double q;
    @Column(name = "CALC_RES")
    private double result;
    @Column(name = "CALC_RES_PRIVATE")
    private double privateCaseResult;

    public UzFormula() {
    }

    public UzFormula(double paramR, double paramAlpha, double paramEpsilon, double paramEta, double paramZ, double paramL, double r, double p, double q, double result, double privateCaseResult) {
        this.paramR = paramR;
        this.paramAlpha = paramAlpha;
        this.paramEpsilon = paramEpsilon;
        this.paramEta = paramEta;
        this.paramZ = paramZ;
        this.paramL = paramL;
        this.r = r;
        this.p = p;
        this.q = q;
        this.result = result;
        this.privateCaseResult = privateCaseResult;
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

    public Double getPrivateCaseResult() {
        return privateCaseResult;
    }

    public void setPrivateCaseResult(Double privateCaseResult) {
        this.privateCaseResult = privateCaseResult;
    }

    @Override
    public String toString() {
        String privateRes = (paramZ == 1 && (paramL == 1 || paramL == 2)) ? ", private case result=" + privateCaseResult : "";
        return "UzFormula{" +
                "id=" + id +
                ", paramR=" + paramR +
                ", paramAlpha=" + paramAlpha +
                ", paramEpsilon=" + paramEpsilon +
                ", paramEta=" + paramEta +
                ", paramZ=" + paramZ +
                ", paramL=" + paramL +
                ", r=" + r +
                ", p=" + p +
                ", q=" + q +
                ", result=" + result +
                privateRes +
                '}';
    }
}
