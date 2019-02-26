package net.ivanz.staffAccountingSystem.repositories;

import net.ivanz.staffAccountingSystem.models.Department;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface DepartmentRepository extends MongoRepository<Department, String> {

}
