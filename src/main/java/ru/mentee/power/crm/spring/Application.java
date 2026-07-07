package ru.mentee.power.crm.spring;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import ru.mentee.power.crm.domain.Address;
import ru.mentee.power.crm.domain.Contact;
import ru.mentee.power.crm.domain.Lead;
import ru.mentee.power.crm.domain.LeadStatus;
import ru.mentee.power.crm.spring.service.LeadService;

import java.util.UUID;

@SpringBootApplication(scanBasePackages = "ru.mentee.power.crm")
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public CommandLineRunner initData(LeadService leadService) {
        return args -> {
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
        };
    }
}