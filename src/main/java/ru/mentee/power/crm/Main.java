package ru.mentee.power.crm;

import org.apache.catalina.Context;
import org.apache.catalina.startup.Tomcat;
import ru.mentee.power.crm.domain.Address;
import ru.mentee.power.crm.domain.Contact;
import ru.mentee.power.crm.domain.Lead;
import ru.mentee.power.crm.domain.LeadStatus;
import ru.mentee.power.crm.infrastructure.InMemoryLeadRepository;
import ru.mentee.power.crm.service.LeadService;
import ru.mentee.power.crm.servlet.LeadListServlet;

import java.io.File;
import java.util.UUID;

public class Main {
    public static void main(String[] args) throws Exception {
        InMemoryLeadRepository inMemoryLeadRepository = new InMemoryLeadRepository();
        LeadService leadService = new LeadService(inMemoryLeadRepository);

        Address address = new Address("Москва", "Ленина", "101000");

        leadService.addLead(new Lead(UUID.randomUUID(),
                new Contact("ivan@gmail.com", "+79001112233", address),
                "TechCorp", LeadStatus.NEW));
        leadService.addLead(new Lead(UUID.randomUUID(),
                new Contact("anna@gmail.com", "+79002223344", address),
                "StartupX", LeadStatus.IN_PROGRESS));
        leadService.addLead(new Lead(UUID.randomUUID(),
                new Contact("petr@gmail.com", "+79003334455", address),
                "BigData LLC", LeadStatus.NEW));
        leadService.addLead(new Lead(UUID.randomUUID(),
                new Contact("olga@gmail.com", "+79004445566", address),
                "DevStudio", LeadStatus.CLOSED));
        leadService.addLead(new Lead(UUID.randomUUID(),
                new Contact("sergey@gmail.com", "+79005556677", address),
                "CloudSoft", LeadStatus.NEW));

        Tomcat tomcat = new Tomcat();
        tomcat.setPort(8080);

        Context context = tomcat.addContext("", new File(".").getAbsolutePath());
        context.getServletContext().setAttribute("leadService", leadService);

        tomcat.addServlet(context, "LeadListServlet", new LeadListServlet());
        context.addServletMappingDecoded("/leads", "LeadListServlet");

        tomcat.getConnector();
        tomcat.start();
        System.out.println("Tomcat started on port 8080");

        tomcat.getServer().await();
    }
}
