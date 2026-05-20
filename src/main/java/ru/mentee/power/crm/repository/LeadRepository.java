package ru.mentee.power.crm.repository;

import ru.mentee.power.crm.domain.Lead;
import java.util.*;

public class LeadRepository {

    private final Map<UUID, Lead> storage = new HashMap<>();

    public void save(Lead lead) {
        if (lead == null) {
            throw new IllegalArgumentException("lead can`t be null");
        }
        storage.put(lead.id(),lead);
    }

    public Optional<Lead> findById(UUID id) {
        if (id == null) {
            throw new IllegalArgumentException("id can`t be null");
        }
        return Optional.ofNullable(storage.get(id));
    }

    public List<Lead> findAll() {
        return new ArrayList<Lead>(storage.values());
    }

    public void delete(UUID id) {
        if (id == null) {
            throw new IllegalArgumentException("id can`t be null");
        }
        if (!storage.containsKey(id)) {
            throw new IllegalArgumentException("id not exist in storage");
        }
        storage.remove(id);
    }

    public int size() {
        return storage.size();
    }
}
