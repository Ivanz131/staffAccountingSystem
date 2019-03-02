package net.ivanz.staffAccountingSystem.services;

import lombok.extern.log4j.Log4j2;
import net.ivanz.staffAccountingSystem.exceptions.ErrorCodes;
import net.ivanz.staffAccountingSystem.exceptions.RestException;
import net.ivanz.staffAccountingSystem.models.Department;
import net.ivanz.staffAccountingSystem.models.Employee;
import net.ivanz.staffAccountingSystem.repositories.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Log4j2
@Service
public class DepartmentManagementService {
    @Autowired
    private DepartmentRepository departmentRepository;

    public List<Department> getAllDepartments(){
        log.debug("getAllDepartments");
        return departmentRepository.findAll();
    }

    public Department getDepartmentById(String id){
        log.debug("getDepartmentById: id = {}", id);
        return departmentRepository.findById(id).orElseThrow(() -> new RestException(ErrorCodes.DEPARTMENT_NOT_EXIST));
    }

    public Department createDepartment(Department department){
        log.debug("createDepartment: department = {}", department);
        return departmentRepository.save(department);
    }

    public Department updateDepartment(Department department, String id){
        log.debug("updateDepartment: department = {}, id = {}", department, id);
        Department fromDB = departmentRepository.findById(id).orElseThrow(() -> new RestException(ErrorCodes.DEPARTMENT_NOT_EXIST));
        fromDB.setName(department.getName());
        fromDB.setChief(department.getChief());
        fromDB.setEmployeeList(department.getEmployeeList());
        return departmentRepository.save(fromDB);
    }

    public void deleteDepartment(String id){
        log.debug("deleteDepartment: id = {}", id);
        departmentRepository.findById(id).orElseThrow(() -> new RestException(ErrorCodes.DEPARTMENT_NOT_EXIST));
        departmentRepository.deleteById(id);
    }

    public Department addNewEmployee(String departmentId, Employee newEmployee){
        log.debug("addNewEmployee: departmentId = {}, newEmployee = {}", departmentId, newEmployee);
        Department department = departmentRepository.findById(departmentId).orElseThrow(() -> new RestException(ErrorCodes.DEPARTMENT_NOT_EXIST));
        department.getEmployeeList().add(newEmployee);
        return departmentRepository.save(department);
    }
}