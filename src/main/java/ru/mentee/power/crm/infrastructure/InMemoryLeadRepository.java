package ru.mentee.power.crm.infrastructure;

import org.springframework.stereotype.Repository;
import ru.mentee.power.crm.domain.Lead;
import ru.mentee.power.crm.repository.LeadRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class InMemoryLeadRepository implements LeadRepository {

    private final List<Lead> storage = new ArrayList<>();

    @Override
    public void add(Lead lead) {
        storage.add(lead);
    }

    @Override
    public void remove(UUID id) {
        storage.removeIf(lead -> lead.id().equals(id));
    }

    @Override
    public Optional<Lead> findById(UUID id) {
        return storage.stream()
                .filter(lead -> lead.id().equals(id))
                .findFirst();
    }

    @Override
    public List<Lead> findAll() {
        return new ArrayList<> (storage);
    }

    @Override
    public Optional<Lead> findByEmail(String email) {
        return storage.stream()
                .filter(lead -> lead.contact().email().equals(email))
                .findFirst();
    }
}
