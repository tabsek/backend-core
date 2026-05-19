package ru.mentee.power.crm.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ContactTest {

    private Address address;

    @BeforeEach
    void setUp() {
        address = new Address("Moscow", "Tverskaya 1", "123456");
    }

    @Test
    void shouldCreateContactWhenValidData() {
        Contact contact = new Contact("test@example.com", "+71234567890", address);

        assertThat(contact.email()).isEqualTo("test@example.com");
        assertThat(contact.phone()).isEqualTo("+71234567890");
        assertThat(contact.address()).isEqualTo(address);
    }

    @Test
    void shouldDelegateToAddressWhenAccessingCity() {
        Contact contact = new Contact("test@example.com", "+71234567890", address);

        assertThat(contact.address().city()).isEqualTo("Moscow");
        assertThat(contact.address().street()).isEqualTo("Tverskaya 1");
        assertThat(contact.address().zip()).isEqualTo("123456");
    }

    @Test
    void shouldBeEqualWhenSameData() {
        Contact c1 = new Contact("test@example.com", "+71234567890", address);
        Contact c2 = new Contact("test@example.com", "+71234567890", address);

        assertThat(c1)
                .isEqualTo(c2)
                .hasSameHashCodeAs(c2);
    }

    @Test
    void shouldNotBeEqualWhenDifferentData() {
        Contact c1 = new Contact("test@example.com", "+71234567890", address);
        Contact c2 = new Contact("other@example.com", "+79999999999", address);

        assertThat(c1).isNotEqualTo(c2);
    }

    @Test
    void shouldThrowExceptionWhenEmailIsNull() {
        assertThatThrownBy(() -> new Contact(null, "+71234567890", address))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void shouldThrowExceptionWhenPhoneIsNull() {
        assertThatThrownBy(() -> new Contact("test@example.com", null, address))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void shouldThrowExceptionWhenAddressIsNull() {
        assertThatThrownBy(() -> new Contact("test@example.com", "+71234567890", null))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
