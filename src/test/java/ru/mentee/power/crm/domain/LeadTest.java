package ru.mentee.power.crm.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class LeadTest {

    private Address address;
    private Contact contact;

    @BeforeEach
    void setUp() {
        address = new Address("Moscow", "Tverskaya 1", "123456");
        contact = new Contact("test@example.com", "+71234567890", address);
    }

    @Test
    void shouldCreateLeadWhenValidData() {
        UUID id = UUID.randomUUID();
        Lead lead = new Lead(id, contact, "TestCorp", LeadStatus.NEW);

        assertThat(lead.id()).isEqualTo(id);
        assertThat(lead.contact()).isEqualTo(contact);
        assertThat(lead.company()).isEqualTo("TestCorp");
        assertThat(lead.status()).isEqualTo(LeadStatus.NEW);
    }

    @Test
    void shouldAccessEmailThroughDelegationWhenLeadCreated() {
        Lead lead = new Lead(UUID.randomUUID(), contact, "TestCorp", LeadStatus.NEW);

        assertThat(lead.contact().email()).isEqualTo("test@example.com");
    }

    @Test
    void shouldAccessPhoneThroughDelegationWhenLeadCreated() {
        Lead lead = new Lead(UUID.randomUUID(), contact, "TestCorp", LeadStatus.NEW);

        assertThat(lead.contact().phone()).isEqualTo("+71234567890");
    }

    @Test
    void shouldDemonstrateThreeLevelCompositionWhenAccessingCity() {
        Lead lead = new Lead(UUID.randomUUID(), contact, "TestCorp", LeadStatus.NEW);

        Contact c = lead.contact();
        Address a = c.address();
        String city = a.city();

        assertThat(city).isEqualTo("Moscow");
        assertThat(lead.contact().address().city()).isEqualTo("Moscow");
    }

    @Test
    void shouldBeReflexiveWhenEqualsCalledOnSameObject() {
        Lead lead = new Lead(UUID.randomUUID(), contact, "TestCorp", LeadStatus.NEW);

        assertThat(lead).isEqualTo(lead);
    }

    @Test
    void shouldBeSymmetricWhenEqualsCalledOnTwoEqualObjects() {
        UUID id = UUID.randomUUID();
        Lead firstLead = new Lead(id, contact, "TestCorp", LeadStatus.NEW);
        Lead secondLead = new Lead(id, contact, "TestCorp", LeadStatus.NEW);

        assertThat(firstLead).isEqualTo(secondLead);
        assertThat(secondLead).isEqualTo(firstLead);
    }

    @Test
    void shouldBeTransitiveWhenEqualsChainOfThreeObjects() {
        UUID id = UUID.randomUUID();
        Lead firstLead = new Lead(id, contact, "TestCorp", LeadStatus.NEW);
        Lead secondLead = new Lead(id, contact, "TestCorp", LeadStatus.NEW);
        Lead thirdLead = new Lead(id, contact, "TestCorp", LeadStatus.NEW);

        assertThat(firstLead).isEqualTo(secondLead);
        assertThat(secondLead).isEqualTo(thirdLead);
        assertThat(firstLead).isEqualTo(thirdLead);
    }

    @Test
    void shouldReturnFalseWhenEqualsComparedWithNull() {
        Lead lead = new Lead(UUID.randomUUID(), contact, "TestCorp", LeadStatus.NEW);

        assertThat(lead).isNotEqualTo(null);
    }

    @Test
    void shouldHaveSameHashCodeWhenObjectsAreEqual() {
        UUID id = UUID.randomUUID();
        Lead firstLead = new Lead(id, contact, "TestCorp", LeadStatus.NEW);
        Lead secondLead = new Lead(id, contact, "TestCorp", LeadStatus.NEW);

        assertThat(firstLead)
                .isEqualTo(secondLead)
                .hasSameHashCodeAs(secondLead);
    }

    @Test
    void shouldWorkInHashMapWhenLeadUsedAsKey() {
        UUID id = UUID.randomUUID();
        Lead keyLead = new Lead(id, contact, "TestCorp", LeadStatus.NEW);
        Lead lookupLead = new Lead(id, contact, "TestCorp", LeadStatus.NEW);

        Map<Lead, String> map = new HashMap<>();
        map.put(keyLead, "CONTACTED");

        assertThat(map).containsEntry(lookupLead, "CONTACTED");
    }

    @Test
    void shouldNotBeEqualWhenIdsAreDifferent() {
        Lead firstLead = new Lead(UUID.randomUUID(), contact, "TestCorp", LeadStatus.NEW);
        Lead differentLead = new Lead(UUID.randomUUID(), contact, "TestCorp", LeadStatus.NEW);

        assertThat(firstLead).isNotEqualTo(differentLead);
    }

    @Test
    void shouldThrowExceptionWhenContactIsNull() {
        UUID id = UUID.randomUUID();

        assertThatThrownBy(() -> new Lead(id, null, "TestCorp", LeadStatus.NEW))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void shouldThrowExceptionWhenIdIsNull() {
        assertThatThrownBy(() -> new Lead(null, contact, "TestCorp", LeadStatus.NEW))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void shouldContainAllDataWhenToStringCalled() {
        UUID id = UUID.randomUUID();
        Lead lead = new Lead(id, contact, "TestCorp", LeadStatus.NEW);

        assertThat(lead.toString())
                .contains(id.toString(), "TestCorp", "NEW");
    }
}