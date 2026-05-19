package ru.mentee.power.crm.infrastructure;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.mentee.power.crm.domain.Contact;
import ru.mentee.power.crm.domain.Address;
import ru.mentee.power.crm.domain.Lead;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class InMemoryLeadRepositoryTest {

    private InMemoryLeadRepository repository;
    private Lead lead;
    private UUID leadId;

    @BeforeEach
    void setUp() {
        repository = new InMemoryLeadRepository();
        leadId = UUID.randomUUID();
        Contact contact = new Contact("john@example.com", "+1234567890",
                new Address("NY", "5th Ave", "10001"));
        lead = new Lead(leadId, contact, "Acme Corp", "NEW");
    }

    @Test
    void addLeadToEmptyRepositoryAppearsInFindAll() {
        repository.add(lead);
        assertThat(repository.findAll()).hasSize(1);
    }

    @Test
    void addedLeadIsFoundById() {
        repository.add(lead);
        Optional<Lead> result = repository.findById(leadId);
        assertThat(result).isPresent().contains(lead);
    }

    @Test
    void findByUnknownIdReturnsEmpty() {
        Optional<Lead> result = repository.findById(UUID.randomUUID());
        assertThat(result).isEmpty();
    }

    @Test
    void addDuplicateLeadKeepsSizeOne() {
        repository.add(lead);
        repository.add(lead);
        assertThat(repository.findAll()).hasSize(1);
    }

    @Test
    void removedLeadIsNotFoundById() {
        repository.add(lead);
        repository.remove(leadId);
        assertThat(repository.findAll()).isEmpty();
        assertThat(repository.findById(leadId)).isEmpty();
    }

    @Test
    void modifyingFindAllResultDoesNotAffectStorage() {
        repository.add(lead);
        List<Lead> result = repository.findAll();
        result.clear();
        assertThat(repository.findAll()).hasSize(1);
    }
}
