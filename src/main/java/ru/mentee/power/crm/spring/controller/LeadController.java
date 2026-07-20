package ru.mentee.power.crm.spring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.mentee.power.crm.domain.Address;
import ru.mentee.power.crm.domain.Contact;
import ru.mentee.power.crm.domain.Lead;
import ru.mentee.power.crm.domain.LeadStatus;
import ru.mentee.power.crm.spring.form.LeadForm;
import ru.mentee.power.crm.spring.service.LeadService;

import java.util.List;
import java.util.UUID;

@Controller
public class LeadController {

    private final LeadService leadService;

    public LeadController(LeadService leadService) {
        this.leadService = leadService;
    }

    @GetMapping("/leads")
    public String showLeads(
            @RequestParam(required = false) LeadStatus status,
            Model model) {
        List<Lead> leads;
        if (status == null) {
            leads = leadService.findAll();
        } else {
            leads = leadService.findByStatus(status);
        }

        model.addAttribute("leads", leads);
        model.addAttribute("currentFilter", status);

        return "leads/list";
    }

    @GetMapping("/leads/new")
    public String showCreateForm() {
        return "leads/create";
    }

    @PostMapping("/leads")
    public String createLead(@ModelAttribute LeadForm form) {
        Lead lead = new Lead(
                UUID.randomUUID(),
                new Contact(
                        form.getEmail(),
                        form.getPhone(),
                        new Address(form.getCity(), form.getStreet(), form.getZip())
                ),
                form.getCompany(),
                form.getStatus()
        );
        leadService.addLead(lead);
        return "redirect:/leads";
    }
}