package edu.tsu.stochastic.automats.web.server.servlet;

import edu.tsu.stochastic.automats.core.database.api.FormulaLocal;
import edu.tsu.stochastic.automats.core.database.entity.UzFormula;
import edu.tsu.stochastic.automats.core.formula.UzFormulaCalculator;
import edu.tsu.stochastic.automats.web.shared.Formula;
import edu.tsu.stochastic.automats.web.shared.UzFormulaParamModel;
import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@WebServlet(name = "fileImportServlet", urlPatterns = {"/App/fileImportServlet"}, loadOnStartup = 1)
public class FileImportServlet extends HttpServlet {
    @EJB
    private FormulaLocal formulaLocal;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Formula formula = Formula.values()[Integer.valueOf(request.getParameter("formula"))];

        ServletFileUpload upload = new ServletFileUpload();
        try {
            FileItemIterator iterator = upload.getItemIterator(request);
            while (iterator.hasNext()) {
                FileItemStream item = iterator.next();
                try (InputStream inputStream = item.openStream()) {
                    switch (formula) {
                        case WN_FUNCTION:
                            break;
                        case UZ_FUNCTION:
                            processUzFormula(inputStream);
                            break;
                        case HDJ_FUNCTION:
                            break;
                    }
                }
            }
        } catch (FileUploadException | InvalidFormatException e) {
            e.printStackTrace();
        }
    }

    private void processUzFormula(InputStream inputStream) throws IOException, InvalidFormatException {
        List<UzFormula> formulas = new ArrayList<>();

        try (Workbook wb = WorkbookFactory.create(inputStream)) {
            Sheet sheet = wb.getSheetAt(0);

            Iterator<Row> rowIterator = sheet.iterator();
            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                if (row.getRowNum() > 0) {
                    double rParam = getValue(row.getCell(0));
                    double aParam = getValue(row.getCell(1));
                    double eParam = getValue(row.getCell(2));
                    double mParam = getValue(row.getCell(3));
                    double zParam = getValue(row.getCell(4));
                    double lParam = getValue(row.getCell(5));

                    UzFormulaParamModel paramModel = new UzFormulaParamModel();
                    paramModel.setR(rParam);
                    paramModel.setA(aParam);
                    paramModel.setE(eParam);
                    paramModel.setM(mParam);
                    paramModel.setZ(zParam);
                    paramModel.setL(lParam);

                    UzFormulaCalculator calculator = new UzFormulaCalculator(paramModel.toUzFormulaCodeParamModel());
                    formulas.add(calculator.getCalculatedUzFormula());
                }
            }
        }
        formulaLocal.saveUzFormulas(formulas);
    }

    private double getValue(Cell cell) {
        switch (cell.getCellType()) {
            case Cell.CELL_TYPE_STRING:
                return Double.valueOf(cell.getStringCellValue());
            case Cell.CELL_TYPE_NUMERIC:
                return cell.getNumericCellValue();
        }
        return -1;
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
