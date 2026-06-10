package ru.mentee.power.crm.servlet;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.mentee.power.crm.domain.Address;
import ru.mentee.power.crm.domain.Contact;
import ru.mentee.power.crm.domain.Lead;
import ru.mentee.power.crm.service.LeadService;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LeadListServletTest {

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private ServletContext servletContext;

    @Mock
    private LeadService leadService;

    private LeadListServlet servlet;
    private StringWriter stringWriter;

    @BeforeEach
    void setUp() throws Exception {
        servlet = spy(new LeadListServlet());
        doReturn(servletContext).when(servlet).getServletContext();
        when(servletContext.getAttribute("leadService")).thenReturn(leadService);

        servlet.init();

        stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(printWriter);
    }

    @Test
    void shouldSetContentTypeBeforeWriter() throws Exception {
        when(leadService.findAll()).thenReturn(List.of());

        servlet.doGet(request, response);

        verify(response).setContentType("text/html; charset=UTF-8");
    }

    @Test
    void shouldCallFindAllOnLeadService() throws Exception {
        when(leadService.findAll()).thenReturn(List.of());

        servlet.doGet(request, response);

        verify(leadService).findAll();
    }

    @Test
    void shouldRenderLeadEmailInTable() throws Exception {
        Lead lead = buildLead("test@gmail.com", "TechCorp", "NEW");
        when(leadService.findAll()).thenReturn(List.of(lead));

        servlet.doGet(request, response);

        assertThat(stringWriter.toString()).contains("test@gmail.com");
    }

    @Test
    void shouldRenderAllLeadFieldsInTable() throws Exception {
        Lead lead = buildLead("ivan@gmail.com", "StartupX", "IN_PROGRESS");
        when(leadService.findAll()).thenReturn(List.of(lead));

        servlet.doGet(request, response);

        String html = stringWriter.toString();
        assertThat(html)
                .contains("ivan@gmail.com")
                .contains("StartupX")
                .contains("IN_PROGRESS");
    }

    @Test
    void shouldRenderAllLeadsWhenMultipleExist() throws Exception {
        List<Lead> leads = List.of(
                buildLead("anna@gmail.com", "Corp A", "NEW"),
                buildLead("petr@gmail.com", "Corp B", "CLOSED")
        );
        when(leadService.findAll()).thenReturn(leads);

        servlet.doGet(request, response);

        String html = stringWriter.toString();
        assertThat(html)
                .contains("anna@gmail.com")
                .contains("petr@gmail.com");
    }

    @Test
    void shouldRenderTableTagsWhenNoLeads() throws Exception {
        when(leadService.findAll()).thenReturn(List.of());

        servlet.doGet(request, response);

        String html = stringWriter.toString();
        assertThat(html).contains("<table")
                .contains("</table>");
    }

    @Test
    void shouldRenderTableHeaders() throws Exception {
        when(leadService.findAll()).thenReturn(List.of());

        servlet.doGet(request, response);

        String html = stringWriter.toString();
        assertThat(html).contains("Email")
                .contains("Company")
                .contains("Status");
    }

    private Lead buildLead(String email, String company, String status) {
        Address address = new Address("Москва", "Ленина", "101000");
        Contact contact = new Contact(email, "+79001112233", address);
        return new Lead(UUID.randomUUID(), contact, company, status);
    }
}
