package edu.tsu.stochastic.automats.core.formula;

import edu.tsu.stochastic.automats.core.model.WnFormulaModel;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.junit.Before;
import org.junit.Test;

import java.io.FileOutputStream;
import java.io.IOException;

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

        int maxN = 50;
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

    @Test
    public void excelExportTest() throws IOException {
        WnFormulaCalculator formula = new WnFormulaCalculator(model);
        int maxN = 50;

        // workbook
        Workbook wb = new HSSFWorkbook();
        Sheet sheet = wb.createSheet("W(N)_Results");
        sheet.createFreezePane(0, 1);

        // header font
        Font headerFont = wb.createFont();
        headerFont.setBoldweight(Font.BOLDWEIGHT_BOLD);

        // header style
        CellStyle headerStyle = wb.createCellStyle();
        headerStyle.setFillForegroundColor(IndexedColors.BLUE_GREY.getIndex());
        headerStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
        headerStyle.setBorderBottom(HSSFCellStyle.BORDER_MEDIUM);
        headerStyle.setBorderTop(HSSFCellStyle.BORDER_MEDIUM);
        headerStyle.setBorderRight(HSSFCellStyle.BORDER_MEDIUM);
        headerStyle.setBorderLeft(HSSFCellStyle.BORDER_MEDIUM);
        headerStyle.setFont(headerFont);
        headerStyle.setAlignment(CellStyle.ALIGN_CENTER);

        // general cell style
        CellStyle cellStyle = wb.createCellStyle();
        cellStyle.setBorderBottom(HSSFCellStyle.BORDER_MEDIUM);
        cellStyle.setBorderTop(HSSFCellStyle.BORDER_MEDIUM);
        cellStyle.setBorderRight(HSSFCellStyle.BORDER_MEDIUM);
        cellStyle.setBorderLeft(HSSFCellStyle.BORDER_MEDIUM);

        // header row
        String[] headerValues = {
                /*0*/"n", /*1*/"x", /*2*/"result"};
        Integer[] headerWidths = {/*0*/2000, /*1*/2000, /*2*/2000};
        Row headerRow = sheet.createRow(0);
        for (int i = 0; i < headerValues.length; i++) {
            sheet.setColumnWidth(i, headerWidths[i]); // init columns widths
            createCell(headerRow, i, headerStyle).setCellValue(headerValues[i]);
        }

        int rowNumber = 1;

        // =====================================================
        for (int n = 1; n <= maxN; n++) {
            for (int x = 1; x <= n; x++) {
                Row row = sheet.createRow(rowNumber);
                createCell(row, 0, cellStyle).setCellValue(n);
                createCell(row, 1, cellStyle).setCellValue(x);
                createCell(row, 2, cellStyle).setCellValue(formula.getResult(n, x));
                rowNumber++;
            }
        }

        try (FileOutputStream out = new FileOutputStream("D://wn_results.xls")) {
            wb.write(out);
            wb.close();
        }
    }

    private Cell createCell(Row row, int column, CellStyle cellStyle) {
        Cell cell = row.createCell(column);
        cell.setCellStyle(cellStyle);
        return cell;
    }


    @Test
    public void hoida() throws IOException {
        double epsilon = 0.5d;
        double eta = 0.3d;
        double r1 = 0.4d;
        double r2 = 0.1d;
        double a1 = 0.5d;
        double a2 = 0.7d;

        double p1 = getBigPForWn(r1, epsilon, a1);
        double p2 = getBigPForWn(r2, epsilon, a2);

        double q1 = getBigQForWn(r1, eta, a1);
        double q2 = getBigQForWn(r2, eta, a2);

        int maxN = 40;

        /*for (int n = 1; n <= maxN; n++) {
            for (int j = 1; j <= n; j++) {
                System.out.println("n = " + n + "; j = " + j + "; result = " + getWn(p1, p2, q1, q2, j, n));
            }
        }*/

        // workbook
        Workbook wb = new HSSFWorkbook();
        Sheet sheet = wb.createSheet("W(N)_Results");
        sheet.createFreezePane(0, 1);

        // header font
        Font headerFont = wb.createFont();
        headerFont.setBoldweight(Font.BOLDWEIGHT_BOLD);

        // header style
        CellStyle headerStyle = wb.createCellStyle();
        headerStyle.setFillForegroundColor(IndexedColors.BLUE_GREY.getIndex());
        headerStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
        headerStyle.setBorderBottom(HSSFCellStyle.BORDER_MEDIUM);
        headerStyle.setBorderTop(HSSFCellStyle.BORDER_MEDIUM);
        headerStyle.setBorderRight(HSSFCellStyle.BORDER_MEDIUM);
        headerStyle.setBorderLeft(HSSFCellStyle.BORDER_MEDIUM);
        headerStyle.setFont(headerFont);
        headerStyle.setAlignment(CellStyle.ALIGN_CENTER);

        // general cell style
        CellStyle cellStyle = wb.createCellStyle();
        cellStyle.setBorderBottom(HSSFCellStyle.BORDER_MEDIUM);
        cellStyle.setBorderTop(HSSFCellStyle.BORDER_MEDIUM);
        cellStyle.setBorderRight(HSSFCellStyle.BORDER_MEDIUM);
        cellStyle.setBorderLeft(HSSFCellStyle.BORDER_MEDIUM);

        // header row
        String[] headerValues = {
                /*0*/"n", /*1*/"j", /*2*/"result"};
        Integer[] headerWidths = {/*0*/2000, /*1*/2000, /*2*/2000};
        Row headerRow = sheet.createRow(0);
        for (int i = 0; i < headerValues.length; i++) {
            sheet.setColumnWidth(i, headerWidths[i]); // init columns widths
            createCell(headerRow, i, headerStyle).setCellValue(headerValues[i]);
        }

        int rowNumber = 1;

        // =====================================================
        for (int n = 1; n <= maxN; n++) {
            for (int j = 1; j <= n; j++) {
                if (n == maxN && (j == 1 || j == maxN)) {
                    double result = getWn(p1, p2, q1, q2, j, n);
                    System.out.println("n = " + n + "; j = " + j + "; Result: " + result);
                    Row row = sheet.createRow(rowNumber);
                    createCell(row, 0, cellStyle).setCellValue(n);
                    createCell(row, 1, cellStyle).setCellValue(j);
                    createCell(row, 2, cellStyle).setCellValue(result);
                    rowNumber++;
                }
            }
        }

        try (FileOutputStream out = new FileOutputStream("D://wn_results.xls")) {
            wb.write(out);
            wb.close();
        }

    }

    private double getBigQForWn(double r, double eta, double a) {
        double q = (1d - r + a) / 2d;
        return q + (eta * r);
    }

    private double getBigPForWn(double r, double epsilon, double a) {
        double p = (1d - r - a) / 2d;
        return p + (epsilon * r);
    }

    private double getWn(double p1, double p2, double q1, double q2, int j, int n) {
        double miltiplier = (p1 * p2) / (q1 * q2);

        double first = Math.pow(q1 - p1, 2) / p1;
        double second = (Math.pow(q2 / p2, j) - 1);
        double third = Math.pow(p1 / q1, n) * Math.pow(p2 / q2, (j - 1));
        double firstPart = first * second * third;

        double fourth = Math.pow(q2 - p2, 2) / p2;
        double fifth = (Math.pow(q1 / p1, j) - 1);
        double sixths = Math.pow(p1 / q1, (j - 1)) * Math.pow(p2 / q2, n);
        double secondPart = fourth * fifth * sixths;

        double firstPlusSecond = firstPart + secondPart;

        double upSide = miltiplier * firstPlusSecond;
        double downSide = 1 - Math.pow(p1 / q1, j) * Math.pow(p2 / q2, j);

        double result = upSide / downSide;

        return result;
    }

    private double calculateWn(double p1, double p2, double q1, double q2, int x, int n) {
        double multiplier = (p1 * p2) / (q1 * q2);

        double point1 = (Math.pow(q1 - p1, 2) / p1) * (Math.pow(q2 / p2, x) - 1) * (Math.pow(p1 / q1, n) * (Math.pow(p2 / q2, x - 1)));
        double point2 = (Math.pow(q2 - p2, 2) / p2) * (Math.pow(q1 / p1, x) - 1) * (Math.pow(p2 / q2, n) * (Math.pow(p1 / q1, x - 1)));

        double separator = 1d - (Math.pow(p1 / q1, x) * Math.pow(p2 / q2, x));

        return (multiplier * (point1 + point2)) / separator;
    }


}
