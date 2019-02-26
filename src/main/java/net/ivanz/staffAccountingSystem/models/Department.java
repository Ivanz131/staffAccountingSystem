package net.ivanz.staffAccountingSystem.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "workplaces")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Department {
    @Id
    private String Id;
    private List<Employee> employeeList;
    private Employee chief;
}
