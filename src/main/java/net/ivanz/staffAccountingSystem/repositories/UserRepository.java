package net.ivanz.staffAccountingSystem.repositories;

import net.ivanz.staffAccountingSystem.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {
    Optional<User> findFirstByUsername(String username);
    void deleteUserByUsername(String username);
}
