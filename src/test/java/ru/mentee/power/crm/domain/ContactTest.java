package ru.mentee.power.crm.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;

class ContactTest {

    @Test
    void shouldCreateContact_whenValidData() {
        var contact = new Contact("John", "Doe", "john@example.com");
        assertThat(contact.firstName()).isEqualTo("John");
        assertThat(contact.lastName()).isEqualTo("Doe");
        assertThat(contact.email()).isEqualTo("john@example.com");
    }

    @Test
    void shouldBeEqual_whenSameData() {
        var c1 = new Contact("John", "Doe", "john@example.com");
        var c2 = new Contact("John", "Doe", "john@example.com");
        assertThat(c1).isEqualTo(c2);
        assertThat(c1.hashCode()).isEqualTo(c2.hashCode());
    }

    @Test
    void shouldNotBeEqual_whenDifferentData() {
        var c1 = new Contact("John", "Doe", "john@example.com");
        var c2 = new Contact("Jane", "Doe", "jane@example.com");
        assertThat(c1).isNotEqualTo(c2);
    }
}