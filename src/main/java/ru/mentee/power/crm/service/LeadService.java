package ru.mentee.power.crm.service;

import ru.mentee.power.crm.domain.Lead;
import ru.mentee.power.crm.domain.LeadStatus;
import ru.mentee.power.crm.repository.LeadRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

public class LeadService {

    private final LeadRepository leadRepository;


    public LeadService(LeadRepository leadRepository) {
        this.leadRepository = leadRepository;
    }

    public void addLead(Lead lead) {
        if (!leadRepository.findByEmail(lead.contact().email()).isEmpty()) {
            throw new IllegalStateException("Lead with this email already exists");
        }
        leadRepository.add(lead);
    }

    public List<Lead> findAll() {
        return leadRepository.findAll();
    }

    public Optional<Lead> findById(UUID id) {
        return leadRepository.findById(id);
    }

    public Optional<Lead> findByEmail(String email) {
        return leadRepository.findByEmail(email);
    }

    public List<Lead> findByStatus(LeadStatus status) {
        return leadRepository.findAll().stream()
                .filter(lead -> lead.status().equals(status))
                .collect(Collectors.toList());
    }

}
