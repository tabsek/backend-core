package ru.mentee.power.crm.domain;

import java.util.UUID;

public record Lead(UUID id, Contact contact, String company, LeadStatus status) {

    public Lead {
        if (id == null) {
            throw new IllegalArgumentException("Id cannot be null");
        }
        if (contact == null) {
            throw new IllegalArgumentException("Contact cannot be null");
        }
        if (status == null) {
            throw new IllegalArgumentException("Invalid status: " + status);
        }
    }
}
