package ru.mentee.power.crm.service;

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

import static org.assertj.core.api.Assertions.*;

class LeadServiceTest {

    private LeadService service;

    @BeforeEach
    void setUp() {
        service = new LeadService(new InMemoryLeadRepository());
    }

    private Lead buildLead(String email) {
        Address address = new Address("Moscow", "Lenina 1", "101000");
        Contact contact = new Contact(email, "+79991234567", address);
        return new Lead(UUID.randomUUID(), contact, "Test Company", LeadStatus.NEW);
    }

    @Test
    void shouldAddLeadWhenEmailIsUnique() {
        Lead lead = buildLead("unique@example.com");

        service.addLead(lead);

        List<Lead> all = service.findAll();
        assertThat(all).hasSize(1);
        assertThat(all.get(0).contact().email()).isEqualTo("unique@example.com");
    }

    @Test
    void shouldThrowExceptionWhenEmailAlreadyExists() {
        Lead lead = buildLead("duplicate@example.com");
        service.addLead(lead);

        Lead duplicate = buildLead("duplicate@example.com");

        assertThatThrownBy(() -> service.addLead(duplicate))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("Lead with this email already exists");
    }

    @Test
    void shouldFindAllReturnsAllAddedLeads() {
        service.addLead(buildLead("one@example.com"));
        service.addLead(buildLead("two@example.com"));

        assertThat(service.findAll()).hasSize(2);
    }

    @Test
    void shouldFindByIdWhenLeadExists() {
        Lead lead = buildLead("find@example.com");
        service.addLead(lead);

        Optional<Lead> result = service.findById(lead.id());

        assertThat(result).isPresent();
        assertThat(result.get().contact().email()).isEqualTo("find@example.com");
    }

    @Test
    void shouldReturnEmptyWhenFindByIdNotFound() {
        Optional<Lead> result = service.findById(UUID.randomUUID());

        assertThat(result).isEmpty();
    }

    @Test
    void shouldFindByEmailWhenLeadExists() {
        service.addLead(buildLead("search@example.com"));

        Optional<Lead> result = service.findByEmail("search@example.com");

        assertThat(result).isPresent();
        assertThat(result.get().company()).isEqualTo("Test Company");
    }

    @Test
    void shouldReturnEmptyWhenFindByEmailNotFound() {
        Optional<Lead> result = service.findByEmail("nobody@example.com");

        assertThat(result).isEmpty();
    }
}