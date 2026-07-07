package ru.mentee.power.crm.spring.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.mentee.power.crm.domain.Address;
import ru.mentee.power.crm.domain.Contact;
import ru.mentee.power.crm.domain.Lead;
import ru.mentee.power.crm.domain.LeadStatus;
import ru.mentee.power.crm.infrastructure.InMemoryLeadRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

class LeadServiceTest {

    private LeadService leadService;

    @BeforeEach
    void setUp() {
        leadService = new LeadService(new InMemoryLeadRepository());
        Address address = new Address("Москва", "Ленина", "101000");
        leadService.addLead(new Lead(UUID.randomUUID(),
                new Contact("a@test.com", "+79001110001", address), "Corp A", LeadStatus.NEW));
        leadService.addLead(new Lead(UUID.randomUUID(),
                new Contact("b@test.com", "+79001110002", address), "Corp B", LeadStatus.NEW));
        leadService.addLead(new Lead(UUID.randomUUID(),
                new Contact("c@test.com", "+79001110003", address), "Corp C", LeadStatus.IN_PROGRESS));
        leadService.addLead(new Lead(UUID.randomUUID(),
                new Contact("d@test.com", "+79001110004", address), "Corp D", LeadStatus.CLOSED));
    }

    @Test
    void shouldReturnOnlyNewLeads() {
        List<Lead> result = leadService.findByStatus(LeadStatus.NEW);

        assertThat(result).hasSize(2);
        assertThat(result).allMatch(lead -> lead.status().equals(LeadStatus.NEW));
    }

    @Test
    void shouldReturnOnlyInProgressLeads() {
        List<Lead> result = leadService.findByStatus(LeadStatus.IN_PROGRESS);

        assertThat(result).hasSize(1);
        assertThat(result).allMatch(lead -> lead.status().equals(LeadStatus.IN_PROGRESS));
    }

    @Test
    void shouldReturnOnlyClosedLeads() {
        List<Lead> result = leadService.findByStatus(LeadStatus.CLOSED);

        assertThat(result).hasSize(1);
        assertThat(result).allMatch(lead -> lead.status().equals(LeadStatus.CLOSED));
    }

    @Test
    void shouldReturnEmptyListWhenNoLeadsWithStatus() {
        LeadService emptyService = new LeadService(new InMemoryLeadRepository());

        List<Lead> result = emptyService.findByStatus(LeadStatus.NEW);

        assertThat(result).isEmpty();
    }

    @Test
    void shouldThrowWhenEmailAlreadyExists() {
        assertThatThrownBy(() -> leadService.addLead(
                new Lead(UUID.randomUUID(),
                        new Contact("a@test.com", "+79001110001",
                                new Address("Москва", "Ленина", "101000")),
                        "Corp X", LeadStatus.NEW)))
                .isInstanceOf(IllegalStateException.class);
    }

    @Test
    void shouldFindByIdWhenExists() {
        Lead firstLead = leadService.findAll().get(0);
        Optional<Lead> secondLead = leadService.findById(firstLead.id());

        assertThat(secondLead).isPresent();
    }

    @Test
    void shouldFindByEmailWhenExists() {
        Optional<Lead> result = leadService.findByEmail("a@test.com");

        assertThat(result).isPresent();
    }
}
