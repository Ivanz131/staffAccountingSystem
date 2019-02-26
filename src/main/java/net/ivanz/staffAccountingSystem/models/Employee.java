package net.ivanz.staffAccountingSystem.models;

import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class Employee {
    @Id
    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private String dateOfBirth;
    private String placeOfBirth;
    private String placeOfRegistration;
    private String phone;
    private String workPhone;
    private String imageLink;
    private String[] languages;
    private String[] languagesLevels;
    private String t_education;
    private String educationalInst;
    private String specialty;
    private String educYear;
    private String skype;
    private String post;
    private String experience;
}
