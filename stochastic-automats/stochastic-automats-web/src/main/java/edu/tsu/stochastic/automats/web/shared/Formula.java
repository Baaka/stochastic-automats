package edu.tsu.stochastic.automats.web.shared;

public enum Formula {
    WN_FUNCTION("W(n)Formula", "edu.tsu.stoch.automats.formula.wn"),
    UZ_FUNCTION("U(z)Formula", "edu.tsu.stoch.automats.formula.uz"),
    HDJ_FUNCTION("H(d-j)Formula", "edu.tsu.stoch.automats.formula.hdj");

    private final String functionName;
    private final String permissionCode;

    Formula(String functionName, String permissionCode) {
        this.functionName = functionName;
        this.permissionCode = permissionCode;
    }

    public String getFunctionName() {
        return functionName;
    }

    public String getPermissionCode() {
        return permissionCode;
    }
}
