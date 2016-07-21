package edu.tsu.stochastic.automats.core.formula;

import org.junit.Test;

public class TestSaveGod {


    @Test
    public void test() {

    }


    private int getHdj(int d, int j, double x, int i, double l) {

        if (i != x && (i != (x - 1)) && (i != (x + l))) {
            return 0;
        }

        //double nd = getNd(x, l, d);
        //if ((d - j) < 0 && i == nd && d == nd) {
        if ((d - j) < 0) {
            return 0;
        }

        return -12;
    }

    private int getMiu(double miuParam) {
        if (miuParam >= 0) {
            return 1;
        }
        return 0;
    }

    private double getNd(double x, double l, double d) {
        return (x + l * d);
    }

}
