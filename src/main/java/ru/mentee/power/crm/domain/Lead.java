package ru.mentee.power.crm.domain;

import java.util.Set;
import java.util.UUID;

public record Lead(UUID id, Contact contact, String company, String status) {

    private static final Set<String> ALLOWED_STATUSES = Set.of("NEW", "IN_PROGRESS", "CLOSED");

    public Lead {
        if (id == null) {
            throw new IllegalArgumentException("Id cannot be null");
        }
        if (contact == null) {
            throw new IllegalArgumentException("Contact cannot be null");
        }
        if (status == null || !ALLOWED_STATUSES.contains(status)) {
            throw new IllegalArgumentException("Invalid status: " + status);
        }
    }
}
