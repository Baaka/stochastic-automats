package edu.tsu.stochastic.automats.web.server.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.Date;

@WebServlet("/formulaResultExportServlet")
public class FormulaResultExportServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String formulaResult = request.getParameter("formulaResult");

        if (formulaResult != null) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");

            StringBuilder sb = new StringBuilder();

            Principal principal = request.getUserPrincipal();
            if (principal != null) {
                sb.append("User: ")
                        .append(request.getUserPrincipal().getName())
                        .append("\n");
            }

            sb.append("Timestamp:").append(dateFormat.format(new Date()))
                    .append("\n\n")
                    .append(formulaResult);

            try (OutputStream out = response.getOutputStream()) {
                String mimetype = "application/octet-stream";
                response.setContentType(mimetype);

                response.setHeader("Content-Disposition", "attachment; filename=\"formulaResult_" + dateFormat.format(new Date()) + ".txt\"");
                out.write(sb.toString().getBytes());
            }
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
