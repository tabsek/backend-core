package ru.mentee.power.crm.storage;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.mentee.power.crm.domain.Address;
import ru.mentee.power.crm.domain.Contact;
import ru.mentee.power.crm.domain.Lead;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class LeadStorageTest {

    private Address address;

    @BeforeEach
    void setUp() {
        address = new Address("Moscow", "Tverskaya 1", "123456");
    }

    private Lead createLead(String email, String phone, String company) {
        Contact contact = new Contact(email, phone, address);
        return new Lead(UUID.randomUUID(), contact, company, "NEW");
    }

    @Test
    void shouldAddLeadWhenLeadIsUnique() {
        LeadStorage storage = new LeadStorage();
        Lead uniqueLead = createLead("ivan@mail.ru", "+7123", "TechCorp");

        boolean added = storage.add(uniqueLead);

        assertThat(added).isTrue();
        assertThat(storage.size()).isEqualTo(1);
        assertThat(storage.findAll()).containsExactly(uniqueLead);
    }

    @Test
    void shouldRejectDuplicateWhenEmailAlreadyExists() {
        LeadStorage storage = new LeadStorage();
        Lead existingLead = createLead("ivan@mail.ru", "+7123", "TechCorp");
        Lead duplicateLead = createLead("ivan@mail.ru", "+7456", "Other");
        storage.add(existingLead);

        boolean added = storage.add(duplicateLead);

        assertThat(added).isFalse();
        assertThat(storage.size()).isEqualTo(1);
        assertThat(storage.findAll()).containsExactly(existingLead);
    }

    @Test
    void shouldThrowExceptionWhenStorageIsFull() {
        LeadStorage storage = new LeadStorage();
        for (int index = 0; index < 100; index++) {
            storage.add(createLead("lead" + index + "@mail.ru", "+7000", "Company"));
        }

        Lead hundredFirstLead = createLead("lead101@mail.ru", "+7001", "Company");

        assertThatThrownBy(() -> storage.add(hundredFirstLead))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("Storage is full");
    }

    @Test
    void shouldReturnOnlyAddedLeadsWhenFindAllCalled() {
        LeadStorage storage = new LeadStorage();
        Lead firstLead = createLead("ivan@mail.ru", "+7123", "TechCorp");
        Lead secondLead = createLead("maria@startup.io", "+7456", "StartupLab");
        storage.add(firstLead);
        storage.add(secondLead);

        Lead[] result = storage.findAll();

        assertThat(result)
                .hasSize(2)
                .containsExactly(firstLead, secondLead);
    }
}