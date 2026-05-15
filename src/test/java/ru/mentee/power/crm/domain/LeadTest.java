package ru.mentee.power.crm.domain;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class LeadTest {

    @Test
    void shouldReturnIdWhenGetIdCalled() {
        UUID idFirst = UUID.randomUUID();
        Lead lead = new Lead(idFirst,
                "test@example.com",
                "+71234567890",
                "TestCorp",
                "NEW");

        UUID id = lead.getId();

        assertThat(id).isEqualTo(idFirst);
    }

    @Test
    void shouldReturnEmailWhenGetEmailCalled() {
        Lead lead = new Lead(UUID.randomUUID(),
                "test@example.com",
                "+71234567890",
                "TestCorp",
                "NEW");

        String email = lead.getEmail();

        assertThat(email).isEqualTo("test@example.com");
    }

    @Test
    void shouldReturnPhoneWhenGetPhoneCalled() {
        Lead lead = new Lead(UUID.randomUUID(),
                "test@example.com",
                "+71234567890",
                "TestCorp",
                "NEW");

        String phone = lead.getPhone();

        assertThat(phone).isEqualTo("+71234567890");
    }

    @Test
    void shouldReturnCompanyWhenGetCompanyCalled() {
        Lead lead = new Lead(UUID.randomUUID(),
                "test@example.com",
                "+71234567890",
                "TestCorp",
                "NEW");

        String company = lead.getCompany();

        assertThat(company).isEqualTo("TestCorp");
    }

    @Test
    void shouldReturnStatusWhenGetStatusCalled() {
        Lead lead = new Lead(UUID.randomUUID(),
                "test@example.com",
                "+71234567890",
                "TestCorp",
                "NEW");

        String status = lead.getStatus();

        assertThat(status).isEqualTo("NEW");
    }

    @Test
    void shouldReturnFormattedStringWhenToStringCalled() {
        UUID id = UUID.randomUUID();
        Lead lead = new Lead(id,
                "test@example.com",
                "+71234567890",
                "TestCorp",
                "NEW");

        String result = lead.toString();

        assertThat(result).contains(id.toString(),
                "test@example.com",
                "+71234567890",
                "TestCorp",
                "NEW");
    }

    @Test
    void shouldBeReflexiveWhenEqualsCalledOnSameObject() {
        Lead lead = new Lead(UUID.randomUUID(), "ivan@mail.ru", "+7123", "TechCorp", "NEW");

        assertThat(lead).isEqualTo(lead);
    }

    @Test
    void shouldBeSymmetricWhenEqualsCalledOnTwoObjects() {
        UUID id = UUID.randomUUID();
        Lead firstLead = new Lead(id, "ivan@mail.ru", "+7123", "TechCorp", "NEW");
        Lead secondLead = new Lead(id, "ivan@mail.ru", "+7123", "TechCorp", "NEW");

        assertThat(firstLead).isEqualTo(secondLead);
        assertThat(secondLead).isEqualTo(firstLead);
    }

    @Test
    void shouldBeTransitiveWhenEqualsChainOfThreeObjects() {
        UUID id = UUID.randomUUID();
        Lead firstLead = new Lead(id, "ivan@mail.ru", "+7123", "TechCorp", "NEW");
        Lead secondLead = new Lead(id, "ivan@mail.ru", "+7123", "TechCorp", "NEW");
        Lead thirdLead = new Lead(id, "ivan@mail.ru", "+7123", "TechCorp", "NEW");

        assertThat(firstLead).isEqualTo(secondLead);
        assertThat(secondLead).isEqualTo(thirdLead);
        assertThat(firstLead).isEqualTo(thirdLead);
    }

    @Test
    void shouldBeConsistentWhenEqualsCalledMultipleTimes() {
        UUID id = UUID.randomUUID();
        Lead firstLead = new Lead(id, "ivan@mail.ru", "+7123", "TechCorp", "NEW");
        Lead secondLead = new Lead(id, "ivan@mail.ru", "+7123", "TechCorp", "NEW");
        Lead thirdLead = new Lead(id, "ivan@mail.ru", "+7123", "TechCorp", "NEW");

        assertThat(firstLead)
                .isEqualTo(secondLead)
                .isEqualTo(thirdLead);
    }

    @Test
    void shouldReturnFalseWhenEqualsComparedWithNull() {
        Lead lead = new Lead(UUID.randomUUID(), "ivan@mail.ru", "+7123", "TechCorp", "NEW");

        assertThat(lead).isNotEqualTo(null);
    }

    @Test
    void shouldHaveSameHashCodeWhenObjectsAreEqual() {
        UUID id = UUID.randomUUID();
        Lead firstLead = new Lead(id, "ivan@mail.ru", "+7123", "TechCorp", "NEW");
        Lead secondLead = new Lead(id, "ivan@mail.ru", "+7123", "TechCorp", "NEW");

        assertThat(firstLead)
                .isEqualTo(secondLead)
                .hasSameHashCodeAs(secondLead);
    }

    @Test
    void shouldWorkInHashMapWhenLeadUsedAsKey() {
        UUID id = UUID.randomUUID();
        Lead keyLead = new Lead(id, "ivan@mail.ru", "+7123", "TechCorp", "NEW");
        Lead lookupLead = new Lead(id, "ivan@mail.ru", "+7123", "TechCorp", "NEW");

        Map<Lead, String> map = new HashMap<>();
        map.put(keyLead, "CONTACTED");

        assertThat(map).containsEntry(lookupLead, "CONTACTED");
    }

    @Test
    void shouldNotBeEqualWhenIdsAreDifferent() {
        Lead firstLead = new Lead(UUID.randomUUID(), "ivan@mail.ru", "+7123", "TechCorp", "NEW");
        Lead differentLead = new Lead(UUID.randomUUID(), "ivan@mail.ru", "+7123", "TechCorp", "NEW");

        assertThat(firstLead).isNotEqualTo(differentLead);
    }
}