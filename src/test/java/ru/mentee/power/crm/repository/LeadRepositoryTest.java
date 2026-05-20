package ru.mentee.power.crm.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.mentee.power.crm.domain.Address;
import ru.mentee.power.crm.domain.Contact;
import ru.mentee.power.crm.domain.Lead;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class LeadRepositoryTest {

    private LeadRepository repository;
    private Contact contact;

    @BeforeEach
    void setUp() {
        repository = new LeadRepository();
        Address address = new Address("Moscow", "Tverskaya 1", "123456");
        contact = new Contact("test@example.com", "+71234567890", address);
    }

    @Test
    void shouldSaveAndFindLeadById() {
        UUID id = UUID.randomUUID();
        Lead lead = new Lead(id, contact, "TestCorp", "NEW");

        repository.save(lead);
        Optional<Lead> found = repository.findById(id);

        assertThat(found).isPresent();
        assertThat(found.get()).isEqualTo(lead);
    }

    @Test
    void shouldReturnEmptyWhenLeadNotFound() {
        UUID nonExistentId = UUID.randomUUID();

        Optional<Lead> found = repository.findById(nonExistentId);

        assertThat(found).isEmpty();
    }

    @Test
    void shouldReturnAllLeadsWhenMultipleLeadsSaved() {
        Lead firstLead = new Lead(UUID.randomUUID(), contact, "Corp A", "NEW");
        Lead secondLead = new Lead(UUID.randomUUID(), contact, "Corp B", "IN_PROGRESS");
        Lead thirdLead = new Lead(UUID.randomUUID(), contact, "Corp C", "CLOSED");

        repository.save(firstLead);
        repository.save(secondLead);
        repository.save(thirdLead);

        List<Lead> all = repository.findAll();

        assertThat(all).hasSize(3);
        assertThat(all).containsExactlyInAnyOrder(firstLead, secondLead, thirdLead);
    }

    @Test
    void shouldDeleteLeadWhenLeadExists() {
        UUID id = UUID.randomUUID();
        Lead lead = new Lead(id, contact, "TestCorp", "NEW");

        repository.save(lead);
        repository.delete(id);

        assertThat(repository.findById(id)).isEmpty();
    }

    @Test
    void shouldOverwriteLeadWhenSaveWithSameId() {
        UUID id = UUID.randomUUID();
        Lead originalLead = new Lead(id, contact, "OriginalCorp", "NEW");

        Address otherAddress = new Address("SPb", "Nevsky 10", "190000");
        Contact otherContact = new Contact("other@example.com", "+79876543210", otherAddress);
        Lead updatedLead = new Lead(id, otherContact, "UpdatedCorp", "IN_PROGRESS");

        repository.save(originalLead);
        repository.save(updatedLead);

        Optional<Lead> found = repository.findById(id);

        assertThat(found).isPresent();
        assertThat(found.get()).isEqualTo(updatedLead);
        assertThat(repository.findAll()).hasSize(1);
    }

    @Test
    void shouldThrowExceptionWhenSaveNullLead() {
        assertThatThrownBy(() -> repository.save(null))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void shouldThrowExceptionWhenFindByNullId() {
        assertThatThrownBy(() -> repository.findById(null))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void shouldThrowExceptionWhenDeleteNullId() {
        assertThatThrownBy(() -> repository.delete(null))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void shouldThrowExceptionWhenDeleteNonExistentId() {
        UUID nonExistentId = UUID.randomUUID();

        assertThatThrownBy(() -> repository.delete(nonExistentId))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void shouldSaveBothLeadsEvenWithSameEmail() {
        Address address = new Address("Moscow", "Tverskaya 1", "123456");
        Contact duplicateContact = new Contact("shared@example.com", "+71234567890", address);

        Lead firstLead = new Lead(UUID.randomUUID(), duplicateContact, "Corp A", "NEW");
        Lead secondLead = new Lead(UUID.randomUUID(), duplicateContact, "Corp B", "NEW");

        repository.save(firstLead);
        repository.save(secondLead);

        assertThat(repository.findAll()).hasSize(2);
    }
}
