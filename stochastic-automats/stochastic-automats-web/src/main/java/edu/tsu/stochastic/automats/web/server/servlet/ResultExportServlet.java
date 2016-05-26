package edu.tsu.stochastic.automats.web.server.servlet;

import edu.tsu.stochastic.automats.core.database.api.FormulaLocal;
import edu.tsu.stochastic.automats.core.database.entity.UzFormula;
import edu.tsu.stochastic.automats.web.shared.ExportFormat;
import edu.tsu.stochastic.automats.web.shared.Formula;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@WebServlet("/resultExportServlet")
public class ResultExportServlet extends HttpServlet {
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");

    @EJB
    private FormulaLocal formulaLocal;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Formula formula = Formula.values()[Integer.valueOf(request.getParameter("formula"))];
        ExportFormat exportFormat = ExportFormat.values()[Integer.valueOf(request.getParameter("exportFormat"))];
        //TODO use ids
        String idParam = request.getParameter("ids");

        List<Long> idsStore = new ArrayList<>();

        if (idParam != null && !idParam.isEmpty()) {
            String[] ids = idParam.split("[,|;]");
            for (String id : ids) {
                idsStore.add(Long.valueOf(id));
            }
        }

        Object exportData = null;
        switch (formula) {
            case WN_FUNCTION:
                break;
            case UZ_FUNCTION:
                exportData = formulaLocal.loadCalculatedUzFormulas(-1, -1);
                break;
            case HDJ_FUNCTION:
                break;
        }

        switch (exportFormat) {
            case TXT:
                exportText(response, formula, exportData);
                break;
            case HTML:
                break;
            case PDF:
                break;
            case XLS:
                exportExcel(response, formula, exportData);
                break;
        }
    }

    //FIXME
    private void exportExcel(HttpServletResponse response, Formula formula, Object data) throws IOException {
        if (formula == Formula.UZ_FUNCTION) {
            List<UzFormula> item = (List<UzFormula>) data;

            // workbook
            Workbook wb = new HSSFWorkbook();
            Sheet sheet = wb.createSheet(formula.getFunctionName() + " Results");
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
                /*0*/"#", /*1*/"Parameter: r", /*2*/"Parameter: Alpha", /*3*/"Parameter: Epsilon", /*4*/"Parameter: Eta", /*5*/"Parameter: z", /*6*/"Parameter: l",
                /*7*/"Calculated: R", /*8*/"Calculated: P", /*9*/"Calculated: Q", /*10*/"Formula Result", /*11*/"Private Case Result"};
            Integer[] headerWidths = {/*0*/1280, /*1*/4640, /*2*/4640, /*3*/4640, /*4*/4640, /*5*/4640, /*6*/4640, /*7*/4640, /*8*/4640,
                /*9*/4640, /*10*/4640, /*11*/4640};
            Row headerRow = sheet.createRow(0);
            for (int i = 0; i < headerValues.length; i++) {
                sheet.setColumnWidth(i, headerWidths[i]); // init columns widths
                createCell(headerRow, i, headerStyle).setCellValue(headerValues[i]);
            }

            int rowNumber = 1;
            for (UzFormula model : item) {
                Row row = sheet.createRow(rowNumber);
                // 0. agent count
                createCell(row, 0, cellStyle).setCellValue(rowNumber);
                // 1. param: r
                createCell(row, 1, cellStyle).setCellValue(model.getParamR());
                // 2. param: alpha
                createCell(row, 2, cellStyle).setCellValue(model.getParamAlpha());
                // 3. param: Epsilon
                createCell(row, 3, cellStyle).setCellValue(model.getParamEpsilon());
                // 4. param: Eta
                createCell(row, 4, cellStyle).setCellValue(model.getParamEta());
                // 5. param: z
                createCell(row, 5, cellStyle).setCellValue(model.getParamZ());
                // 6. param: l
                createCell(row, 6, cellStyle).setCellValue(model.getParamL());
                // 7. calc: R
                createCell(row, 7, cellStyle).setCellValue(model.getR());
                // 8. calc: P
                createCell(row, 8, cellStyle).setCellValue(model.getP());
                // 9. calc: Q
                createCell(row, 9, cellStyle).setCellValue(model.getQ());
                // 10. formula result
                createCell(row, 10, cellStyle).setCellValue(model.getResult());
                // 11. private case result
                createCell(row, 11, cellStyle).setCellValue(model.getPrivateCaseResult());
                rowNumber++;
            }

            try (OutputStream out = response.getOutputStream()) {
                response.setContentType("application/vnd.ms-excel");
                response.setHeader("Content-Disposition", "attachment; filename=\"" + formula.getFunctionName() + "_results_" + dateFormat.format(new Date()) + ".xls\"");

                wb.write(out);
            }
        }
    }

    private Cell createCell(Row row, int column, CellStyle cellStyle) {
        Cell cell = row.createCell(column);
        cell.setCellStyle(cellStyle);
        return cell;
    }

    private void exportText(HttpServletResponse response, Formula formula, Object data) throws IOException {
        StringBuilder sb = new StringBuilder();

        switch (formula) {
            case WN_FUNCTION:
                break;
            case UZ_FUNCTION:
                List<UzFormula> uzFormulas = (List<UzFormula>) data;
                for (UzFormula uz : uzFormulas) {
                    sb.append(uz.toString())
                            .append(System.lineSeparator());
                }
                break;
            case HDJ_FUNCTION:
                break;
        }

        try (OutputStream out = response.getOutputStream()) {
            String mimetype = "application/octet-stream";
            response.setContentType(mimetype);

            response.setHeader("Content-Disposition", "attachment; filename=\"" + formula.getFunctionName() + "_results_" + dateFormat.format(new Date()) + ".txt\"");
            out.write(sb.toString().getBytes());
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

}
