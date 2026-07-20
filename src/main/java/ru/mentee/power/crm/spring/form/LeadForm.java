package ru.mentee.power.crm.spring.form;

import lombok.Data;
import ru.mentee.power.crm.domain.LeadStatus;

@Data
public class LeadForm {
    private String email;
    private String phone;
    private String company;
    private LeadStatus status;
    private String city;
    private String street;
    private String zip;
}