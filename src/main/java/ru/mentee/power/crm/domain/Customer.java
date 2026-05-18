package ru.mentee.power.crm.domain;

import java.util.UUID;
import java.util.Set;

public record Customer(UUID id, Contact contact, Address billingAddress, String loyaltyTier) {

    private static final Set<String> TIERS = Set.of("BRONZE", "SILVER", "GOLD");

    public Customer {
        if (id == null) {
            throw new IllegalArgumentException("Id cannot be null");
        }
        if (contact == null) {
            throw new IllegalArgumentException("Contact cannot be null");
        }
        if (billingAddress == null) {
            throw new IllegalArgumentException("BillingAddress cannot be null");
        }
        if (loyaltyTier == null || !TIERS.contains(loyaltyTier)) {
            throw new IllegalArgumentException("Invalid loyaltyTier: " + loyaltyTier);
        }
    }
}
