package edu.tsu.stochastic.automats.web.shared;

public enum Portlets {
    UZ_FORMULA_CREATORS("U(z) Formula Creators Counter", 0);

    private final String value;
    private final int column;

    Portlets(String value, int column) {
        this.value = value;
        this.column = column;
    }

    public String getValue() {
        return value;
    }

    public int getColumn() {
        return column;
    }
}
