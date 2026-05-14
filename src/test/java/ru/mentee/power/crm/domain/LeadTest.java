package ru.mentee.power.crm.domain;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class LeadTest {

    @Test
    void shouldReturnId_whenGetIdCalled(){
        Lead lead = new Lead("L1",
                "test@example.com",
                "+71234567890",
                "TestCorp",
                "NEW");

        String id = lead.getId();

        assertThat(id).isEqualTo("L1");
    }

    @Test
    void shouldReturnEmail_whenGetEmailCalled() {

        Lead lead = new Lead("L1",
                "test@example.com",
                "+71234567890",
                "TestCorp",
                "NEW");


        String email = lead.getEmail();


        assertThat(email).isEqualTo("test@example.com");
    }

    @Test
    void shouldReturnPhone_whenGetPhoneCalled() {

        Lead lead = new Lead("L1",
                "test@example.com",
                "+71234567890",
                "TestCorp",
                "NEW");

        String phone = lead.getPhone();


        assertThat(phone).isEqualTo("+71234567890");
    }

    @Test
    void shouldReturnCompany_whenGetCompanyCalled() {

        Lead lead = new Lead("L1",
                "test@example.com",
                "+71234567890",
                "TestCorp",
                "NEW");

        String company = lead.getCompany();

        assertThat(company).isEqualTo("TestCorp");
    }

    @Test
    void shouldReturnStatus_whenGetStatusCalled() {

        Lead lead = new Lead("L1",
                "test@example.com",
                "+71234567890",
                "TestCorp",
                "NEW");

        String status = lead.getStatus();

        assertThat(status).isEqualTo("NEW");
    }

    @Test
    void shouldReturnFormattedString_whenToStringCalled() {

        Lead lead = new Lead("L1",
                "test@example.com",
                "+71234567890",
                "TestCorp",
                "NEW");

        String result = lead.toString();

        assertThat(result).contains("L1",
                "test@example.com",
                "+71234567890",
                "TestCorp",
                "NEW");
    }

    @Test
    void shouldBeReflexive_whenEqualsCalledOnSameObject() {
        Lead lead = new Lead("1", "ivan@mail.ru", "+7123", "TechCorp", "NEW");

        assertThat(lead).isEqualTo(lead);
    }

    @Test
    void shouldBeSymmetric_whenEqualsCalledOnTwoObjects() {

        Lead firstLead = new Lead("1", "ivan@mail.ru", "+7123", "TechCorp", "NEW");
        Lead secondLead = new Lead("1", "ivan@mail.ru", "+7123", "TechCorp", "NEW");

        assertThat(firstLead).isEqualTo(secondLead);
        assertThat(secondLead).isEqualTo(firstLead);
    }

    @Test
    void shouldBeTransitive_whenEqualsChainOfThreeObjects() {
        Lead firstLead = new Lead("1", "ivan@mail.ru", "+7123", "TechCorp", "NEW");
        Lead secondLead = new Lead("1", "ivan@mail.ru", "+7123", "TechCorp", "NEW");
        Lead thirdLead = new Lead("1", "ivan@mail.ru", "+7123", "TechCorp", "NEW");

        assertThat(firstLead).isEqualTo(secondLead);
        assertThat(secondLead).isEqualTo(thirdLead);
        assertThat(firstLead).isEqualTo(thirdLead);
    }

    @Test
    void shouldBeConsistent_whenEqualsCalledMultipleTimes() {
        Lead firstLead = new Lead("1", "ivan@mail.ru", "+7123", "TechCorp", "NEW");
        Lead secondLead = new Lead("1", "ivan@mail.ru", "+7123", "TechCorp", "NEW");

        assertThat(firstLead).isEqualTo(secondLead);
        assertThat(firstLead).isEqualTo(secondLead);
        assertThat(firstLead).isEqualTo(secondLead);
    }

    @Test
    void shouldReturnFalse_whenEqualsComparedWithNull() {
        Lead lead = new Lead("1", "ivan@mail.ru", "+7123", "TechCorp", "NEW");

        assertThat(lead).isNotEqualTo(null);
    }

    @Test
    void shouldHaveSameHashCode_whenObjectsAreEqual() {
        Lead firstLead = new Lead("1", "ivan@mail.ru", "+7123", "TechCorp", "NEW");
        Lead secondLead = new Lead("1", "ivan@mail.ru", "+7123", "TechCorp", "NEW");

        assertThat(firstLead).isEqualTo(secondLead);
        assertThat(firstLead.hashCode()).isEqualTo(secondLead.hashCode());
    }

    @Test
    void shouldWorkInHashMap_whenLeadUsedAsKey() {
        Lead keyLead = new Lead("1", "ivan@mail.ru", "+7123", "TechCorp", "NEW");
        Lead lookupLead = new Lead("1", "ivan@mail.ru", "+7123", "TechCorp", "NEW");

        Map<Lead, String> map = new HashMap<>();
        map.put(keyLead, "CONTACTED");

        String status = map.get(lookupLead);

        assertThat(status).isEqualTo("CONTACTED");
    }

    @Test
    void shouldNotBeEqual_whenIdsAreDifferent() {
        Lead firstLead = new Lead("1", "ivan@mail.ru", "+7123", "TechCorp", "NEW");
        Lead differentLead = new Lead("2", "ivan@mail.ru", "+7123", "TechCorp", "NEW");

        assertThat(firstLead).isNotEqualTo(differentLead);
    }
}