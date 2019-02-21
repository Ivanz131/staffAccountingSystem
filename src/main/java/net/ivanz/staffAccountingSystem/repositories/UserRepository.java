package net.ivanz.staffAccountingSystem.repositories;

import net.ivanz.staffAccountingSystem.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {
    User findFirstByUsername(String login);
}
