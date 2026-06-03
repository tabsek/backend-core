package ru.mentee.power.crm.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.mentee.power.crm.domain.Lead;
import ru.mentee.power.crm.service.LeadService;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/leads")
public class LeadListServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        var servletContext = getServletContext();
        LeadService leadService = (LeadService) servletContext.getAttribute("leadService");
        List<Lead> leads = leadService.findAll();
        response.setContentType("text/html; charset=UTF-8");
        PrintWriter printWriter = response.getWriter();

        printWriter.println("<!DOCTYPE html>");
        printWriter.println("<html><head><title>CRM Leads</title></head><body>");
        printWriter.println("<h1>Lead List</h1>");
        printWriter.println("<table border='1'><thead><tr>" +
                "<th>Email</th>" +
                "<th>Company</th>" +
                "<th>Status</th>" +
                "</tr></thead><tbody>");
        for (Lead lead : leads) {
            printWriter.println(String.format("<tr><td>%s</td><td>%s</td><td>%s</td></tr>",
                    lead.contact().email(), lead.company(), lead.status()));
        }

        printWriter.println("</tbody>");
        printWriter.println("</table>");
        printWriter.println("</body>");
        printWriter.println("</html>");
    }

}
