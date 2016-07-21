package edu.tsu.stochastic.automats.core.formula;

import org.junit.Test;

public class HdjTest {

    private int l = 2;


    @Test
    public void test() {
        int x = 0;
        int d = 2;
        int j = 1;
        int i = 1;

        int par1 = getHdj(x, getNd(x, l, d - 1) + l + 1 - i, d - 1, j, 1);
        int par2 = getHdj(x, getNd(x, l, d - 1) - i, d - 1, j, 1);
        int par3 = getHdj(x, getNd(x, l, d - 1) + l - i, d - 1, j, 3);
        int result = par1 + par2 + par3;

        System.out.println("Res: " + result);

        double p = 0.18d;
        double q = 0.8d;
        //double epsilon = 0.4d;
        double epsilon = getEpsilon(l,p,q);

        //double epsilon =

        // sigma
        double res = 0.0d;
        double ro = getRo(l, p, q);
        for (int de = 1; de <= 50; de++) {
            for (int ji = 1; ji <= de; ji++) {
                double reslt = Math.pow(epsilon, ji) * getHdj(x, -1, de, ji);
                res += reslt;
                //System.out.println(reslt);
                //System.out.println(getRo(l, p, q) * res);
            }
        }
        //System.out.println("RO: " + ro);
        //System.out.println("FINAL:" + res);
        System.out.println("Sigma: " + res * ro);
        System.out.println("Sigmius: " + (p / q));

        double sigmius2 = (Math.sqrt(1 + 4 * (p / q)) - 1) / 2;
        System.out.println("Sigmius: l=2; " + sigmius2);


        // tau
        double taures = 0.0d;
        for (int dze = 1; dze <= 50; dze++) {
            for (int je = 1; je <= dze; je++) {
                double rs = dze * Math.pow(epsilon, je) * getHdj(x, -1, dze, je);
                taures += rs;
            }
        }
        taures = (taures * ro);
        System.out.println("TAU: " + taures);

    }


    public double getRo(int l, double q, double p) {
        double x = q / p;
        return nthRoot(l + 1, x);
    }

    public double getEpsilon(int l, double p, double q) {
        double x = q * Math.pow(p, l);
        return nthRoot(l + 1, x);
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

    private int getHdj(int x, int i, int d, int j) {
        int par1 = getHdj(x, getNd(x, l, d - 1) + l + 1 - i, d - 1, j, 1);
        int par2 = getHdj(x, getNd(x, l, d - 1) - i, d - 1, j, 1);
        int par3 = getHdj(x, getNd(x, l, d - 1) + l - i, d - 1, j, 3);
        int result = par1 + par2 + par3;

        return result;
    }


    public int getHdj(int x, int i, int d, int j, int nth) {
        if (d == 1 && ((i != d) && (i != (x - 1)) && (i != x + l))) {
            return 0;
        }

        if ((d - j) < 0) {
            return 0;
        }

        if (nth == 3) {
            if (d == 1 && (j == (d - 1))) {
                return 1;
            } else if (d == 1 && (j != (d - 1))) {
                return 0;
            }
        }

        if (nth == 1) {
            if (d == 1 && j == d) {
                return 1;
            } else if (d == 1) {
                return 0;
            }
        }


        if (j == d) {
            return 1;
        }
        return 0;
    }

    double getMiu(double x) {
        if (x >= 0) {
            return 1;
        }
        return 0;
    }

    public int getNd(int x, int l, int d) {
        return (x + (l * d));
    }

}
