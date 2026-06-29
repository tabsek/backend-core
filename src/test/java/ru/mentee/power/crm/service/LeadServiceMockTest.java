package ru.mentee.power.crm.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.mentee.power.crm.domain.Address;
import ru.mentee.power.crm.domain.Contact;
import ru.mentee.power.crm.domain.Lead;
import ru.mentee.power.crm.domain.LeadStatus;
import ru.mentee.power.crm.repository.LeadRepository;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LeadServiceMockTest {

    @Mock
    private LeadRepository mockRepository;

    private LeadService service;

    private Lead testLead;

    @BeforeEach
    void setUp() {
        service = new LeadService(mockRepository);

        Address address = new Address("Москва", "Ленина 1", "101000");
        Contact contact = new Contact("new@example.com", "+79001234567", address);
        testLead = new Lead(UUID.randomUUID(), contact, "Company", LeadStatus.NEW);
    }

    @Test
    void shouldCallRepositoryAddWhenAddingNewLead() {
        when(mockRepository.findByEmail("new@example.com"))
                .thenReturn(Optional.empty());

        service.addLead(testLead);

        verify(mockRepository, times(1)).add(any(Lead.class));
    }

    @Test
    void shouldNotCallAddWhenEmailExists() {
        when(mockRepository.findByEmail("new@example.com"))
                .thenReturn(Optional.of(testLead));

        assertThatThrownBy(() -> service.addLead(testLead))
                .isInstanceOf(IllegalStateException.class);

        verify(mockRepository, never()).add(any(Lead.class));
    }

    @Test
    void shouldCallFindByEmailBeforeAdd() {
        when(mockRepository.findByEmail("new@example.com"))
                .thenReturn(Optional.empty());

        service.addLead(testLead);

        var inOrder = inOrder(mockRepository);
        inOrder.verify(mockRepository).findByEmail("new@example.com");
        inOrder.verify(mockRepository).add(any(Lead.class));
    }
}