package edu.tsu.stochastic.automats.core.formula;

import org.junit.Test;

public class HdFormulaCalculatorTest {

    @Test
    public void test() {
        int x = 2;
        int d = 1;
        int j = 1;

        int res = calculateH(j, d, x, 1, 1);
        System.out.println("RESULT: " + res);
    }

    @Test
    public void testAlphaX() {
        int l = 1;
        double q = 1;
        double p = 2;

        int x = 2;

        int maxd = 5;
        double e = 1;

        double ro = Math.pow(getRo(l, q, p), (x - 2) * -1);

        //fixme
        double R = 1;

        double res = 0;
        for (int d = 1; d <= maxd; d++) {
            for (int j = 1; j <= d; j++) {
                double zz = Math.pow(e, j) * Math.pow(R, d - j) * calculateH(j, d, x, l, 1);
                res += zz;
            }
        }

        res *= ro;
        System.out.println("RES: " + res);
    }

    @Test
    public void testIotaX() {

        int l = 1;
        double q = 1;
        double p = 2;

        int x = 2;

        int maxd = 5;
        double e = 1;

        double ro = Math.pow(getRo(l, q, p), (x - 2) * -1);

        //fixme
        double R = 1;

        double res = 0;
        for (int d = 1; d <= maxd; d++) {
            for (int j = 1; j <= d; j++) {
                double zz = d * Math.pow(e, j) * Math.pow(R, d - j) * calculateH(j, d, x, l, 1);
                res += zz;
            }
        }

        res *= ro;
        System.out.println("RES: " + res);
    }

    private int calculateH(int j, int d, int x, int l, int i) {
        System.out.print("(x, N(d-1)+l+1-i, d-1) _ (" + x + ", " + (getN(x, l, d - 1) + l + 1 - i) + ", " + (d - 1) + ")");
        int par1 = getHdj0(j, d) * getM(getN(x, l, d - 1) + l + 1 - i);
        System.out.println(" = " + par1);

        System.out.print("(x, N(d-1)-i, d-1) _" + "(" + x + ", " + (getN(x, l, d - 1) - i) + ", " + (d - 1) + ")");
        int par2 = getHdj1(j, d) * getM(getN(x, l, d - 1) - i);
        System.out.println(" = " + par2);

        System.out.print("(x, N(d-1)+l-i, d-1) _ " + "(" + x + ", " + (getN(x, l, d - 1) + l - i) + ", " + (d - 1) + ")");
        int par3 = getHdj2(j, d) * getM(getN(x, l, d - 1) + l + i);
        System.out.println(" = " + par3);

        return par1 + par2 + par3;
    }

    private int getN(int x, int l, int d) {
        return x + (l * d);
    }

    private int getHdj0(int j, int d) {
        if (j == d) {
            return 1;
        }
        return 0;
    }

    private int getHdj1(int j, int d) {
        if (j == d) {
            return 1;
        }
        return 0;
    }

    private int getHdj2(int j, int d) {
        if (j == (d - 1)) {
            return 1;
        }
        return 0;
    }

    private int getM(int x) {
        if (x >= 0) {
            return 1;
        }
        return 0;
    }

    public double getRo(int l, double q, double p) {
        double x = q / p;
        return nthRoot(l + 1, x);
    }

    private double getE(int l, double q, double p) {
        double x = q * Math.pow(p, l);
        return nthRoot(l + 1, x);
    }


    private double getQ(double q, double o, double r) {
        return q + (o * r);
    }

    private double getP(double p, double w, double r) {
        return p + (w * r);
    }


    private static double nthRoot(int n, double A) {
        double x0 = 1;
        boolean accurate = false;
        while (!accurate) {
            double x1 = (1d / (double) n) * ((n - 1d) * x0 + A / Math.pow(x0, n - 1d));
            accurate = accurate(x0, x1);
            x0 = x1;
        }
        return x0;
    }


    private static boolean accurate(double x0, double x1) {
        return Math.abs(x1 - x0) < 0.000001;
    }
}
