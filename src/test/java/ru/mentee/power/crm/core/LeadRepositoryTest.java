package ru.mentee.power.crm.core;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.mentee.power.crm.domain.Address;
import ru.mentee.power.crm.domain.Contact;
import ru.mentee.power.crm.domain.Lead;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;

class LeadRepositoryTest {

    private LeadRepository repository;
    private Lead lead;
    private Contact contact;

    @BeforeEach
    void setUp() {
        Address address = new Address("Moscow", "Lenina 1", "101000");
        contact = new Contact("test@mail.ru", "+79001234567", address);
        lead = new Lead(UUID.randomUUID(), contact, "TechCorp", "NEW");
        repository = new LeadRepository();
    }

    @Test
    void shouldDeduplicateLeadsById() {
        Lead duplicate = new Lead(lead.id(), contact, "TechCorp", "NEW");

        boolean firstAdd = repository.add(lead);
        boolean secondAdd = repository.add(duplicate);

        assertThat(firstAdd).isTrue();
        assertThat(secondAdd).isFalse();
        assertThat(repository.size()).isEqualTo(1);
    }

    @Test
    void shouldAllowDifferentLeads() {
        Lead anotherLead = new Lead(UUID.randomUUID(), contact, "OtherCorp", "NEW");

        boolean firstAdd = repository.add(lead);
        boolean secondAdd = repository.add(anotherLead);

        assertThat(firstAdd).isTrue();
        assertThat(secondAdd).isTrue();
        assertThat(repository.size()).isEqualTo(2);
    }

    @Test
    void shouldFindExistingLead() {
        repository.add(lead);

        boolean result = repository.contains(lead);

        assertThat(result).isTrue();
    }

    @Test
    void shouldReturnUnmodifiableSet() {
        repository.add(lead);

        Set<Lead> result = repository.findAll();

        assertThatThrownBy(() -> result.add(lead))
                .isInstanceOf(UnsupportedOperationException.class);
    }

    @Test
    void shouldPerformFasterThanArrayList() {
        int size = 10000;
        Set<Lead> hashSet = new HashSet<>();
        List<Lead> arrayList = new ArrayList<>();
        Lead target = new Lead(UUID.randomUUID(), contact, "Target", "NEW");

        for (int i = 0; i < size; i++) {
            Lead l = new Lead(UUID.randomUUID(), contact, "Corp" + i, "NEW");
            hashSet.add(l);
            arrayList.add(l);
        }
        hashSet.add(target);
        arrayList.add(target);

        long start = System.nanoTime();
        for (int i = 0; i < 1000; i++) {
            hashSet.contains(target);
        }
        long hashSetTime = System.nanoTime() - start;

        start = System.nanoTime();
        for (int i = 0; i < 1000; i++) {
            arrayList.contains(target);
        }
        long arrayListTime = System.nanoTime() - start;

        System.out.printf("HashSet: %d ns, ArrayList: %d ns, ratio: %.1fx%n",
                hashSetTime, arrayListTime, (double) arrayListTime / hashSetTime);
        assertThat(arrayListTime).isGreaterThan(hashSetTime * 10);
    }
}
