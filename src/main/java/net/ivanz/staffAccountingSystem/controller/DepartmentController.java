package net.ivanz.staffAccountingSystem.controller;

import lombok.extern.log4j.Log4j2;
import net.ivanz.staffAccountingSystem.models.Department;
import net.ivanz.staffAccountingSystem.models.Employee;
import net.ivanz.staffAccountingSystem.services.DepartmentManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Log4j2
@RestController
@RequestMapping("/api/department")
public class DepartmentController {
    @Autowired
    private DepartmentManagementService departmentManagementService;

    @GetMapping("")
    public List<Department> getAllDepartments() {
        log.info("getAllDepartments");
        return departmentManagementService.getAllDepartments();
    }

    @GetMapping("/{id}")
    public Department getDepartmentById(@PathVariable String id) {
        log.info("getDepartmentById: id = {}", id);
        return departmentManagementService.getDepartmentById(id);
    }

    @PostMapping("")
    public Department createDepartment(@RequestBody Department department) {
        log.info("createDepartment: department = {}", department);
        return departmentManagementService.createDepartment(department);
    }

    @PutMapping("/{id}")
    public Department updateDepartment(@RequestBody Department department, @PathVariable String id) {
        log.info("updateDepartment: department = {}, id = {}", department, id);
        return departmentManagementService.updateDepartment(department, id);
    }

    @DeleteMapping("/{id}")
    public void deleteDepartment(@PathVariable String id) {
        log.info("deleteDepartment: id = {}", id);
        departmentManagementService.deleteDepartment(id);
    }

    @PostMapping("/{id}/addNewEmployee")
    public Department addNewEmployee(@PathVariable String id, @RequestBody Employee newEmployee) {
        log.info("addNewEmployee: id = {}, newEmployee = {}", id, newEmployee);
        return departmentManagementService.addNewEmployee(id, newEmployee);
    }
}
