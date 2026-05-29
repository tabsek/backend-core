package ru.mentee.power.crm.repository;

import ru.mentee.power.crm.domain.Lead;
import ru.mentee.power.crm.domain.Repository;

import java.util.Optional;

public interface LeadRepository extends Repository<Lead> {
    Optional<Lead> findByEmail(String email);
}
