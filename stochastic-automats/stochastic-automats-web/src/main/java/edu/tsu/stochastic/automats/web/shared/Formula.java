package edu.tsu.stochastic.automats.web.shared;

public enum Formula {
    WN_FUNCTION("W(n)Formula"),
    UZ_FUNCTION("U(z)Formula"),
    HDJ_FUNCTION("H(d-j)Formula");

    private final String functionName;

    Formula(String functionName) {
        this.functionName = functionName;
    }

    public String getFunctionName() {
        return functionName;
    }
}
